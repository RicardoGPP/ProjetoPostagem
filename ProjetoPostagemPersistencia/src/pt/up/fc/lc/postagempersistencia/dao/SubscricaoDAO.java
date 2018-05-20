package pt.up.fc.lc.postagempersistencia.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe para acesso a dados da entidade de subscri��o.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class SubscricaoDAO extends DAO<Subscricao>
{
	private static final String CAMINHO = "SUBSCRICAO";
	
	/**
		Cria o DAO e passa o caminho do arquivo � superclasse.
	*/
	public SubscricaoDAO()
	{
		super(CAMINHO);
	}
	
	/**
		Converte uma linha de dados delimitados por ponto e v�rgula
		para um objeto de subscri��o.
		
		@param Uma linha de dados.
		@return Um objeto de subscri��o.
	*/
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

	/**
		Converte um objeto de subscri��o para uma linha de dados delimitada
		por ponto e v�rgula.
		
		@param Um objeto de subscri��o.
		@return Uma linha de dados. 
	*/
	protected String deObjetoParaString(Subscricao subscricao)
	{
		if ((subscricao != null) && (subscricao.getUsuario() != null) && (subscricao.getTopico() != null))
		{
			String linha = "";			
			linha += subscricao.getUsuario().getNomeUsuario() + ";";
			linha += subscricao.getTopico().getTitulo() + ";";
			linha += subscricao.isFavorito();
			return linha;
		}
		return "";
	}

	/**
		Verifica se a subscri��o existe no arquivo.
		
		@param Uma subscri��o.
		@return Se a subscri��o existe ou n�o.
	*/
	public boolean existe(Subscricao subscricao)
	{
		return (this.obterRegistro(subscricao.getUsuario(), subscricao.getTopico()) != null);
	}
	
	/**
		Verifica se a subscri��o � consistente, isto �, se o usu�rio e o
		t�pico vinculados � ela existem.
		
		@param A subscri��o a ser validada.
		@return Se a subscri��o � v�lida ou n�o. 
	*/
	protected boolean eValido(Subscricao subscricao)
	{
		return ((new UsuarioDAO()).existe(subscricao.getUsuario())) &&
			   ((new TopicoDAO()).existe(subscricao.getTopico()));
	}
	
	/**
		Busca uma subscri��o no arquivo por meio de um usu�rio e um t�pico.
		
		@param O usu�rio e o t�pico da subscri��o a ser recuperada.
		@return A subscri��o se for encontrada ou null se n�o for. 
	*/
	public Subscricao obterRegistro(Usuario usuario, Topico topico)
	{
		if ((usuario != null) && (topico != null))
			for (Subscricao subscricao : obterLista())
				if ((subscricao.getUsuario().comparar(usuario)) && (subscricao.getTopico().comparar(topico)))
					return subscricao;
		return null;
	}
	
	/**
		Busca as subscri��es de um usu�rio.
	
		@param O usu�rio a ser usado na pesquisa.
		@return Uma lista com as subscri��es encontradas.
	*/
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
	
	/**
		Busca as subscri��es de um t�pico.
	
		@param O t�pico a ser usado na pesquisa.
		@return Uma lista com as subscri��es encontradas.
	*/
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
	
	/**
		Insere uma subscri��o no arquivo.
	
		@param A subscri��o a ser inserida.
		@return Se o subscri��o foi inserida ou n�o.
	*/
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
	
	/**
		Deleta uma subscri��o no arquivo.
	
		@param A subscri��o a ser deletada.
		@return Se a subscri��o foi deletada ou n�o.
	*/
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
	
	/**
		Deleta as subscri��es que estejam vinculadas a um usu�rio.
	
		@param O usu�rio a ser pesquisado na exclus�o.
		@return Se as subscri��es foram deletadas ou n�o.
	*/
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
	
	/**
		Deleta as subscri��es que estejam vinculadas a um t�pico.
	
		@param O t�pico a ser pesquisado na exclus�o.
		@return Se as subscri��es foram deletadas ou n�o.
	*/
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
	
	/**
		Edita uma subscri��o no arquivo.
	
		@param A subscri��o a ser editada.
		@return Se a subscri��o foi editada ou n�o.
	*/
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