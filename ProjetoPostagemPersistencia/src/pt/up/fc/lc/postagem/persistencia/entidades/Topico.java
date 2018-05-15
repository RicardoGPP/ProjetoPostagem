package pt.up.fc.lc.postagem.persistencia.entidades;

public class Topico implements Entidade<Topico>
{
	private String identificador;
	private String descricao;
	private int limiteMensagens;
	
	public String getIdentificador()
	{
		return identificador;
	}
	public void setIdentificador(String identificador)
	{
		this.identificador = identificador;
	}
	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
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
		this.identificador = "";
		this.descricao = "";
		this.limiteMensagens = 50;
	}

	public boolean comparar(Topico objeto)
	{
		return this.identificador.equalsIgnoreCase(objeto.getIdentificador());
	}
}