package pt.up.fc.lc.postagempersistencia.entidades;

import java.util.Date;

import pt.up.fc.lc.postagempersistencia.util.Formatador;

/**
	Classe que representa o pedido de utilizador para persist�ncia e uso
	no sistema.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class PedidoUtilizador implements Entidade<PedidoUtilizador>
{
	private String nomeUsuario;
	private String senha;
	private String email;
	private Date dataNascimento;
		
	/**
		M�todo getter para a obten��o do nome de usu�rio do pedido de
		utilizador.
		
		@return O nome de usu�rio do pedido de utilizador.
	*/
	public String getNomeUsuario()
	{
		return nomeUsuario;
	}
	
	/**
		M�todo setter para a defini��o do nome de usu�rio do pedido
		de utilizador. 
		
		@param O nome de usu�rio do pedido de utilizador.
	*/
	public void setNomeUsuario(String nomeUsuario)
	{
		this.nomeUsuario = Formatador.formatar(nomeUsuario);
	}
	
	/**
		M�todo getter para a obten��o da senha do pedido de utilizador.
		
		@return A senha do pedido de utilizador.
	*/
	public String getSenha()
	{
		return senha;
	}
	
	/**
		M�todo setter para a defini��o da senha do pedido de utilizador. 
		
		@param A senha do pedido de utilizador.
	*/
	public void setSenha(String senha)
	{
		this.senha = Formatador.formatar(senha);
	}
	
	/**
		M�todo getter para a obten��o do e-mail do pedido de utilizador.
		
		@return O e-mail do pedido de utilizador.
	*/
	public String getEmail()
	{
		return email;
	}
	
	/**
		M�todo setter para a defini��o do email do pedido de utilizador. 
		
		@param O e-mail do pedido de utilizador.
	*/
	public void setEmail(String email)
	{
		this.email = Formatador.formatar(email);
	}
	
	/**
		M�todo getter para a obten��o da data de nascimento do
		pedido de utilizador.
		
		@return A data de nascimento do pedido de utilizador.
	*/
	public Date getDataNascimento()
	{
		return dataNascimento;
	}
	
	/**
		M�todo setter para a defini��o da data de nascimento do
		pedido de utilizador. 
		
		@param A data de nascimento no pedido de utilizador.
	*/
	public void setDataNascimento(Date dataNascimento)
	{
		this.dataNascimento = dataNascimento;
	}

	/**
		Cria o pedido de utilizador com todos os campos vazios.
	*/
	public PedidoUtilizador()
	{
		this.nomeUsuario = "";
		this.senha = "";
		this.email = "";
		this.dataNascimento = null;
	}
	
	/**
		Compara o pedido de utilizador com outro com outro.
		
		@param Um outro pedido de utilizador para compara��o.
		@return Se os pedidos de utilizador s�o iguais ou n�o.
	*/
	public boolean comparar(PedidoUtilizador objeto)
	{
		return this.nomeUsuario.equals(objeto.getNomeUsuario());
	}
	
	/**
		Representa o pedido de utilizador com seu respectivo nome
		de usu�rio.
		
		@return A representa��o do pedido de utilizador.
	*/
	public String toString()
	{
		return this.nomeUsuario;
	}
}