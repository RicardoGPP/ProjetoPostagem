package pt.up.fc.lc.postagempersistencia.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

/**
	Classe para acesso a dados da entidade de t�pico.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class TopicoDAO extends DAO<Topico>
{
	private static final String CAMINHO = "TOPICO";
	
	/**
		Cria o DAO e passa o caminho do arquivo � superclasse.
	*/
	public TopicoDAO()
	{
		super(CAMINHO);
	}
	
	/**
		Converte uma linha de dados delimitados por ponto e v�rgula
		para um objeto de t�pico.
		
		@param Uma linha de dados.
		@return Um objeto de t�pico.
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
		Converte um objeto de t�pico para uma linha de dados delimitada
		por ponto e v�rgula.
		
		@param Um objeto de t�pico.
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
		Verifica se o t�pico existe no arquivo.
		
		@param Um t�pico.
		@return Se o t�pico existe ou n�o.
	*/
	public boolean existe(Topico topico)
	{
		return (this.obterRegistro(topico.getTitulo()) != null);
	}
	
	/**
		Busca um t�pico no arquivo por meio de um titulo.
		
		@param O t�tulo do t�pico a ser recuperado.
		@return O t�pico se for encontrado ou null se n�o for. 
	*/
	public Topico obterRegistro(String titulo)
	{			
		for (Topico topico : obterLista())
			if (topico.getTitulo().equalsIgnoreCase(titulo))
				return topico;
		return null;
	}
	
	/**
		Insere um t�pico no arquivo.
	
		@param O t�pico a ser inserido.
		@return Se o t�pico foi inserido ou n�o.
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
		Deleta um t�pico no arquivo.
	
		@param O t�pico a ser deletado.
		@return Se o t�pico foi deletado ou n�o.
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
		Edita um t�pico no arquivo.
	
		@param O t�pico a ser deletado.
		@return Se o t�pico foi deletado ou n�o.
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