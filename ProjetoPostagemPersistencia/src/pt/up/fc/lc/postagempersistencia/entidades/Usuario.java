package pt.up.fc.lc.postagempersistencia.entidades;

import java.util.Date;

public class Usuario implements Entidade<Usuario>
{	
	private String utilizador;
	private String senha;
	private Grupo grupo;
	private Contacto contacto;
	private int limiteSubscricoes;
	private boolean ativo;
	
	public String getUtilizador()
	{
		return utilizador;
	}
	public void setUtilizador(String utilizador)
	{
		this.utilizador = utilizador;
	}
	public String getSenha()
	{
		return senha;
	}
	public void setSenha(String senha)
	{
		this.senha = senha;
	}
	public Grupo getGrupo()
	{
		return grupo;
	}
	public void setGrupo(Grupo grupo)
	{
		this.grupo = grupo;
	}
	public Contacto getContacto()
	{
		return contacto;
	}
	public int getLimiteSubscricoes()
	{
		return limiteSubscricoes;
	}
	public void setLimiteSubscricoes(int limiteSubscricoes)
	{
		this.limiteSubscricoes = limiteSubscricoes;
	}
	public boolean isAtivo()
	{
		return ativo;
	}
	public void setAtivo(boolean ativo)
	{
		this.ativo = ativo;
	}

	public Usuario()
	{
		this.utilizador = "";
		this.senha = "";
		this.grupo = Grupo.OTHER;
		this.contacto = new Contacto();
		this.limiteSubscricoes = 50;
		this.ativo = false;
	}
	
	public Usuario(PedidoUtilizador pedidoUtilizador)
	{
		this.utilizador = pedidoUtilizador.getNome();
		this.senha = pedidoUtilizador.getSenha();
		this.grupo = Grupo.OTHER;
		this.contacto = new Contacto();
		this.contacto.setDataNascimento(pedidoUtilizador.getDataNascimento());
		this.contacto.setEmail(pedidoUtilizador.getEmail());
		this.limiteSubscricoes = 50;
		this.ativo = false;
	}
	
	public boolean comparar(Usuario objeto)
	{
		return this.utilizador.equalsIgnoreCase(objeto.getUtilizador());
	}
	
	public boolean autenticar(String senha)
	{
		return ((this.senha.equals(senha)) && (this.ativo));
	}

	public String toString()
	{
		return this.utilizador.trim();
	}
	
	public class Contacto
	{
		private String nome;
		private String email;
		private String telefone;
		private Date dataNascimento;
		
		public String getNome()
		{
			return nome;
		}
		public void setNome(String nome)
		{
			this.nome = nome;
		}
		public String getEmail()
		{
			return email;
		}
		public void setEmail(String email)
		{
			this.email = email;
		}
		public String getTelefone()
		{
			return telefone;
		}
		public void setTelefone(String telefone)
		{
			this.telefone = telefone;
		}
		public Date getDataNascimento()
		{
			return dataNascimento;
		}
		public void setDataNascimento(Date dataNascimento)
		{
			this.dataNascimento = dataNascimento;
		}

		public Contacto()
		{
			this.nome = "";
			this.email = "";
			this.telefone = "";
			this.dataNascimento = null;
		}
	}
}