package pt.up.fc.lc.postagempersistencia.entidades;

import java.util.Date;

import pt.up.fc.lc.postagempersistencia.util.Formatador;

public class Usuario implements Entidade<Usuario>
{	
	private String nomeUsuario;
	private String senha;
	private Grupo grupo;
	private Contato contato;
	private int limiteSubscricoes;
	private boolean ativo;
	
	public enum Grupo
	{
		ADMIN,
		OTHER
	}
	
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
	public Grupo getGrupo()
	{
		return grupo;
	}
	public void setGrupo(Grupo grupo)
	{
		this.grupo = grupo;
	}
	public Contato getContato()
	{
		return contato;
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
		this.nomeUsuario = "";
		this.senha = "";
		this.grupo = Grupo.OTHER;
		this.contato = new Contato();
		this.limiteSubscricoes = 50;
		this.ativo = false;
	}
	
	public Usuario(PedidoUtilizador pedidoUtilizador)
	{
		this.nomeUsuario = pedidoUtilizador.getNomeUsuario();
		this.senha = pedidoUtilizador.getSenha();
		this.grupo = Grupo.OTHER;
		this.contato = new Contato();
		this.contato.setDataNascimento(pedidoUtilizador.getDataNascimento());
		this.contato.setEmail(pedidoUtilizador.getEmail());
		this.limiteSubscricoes = 50;
		this.ativo = false;
	}
	
	public boolean comparar(Usuario objeto)
	{
		return this.nomeUsuario.equalsIgnoreCase(objeto.getNomeUsuario());
	}
	
	public boolean autenticar(String senha)
	{
		return ((this.senha.equals(senha)) && (this.ativo));
	}

	public String toString()
	{
		return this.nomeUsuario.trim();
	}
	
	public class Contato
	{
		private String nomeCompleto;
		private String email;
		private String telefone;
		private Date dataNascimento;
		
		public String getNomeCompleto()
		{
			return nomeCompleto;
		}
		public void setNomeCompleto(String nomeCompleto)
		{
			this.nomeCompleto = Formatador.formatar(nomeCompleto);
		}
		public String getEmail()
		{
			return email;
		}
		public void setEmail(String email)
		{
			this.email = Formatador.formatar(email);
		}
		public String getTelefone()
		{
			return telefone;
		}
		public void setTelefone(String telefone)
		{
			this.telefone = Formatador.formatar(telefone);
		}
		public Date getDataNascimento()
		{
			return dataNascimento;
		}
		public void setDataNascimento(Date dataNascimento)
		{
			this.dataNascimento = dataNascimento;
		}

		public Contato()
		{
			this.nomeCompleto = "";
			this.email = "";
			this.telefone = "";
			this.dataNascimento = null;
		}
	}
}