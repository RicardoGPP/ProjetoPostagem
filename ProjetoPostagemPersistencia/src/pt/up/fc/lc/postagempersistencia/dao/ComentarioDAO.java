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
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				TopicoDAO topicoDAO = new TopicoDAO();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA_HORA);				
				comentario.setUsuario(usuarioDAO.obterRegistro(dados[0]));
				comentario.setTopico(topicoDAO.obterRegistro(dados[1]));
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
		if ((objeto != null) && (objeto.getUsuario() != null) && (objeto.getTopico() != null))
		{
			String linha = "";			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA_HORA);			
			linha += objeto.getUsuario().getNomeUsuario() + ";";
			linha += objeto.getTopico().getTitulo() + ";";
			linha += simpleDateFormat.format(objeto.getData()) + ";";
			linha += objeto.getMensagem();			
			return linha;			
		}
		return "";
	}
	
	public Comentario obterRegistro(Usuario usuario, Topico topico, Date data)
	{			
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
		for (Comentario comentario : obterLista())
			if (comentario.getTopico().comparar(topico))
				comentarios.add(comentario);
		return comentarios;
	}
	
	public List<Comentario> obterLista(Usuario usuario)
	{
		List<Comentario> comentarios = new ArrayList<>();
		for (Comentario comentario : obterLista())
			if (comentario.getUsuario().comparar(usuario))
				comentarios.add(comentario);
		return comentarios;
	}
	
	public boolean inserir(Comentario comentario)
	{
		if ((comentario != null) && (obterRegistro(comentario.getUsuario(), comentario.getTopico(), comentario.getData()) == null))
		{
			List<Comentario> comentarios = this.obterLista(comentario.getTopico());
			if (comentarios.size() >= comentario.getTopico().getLimiteMensagens())
			{
				Collections.sort(comentarios, (c1, c2) -> c1.getData().compareTo(c2.getData()));
				deletar(comentarios.get(0));
			}			
			try
			{
				escrever(deObjetoParaString(comentario), this.arquivo, false);
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
		if (comentario != null)
		{			
			CurtidaDAO curtidaDAO = new CurtidaDAO();
			curtidaDAO.deletar(comentario);			
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
				linhas.add(deObjetoParaString(comentarioRestante));			
			try
			{
				escrever(linhas, this.arquivo, true);
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
		if (usuario != null)
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
				linhas.add(deObjetoParaString(comentario));			
			try
			{
				escrever(linhas, this.arquivo, true);
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
		if (topico != null)
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
				linhas.add(deObjetoParaString(comentario));			
			try
			{
				escrever(linhas, this.arquivo, true);
				return true;
			} catch (IOException e)
			{
				return false;
			}			
		}
		return false;
	}
	
	public boolean editar(Comentario comentario)
	{
		if (comentario != null)
		{
			List<String> linhas = new ArrayList<>();
			List<Comentario> comentarios = obterLista();		
			for (int i = 0; i < comentarios.size(); i++)
				if (comentario.comparar(comentarios.get(i)))
					comentarios.set(i, comentario);			
			for (Comentario novoComentario : comentarios)
				linhas.add(deObjetoParaString(novoComentario));			
			try
			{
				escrever(linhas, this.arquivo, true);
				return true;
			} catch (IOException e)
			{
				return false;
			}
		}
		return false;
	}
}