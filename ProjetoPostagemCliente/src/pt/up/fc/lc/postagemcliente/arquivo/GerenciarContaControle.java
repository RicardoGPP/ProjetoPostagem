package pt.up.fc.lc.postagemcliente.arquivo;

import java.util.Date;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;
import pt.up.fc.lc.postagemcliente.nucleo.Autenticavel;
import pt.up.fc.lc.postagemcliente.nucleo.LoginVisao;

/**
	Classe da camada de controle do gerenciamento de conta do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class GerenciarContaControle implements Autenticavel
{
	private UsuarioDAO usuarioDAO;
	private GerenciarContaVisao gerenciarContaVisao;
	private Usuario logado;
	private Usuario autenticado;
	
	/**
		Cria e inicializa o controle de gerenciamento de conta.
		
		@param A visão do gerenciamento de conta e o usuário logado na sessão.
	*/
	public GerenciarContaControle(GerenciarContaVisao gerenciarContaVisao, Usuario logado)
	{
		this.usuarioDAO = new UsuarioDAO();
		this.gerenciarContaVisao = gerenciarContaVisao;
		this.logado = logado;
		this.autenticado = null;
	}

	/**
		Define o usuário recuperado pela autenticação.
	
		@param Um usuário.
	*/
	public void definirUsuario(Usuario usuario)
	{
		this.autenticado = usuario;
	}
	
	/**
		Carrega os campos da visão com os dados do usuário logado.
	*/
	public void carregarCampos()
	{
		this.gerenciarContaVisao.definirSenha(this.logado.getSenha());
		this.gerenciarContaVisao.definirNomeCompleto(this.logado.getContato().getNomeCompleto());
		this.gerenciarContaVisao.definirEmail(this.logado.getContato().getEmail());
		this.gerenciarContaVisao.definirTelefone(this.logado.getContato().getTelefone());
		this.gerenciarContaVisao.definirDataDeNascimento(this.logado.getContato().getDataNascimento());
	}
	
	/**
		Verifica se todos os campos obrigatórios da visão foram
		preenchidos.
	
		@return Se todos os campos foram preenchidos ou não.
	*/
	public boolean tudoPreenchido()
	{
		String senha = this.gerenciarContaVisao.obterSenha();
		String nomeCompleto = this.gerenciarContaVisao.obterNomeCompleto().trim();
		String email = this.gerenciarContaVisao.obterEmail().trim();
		String telefone = this.gerenciarContaVisao.obterTelefone().trim();
		Date dataNascimento = this.gerenciarContaVisao.obterDataNascimento();		
		return ((!senha.equals("")) && (!nomeCompleto.equals("")) && (!email.equals("")) &&
			   (!telefone.equals("")) && (dataNascimento != null));
	}
	
	/**
		Verifica se a senha informada é diferente da senha do usuário
		logado.
		
		@return Se as senhas são diferentes ou não.
	*/
	public boolean senhaEstaDiferente()
	{
		return (!this.gerenciarContaVisao.obterSenha().equals(this.logado.getSenha())); 
	}
	
	/**
		Abre a tela de login e refaz a autenticação do usuário.
		
		@return Se a autenticação foi bem-sucedida ou não.
	*/
	public boolean autenticar()
	{
		new LoginVisao(this, this.logado);
		return (this.autenticado != null);
	}
	
	/**
		Salva os novos dados do usuário no arquivo.
		
		@return Se conseguiu salvar ou não.
	*/
	public boolean salvar()
	{
		Usuario usuario = this.usuarioDAO.obterRegistro(this.logado.getNomeUsuario());		
		usuario.setSenha(this.gerenciarContaVisao.obterSenha());
		usuario.getContato().setNomeCompleto(this.gerenciarContaVisao.obterNomeCompleto().trim());
		usuario.getContato().setEmail(this.gerenciarContaVisao.obterEmail().trim());
		usuario.getContato().setTelefone(this.gerenciarContaVisao.obterTelefone().trim());
		usuario.getContato().setDataNascimento(this.gerenciarContaVisao.obterDataNascimento());		
		if (this.usuarioDAO.editar(usuario))
		{
			this.logado.setSenha(usuario.getSenha());
			this.logado.getContato().setNomeCompleto(usuario.getContato().getNomeCompleto());
			this.logado.getContato().setEmail(usuario.getContato().getEmail());
			this.logado.getContato().setTelefone(usuario.getContato().getTelefone());
			this.logado.getContato().setDataNascimento(usuario.getContato().getDataNascimento());
			return true;
		}
		return false;
	}
}