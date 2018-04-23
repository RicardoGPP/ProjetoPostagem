package pt.up.fc.lc.postagem.persistencia.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.up.fc.lc.postagem.persistencia.entidades.Entidade;

public abstract class DAO<T extends Entidade<T>>
{
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
	
	protected abstract T deStringParaObjeto(String linha);
	
	protected abstract String deObjetoParaString(T objeto);
}