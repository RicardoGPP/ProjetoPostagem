package pt.up.fc.lc.postagempersistencia.entidades;

import java.util.Date;

public class Comentario implements Entidade<Comentario>
{	
	private Usuario usuario;
	private Topico topico;
	private Date data;
	private String mensagem;
	
	public Usuario getUsuario()
	{
		return usuario;
	}
	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
	public Topico getTopico()
	{
		return topico;
	}
	public void setTopico(Topico topico)
	{
		this.topico = topico;
	}
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public String getMensagem()
	{
		return mensagem;
	}
	public void setMensagem(String mensagem)
	{
		this.mensagem = mensagem;
	}
	
	public Comentario()
	{
		this.usuario = null;
		this.topico = null;
		this.data = new Date();
		this.mensagem = "";
	}
	
	public boolean comparar(Comentario objeto)
	{
		return ((objeto != null) && (objeto.getUsuario() != null) && (objeto.getTopico() != null) &&
			   (objeto.getData() != null) && (this.usuario != null) && (this.topico != null) &&
			   (this.data != null) && (this.data.compareTo(objeto.getData()) == 0));		
	}
}