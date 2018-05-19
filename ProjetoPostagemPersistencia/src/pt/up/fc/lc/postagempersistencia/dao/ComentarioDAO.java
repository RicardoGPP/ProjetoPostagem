package pt.up.fc.lc.postagempersistencia.dao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class ComentarioDAO extends DAO<Comentario>
{
	private static final String CAMINHO = "COMENTARIO";	
	
	public ComentarioDAO()
	{
		super(CAMINHO);
	}
	
	protected Comentario deStringParaObjeto(String linha)
	{
		String dados[] = linha.split(";");
		if (dados.length == 4)
		{			
			try
			{
				Comentario comentario = new Comentario();				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA_HORA);				
				comentario.setUsuario((new UsuarioDAO()).obterRegistro(dados[0]));
				comentario.setTopico((new TopicoDAO()).obterRegistro(dados[1]));
				comentario.setData(simpleDateFormat.parse(dados[2]));
				comentario.setMensagem(dados[3]);
				return comentario;
			} catch (ParseException e)
			{
				return null;
			}
		}
		return null;
	}

	protected String deObjetoParaString(Comentario objeto)
	{
		if ((objeto != null) && (objeto.getUsuario() != null) &&
		   (objeto.getTopico() != null) && (objeto.getData() != null))
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA_HORA);
			String linha = "";								
			linha += objeto.getUsuario().getNomeUsuario() + ";";
			linha += objeto.getTopico().getTitulo() + ";";
			linha += simpleDateFormat.format(objeto.getData()) + ";";
			linha += objeto.getMensagem();			
			return linha;			
		}
		return "";
	}
	
	public boolean existe(Comentario objeto)
	{
		return (this.obterRegistro(objeto.getUsuario(), objeto.getTopico(), objeto.getData()) != null);			   
	}
	
	protected boolean eValido(Comentario objeto)
	{
		boolean usuarioEstaSubscrito = false;
		for (Subscricao subscricao : (new SubscricaoDAO()).obterLista(objeto.getTopico()))
		{
			if (objeto.getUsuario().comparar(subscricao.getUsuario()))
			{
				usuarioEstaSubscrito = true;
				break;
			}
		}		
		return ((new UsuarioDAO()).existe(objeto.getUsuario())) &&
			   ((new TopicoDAO()).existe(objeto.getTopico()) &&
			   (usuarioEstaSubscrito));
	}
	
	public Comentario obterRegistro(Usuario usuario, Topico topico, Date data)
	{			
		if ((usuario != null) && (topico != null) && (data != null))
			for (Comentario comentario : obterLista())
				if ((comentario.getUsuario().comparar(usuario)) &&
					(comentario.getTopico().comparar(topico)) &&
					(comentario.getData().compareTo(data) == 0))
					return comentario;
		return null;
	}
	
	public List<Comentario> obterLista(Topico topico)
	{		
		List<Comentario> comentarios = new ArrayList<>();
		if ((topico != null) && ((new TopicoDAO()).existe(topico)))
		{
			for (Comentario comentario : obterLista())
				if (comentario.getTopico().comparar(topico))
					comentarios.add(comentario);
		}
		return comentarios;
	}
	
	public List<Comentario> obterLista(Usuario usuario)
	{
		List<Comentario> comentarios = new ArrayList<>();
		if ((usuario != null) && ((new UsuarioDAO()).existe(usuario)))
		{
			for (Comentario comentario : obterLista())
				if (comentario.getUsuario().comparar(usuario))
					comentarios.add(comentario);
		}
		return comentarios;
	}
	
	public boolean inserir(Comentario comentario)
	{
		if ((comentario != null) && (!this.existe(comentario)) && (this.eValido(comentario)))
		{
			List<Comentario> comentarios = this.obterLista(comentario.getTopico());
			if (comentarios.size() >= comentario.getTopico().getLimiteMensagens())
			{
				Collections.sort(comentarios, (c1, c2) -> c1.getData().compareTo(c2.getData()));
				this.deletar(comentarios.get(0));
			}			
			try
			{
				this.escrever(this.deObjetoParaString(comentario), this.arquivo, false);
				return true;
			} catch (IOException e)
			{
				return false;
			}
		}
		return false;
	}
	
	public boolean deletar(Comentario comentario)
	{
		if ((comentario != null) && (this.existe(comentario)) && (this.eValido(comentario)))
		{			
			(new CurtidaDAO()).deletar(comentario);			
			List<String> linhas = new ArrayList<>();
			List<Comentario> comentarios = obterLista();			
			for (Iterator<Comentario> iterator = comentarios.iterator(); iterator.hasNext();)
			{
				Comentario outroComentario = iterator.next();
				if (comentario.comparar(outroComentario))
				{
					iterator.remove();
					break;
				}
			}			
			for (Comentario comentarioRestante : comentarios)
				linhas.add(this.deObjetoParaString(comentarioRestante));			
			try
			{
				this.escrever(linhas, this.arquivo, true);
				return true;
			} catch (IOException e)
			{
				return false;
			}			
		}
		return false;
	}
	
	public boolean deletar(Usuario usuario)
	{
		if ((usuario != null) && ((new UsuarioDAO()).existe(usuario)))
		{
			List<String> linhas = new ArrayList<>();
			List<Comentario> comentarios = obterLista();			
			for (Iterator<Comentario> iterator = comentarios.iterator(); iterator.hasNext();)
			{
				Comentario comentario = iterator.next();				
				if (comentario.getUsuario().comparar(usuario))
					iterator.remove();				
			}			
			for (Comentario comentario : comentarios)
				linhas.add(this.deObjetoParaString(comentario));			
			try
			{
				this.escrever(linhas, this.arquivo, true);
				return true;
			} catch (IOException e)
			{
				return false;
			}			
		}
		return false;
	}
	
	public boolean deletar(Topico topico)
	{
		if ((topico != null) && ((new TopicoDAO()).existe(topico)))
		{
			List<String> linhas = new ArrayList<>();
			List<Comentario> comentarios = obterLista();			
			for (Iterator<Comentario> iterator = comentarios.iterator(); iterator.hasNext();)
			{
				Comentario comentario = iterator.next();				
				if (comentario.getTopico().comparar(topico))
					iterator.remove();				
			}			
			for (Comentario comentario : comentarios)
				linhas.add(this.deObjetoParaString(comentario));			
			try
			{
				this.escrever(linhas, this.arquivo, true);
				return true;
			} catch (IOException e)
			{
				return false;
			}			
		}
		return false;
	}
	
	public boolean deletar(Subscricao subscricao)
	{
		if ((subscricao != null) && ((new SubscricaoDAO()).existe(subscricao)))
		{
			List<String> linhas = new ArrayList<>();
			List<Comentario> comentarios = obterLista();			
			for (Iterator<Comentario> iterator = comentarios.iterator(); iterator.hasNext();)
			{
				Comentario comentario = iterator.next();				
				if (comentario.getUsuario().comparar(subscricao.getUsuario()) &&
				   (comentario.getTopico().comparar(subscricao.getTopico())))
					iterator.remove();				
			}			
			for (Comentario comentario : comentarios)
				linhas.add(this.deObjetoParaString(comentario));			
			try
			{
				this.escrever(linhas, this.arquivo, true);
				return true;
			} catch (IOException e)
			{
				return false;
			}			
		}
		return false;
	}
}