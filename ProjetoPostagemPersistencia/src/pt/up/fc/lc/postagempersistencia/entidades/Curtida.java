package pt.up.fc.lc.postagempersistencia.entidades;

/**
	Classe que representa a curtida para persistência e uso no sistema.
	A curtida é representada por uma referência entre um usuário e um
	comentário.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class Curtida implements Entidade<Curtida>
{
	private Usuario usuario;
	private Comentario comentario;
	
	/**
		Método getter para a obtenção do usuário da curtida.
		
		@return O usuário da curtida.
	*/
	public Usuario getUsuario()
	{
		return usuario;
	}
	
	/**
		Método setter para a definição do usuário da curtida. 
		
		@param O usuário da curtida.
	*/
	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
	
	/**
		Método getter para a obtenção do comentário da curtida.
		
		@return O comentário da curtida.
	*/
	public Comentario getComentario()
	{
		return comentario;
	}
	
	/**
		Método setter para a definição do comentário da curtida. 
		
		@param O comentário da curtida.
	*/
	public void setComentario(Comentario comentario)
	{
		this.comentario = comentario;
	}
	
	/**
		Cria a curtida com todos os campos vazios.
	*/
	public Curtida()
	{
		this.usuario = null;
		this.comentario = null;
	}
	
	/**
		Compara a curtida com outra.
		
		@param Uma outra curtida para comparação.
		@return Se as curtidas são iguais ou não.
	*/
	public boolean comparar(Curtida objeto)
	{
		return ((objeto != null) && (objeto.getUsuario() != null) & (objeto.getComentario() != null) &&
			   (this.usuario != null) && (this.comentario != null) && (this.usuario.comparar(objeto.getUsuario())) &&
			   (this.comentario.comparar(objeto.comentario)));
	}
}