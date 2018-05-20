package pt.up.fc.lc.postagempersistencia.entidades;

import java.util.Date;

import pt.up.fc.lc.postagempersistencia.util.Formatador;

/**
	Classe que representa o pedido de utilizador para persistência e uso
	no sistema.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class PedidoUtilizador implements Entidade<PedidoUtilizador>
{
	private String nomeUsuario;
	private String senha;
	private String email;
	private Date dataNascimento;
		
	/**
		Método getter para a obtenção do nome de usuário do pedido de
		utilizador.
		
		@return O nome de usuário do pedido de utilizador.
	*/
	public String getNomeUsuario()
	{
		return nomeUsuario;
	}
	
	/**
		Método setter para a definição do nome de usuário do pedido
		de utilizador. 
		
		@param O nome de usuário do pedido de utilizador.
	*/
	public void setNomeUsuario(String nomeUsuario)
	{
		this.nomeUsuario = Formatador.formatar(nomeUsuario);
	}
	
	/**
		Método getter para a obtenção da senha do pedido de utilizador.
		
		@return A senha do pedido de utilizador.
	*/
	public String getSenha()
	{
		return senha;
	}
	
	/**
		Método setter para a definição da senha do pedido de utilizador. 
		
		@param A senha do pedido de utilizador.
	*/
	public void setSenha(String senha)
	{
		this.senha = Formatador.formatar(senha);
	}
	
	/**
		Método getter para a obtenção do e-mail do pedido de utilizador.
		
		@return O e-mail do pedido de utilizador.
	*/
	public String getEmail()
	{
		return email;
	}
	
	/**
		Método setter para a definição do email do pedido de utilizador. 
		
		@param O e-mail do pedido de utilizador.
	*/
	public void setEmail(String email)
	{
		this.email = Formatador.formatar(email);
	}
	
	/**
		Método getter para a obtenção da data de nascimento do
		pedido de utilizador.
		
		@return A data de nascimento do pedido de utilizador.
	*/
	public Date getDataNascimento()
	{
		return dataNascimento;
	}
	
	/**
		Método setter para a definição da data de nascimento do
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
		
		@param Um outro pedido de utilizador para comparação.
		@return Se os pedidos de utilizador são iguais ou não.
	*/
	public boolean comparar(PedidoUtilizador objeto)
	{
		return this.nomeUsuario.equals(objeto.getNomeUsuario());
	}
	
	/**
		Representa o pedido de utilizador com seu respectivo nome
		de usuário.
		
		@return A representação do pedido de utilizador.
	*/
	public String toString()
	{
		return this.nomeUsuario;
	}
}