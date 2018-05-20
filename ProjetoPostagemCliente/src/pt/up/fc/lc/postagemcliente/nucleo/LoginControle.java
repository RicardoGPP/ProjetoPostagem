package pt.up.fc.lc.postagemcliente.nucleo;

import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do login do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class LoginControle
{
	private UsuarioDAO usuarioDAO;
	private LoginVisao loginVisao;
	private Autenticavel autenticavel;
	
	/**
		Cria e inicializa o controle de login.
		
		@param A visão do login.
	*/
	public LoginControle(LoginVisao loginVisao, Autenticavel autenticavel)
	{
		this.usuarioDAO = new UsuarioDAO();
		this.loginVisao = loginVisao;
		this.autenticavel = autenticavel;
	}
	
	/**
		Autentica o usuário.
		
		@return Se a autenticação foi válida ou não.
	*/
	public boolean usuarioFoiAutenticado()
	{
		String nomeUsuario = this.loginVisao.obterNomeUsuario().trim();
		String senha = this.loginVisao.obterSenha();
		Usuario usuario = this.usuarioDAO.obterRegistro(nomeUsuario);			
		if (usuario == null)
			return false;
		else
			return usuario.autenticar(senha);
	}
	
	/**
		Verifica se todos os campos obrigatórios da visão foram
		preenchidos.
	
		@return Se todos os campos foram preenchidos ou não.
	*/
	public boolean tudoPreenchido()
	{
		return ((!this.loginVisao.obterNomeUsuario().trim().equals("")) && (!this.loginVisao.obterSenha().equals("")));
	}
	
	/**
		Define o usuário autenticado no objeto autenticável.
	*/
	public void definirUsuario()
	{
		this.autenticavel.definirUsuario(this.usuarioDAO.obterRegistro(this.loginVisao.obterNomeUsuario().trim()));
	}
	
	/**
		Abre a tela do pedido de utilizador.
	*/
	public void abrirPedidoDeUtilizador()
	{
		new PedidoUtilizadorVisao();
	}
}