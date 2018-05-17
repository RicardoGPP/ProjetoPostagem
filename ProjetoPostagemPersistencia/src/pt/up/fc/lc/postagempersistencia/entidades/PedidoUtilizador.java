package pt.up.fc.lc.postagempersistencia.entidades;

import java.util.Date;

public class PedidoUtilizador implements Entidade<PedidoUtilizador>
{
	private String nome;
	private String senha;
	private String email;
	private Date dataNascimento;
		
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public String getSenha()
	{
		return senha;
	}
	public void setSenha(String senha)
	{
		this.senha = senha;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public Date getDataNascimento()
	{
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento)
	{
		this.dataNascimento = dataNascimento;
	}

	public PedidoUtilizador()
	{
		this.nome = "";
		this.senha = "";
		this.email = "";
		this.dataNascimento = null;
	}
	
	public boolean comparar(PedidoUtilizador objeto)
	{
		return this.nome.equals(objeto.getNome());
	}
	
	public String toString()
	{
		return this.nome;
	}
}