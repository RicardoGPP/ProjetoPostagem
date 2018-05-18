package pt.up.fc.lc.postagemservidor.arquivo;

import java.util.Date;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;
import pt.up.fc.lc.postagemservidor.nucleo.Autenticavel;
import pt.up.fc.lc.postagemservidor.nucleo.LoginVisao;

public class GerenciarContaControle implements Autenticavel
{
	private UsuarioDAO usuarioDAO;
	private GerenciarContaVisao gerenciarContaVisao;
	private Usuario logado;
	private Usuario autenticado;
	
	public GerenciarContaControle(GerenciarContaVisao gerenciarContaVisao, Usuario logado)
	{
		this.usuarioDAO = new UsuarioDAO();
		this.gerenciarContaVisao = gerenciarContaVisao;
		this.logado = logado;
		this.autenticado = null;
	}

	public void definirUsuario(Usuario usuario)
	{
		this.autenticado = usuario;
	}
	
	public void carregarCampos()
	{
		this.gerenciarContaVisao.definirSenha(this.logado.getSenha());
		this.gerenciarContaVisao.definirNomeCompleto(this.logado.getContato().getNomeCompleto());
		this.gerenciarContaVisao.definirEmail(this.logado.getContato().getEmail());
		this.gerenciarContaVisao.definirTelefone(this.logado.getContato().getTelefone());
		this.gerenciarContaVisao.definirDataDeNascimento(this.logado.getContato().getDataNascimento());
	}
	
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
	
	public boolean senhaEstaDiferente()
	{
		return (!this.gerenciarContaVisao.obterSenha().equals(this.logado.getSenha())); 
	}
	
	public boolean autenticar()
	{
		new LoginVisao(this, this.logado);
		return (this.autenticado != null);
	}
	
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