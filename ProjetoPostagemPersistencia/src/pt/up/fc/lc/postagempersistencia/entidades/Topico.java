package pt.up.fc.lc.postagempersistencia.entidades;

import pt.up.fc.lc.postagempersistencia.util.Formatador;

public class Topico implements Entidade<Topico>
{
	private String titulo;
	private String descricao;
	private int limiteMensagens;
	
	public String getTitulo()
	{
		return titulo;
	}
	public void setTitulo(String titulo)
	{
		this.titulo = Formatador.formatar(titulo);
	}
	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = Formatador.formatar(descricao);
	}
	public int getLimiteMensagens()
	{
		return limiteMensagens;
	}
	public void setLimiteMensagens(int limiteMensagens)
	{
		this.limiteMensagens = limiteMensagens;
	}
	
	public Topico()
	{
		this.titulo = "";
		this.descricao = "";
		this.limiteMensagens = 50;
	}

	public boolean comparar(Topico objeto)
	{
		return this.titulo.equalsIgnoreCase(objeto.getTitulo());
	}

	public String toString()
	{
		return this.titulo.trim();
	}
}