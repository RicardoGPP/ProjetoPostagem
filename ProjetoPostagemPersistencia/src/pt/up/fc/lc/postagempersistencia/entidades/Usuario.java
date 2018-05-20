package pt.up.fc.lc.postagempersistencia.entidades;

import java.util.Date;
import pt.up.fc.lc.postagempersistencia.util.Formatador;

/**
	Classe que representa o usuário para persistência e uso no sistema.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
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
		Enumeração que representa os possíveis grupos dos usuários
		do sistema.
		
		@version 1.0
		@author  Ricardo Giovani Piantavinha Perandré,
	         	 Pedro Sobral da Costa
	*/
	public enum Grupo
	{
		ADMIN,
		OTHER
	}
	
	/**
		Método getter para a obtenção do nome de usuário.
		
		@return O nome de usuário.
	*/
	public String getNomeUsuario()
	{
		return nomeUsuario;
	}
	
	/**
		Método setter para a definição do nome de usuário. 
		
		@param O nome de usuário.
	*/
	public void setNomeUsuario(String nomeUsuario)
	{
		this.nomeUsuario = Formatador.formatar(nomeUsuario);
	}
	
	/**
		Método getter para a obtenção da senha do usuário.
	
		@return A senha do usuário.
	*/
	public String getSenha()
	{
		return senha;
	}
	
	/**
		Método setter para a definição da senha do usuário.
		
		@param A senha do usuário.
	*/
	public void setSenha(String senha)
	{
		this.senha = Formatador.formatar(senha);
	}
	
	/**
		Método getter para obtenção do grupo do usuário.
	
		@return O grupo do usuário.
	*/
	public Grupo getGrupo()
	{
		return grupo;
	}
	
	/**
		Método setter para a definição do grupo do usuário.
		
		@param O grupo do usuário.
	*/
	public void setGrupo(Grupo grupo)
	{
		this.grupo = grupo;
	}
	
	/**
		Método getter para a obtenção do objeto de contato do usuário.
		
		@return O objeto de contato do usuário.
	*/
	public Contato getContato()
	{
		return contato;
	}
	
	/**
		Método getter para a obtenção do limite de subscrições do usuário.
	
		@return O limite de subscrições do usuário.
	 */
	public int getLimiteSubscricoes()
	{
		return limiteSubscricoes;
	}
	
	/**
		Método setter para a definição do limite de subscrições do usuário.
	
		@param O limite de subscrições do usuário.
	 */
	public void setLimiteSubscricoes(int limiteSubscricoes)
	{
		this.limiteSubscricoes = limiteSubscricoes;
	}
	
	/**
		Método getter para a verificar se o usuário está ativo ou não.
	
		@return A flag de ativo do usuário.
	 */
	public boolean isAtivo()
	{
		return ativo;
	}
	
	/**
		Método setter para a definição da flag de ativo do usuário.
	
		@param A flag de ativo do usuário.
	 */
	public void setAtivo(boolean ativo)
	{
		this.ativo = ativo;
	}
	
	/**
		Cria um usuário com todos os campos em modo inicial, instanciando
		o seu objeto de contato e definindo o grupo como "OTHER" e o
		limite de subscrições como 50.
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
		Cria um usuário através de um pedido de utilizador, definindo
		os valores do pedido do parâmetro nos respectivos campos do
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
		Compara o usuário com outro.
		
		@param Um outro usuário para comparação.
		@return Se os usuários são iguais ou não.
	*/
	public boolean comparar(Usuario objeto)
	{
		return this.nomeUsuario.equalsIgnoreCase(objeto.getNomeUsuario());
	}
	
	/**
		Compara se a senha fornecida no parâmetro é igual a do usuário e
		se este está ativo.
	
		@param A senha do usuário.
		@return Se a autenticação foi bem-sucedida ou não.
	*/
	public boolean autenticar(String senha)
	{
		return ((this.senha.equals(senha)) && (this.ativo));
	}

	/**
		Representa o usuário com seu respectivo nome.
		
		@return A representação do usuário.
	*/
	public String toString()
	{
		return this.nomeUsuario.trim();
	}
	
	/**
		Classe interna ao usuário que encapsula seus dados
		de contato.
		
		@version 1.0
		@author  Ricardo Giovani Piantavinha Perandré,
			     Pedro Sobral da Costa
	*/
	public class Contato
	{
		private String nomeCompleto;
		private String email;
		private String telefone;
		private Date dataNascimento;
		
		/**
			Método getter para a obtenção do nome completo do usuário.
		
			@return O nome completo do usuário.
		*/
		public String getNomeCompleto()
		{
			return nomeCompleto;
		}
		
		/**
			Método setter para a definição do nome completo do usuário.
			
			@param O nome completo do usuário.
		*/
		public void setNomeCompleto(String nomeCompleto)
		{
			this.nomeCompleto = Formatador.formatar(nomeCompleto);
		}
		
		/**
			Método getter para a obtenção do e-mail do usuário.
	
			@return O e-mail do usuário.
		 */
		public String getEmail()
		{
			return email;
		}
		
		/**
			Método setter para a definição do e-mail do usuário.
			
			@param O e-mail do usuário.
		*/
		public void setEmail(String email)
		{
			this.email = Formatador.formatar(email);
		}
		
		/**
			Método getter para a obtenção do telefone do usuário.
		
			@return O telefone do usuário.
		*/
		public String getTelefone()
		{
			return telefone;
		}
		
		/**
			Método setter para a definição do telefone do usuário.
			
			@param O telefone do usuário.
		*/
		public void setTelefone(String telefone)
		{
			this.telefone = Formatador.formatar(telefone);
		}
		
		/**
			Método getter para a obtenção da data de nascimento do usuário.
		
			@return A data de nascimento usuário.
		*/
		public Date getDataNascimento()
		{
			return dataNascimento;
		}
		
		/**
			Método setter para a definição da data de nascimento do usuário.
			
			@param A data de nascimento do usuário.
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