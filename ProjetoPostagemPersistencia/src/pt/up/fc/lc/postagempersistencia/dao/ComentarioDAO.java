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
	Classe para acesso a dados da entidade de coment�rio.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class ComentarioDAO extends DAO<Comentario>
{
	private static final String CAMINHO = "COMENTARIO";	
	
	/**
		Cria o DAO e passa o caminho do arquivo � superclasse.
	*/
	public ComentarioDAO()
	{
		super(CAMINHO);
	}
	
	/**
		Converte uma linha de dados delimitados por ponto e v�rgula
		para um objeto de coment�rio.
		
		@param Uma linha de dados.
		@return Um objeto de coment�rios.
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
		Converte um objeto de coment�rio para uma linha de dados delimitada
		por ponto e v�rgula.
		
		@param Um objeto de coment�rio.
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
		Verifica se o coment�rio existe no arquivo.
		
		@param Um coment�rio.
		@return Se o t�pico existe ou n�o.
	*/
	public boolean existe(Comentario comentario)
	{
		return (this.obterRegistro(comentario.getUsuario(), comentario.getTopico(), comentario.getData()) != null);			   
	}
	
	/**
		Verifica se o coment�rio � consistente, isto �, se o usu�rio existe e est�
		subscrito no t�pico, e se o t�pico existe.
		
		@param O coment�rio a ser validada.
		@return Se o coment�rio � v�lido ou n�o. 
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
		Busca um coment�rio no arquivo por meio de um usu�rio, um t�pico e uma data.
		
		@param O usu�rio, o t�pico e a data do coment�rio a ser recuperado.
		@return O coment�rio se for encontrado ou null se n�o for. 
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
		Busca os coment�rios de um usu�rio.
	
		@param O usu�rio a ser usado na pesquisa.
		@return Uma lista com os coment�rios encontrados.
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
		Busca os coment�rios de um t�pico.
	
		@param O t�pico a ser usado na pesquisa.
		@return Uma lista com os t�picos encontrados.
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
		Insere um coment�rio no arquivo.
	
		@param O coment�rio a ser inserido.
		@return Se o coment�rio foi inserido ou n�o.
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
		Deleta um coment�rio no arquivo.
	
		@param O coment�rio a ser deletado.
		@return Se o coment�rio foi deletado ou n�o.
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
		Deleta os coment�rios que estejam vinculados a um usu�rio.
	
		@param O usu�rio a ser pesquisado na exclus�o.
		@return Se os coment�rios foram deletados ou n�o.
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
		Deleta os coment�rios que estejam vinculados a um t�pico.
	
		@param O t�pico a ser pesquisado na exclus�o.
		@return Se os coment�rios foram deletados ou n�o.
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
		Deleta os coment�rios que estejam vinculados a uma subscri��o.
	
		@param A subscri��o a ser pesquisada na exclus�o.
		@return Se os coment�rios foram deletados ou n�o.
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