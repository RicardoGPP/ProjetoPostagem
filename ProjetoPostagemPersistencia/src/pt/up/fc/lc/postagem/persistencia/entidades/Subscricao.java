package pt.up.fc.lc.postagem.persistencia.entidades;

public class Subscricao implements Entidade<Subscricao>
{
	private Usuario usuario;
	private Topico topico;
	
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
	
	public Subscricao()
	{
		this.usuario = null;
		this.topico = null;
	}
	
	public boolean comparar(Subscricao subscricao)
	{
		return ((subscricao != null) && (subscricao.getUsuario() != null) &&
			   (subscricao.getTopico() != null) && (this.usuario != null) &&
		       (this.topico != null) && (this.usuario.comparar(subscricao.getUsuario())) &&
		       (this.topico.comparar(subscricao.getTopico())));
	}
}