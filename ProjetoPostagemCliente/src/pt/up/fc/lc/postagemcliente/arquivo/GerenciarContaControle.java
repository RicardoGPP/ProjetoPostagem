package pt.up.fc.lc.postagemcliente.arquivo;

import java.util.Date;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;
import pt.up.fc.lc.postagemcliente.nucleo.Autenticavel;
import pt.up.fc.lc.postagemcliente.nucleo.LoginVisao;

/**
	Classe da camada de controle do gerenciamento de conta do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class GerenciarContaControle implements Autenticavel
{
	private UsuarioDAO usuarioDAO;
	private GerenciarContaVisao gerenciarContaVisao;
	private Usuario logado;
	private Usuario autenticado;
	
	/**
		Cria e inicializa o controle de gerenciamento de conta.
		
		@param A vis�o do gerenciamento de conta e o usu�rio logado na sess�o.
	*/
	public GerenciarContaControle(GerenciarContaVisao gerenciarContaVisao, Usuario logado)
	{
		this.usuarioDAO = new UsuarioDAO();
		this.gerenciarContaVisao = gerenciarContaVisao;
		this.logado = logado;
		this.autenticado = null;
	}

	/**
		Define o usu�rio recuperado pela autentica��o.
	
		@param Um usu�rio.
	*/
	public void definirUsuario(Usuario usuario)
	{
		this.autenticado = usuario;
	}
	
	/**
		Carrega os campos da vis�o com os dados do usu�rio logado.
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
		Verifica se todos os campos obrigat�rios da vis�o foram
		preenchidos.
	
		@return Se todos os campos foram preenchidos ou n�o.
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
		Verifica se a senha informada � diferente da senha do usu�rio
		logado.
		
		@return Se as senhas s�o diferentes ou n�o.
	*/
	public boolean senhaEstaDiferente()
	{
		return (!this.gerenciarContaVisao.obterSenha().equals(this.logado.getSenha())); 
	}
	
	/**
		Abre a tela de login e refaz a autentica��o do usu�rio.
		
		@return Se a autentica��o foi bem-sucedida ou n�o.
	*/
	public boolean autenticar()
	{
		new LoginVisao(this, this.logado);
		return (this.autenticado != null);
	}
	
	/**
		Salva os novos dados do usu�rio no arquivo.
		
		@return Se conseguiu salvar ou n�o.
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