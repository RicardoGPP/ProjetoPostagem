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

/**
	Classe para acesso a dados da entidade de curtida.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class CurtidaDAO extends DAO<Curtida>
{
	private static final String CAMINHO = "CURTIDA";
	
	/**
		Cria o DAO e passa o caminho do arquivo à superclasse.
	*/
	public CurtidaDAO()
	{
		super(CAMINHO);
	}

	/**
		Converte uma linha de dados delimitados por ponto e vírgula
		para um objeto de curtida.
		
		@param Uma linha de dados.
		@return Um objeto de curtida.
	*/
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
	
	/**
		Converte um objeto de curtida para uma linha de dados delimitada
		por ponto e vírgula.
		
		@param Um objeto de curtida.
		@return Uma linha de dados. 
	*/
	protected String deObjetoParaString(Curtida curtida)
	{
		if (curtida != null)
		{
			String linha = "";			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA_HORA);			
			linha += curtida.getUsuario().getNomeUsuario() + ";";
			linha += curtida.getComentario().getUsuario().getNomeUsuario() + ";";
			linha += curtida.getComentario().getTopico().getTitulo() + ";";
			linha += simpleDateFormat.format(curtida.getComentario().getData());			
			return linha;
		}
		return "";
	}
	
	/**
		Verifica se a curtida existe no arquivo.
		
		@param Uma curtida.
		@return Se a curtida existe ou não.
	*/
	public boolean existe(Curtida curtida)
	{
		return (this.obterRegistro(curtida.getUsuario(), curtida.getComentario()) != null);			   
	}
	
	/**
		Verifica se a curtida é consistente, isto é, se o usuário existe e está
		subscrito no tópico do comentário, e se o comentário existe e é valido.
		
		@param A curtida a ser validada.
		@return Se a curtida é válida ou não. 
	*/
	protected boolean eValido(Curtida curtida)
	{
		ComentarioDAO comentarioDAO = new ComentarioDAO();
		boolean usuarioEstaSubscrito = false;
		for (Subscricao subscricoes : (new SubscricaoDAO()).obterLista(curtida.getComentario().getTopico()))
		{
			if (curtida.getUsuario().comparar(subscricoes.getUsuario()))
			{
				usuarioEstaSubscrito = true;
				break;
			}
		}		
		return ((new UsuarioDAO()).existe(curtida.getUsuario())) &&
			   (comentarioDAO.existe(curtida.getComentario()) &&
			   (comentarioDAO.eValido(curtida.getComentario())) &&
			   (usuarioEstaSubscrito));
	}
	
	/**
		Verifica se um usuário curtiu um comentário.
		
		@param Um usuário e um comentário.
		@return Se o usuário curtiu o comentário ou não.
	*/
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
	
	/**
		Recupera a quantidade de curtidas de um comentário.
		
		@param Um comentário.
		@return A quantidade de curtidas do comentário.
	*/
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
	
	/**
		Busca uma curtida no arquivo por meio de um usuário e um comentário.
		
		@param O usuário e o comentário da curtida a ser recuperada.
		@return A curtida se for encontrada ou null se não for. 
	*/
	public Curtida obterRegistro(Usuario usuario, Comentario comentario)
	{			
		if ((usuario != null) && (comentario != null))
			for (Curtida curtida : obterLista())
				if ((curtida.getUsuario().comparar(usuario)) && (curtida.getComentario().comparar(comentario)))
					return curtida;
		return null;
	}
	
	/**
		Insere uma curtida no arquivo.
	
		@param A curtida a ser inserida.
		@return Se a curtida foi inserida ou não.
	*/
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
	
	/**
		Deleta uma curtida no arquivo.
	
		@param A curtida a ser deletada.
		@return Se a curtida foi deletada ou não.
	*/
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
	
	/**
		Deleta as curtidas que estejam vinculadas a um usuário.
	
		@param O usuário a ser pesquisado na exclusão.
		@return Se as curtidas foram deletadas ou não.
	*/
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
	
	/**
		Deleta as curtidas que estejam vinculadas a um comentário.
	
		@param O comentário a ser pesquisado na exclusão.
		@return Se as curtidas foram deletadas ou não.
	*/
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
	
	/**
		Deleta as curtidas que estejam vinculadas a uma subscrição.
	
		@param A subscrição a ser pesquisada na exclusão.
		@return Se as curtidas foram deletadas ou não.
	*/
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