package pt.up.fc.lc.postagempersistencia.entidades;

public class Curtida implements Entidade<Curtida>
{
	private Usuario usuario;
	private Comentario comentario;
		
	public Usuario getUsuario()
	{
		return usuario;
	}
	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
	public Comentario getComentario()
	{
		return comentario;
	}
	public void setComentario(Comentario comentario)
	{
		this.comentario = comentario;
	}
	
	public Curtida()
	{
		this.usuario = null;
		this.comentario = null;
	}
	
	public boolean comparar(Curtida objeto)
	{
		return ((objeto != null) && (objeto.getUsuario() != null) & (objeto.getComentario() != null) &&
			   (this.usuario != null) && (this.comentario != null) && (this.usuario.comparar(objeto.getUsuario())) &&
			   (this.comentario.comparar(objeto.comentario)));
	}
}