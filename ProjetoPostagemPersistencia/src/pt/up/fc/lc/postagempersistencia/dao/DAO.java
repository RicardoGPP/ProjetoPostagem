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
	Classe abstrata e genérica geral para todos os objetos de acesso a dados
	do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public abstract class DAO<T extends Entidade<T>>
{
	public static final String FORMATO_DATA = "dd/MM/yyyy";
	public static final String FORMATO_DATA_HORA = "dd/MM/yyyy HH:mm:ss";
	protected File arquivo;
	
	/**
		Inicializa o arquivo do DAO com caminho passado por parâmetro.
		
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
		Escreve a linha no arquivo, podendo sobrescrever ou não.
		
		@param A linha a ser escrita, o arquivo e se deve sobrescrever ou não.
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
		Escreve as linhas no arquivo, podendo sobrescrever ou não.
		
		@param As linhas a serem escritas, o arquivo e se deve sobrescrever ou não.
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
		Verifica se um objeto é consistente ou não. Por padrão, objetos não
		compostos são sempre consistentes. Este método deve ser sobrescrito
		nas classes em que a entidade seja constituída de outras.
	
		@param Um objeto a ser validado.
		@return Se o objeto é válido ou não.
	*/
	protected boolean eValido(T objeto)
	{
		return true;
	}
	
	/**
		Converte uma linha de dados delimitados por ponto e vírgula para um objeto.
		
		@param Uma linha de dados.
		@return Um objeto.
	*/
	protected abstract T deStringParaObjeto(String linha);
	
	/**
		Converte um objeto para uma linha de dados delimitada por ponto e vírgula.
		
		@param Um objeto.
		@return Uma linha de dados. 
	*/
	protected abstract String deObjetoParaString(T objeto);
	
	/**
		Verifica se o objeto existe no arquivo.
		
		@param Um objeto.
		@return Se o objeto existe ou não.
	*/
	public abstract boolean existe(T objeto);
}