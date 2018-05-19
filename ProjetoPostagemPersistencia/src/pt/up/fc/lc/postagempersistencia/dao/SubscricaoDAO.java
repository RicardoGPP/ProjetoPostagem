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
			subscricao.setUsuario((new UsuarioDAO()).obterRegistro(dados[0]));
			subscricao.setTopico((new TopicoDAO()).obterRegistro(dados[1]));			
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

	public boolean existe(Subscricao objeto)
	{
		return (this.obterRegistro(objeto.getUsuario(), objeto.getTopico()) != null);
	}
	
	protected boolean eValido(Subscricao objeto)
	{
		return ((new UsuarioDAO()).existe(objeto.getUsuario())) &&
			   ((new TopicoDAO()).existe(objeto.getTopico()));
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
		if ((topico != null) && ((new TopicoDAO()).existe(topico)))
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
		if ((usuario != null) && ((new UsuarioDAO()).existe(usuario)))
		{
			for (Subscricao subscricao : obterLista())
				if (subscricao.getUsuario().comparar(usuario))
					subscricoes.add(subscricao);
		}
		return subscricoes;
	}
	
	public boolean inserir(Subscricao subscricao)
	{
		if ((subscricao != null) && (!this.existe(subscricao)) && (this.eValido(subscricao)))
		{
			try
			{
				this.escrever(this.deObjetoParaString(subscricao), this.arquivo, false);
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
		if ((subscricao != null) && (this.existe(subscricao)) && this.eValido(subscricao))
		{			
			(new CurtidaDAO()).deletar(subscricao);
			(new ComentarioDAO()).deletar(subscricao);
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
				linhas.add(this.deObjetoParaString(subscricaoRestante));			
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
			List<Subscricao> subscricoes = obterLista();			
			for (Iterator<Subscricao> iterator = subscricoes.iterator(); iterator.hasNext();)
			{
				Subscricao subscricao = iterator.next();				
				if (subscricao.getUsuario().comparar(usuario))
					iterator.remove();				
			}			
			for (Subscricao subscricao : subscricoes)
				linhas.add(this.deObjetoParaString(subscricao));			
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
			List<Subscricao> subscricoes = obterLista();			
			for (Iterator<Subscricao> iterator = subscricoes.iterator(); iterator.hasNext();)
			{
				Subscricao subscricao = iterator.next();				
				if (subscricao.getTopico().comparar(topico))
					iterator.remove();
			}			
			for (Subscricao subscricao : subscricoes)
				linhas.add(this.deObjetoParaString(subscricao));			
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
	
	public boolean editar(Subscricao subscricao)
	{
		if ((subscricao != null) && (this.existe(subscricao)) && (this.eValido(subscricao)))
		{
			List<String> linhas = new ArrayList<>();
			List<Subscricao> subscricoes = obterLista();
			for (int i = 0; i < subscricoes.size(); i++)
				if (subscricao.comparar(subscricoes.get(i)))
					subscricoes.set(i, subscricao);			
			for (Subscricao novaSubscricao : subscricoes)
				linhas.add(this.deObjetoParaString(novaSubscricao));			
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