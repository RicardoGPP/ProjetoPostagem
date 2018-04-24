package pt.up.fc.lc.postagem.persistencia.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pt.up.fc.lc.postagem.persistencia.entidades.Subscricao;
import pt.up.fc.lc.postagem.persistencia.entidades.Topico;
import pt.up.fc.lc.postagem.persistencia.entidades.Usuario;

public class SubscricaoDAO extends DAO<Subscricao>
{
	private static final String CAMINHO = "subscricao.txt";
	
	public SubscricaoDAO()
	{
		super(CAMINHO);
	}
	
	protected Subscricao deStringParaObjeto(String linha)
	{
		String dados[] = linha.split(";");
		if (dados.length == 2)
		{			
			Subscricao subscricao = new Subscricao();			
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			TopicoDAO topicoDAO = new TopicoDAO();						
			subscricao.setUsuario(usuarioDAO.obterRegistro(dados[0]));
			subscricao.setTopico(topicoDAO.obterRegistro(dados[1]));			
			return subscricao;			
		}
		return null;
	}

	protected String deObjetoParaString(Subscricao objeto)
	{
		if ((objeto != null) && (objeto.getUsuario() != null) && (objeto.getTopico() != null))
		{
			String linha = "";			
			linha += objeto.getUsuario().getUtilizador() + ";";
			linha += objeto.getTopico().getIdentificador();			
			return linha;
		}
		return "";
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

	public List<Usuario> obterSubscritores(Topico topico)
	{
		List<Usuario> usuarios = new ArrayList<>();
		if (topico != null)
		{
			for (Subscricao subscricao : obterLista())
				if (subscricao.getTopico().comparar(topico))
					usuarios.add(subscricao.getUsuario());
		}
		return usuarios;
	}
	
	public List<Topico> obterTopicosSubscritos(Usuario usuario)
	{
		List<Topico> topicos = new ArrayList<>();
		if (usuario != null)
		{
			for (Subscricao subscricao : obterLista())
				if (subscricao.getUsuario().comparar(usuario))
					topicos.add(subscricao.getTopico());
		}
		return topicos;
	}
}