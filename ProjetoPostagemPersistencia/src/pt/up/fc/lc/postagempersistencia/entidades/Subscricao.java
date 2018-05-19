package pt.up.fc.lc.postagempersistencia.entidades;

public class Subscricao implements Entidade<Subscricao>
{
	private Usuario usuario;
	private Topico topico;
	private boolean favorito;
	
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
	public boolean isFavorito()
	{
		return favorito;
	}
	public void setFavorito(boolean favorito)
	{
		this.favorito = favorito;
	}
	
	public Subscricao()
	{
		this.usuario = null;
		this.topico = null;
		this.favorito = false;
	}
	
	public boolean comparar(Subscricao objeto)
	{
		return ((objeto != null) && (objeto.getUsuario() != null) &&
			   (objeto.getTopico() != null) && (this.usuario != null) &&
		       (this.topico != null) && (this.usuario.comparar(objeto.getUsuario())) &&
		       (this.topico.comparar(objeto.getTopico())));
	}
	
	public String toString()
	{
		return (this.favorito) ? this.topico.getTitulo() + "*" : this.topico.getTitulo();
	}
}