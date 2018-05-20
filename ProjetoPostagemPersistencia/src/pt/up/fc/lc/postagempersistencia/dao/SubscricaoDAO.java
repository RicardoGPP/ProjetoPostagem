package pt.up.fc.lc.postagempersistencia.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe para acesso a dados da entidade de subscrição.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class SubscricaoDAO extends DAO<Subscricao>
{
	private static final String CAMINHO = "SUBSCRICAO";
	
	/**
		Cria o DAO e passa o caminho do arquivo à superclasse.
	*/
	public SubscricaoDAO()
	{
		super(CAMINHO);
	}
	
	/**
		Converte uma linha de dados delimitados por ponto e vírgula
		para um objeto de subscrição.
		
		@param Uma linha de dados.
		@return Um objeto de subscrição.
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
		Converte um objeto de subscrição para uma linha de dados delimitada
		por ponto e vírgula.
		
		@param Um objeto de subscrição.
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
		Verifica se a subscrição existe no arquivo.
		
		@param Uma subscrição.
		@return Se a subscrição existe ou não.
	*/
	public boolean existe(Subscricao subscricao)
	{
		return (this.obterRegistro(subscricao.getUsuario(), subscricao.getTopico()) != null);
	}
	
	/**
		Verifica se a subscrição é consistente, isto é, se o usuário e o
		tópico vinculados à ela existem.
		
		@param A subscrição a ser validada.
		@return Se a subscrição é válida ou não. 
	*/
	protected boolean eValido(Subscricao subscricao)
	{
		return ((new UsuarioDAO()).existe(subscricao.getUsuario())) &&
			   ((new TopicoDAO()).existe(subscricao.getTopico()));
	}
	
	/**
		Busca uma subscrição no arquivo por meio de um usuário e um tópico.
		
		@param O usuário e o tópico da subscrição a ser recuperada.
		@return A subscrição se for encontrada ou null se não for. 
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
		Busca as subscrições de um usuário.
	
		@param O usuário a ser usado na pesquisa.
		@return Uma lista com as subscrições encontradas.
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
		Busca as subscrições de um tópico.
	
		@param O tópico a ser usado na pesquisa.
		@return Uma lista com as subscrições encontradas.
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
		Insere uma subscrição no arquivo.
	
		@param A subscrição a ser inserida.
		@return Se o subscrição foi inserida ou não.
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
		Deleta uma subscrição no arquivo.
	
		@param A subscrição a ser deletada.
		@return Se a subscrição foi deletada ou não.
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
		Deleta as subscrições que estejam vinculadas a um usuário.
	
		@param O usuário a ser pesquisado na exclusão.
		@return Se as subscrições foram deletadas ou não.
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
		Deleta as subscrições que estejam vinculadas a um tópico.
	
		@param O tópico a ser pesquisado na exclusão.
		@return Se as subscrições foram deletadas ou não.
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
		Edita uma subscrição no arquivo.
	
		@param A subscrição a ser editada.
		@return Se a subscrição foi editada ou não.
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