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
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class CurtidaDAO extends DAO<Curtida>
{
	private static final String CAMINHO = "curtidas.txt";
	
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
				TopicoDAO topicoDAO = new TopicoDAO();
				ComentarioDAO comentarioDAO = new ComentarioDAO();			
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA_HORA);				
				Usuario usuarioCurtiu = usuarioDAO.obterRegistro(dados[0]);			
				Usuario usuarioComentou = usuarioDAO.obterRegistro(dados[1]);			
				Topico topico = topicoDAO.obterRegistro(dados[2]);			
				Date data = simpleDateFormat.parse(dados[3]);							
				curtida.setUsuario(usuarioCurtiu);
				curtida.setComentario(comentarioDAO.obterRegistro(usuarioComentou, topico, data));
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
			linha += objeto.getUsuario().getUtilizador() + ";";
			linha += objeto.getComentario().getUsuario().getUtilizador() + ";";
			linha += objeto.getComentario().getTopico().getIdentificador() + ";";
			linha += simpleDateFormat.format(objeto.getComentario().getData());			
			return linha;
		}
		return "";
	}
	
	public boolean curtiu(Usuario usuario, Comentario comentario)
	{
		if ((usuario != null) && (comentario != null))
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
		if (comentario != null)
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
		for (Curtida curtida : obterLista())
			if ((curtida.getUsuario().comparar(usuario)) && (curtida.getComentario().comparar(comentario)))
				return curtida;
		return null;
	}
	
	public boolean inserir(Curtida curtida)
	{
		if ((curtida != null) && (obterRegistro(curtida.getUsuario(), curtida.getComentario()) == null))
		{
			try
			{
				escrever(deObjetoParaString(curtida), this.arquivo, false);
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
		if (curtida != null)
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
				linhas.add(deObjetoParaString(curtidaRestante));			
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
				linhas.add(deObjetoParaString(curtidaRestante));			
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
	
	public boolean deletar(Comentario comentario)
	{
		if (comentario != null)
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
				linhas.add(deObjetoParaString(curtidaRestante));			
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