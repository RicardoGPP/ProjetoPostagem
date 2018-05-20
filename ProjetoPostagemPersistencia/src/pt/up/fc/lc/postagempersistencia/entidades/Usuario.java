package pt.up.fc.lc.postagempersistencia.entidades;

import java.util.Date;
import pt.up.fc.lc.postagempersistencia.util.Formatador;

/**
	Classe que representa o usu�rio para persist�ncia e uso no sistema.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class Usuario implements Entidade<Usuario>
{	
	private String nomeUsuario;
	private String senha;
	private Grupo grupo;
	private Contato contato;
	private int limiteSubscricoes;
	private boolean ativo;
	
	/**
		Enumera��o que representa os poss�veis grupos dos usu�rios
		do sistema.
		
		@version 1.0
		@author  Ricardo Giovani Piantavinha Perandr�,
	         	 Pedro
	*/
	public enum Grupo
	{
		ADMIN,
		OTHER
	}
	
	/**
		M�todo getter para a obten��o do nome de usu�rio.
		
		@return O nome de usu�rio.
	*/
	public String getNomeUsuario()
	{
		return nomeUsuario;
	}
	
	/**
		M�todo setter para a defini��o do nome de usu�rio. 
		
		@param O nome de usu�rio.
	*/
	public void setNomeUsuario(String nomeUsuario)
	{
		this.nomeUsuario = Formatador.formatar(nomeUsuario);
	}
	
	/**
		M�todo getter para a obten��o da senha do usu�rio.
	
		@return A senha do usu�rio.
	*/
	public String getSenha()
	{
		return senha;
	}
	
	/**
		M�todo setter para a defini��o da senha do usu�rio.
		
		@param A senha do usu�rio.
	*/
	public void setSenha(String senha)
	{
		this.senha = Formatador.formatar(senha);
	}
	
	/**
		M�todo getter para obten��o do grupo do usu�rio.
	
		@return O grupo do usu�rio.
	*/
	public Grupo getGrupo()
	{
		return grupo;
	}
	
	/**
		M�todo setter para a defini��o do grupo do usu�rio.
		
		@param O grupo do usu�rio.
	*/
	public void setGrupo(Grupo grupo)
	{
		this.grupo = grupo;
	}
	
	/**
		M�todo getter para a obten��o do objeto de contato do usu�rio.
		
		@return O objeto de contato do usu�rio.
	*/
	public Contato getContato()
	{
		return contato;
	}
	
	/**
		M�todo getter para a obten��o do limite de subscri��es do usu�rio.
	
		@return O limite de subscri��es do usu�rio.
	 */
	public int getLimiteSubscricoes()
	{
		return limiteSubscricoes;
	}
	
	/**
		M�todo setter para a defini��o do limite de subscri��es do usu�rio.
	
		@param O limite de subscri��es do usu�rio.
	 */
	public void setLimiteSubscricoes(int limiteSubscricoes)
	{
		this.limiteSubscricoes = limiteSubscricoes;
	}
	
	/**
		M�todo getter para a verificar se o usu�rio est� ativo ou n�o.
	
		@return A flag de ativo do usu�rio.
	 */
	public boolean isAtivo()
	{
		return ativo;
	}
	
	/**
		M�todo setter para a defini��o da flag de ativo do usu�rio.
	
		@param A flag de ativo do usu�rio.
	 */
	public void setAtivo(boolean ativo)
	{
		this.ativo = ativo;
	}
	
	/**
		Cria um usu�rio com todos os campos em modo inicial, instanciando
		o seu objeto de contato e definindo o grupo como "OTHER" e o
		limite de subscri��es como 50.
	*/
	public Usuario()
	{
		this.nomeUsuario = "";
		this.senha = "";
		this.grupo = Grupo.OTHER;
		this.contato = new Contato();
		this.limiteSubscricoes = 50;
		this.ativo = false;
	}
	
	/**
		Cria um usu�rio atrav�s de um pedido de utilizador, definindo
		os valores do pedido do par�metro nos respectivos campos do
		objeto.
		
		@param Um objeto do pedido de utilizador.
	 */
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
	
	/**
		Compara o usu�rio com outro.
		
		@param Um outro usu�rio para compara��o.
		@return Se os usu�rios s�o iguais ou n�o.
	*/
	public boolean comparar(Usuario objeto)
	{
		return this.nomeUsuario.equalsIgnoreCase(objeto.getNomeUsuario());
	}
	
	/**
		Compara se a senha fornecida no par�metro � igual a do usu�rio e
		se este est� ativo.
	
		@param A senha do usu�rio.
		@return Se a autentica��o foi bem-sucedida ou n�o.
	*/
	public boolean autenticar(String senha)
	{
		return ((this.senha.equals(senha)) && (this.ativo));
	}

	/**
		Representa o usu�rio com seu respectivo nome.
		
		@return A representa��o do usu�rio.
	*/
	public String toString()
	{
		return this.nomeUsuario.trim();
	}
	
	/**
		Classe interna ao usu�rio que encapsula seus dados
		de contato.
		
		@version 1.0
		@author  Ricardo Giovani Piantavinha Perandr�,
			     Pedro
	*/
	public class Contato
	{
		private String nomeCompleto;
		private String email;
		private String telefone;
		private Date dataNascimento;
		
		/**
			M�todo getter para a obten��o do nome completo do usu�rio.
		
			@return O nome completo do usu�rio.
		*/
		public String getNomeCompleto()
		{
			return nomeCompleto;
		}
		
		/**
			M�todo setter para a defini��o do nome completo do usu�rio.
			
			@param O nome completo do usu�rio.
		*/
		public void setNomeCompleto(String nomeCompleto)
		{
			this.nomeCompleto = Formatador.formatar(nomeCompleto);
		}
		
		/**
			M�todo getter para a obten��o do e-mail do usu�rio.
	
			@return O e-mail do usu�rio.
		 */
		public String getEmail()
		{
			return email;
		}
		
		/**
			M�todo setter para a defini��o do e-mail do usu�rio.
			
			@param O e-mail do usu�rio.
		*/
		public void setEmail(String email)
		{
			this.email = Formatador.formatar(email);
		}
		
		/**
			M�todo getter para a obten��o do telefone do usu�rio.
		
			@return O telefone do usu�rio.
		*/
		public String getTelefone()
		{
			return telefone;
		}
		
		/**
			M�todo setter para a defini��o do telefone do usu�rio.
			
			@param O telefone do usu�rio.
		*/
		public void setTelefone(String telefone)
		{
			this.telefone = Formatador.formatar(telefone);
		}
		
		/**
			M�todo getter para a obten��o da data de nascimento do usu�rio.
		
			@return A data de nascimento usu�rio.
		*/
		public Date getDataNascimento()
		{
			return dataNascimento;
		}
		
		/**
			M�todo setter para a defini��o da data de nascimento do usu�rio.
			
			@param A data de nascimento do usu�rio.
		*/
		public void setDataNascimento(Date dataNascimento)
		{
			this.dataNascimento = dataNascimento;
		}

		/**
			Cria um objeto de contato com todos os atributos vazios.
		*/
		private Contato()
		{
			this.nomeCompleto = "";
			this.email = "";
			this.telefone = "";
			this.dataNascimento = null;
		}
	}
}