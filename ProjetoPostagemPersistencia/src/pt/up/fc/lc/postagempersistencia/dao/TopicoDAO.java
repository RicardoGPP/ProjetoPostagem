package pt.up.fc.lc.postagempersistencia.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

/**
	Classe para acesso a dados da entidade de tópico.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class TopicoDAO extends DAO<Topico>
{
	private static final String CAMINHO = "TOPICO";
	
	/**
		Cria o DAO e passa o caminho do arquivo à superclasse.
	*/
	public TopicoDAO()
	{
		super(CAMINHO);
	}
	
	/**
		Converte uma linha de dados delimitados por ponto e vírgula
		para um objeto de tópico.
		
		@param Uma linha de dados.
		@return Um objeto de tópico.
	*/
	protected Topico deStringParaObjeto(String linha)
	{
		String dados[] = linha.split(";");
		if (dados.length == 3)
		{
			Topico topico = new Topico();			
			topico.setTitulo(dados[0]);
			topico.setDescricao(dados[1]);
			topico.setLimiteMensagens(Integer.parseInt(dados[2]));
			return topico;
		}
		return null;
	}
	
	/**
		Converte um objeto de tópico para uma linha de dados delimitada
		por ponto e vírgula.
		
		@param Um objeto de tópico.
		@return Uma linha de dados. 
	*/
	protected String deObjetoParaString(Topico topico)
	{
		if (topico != null)
		{
			String linha = "";
			linha += topico.getTitulo() + ";";
			linha += topico.getDescricao() + ";";
			linha += topico.getLimiteMensagens();					
			return linha;					
		}
		return "";
	}
	
	/**
		Verifica se o tópico existe no arquivo.
		
		@param Um tópico.
		@return Se o tópico existe ou não.
	*/
	public boolean existe(Topico topico)
	{
		return (this.obterRegistro(topico.getTitulo()) != null);
	}
	
	/**
		Busca um tópico no arquivo por meio de um titulo.
		
		@param O título do tópico a ser recuperado.
		@return O tópico se for encontrado ou null se não for. 
	*/
	public Topico obterRegistro(String titulo)
	{			
		for (Topico topico : obterLista())
			if (topico.getTitulo().equalsIgnoreCase(titulo))
				return topico;
		return null;
	}
	
	/**
		Insere um tópico no arquivo.
	
		@param O tópico a ser inserido.
		@return Se o tópico foi inserido ou não.
	*/
	public boolean inserir(Topico topico)
	{
		if ((topico != null) && (!this.existe(topico)))
		{
			try
			{
				this.escrever(this.deObjetoParaString(topico), this.arquivo, false);
				return true;
			} catch (IOException e)
			{
				return false;
			}
		}
		return false;
	}
	
	/**
		Deleta um tópico no arquivo.
	
		@param O tópico a ser deletado.
		@return Se o tópico foi deletado ou não.
	*/
	public boolean deletar(Topico topico)
	{
		if ((topico != null) && (this.existe(topico)))
		{
			(new SubscricaoDAO()).deletar(topico);
			(new TopicoDAO()).deletar(topico);
			List<String> linhas = new ArrayList<>();
			List<Topico> topicos = obterLista();			
			for (Iterator<Topico> iterator = topicos.iterator(); iterator.hasNext();)
			{
				Topico outroTopico = iterator.next();
				if (topico.comparar(outroTopico))
				{
					iterator.remove();
					break;
				}
			}			
			for (Topico topicoRestante : topicos)
				linhas.add(this.deObjetoParaString(topicoRestante));			
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
		Edita um tópico no arquivo.
	
		@param O tópico a ser deletado.
		@return Se o tópico foi deletado ou não.
	*/
	public boolean editar(Topico topico)
	{
		if ((topico != null) && (this.existe(topico)))
		{
			List<String> linhas = new ArrayList<>();
			List<Topico> topicos = obterLista();
			for (int i = 0; i < topicos.size(); i++)
				if (topico.comparar(topicos.get(i)))
					topicos.set(i, topico);			
			for (Topico novoTopico : topicos)
				linhas.add(this.deObjetoParaString(novoTopico));			
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