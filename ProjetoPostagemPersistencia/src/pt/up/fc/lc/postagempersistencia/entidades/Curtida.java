package pt.up.fc.lc.postagempersistencia.entidades;

/**
	Classe que representa a curtida para persist�ncia e uso no sistema.
	A curtida � representada por uma refer�ncia entre um usu�rio e um
	coment�rio.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class Curtida implements Entidade<Curtida>
{
	private Usuario usuario;
	private Comentario comentario;
	
	/**
		M�todo getter para a obten��o do usu�rio da curtida.
		
		@return O usu�rio da curtida.
	*/
	public Usuario getUsuario()
	{
		return usuario;
	}
	
	/**
		M�todo setter para a defini��o do usu�rio da curtida. 
		
		@param O usu�rio da curtida.
	*/
	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
	
	/**
		M�todo getter para a obten��o do coment�rio da curtida.
		
		@return O coment�rio da curtida.
	*/
	public Comentario getComentario()
	{
		return comentario;
	}
	
	/**
		M�todo setter para a defini��o do coment�rio da curtida. 
		
		@param O coment�rio da curtida.
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
		
		@param Uma outra curtida para compara��o.
		@return Se as curtidas s�o iguais ou n�o.
	*/
	public boolean comparar(Curtida objeto)
	{
		return ((objeto != null) && (objeto.getUsuario() != null) & (objeto.getComentario() != null) &&
			   (this.usuario != null) && (this.comentario != null) && (this.usuario.comparar(objeto.getUsuario())) &&
			   (this.comentario.comparar(objeto.comentario)));
	}
}