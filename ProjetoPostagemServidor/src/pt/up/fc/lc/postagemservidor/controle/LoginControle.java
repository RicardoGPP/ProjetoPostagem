package pt.up.fc.lc.postagemservidor.controle;

import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Grupo;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;
import pt.up.fc.lc.postagemservidor.visao.LoginVisao;

public class LoginControle
{
	private UsuarioDAO usuarioDAO;
	private LoginVisao loginVisao;
	
	public LoginControle(LoginVisao loginVisao)
	{
		this.usuarioDAO = new UsuarioDAO();
		this.loginVisao = loginVisao;
	}
	
	public boolean entradaPermitida()
	{
		String nomeUsuario = this.loginVisao.obterNomeUsuario().trim();
		String senha = this.loginVisao.obterSenha();
			
		if ((nomeUsuario.equalsIgnoreCase("Master")) && (senha.equals("master")))
			return true;
		else
		{
			Usuario usuario = this.usuarioDAO.obterRegistro(nomeUsuario);			
			if ((usuario == null) || (usuario.getGrupo() == Grupo.OTHER))
				return false;
			else
				return usuario.autenticar(senha);
		}
	}
	
	public boolean tudoPreenchido()
	{
		return ((!this.loginVisao.obterNomeUsuario().trim().equals("")) && (!this.loginVisao.obterSenha().equals("")));
	}
	
	public void definirUsuario(Autenticavel autenticacao)
	{
		autenticacao.definirUsuario(this.usuarioDAO.obterRegistro(this.loginVisao.obterNomeUsuario().trim()));
	}
}