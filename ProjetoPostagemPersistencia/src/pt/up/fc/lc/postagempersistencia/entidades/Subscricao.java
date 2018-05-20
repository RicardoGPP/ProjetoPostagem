package pt.up.fc.lc.postagempersistencia.entidades;

/**
	Classe que representa a subscrição para persistência e uso no sistema.
	A subscrição é representada por uma referência entre um usuário e um
	tópico.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class Subscricao implements Entidade<Subscricao>
{
	private Usuario usuario;
	private Topico topico;
	private boolean favorito;
	
	/**
	 	Método getter para a obtenção do usuário da subscrição.
	 	
		@return O usuário da subscrição.
	*/
	public Usuario getUsuario()
	{
		return usuario;
	}
	
	/**
		Método setter para a definição do usuário da subscrição.
		
		@param O usuário da subscrição.
	*/
	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
	
	/**
	 	Método getter para a obtenção do tópico da subscrição.
	 	
		@return O tópico da subscrição.
	*/
	public Topico getTopico()
	{
		return topico;
	}
	
	/**
		Método setter para a definição do tópico da subscrição.
		
		@param O tópico da subscrição.
	*/
	public void setTopico(Topico topico)
	{
		this.topico = topico;
	}
	
	/**
		Método getter para a verificar se o usuário favoritou o tópico ou não.
	
		@return A flag de favorito da subscrição.
	 */
	public boolean isFavorito()
	{
		return favorito;
	}
	
	/**
		Método setter para a definição da flag de favorito da subscrição.
		
		@param A flag de favorito da subscrição.
	*/
	public void setFavorito(boolean favorito)
	{
		this.favorito = favorito;
	}
	
	/**
		Cria a subscrição com todos os campos vazios.
	*/
	public Subscricao()
	{
		this.usuario = null;
		this.topico = null;
		this.favorito = false;
	}
	
	/**
		Compara a subscrição com outra.
		
		@param Uma outra subscrição.
		@return Se as subscrições são iguais ou não.
	*/
	public boolean comparar(Subscricao objeto)
	{
		return ((objeto != null) && (objeto.getUsuario() != null) &&
			   (objeto.getTopico() != null) && (this.usuario != null) &&
		       (this.topico != null) && (this.usuario.comparar(objeto.getUsuario())) &&
		       (this.topico.comparar(objeto.getTopico())));
	}
	
	/**
		Representa a subscrição com o título do tópico seguido
		de um asterísco caso este seja favorito do usuário.
		
		@return A representação da subscrição.
	*/
	public String toString()
	{
		return (this.favorito) ? this.topico.getTitulo() + "*" : this.topico.getTitulo();
	}
}