package pt.up.fc.lc.postagempersistencia.entidades;

import java.util.Date;

import pt.up.fc.lc.postagempersistencia.util.Formatador;

public class PedidoUtilizador implements Entidade<PedidoUtilizador>
{
	private String nomeUsuario;
	private String senha;
	private String email;
	private Date dataNascimento;
		
	public String getNomeUsuario()
	{
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario)
	{
		this.nomeUsuario = Formatador.formatar(nomeUsuario);
	}
	public String getSenha()
	{
		return senha;
	}
	public void setSenha(String senha)
	{
		this.senha = Formatador.formatar(senha);
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = Formatador.formatar(email);
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
		this.nomeUsuario = "";
		this.senha = "";
		this.email = "";
		this.dataNascimento = null;
	}
	
	public boolean comparar(PedidoUtilizador objeto)
	{
		return this.nomeUsuario.equals(objeto.getNomeUsuario());
	}
	
	public String toString()
	{
		return this.nomeUsuario;
	}
}