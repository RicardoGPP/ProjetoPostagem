package pt.up.fc.lc.postagempersistencia.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.entidades.Entidade;

/**
	Classe abstrata e gen�rica geral para todos os objetos de acesso a dados
	do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public abstract class DAO<T extends Entidade<T>>
{
	public static final String FORMATO_DATA = "dd/MM/yyyy";
	public static final String FORMATO_DATA_HORA = "dd/MM/yyyy HH:mm:ss";
	protected File arquivo;
	
	/**
		Inicializa o arquivo do DAO com caminho passado por par�metro.
		
		@param Caminho do arquivo.
	*/
	protected DAO(String caminho)
	{
		this.arquivo = new File(caminho);
	}

	/**
		Faz a leitura do arquivo e recupera uma ArrayList com as linhas
		do arquivo.
		
		@param O arquivo a ser lido.
		@return Uma lista com as linhas do arquivo.
		@throws IOException
	*/
	protected List<String> ler(File arquivo) throws IOException
	{
		List<String> linhas = new ArrayList<>();		
		try (FileReader stream = new FileReader(arquivo);
			 BufferedReader leitor = new BufferedReader(stream))
		{
			String linha = "";
			while ((linha = leitor.readLine()) != null)
				linhas.add(linha);	
		} catch (IOException e)
		{
			throw e;
		}
		return linhas;
	}
	
	/**
		Escreve a linha no arquivo, podendo sobrescrever ou n�o.
		
		@param A linha a ser escrita, o arquivo e se deve sobrescrever ou n�o.
		@throws IOException
	*/
	protected void escrever(String linha, File arquivo, boolean sobrescrever) throws IOException
	{
		try (FileWriter stream = new FileWriter(arquivo, !sobrescrever);
			 BufferedWriter escritor = new BufferedWriter(stream))
		{
			escritor.append(linha + "\n");
		} catch (IOException e)
		{
			throw e;
		}
	}
	
	/**
		Escreve as linhas no arquivo, podendo sobrescrever ou n�o.
		
		@param As linhas a serem escritas, o arquivo e se deve sobrescrever ou n�o.
		@throws IOException
	*/
	protected void escrever(List<String> linhas, File arquivo, boolean sobrescrever) throws IOException
	{
		try (FileWriter stream = new FileWriter(arquivo, !sobrescrever);				
			 BufferedWriter escritor = new BufferedWriter(stream))
		{
			for (String linha : linhas)
				escritor.append(linha + "\n");
		} catch (IOException e)
		{
			throw e;
		}
	}
	
	/**
		Recupera a lista de todos os objetos no arquivo com o tipo especificado
		pelo generics da classe DAO.
		
		@return Uma lista com todos os objetos no arquivo.
	*/
	public List<T> obterLista()
	{
		List<T> objetos = new ArrayList<>();
		try
		{
			for (String linha : ler(this.arquivo))
				objetos.add(deStringParaObjeto(linha));						
		} catch (IOException e)
		{
			objetos.clear();
		}
		return objetos;
	}
	
	/**
		Verifica se um objeto � consistente ou n�o. Por padr�o, objetos n�o
		compostos s�o sempre consistentes. Este m�todo deve ser sobrescrito
		nas classes em que a entidade seja constitu�da de outras.
	
		@param Um objeto a ser validado.
		@return Se o objeto � v�lido ou n�o.
	*/
	protected boolean eValido(T objeto)
	{
		return true;
	}
	
	/**
		Converte uma linha de dados delimitados por ponto e v�rgula para um objeto.
		
		@param Uma linha de dados.
		@return Um objeto.
	*/
	protected abstract T deStringParaObjeto(String linha);
	
	/**
		Converte um objeto para uma linha de dados delimitada por ponto e v�rgula.
		
		@param Um objeto.
		@return Uma linha de dados. 
	*/
	protected abstract String deObjetoParaString(T objeto);
	
	/**
		Verifica se o objeto existe no arquivo.
		
		@param Um objeto.
		@return Se o objeto existe ou n�o.
	*/
	public abstract boolean existe(T objeto);
}