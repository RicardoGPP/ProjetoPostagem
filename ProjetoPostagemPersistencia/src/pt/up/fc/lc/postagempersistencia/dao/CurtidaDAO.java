package pt.up.fc.lc.postagempersistencia.dao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Curtida;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class CurtidaDAO extends DAO<Curtida>
{
	private static final String CAMINHO = "CURTIDA";
	
	public CurtidaDAO()
	{
		super(CAMINHO);
	}

	protected Curtida deStringParaObjeto(String linha)
	{
		String dados[] = linha.split(";");
		if (dados.length == 4)
		{
			try
			{			
				Curtida curtida = new Curtida();
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA_HORA);				
				Usuario usuarioCurtiu = usuarioDAO.obterRegistro(dados[0]);			
				Usuario usuarioComentou = usuarioDAO.obterRegistro(dados[1]);			
				Topico topico = (new TopicoDAO()).obterRegistro(dados[2]);			
				Date data = simpleDateFormat.parse(dados[3]);							
				curtida.setUsuario(usuarioCurtiu);
				curtida.setComentario((new ComentarioDAO()).obterRegistro(usuarioComentou, topico, data));
				return curtida;
			} catch (ParseException e)
			{
				return null;
			}			
		}
		return null;
	}
	
	protected String deObjetoParaString(Curtida objeto)
	{
		if (objeto != null)
		{
			String linha = "";			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA_HORA);			
			linha += objeto.getUsuario().getNomeUsuario() + ";";
			linha += objeto.getComentario().getUsuario().getNomeUsuario() + ";";
			linha += objeto.getComentario().getTopico().getTitulo() + ";";
			linha += simpleDateFormat.format(objeto.getComentario().getData());			
			return linha;
		}
		return "";
	}
	
	public boolean existe(Curtida objeto)
	{
		return (this.obterRegistro(objeto.getUsuario(), objeto.getComentario()) != null);			   
	}
	
	protected boolean eValido(Curtida objeto)
	{
		ComentarioDAO comentarioDAO = new ComentarioDAO();
		boolean usuarioEstaSubscrito = false;
		for (Subscricao subscricoes : (new SubscricaoDAO()).obterLista(objeto.getComentario().getTopico()))
		{
			if (objeto.getUsuario().comparar(subscricoes.getUsuario()))
			{
				usuarioEstaSubscrito = true;
				break;
			}
		}		
		return ((new UsuarioDAO()).existe(objeto.getUsuario())) &&
			   (comentarioDAO.existe(objeto.getComentario()) &&
			   (comentarioDAO.eValido(objeto.getComentario())) &&
			   (usuarioEstaSubscrito));
	}
	
	public boolean curtiu(Usuario usuario, Comentario comentario)
	{
		ComentarioDAO comentarioDAO = new ComentarioDAO();
		if ((usuario != null) && (comentario != null) &&
		   ((new UsuarioDAO()).existe(usuario)) &&
		   (comentarioDAO.existe(comentario)) &&
		   (comentarioDAO.eValido(comentario)))
		{
			List<Curtida> curtidas = obterLista();			
			for (Curtida curtida : curtidas)
				if ((curtida.getUsuario().comparar(usuario)) && (curtida.getComentario().comparar(comentario)))
					return true;
			return false;
		}
		return false;
	}
	
	public int obterQuantidadeCurtidas(Comentario comentario)
	{
		ComentarioDAO comentarioDAO = new ComentarioDAO();
		if ((comentario != null) &&
		   (comentarioDAO.existe(comentario)) &&
		   (comentarioDAO.eValido(comentario)))
		{
			int contador = 0;
			List<Curtida> curtidas = obterLista();						
			for (Curtida curtida : curtidas)
				if (curtida.getComentario().comparar(comentario))
					contador++;			
			return contador;
		}
		return 0;
	}
	
	public Curtida obterRegistro(Usuario usuario, Comentario comentario)
	{			
		if ((usuario != null) && (comentario != null))
			for (Curtida curtida : obterLista())
				if ((curtida.getUsuario().comparar(usuario)) && (curtida.getComentario().comparar(comentario)))
					return curtida;
		return null;
	}
	
	public boolean inserir(Curtida curtida)
	{
		if ((curtida != null) && (!this.existe(curtida)) && (this.eValido(curtida)))
		{
			try
			{
				this.escrever(this.deObjetoParaString(curtida), this.arquivo, false);
				return true;
			} catch (IOException e)
			{
				return false;
			}
		}
		return false;
	}
	
	public boolean deletar(Curtida curtida)
	{
		if ((curtida != null) && (this.existe(curtida)) && (this.eValido(curtida)))
		{						
			List<String> linhas = new ArrayList<>();
			List<Curtida> curtidas = obterLista();						
			for (Iterator<Curtida> iterator = curtidas.iterator(); iterator.hasNext();)
			{
				Curtida outroCurtida = iterator.next();
				if (curtida.comparar(outroCurtida))
				{
					iterator.remove();
					break;
				}
			}
			for (Curtida curtidaRestante : curtidas)
				linhas.add(this.deObjetoParaString(curtidaRestante));			
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
			List<Curtida> curtidas = obterLista();						
			for (Iterator<Curtida> iterator = curtidas.iterator(); iterator.hasNext();)
			{
				Curtida curtida = iterator.next();
				if (curtida.getUsuario().comparar(usuario))
				{
					iterator.remove();
					break;
				}
			}
			for (Curtida curtidaRestante : curtidas)
				linhas.add(this.deObjetoParaString(curtidaRestante));			
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
	
	public boolean deletar(Comentario comentario)
	{
		ComentarioDAO comentarioDAO = new ComentarioDAO();
		if ((comentario != null) && (comentarioDAO.existe(comentario) && (comentarioDAO.eValido(comentario))))
		{						
			List<String> linhas = new ArrayList<>();
			List<Curtida> curtidas = obterLista();						
			for (Iterator<Curtida> iterator = curtidas.iterator(); iterator.hasNext();)
			{
				Curtida curtida = iterator.next();
				if (curtida.getComentario().comparar(comentario))
				{
					iterator.remove();
					break;
				}
			}
			for (Curtida curtidaRestante : curtidas)
				linhas.add(this.deObjetoParaString(curtidaRestante));			
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
		SubscricaoDAO subscricaoDAO = new SubscricaoDAO();
		if ((subscricao != null) && (subscricaoDAO.existe(subscricao) && (subscricaoDAO.eValido(subscricao))))
		{						
			List<String> linhas = new ArrayList<>();
			List<Curtida> curtidas = obterLista();						
			for (Iterator<Curtida> iterator = curtidas.iterator(); iterator.hasNext();)
			{
				Curtida curtida = iterator.next();
				if (curtida.getUsuario().comparar(subscricao.getUsuario()) &&
				   (curtida.getComentario().getTopico().comparar(subscricao.getTopico())))
				{
					iterator.remove();
					break;
				}
			}
			for (Curtida curtidaRestante : curtidas)
				linhas.add(this.deObjetoParaString(curtidaRestante));			
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