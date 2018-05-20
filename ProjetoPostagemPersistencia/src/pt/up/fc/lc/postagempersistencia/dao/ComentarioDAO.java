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
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe para acesso a dados da entidade de comentário.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class ComentarioDAO extends DAO<Comentario>
{
	private static final String CAMINHO = "COMENTARIO";	
	
	/**
		Cria o DAO e passa o caminho do arquivo à superclasse.
	*/
	public ComentarioDAO()
	{
		super(CAMINHO);
	}
	
	/**
		Converte uma linha de dados delimitados por ponto e vírgula
		para um objeto de comentário.
		
		@param Uma linha de dados.
		@return Um objeto de comentários.
	*/
	protected Comentario deStringParaObjeto(String linha)
	{
		String dados[] = linha.split(";");
		if (dados.length == 4)
		{			
			try
			{
				Comentario comentario = new Comentario();				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA_HORA);				
				comentario.setUsuario((new UsuarioDAO()).obterRegistro(dados[0]));
				comentario.setTopico((new TopicoDAO()).obterRegistro(dados[1]));
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

	/**
		Converte um objeto de comentário para uma linha de dados delimitada
		por ponto e vírgula.
		
		@param Um objeto de comentário.
		@return Uma linha de dados. 
	*/
	protected String deObjetoParaString(Comentario comentario)
	{
		if ((comentario != null) && (comentario.getUsuario() != null) &&
		   (comentario.getTopico() != null) && (comentario.getData() != null))
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA_HORA);
			String linha = "";								
			linha += comentario.getUsuario().getNomeUsuario() + ";";
			linha += comentario.getTopico().getTitulo() + ";";
			linha += simpleDateFormat.format(comentario.getData()) + ";";
			linha += comentario.getMensagem();			
			return linha;			
		}
		return "";
	}
	
	/**
		Verifica se o comentário existe no arquivo.
		
		@param Um comentário.
		@return Se o tópico existe ou não.
	*/
	public boolean existe(Comentario comentario)
	{
		return (this.obterRegistro(comentario.getUsuario(), comentario.getTopico(), comentario.getData()) != null);			   
	}
	
	/**
		Verifica se o comentário é consistente, isto é, se o usuário existe e está
		subscrito no tópico, e se o tópico existe.
		
		@param O comentário a ser validada.
		@return Se o comentário é válido ou não. 
	*/
	protected boolean eValido(Comentario comentario)
	{
		boolean usuarioEstaSubscrito = false;
		for (Subscricao subscricao : (new SubscricaoDAO()).obterLista(comentario.getTopico()))
		{
			if (comentario.getUsuario().comparar(subscricao.getUsuario()))
			{
				usuarioEstaSubscrito = true;
				break;
			}
		}		
		return ((new UsuarioDAO()).existe(comentario.getUsuario())) &&
			   ((new TopicoDAO()).existe(comentario.getTopico()) &&
			   (usuarioEstaSubscrito));
	}
	
	/**
		Busca um comentário no arquivo por meio de um usuário, um tópico e uma data.
		
		@param O usuário, o tópico e a data do comentário a ser recuperado.
		@return O comentário se for encontrado ou null se não for. 
	*/
	public Comentario obterRegistro(Usuario usuario, Topico topico, Date data)
	{			
		if ((usuario != null) && (topico != null) && (data != null))
			for (Comentario comentario : obterLista())
				if ((comentario.getUsuario().comparar(usuario)) &&
					(comentario.getTopico().comparar(topico)) &&
					(comentario.getData().compareTo(data) == 0))
					return comentario;
		return null;
	}
	
	/**
		Busca os comentários de um usuário.
	
		@param O usuário a ser usado na pesquisa.
		@return Uma lista com os comentários encontrados.
	*/
	public List<Comentario> obterLista(Usuario usuario)
	{
		List<Comentario> comentarios = new ArrayList<>();
		if ((usuario != null) && ((new UsuarioDAO()).existe(usuario)))
		{
			for (Comentario comentario : obterLista())
				if (comentario.getUsuario().comparar(usuario))
					comentarios.add(comentario);
		}
		return comentarios;
	}
	
	/**
		Busca os comentários de um tópico.
	
		@param O tópico a ser usado na pesquisa.
		@return Uma lista com os tópicos encontrados.
	*/
	public List<Comentario> obterLista(Topico topico)
	{		
		List<Comentario> comentarios = new ArrayList<>();
		if ((topico != null) && ((new TopicoDAO()).existe(topico)))
		{
			for (Comentario comentario : obterLista())
				if (comentario.getTopico().comparar(topico))
					comentarios.add(comentario);
		}
		return comentarios;
	}	
	
	/**
		Insere um comentário no arquivo.
	
		@param O comentário a ser inserido.
		@return Se o comentário foi inserido ou não.
	*/
	public boolean inserir(Comentario comentario)
	{
		if ((comentario != null) && (!this.existe(comentario)) && (this.eValido(comentario)))
		{
			List<Comentario> comentarios = this.obterLista(comentario.getTopico());
			if (comentarios.size() >= comentario.getTopico().getLimiteMensagens())
			{
				Collections.sort(comentarios, (c1, c2) -> c1.getData().compareTo(c2.getData()));
				this.deletar(comentarios.get(0));
			}			
			try
			{
				this.escrever(this.deObjetoParaString(comentario), this.arquivo, false);
				return true;
			} catch (IOException e)
			{
				return false;
			}
		}
		return false;
	}
	
	/**
		Deleta um comentário no arquivo.
	
		@param O comentário a ser deletado.
		@return Se o comentário foi deletado ou não.
	*/
	public boolean deletar(Comentario comentario)
	{
		if ((comentario != null) && (this.existe(comentario)) && (this.eValido(comentario)))
		{			
			(new CurtidaDAO()).deletar(comentario);			
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
				linhas.add(this.deObjetoParaString(comentarioRestante));			
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
		Deleta os comentários que estejam vinculados a um usuário.
	
		@param O usuário a ser pesquisado na exclusão.
		@return Se os comentários foram deletados ou não.
	*/
	public boolean deletar(Usuario usuario)
	{
		if ((usuario != null) && ((new UsuarioDAO()).existe(usuario)))
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
				linhas.add(this.deObjetoParaString(comentario));			
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
		Deleta os comentários que estejam vinculados a um tópico.
	
		@param O tópico a ser pesquisado na exclusão.
		@return Se os comentários foram deletados ou não.
	*/
	public boolean deletar(Topico topico)
	{
		if ((topico != null) && ((new TopicoDAO()).existe(topico)))
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
				linhas.add(this.deObjetoParaString(comentario));			
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
		Deleta os comentários que estejam vinculados a uma subscrição.
	
		@param A subscrição a ser pesquisada na exclusão.
		@return Se os comentários foram deletados ou não.
	*/
	public boolean deletar(Subscricao subscricao)
	{
		if ((subscricao != null) && ((new SubscricaoDAO()).existe(subscricao)))
		{
			List<String> linhas = new ArrayList<>();
			List<Comentario> comentarios = obterLista();			
			for (Iterator<Comentario> iterator = comentarios.iterator(); iterator.hasNext();)
			{
				Comentario comentario = iterator.next();				
				if (comentario.getUsuario().comparar(subscricao.getUsuario()) &&
				   (comentario.getTopico().comparar(subscricao.getTopico())))
					iterator.remove();				
			}			
			for (Comentario comentario : comentarios)
				linhas.add(this.deObjetoParaString(comentario));			
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