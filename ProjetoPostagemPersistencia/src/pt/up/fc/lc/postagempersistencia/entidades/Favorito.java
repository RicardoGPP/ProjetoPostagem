package pt.up.fc.lc.postagempersistencia.entidades;

public class Favorito implements Entidade<Favorito>
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
	
	public Favorito()
	{
		this.usuario = null;
		this.topico = null;
	}
	
	public boolean comparar(Favorito objeto)
	{
		return ((objeto.getUsuario().comparar(this.usuario)) &&
			   (objeto.getTopico().comparar(this.topico)));	
	}
}