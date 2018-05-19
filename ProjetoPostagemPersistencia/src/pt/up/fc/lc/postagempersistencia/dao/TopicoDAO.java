package pt.up.fc.lc.postagempersistencia.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pt.up.fc.lc.postagempersistencia.entidades.Topico;

public class TopicoDAO extends DAO<Topico>
{
	private static final String CAMINHO = "TOPICO";
	
	public TopicoDAO()
	{
		super(CAMINHO);
	}
	
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
	
	protected String deObjetoParaString(Topico objeto)
	{
		if (objeto != null)
		{
			String linha = "";
			linha += objeto.getTitulo() + ";";
			linha += objeto.getDescricao() + ";";
			linha += objeto.getLimiteMensagens();					
			return linha;					
		}
		return "";
	}
	
	public Topico obterRegistro(String identificador)
	{			
		for (Topico topico : obterLista())
			if (topico.getTitulo().equalsIgnoreCase(identificador))
				return topico;
		return null;
	}
	
	public boolean inserir(Topico topico)
	{
		if ((topico != null) && (obterRegistro(topico.getTitulo()) == null))
		{
			try
			{
				escrever(deObjetoParaString(topico), this.arquivo, false);
				return true;
			} catch (IOException e)
			{
				return false;
			}
		}
		return false;
	}
	
	public boolean deletar(Topico topico)
	{
		if (topico != null)
		{
			SubscricaoDAO subscricaoDAO = new SubscricaoDAO();
			ComentarioDAO comentarioDAO = new ComentarioDAO();
			subscricaoDAO.deletar(topico);
			comentarioDAO.deletar(topico);
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
				linhas.add(deObjetoParaString(topicoRestante));			
			try
			{
				escrever(linhas, this.arquivo, true);
				return true;
			} catch (IOException e)
			{
				return false;
			}			
		}
		return false;
	}
	
	public boolean editar(Topico topico)
	{
		if (topico != null)
		{
			List<String> linhas = new ArrayList<>();
			List<Topico> topicos = obterLista();
			for (int i = 0; i < topicos.size(); i++)
				if (topico.comparar(topicos.get(i)))
					topicos.set(i, topico);			
			for (Topico novoTopico : topicos)
				linhas.add(deObjetoParaString(novoTopico));			
			try
			{
				escrever(linhas, this.arquivo, true);
				return true;
			} catch (IOException e)
			{
				return false;
			}
		}
		return false;
	}
}