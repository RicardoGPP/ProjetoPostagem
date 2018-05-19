package pt.up.fc.lc.postagempersistencia.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class SubscricaoDAO extends DAO<Subscricao>
{
	private static final String CAMINHO = "SUBSCRICAO";
	
	public SubscricaoDAO()
	{
		super(CAMINHO);
	}
	
	protected Subscricao deStringParaObjeto(String linha)
	{
		String dados[] = linha.split(";");
		if (dados.length == 3)
		{			
			Subscricao subscricao = new Subscricao();			
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			TopicoDAO topicoDAO = new TopicoDAO();						
			subscricao.setUsuario(usuarioDAO.obterRegistro(dados[0]));
			subscricao.setTopico(topicoDAO.obterRegistro(dados[1]));			
			subscricao.setFavorito(dados[2].equals("true"));			
			return subscricao;			
		}
		return null;
	}

	protected String deObjetoParaString(Subscricao objeto)
	{
		if ((objeto != null) && (objeto.getUsuario() != null) && (objeto.getTopico() != null))
		{
			String linha = "";			
			linha += objeto.getUsuario().getNomeUsuario() + ";";
			linha += objeto.getTopico().getTitulo() + ";";
			linha += objeto.isFavorito();
			return linha;
		}
		return "";
	}

	public Subscricao obterRegistro(Usuario usuario, Topico topico)
	{
		if ((usuario != null) && (topico != null))
			for (Subscricao subscricao : obterLista())
				if ((subscricao.getUsuario().comparar(usuario)) && (subscricao.getTopico().comparar(topico)))
					return subscricao;
		return null;
	}
	
	public List<Subscricao> obterLista(Topico topico)
	{
		List<Subscricao> subscricoes = new ArrayList<>();
		if (topico != null)
		{
			for (Subscricao subscricao : obterLista())
				if (subscricao.getTopico().comparar(topico))
					subscricoes.add(subscricao);
		}
		return subscricoes;
	}
	
	public List<Subscricao> obterLista(Usuario usuario)
	{
		List<Subscricao> subscricoes = new ArrayList<>();
		if (usuario != null)
		{
			for (Subscricao subscricao : obterLista())
				if (subscricao.getUsuario().comparar(usuario))
					subscricoes.add(subscricao);
		}
		return subscricoes;
	}
	
	private boolean jaExiste(Subscricao subscricao)
	{
		if (this.arquivo.exists())
		{
			try
			{
				for (String linha : ler(this.arquivo))
					if (deStringParaObjeto(linha).comparar(subscricao))
						return true;
				return false;			
			} catch (IOException e)
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean inserir(Subscricao subscricao)
	{
		if ((subscricao != null) && (!jaExiste(subscricao)))
		{
			try
			{
				escrever(deObjetoParaString(subscricao), this.arquivo, false);
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
		if (subscricao != null)
		{			
			List<String> linhas = new ArrayList<>();
			List<Subscricao> subscricoes = obterLista();			
			for (Iterator<Subscricao> iterator = subscricoes.iterator(); iterator.hasNext();)
			{
				Subscricao outroSubscricao = iterator.next();
				if (subscricao.comparar(outroSubscricao))
				{
					iterator.remove();
					break;
				}
			}			
			for (Subscricao subscricaoRestante : subscricoes)
				linhas.add(deObjetoParaString(subscricaoRestante));			
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
			List<Subscricao> subscricoes = obterLista();			
			for (Iterator<Subscricao> iterator = subscricoes.iterator(); iterator.hasNext();)
			{
				Subscricao subscricao = iterator.next();				
				if (subscricao.getUsuario().comparar(usuario))
					iterator.remove();				
			}			
			for (Subscricao subscricao : subscricoes)
				linhas.add(deObjetoParaString(subscricao));			
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
			List<Subscricao> subscricoes = obterLista();			
			for (Iterator<Subscricao> iterator = subscricoes.iterator(); iterator.hasNext();)
			{
				Subscricao subscricao = iterator.next();				
				if (subscricao.getTopico().comparar(topico))
					iterator.remove();
			}			
			for (Subscricao subscricao : subscricoes)
				linhas.add(deObjetoParaString(subscricao));			
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
	
	public boolean editar(Subscricao subscricao)
	{
		if (subscricao != null)
		{
			List<String> linhas = new ArrayList<>();
			List<Subscricao> subscricoes = obterLista();
			for (int i = 0; i < subscricoes.size(); i++)
				if (subscricao.comparar(subscricoes.get(i)))
					subscricoes.set(i, subscricao);			
			for (Subscricao novaSubscricao : subscricoes)
				linhas.add(deObjetoParaString(novaSubscricao));			
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