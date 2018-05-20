package pt.up.fc.lc.postagempersistencia.entidades;

/**
	Classe que representa a subscri��o para persist�ncia e uso no sistema.
	A subscri��o � representada por uma refer�ncia entre um usu�rio e um
	t�pico.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class Subscricao implements Entidade<Subscricao>
{
	private Usuario usuario;
	private Topico topico;
	private boolean favorito;
	
	/**
	 	M�todo getter para a obten��o do usu�rio da subscri��o.
	 	
		@return O usu�rio da subscri��o.
	*/
	public Usuario getUsuario()
	{
		return usuario;
	}
	
	/**
		M�todo setter para a defini��o do usu�rio da subscri��o.
		
		@param O usu�rio da subscri��o.
	*/
	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
	
	/**
	 	M�todo getter para a obten��o do t�pico da subscri��o.
	 	
		@return O t�pico da subscri��o.
	*/
	public Topico getTopico()
	{
		return topico;
	}
	
	/**
		M�todo setter para a defini��o do t�pico da subscri��o.
		
		@param O t�pico da subscri��o.
	*/
	public void setTopico(Topico topico)
	{
		this.topico = topico;
	}
	
	/**
		M�todo getter para a verificar se o usu�rio favoritou o t�pico ou n�o.
	
		@return A flag de favorito da subscri��o.
	 */
	public boolean isFavorito()
	{
		return favorito;
	}
	
	/**
		M�todo setter para a defini��o da flag de favorito da subscri��o.
		
		@param A flag de favorito da subscri��o.
	*/
	public void setFavorito(boolean favorito)
	{
		this.favorito = favorito;
	}
	
	/**
		Cria a subscri��o com todos os campos vazios.
	*/
	public Subscricao()
	{
		this.usuario = null;
		this.topico = null;
		this.favorito = false;
	}
	
	/**
		Compara a subscri��o com outra.
		
		@param Uma outra subscri��o.
		@return Se as subscri��es s�o iguais ou n�o.
	*/
	public boolean comparar(Subscricao objeto)
	{
		return ((objeto != null) && (objeto.getUsuario() != null) &&
			   (objeto.getTopico() != null) && (this.usuario != null) &&
		       (this.topico != null) && (this.usuario.comparar(objeto.getUsuario())) &&
		       (this.topico.comparar(objeto.getTopico())));
	}
	
	/**
		Representa a subscri��o com o t�tulo do t�pico seguido
		de um aster�sco caso este seja favorito do usu�rio.
		
		@return A representa��o da subscri��o.
	*/
	public String toString()
	{
		return (this.favorito) ? this.topico.getTitulo() + "*" : this.topico.getTitulo();
	}
}