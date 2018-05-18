package pt.up.fc.lc.postagempersistencia.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.entidades.Favorito;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class FavoritoDAO extends DAO<Favorito>
{
	private static final String CAMINHO = "FAVORITO";
	
	public FavoritoDAO()
	{
		super(CAMINHO);
	}

	protected Favorito deStringParaObjeto(String linha)
	{
		String dados[] = linha.split(";");
		if (dados.length == 2)
		{
			Favorito favorito = new Favorito();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			TopicoDAO topicoDAO = new TopicoDAO();
			favorito.setUsuario(usuarioDAO.obterRegistro(dados[0]));
			favorito.setTopico(topicoDAO.obterRegistro(dados[1]));
			return favorito;
		}
		return null;
	}
	
	protected String deObjetoParaString(Favorito objeto)
	{
		if (objeto != null)
		{
			String linha = "";			
			linha += objeto.getUsuario().getNomeUsuario() + ";";
			linha += objeto.getTopico().getTitulo();
			return linha;
		}
		return "";
	}
	
	public boolean favoritou(Usuario usuario, Topico topico)
	{
		if ((usuario != null) && (topico != null))
		{
			List<Favorito> favoritos = obterLista();			
			for (Favorito favorito : favoritos)
				if ((favorito.getUsuario().comparar(usuario)) && (favorito.getTopico().comparar(topico)))
					return true;
			return false;
		}
		return false;
	}
	
	public Favorito obterRegistro(Usuario usuario, Topico topico)
	{			
		for (Favorito favorito : obterLista())
			if ((favorito.getUsuario().comparar(usuario)) && (favorito.getTopico().comparar(topico)))
				return favorito;
		return null;
	}
	
	public boolean inserir(Favorito favorito)
	{
		if ((favorito != null) && (obterRegistro(favorito.getUsuario(), favorito.getTopico()) == null))
		{
			try
			{
				escrever(deObjetoParaString(favorito), this.arquivo, false);
				return true;
			} catch (IOException e)
			{
				return false;
			}
		}
		return false;
	}
	
	public boolean deletar(Favorito favorito)
	{
		if (favorito != null)
		{						
			List<String> linhas = new ArrayList<>();
			List<Favorito> favoritos = obterLista();						
			for (Iterator<Favorito> iterator = favoritos.iterator(); iterator.hasNext();)
			{
				Favorito outroFavorito = iterator.next();
				if (favorito.comparar(outroFavorito))
				{
					iterator.remove();
					break;
				}
			}
			for (Favorito favoritoRestante : favoritos)
				linhas.add(deObjetoParaString(favoritoRestante));			
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
			List<Favorito> favoritos = obterLista();						
			for (Iterator<Favorito> iterator = favoritos.iterator(); iterator.hasNext();)
			{
				Favorito favorito = iterator.next();
				if (favorito.getUsuario().comparar(usuario))
				{
					iterator.remove();
					break;
				}
			}
			for (Favorito favoritoRestante : favoritos)
				linhas.add(deObjetoParaString(favoritoRestante));			
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
			List<Favorito> favoritos = obterLista();						
			for (Iterator<Favorito> iterator = favoritos.iterator(); iterator.hasNext();)
			{
				Favorito favorito = iterator.next();
				if (favorito.getTopico().comparar(topico))
				{
					iterator.remove();
					break;
				}
			}
			for (Favorito favoritoRestante : favoritos)
				linhas.add(deObjetoParaString(favoritoRestante));			
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