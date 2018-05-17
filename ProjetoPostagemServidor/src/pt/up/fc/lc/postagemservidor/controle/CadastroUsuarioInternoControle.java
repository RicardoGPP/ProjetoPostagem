package pt.up.fc.lc.postagemservidor.controle;

import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Grupo;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;
import pt.up.fc.lc.postagemservidor.visao.CadastroUsuarioInternoVisao;

public class CadastroUsuarioInternoControle
{
	private UsuarioDAO usuarioDAO;
	private SubscricaoDAO subscricaoDAO;
	private CadastroUsuarioInternoVisao cadastroUsuarioInternoVisao;
	private Usuario usuario;
	
	public Usuario getUsuario()
	{
		return this.usuario;
	}
	
	public CadastroUsuarioInternoControle(CadastroUsuarioInternoVisao cadastroUsuarioInternoVisao)
	{
		this.usuarioDAO = new UsuarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
		this.cadastroUsuarioInternoVisao = cadastroUsuarioInternoVisao;
		this.usuario = new Usuario();
	}
	
	public CadastroUsuarioInternoControle(CadastroUsuarioInternoVisao cadastroUsuarioInternoVisao, Usuario usuario)
	{
		this.usuarioDAO = new UsuarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
		this.cadastroUsuarioInternoVisao = cadastroUsuarioInternoVisao;
		this.usuario = usuario;
	}
	
	public boolean tudoPreenchido()
	{
		String nomeUsuario = this.cadastroUsuarioInternoVisao.obterNomeUsuario().trim();
		String senha = this.cadastroUsuarioInternoVisao.obterSenha();
		Grupo grupo = this.cadastroUsuarioInternoVisao.obterGrupo();
		String nomeCompleto = this.cadastroUsuarioInternoVisao.obterNomeCompleto().trim();
		String email = this.cadastroUsuarioInternoVisao.obterEmail().trim();
		String telefone = this.cadastroUsuarioInternoVisao.obterTelefone().trim();
		
		return ((!nomeUsuario.equals("")) && (!senha.equals("")) && (grupo != null) &&
			   (!nomeCompleto.equals("")) && (!email.equals("")) && (!telefone.equals("")));		
	}
	
	public boolean usuarioJaExiste()
	{
		return ((this.usuario.getUtilizador().equals("")) &&
			   (this.usuarioDAO.obterRegistro(this.cadastroUsuarioInternoVisao.obterNomeUsuario().trim()) != null));
	}
	
	public boolean limiteSubscricoesEValido()
	{
		return ((this.usuario.getUtilizador().equals("")) ||
			   (this.subscricaoDAO.obterTopicosSubscritos(this.usuario).size() <= this.cadastroUsuarioInternoVisao.obterLimiteSubscricoes()));
	}
	
	public void definirUsuario()
	{		
		this.usuario.setUtilizador(this.cadastroUsuarioInternoVisao.obterNomeUsuario().trim());
		this.usuario.setSenha(this.cadastroUsuarioInternoVisao.obterSenha());
		this.usuario.setGrupo(this.cadastroUsuarioInternoVisao.obterGrupo());
		this.usuario.getContacto().setNome(this.cadastroUsuarioInternoVisao.obterNomeCompleto().trim());
		this.usuario.getContacto().setEmail(this.cadastroUsuarioInternoVisao.obterEmail().trim());
		this.usuario.getContacto().setTelefone(this.cadastroUsuarioInternoVisao.obterTelefone().trim());
		this.usuario.setLimiteSubscricoes(this.cadastroUsuarioInternoVisao.obterLimiteSubscricoes());
		this.usuario.setValidado(true);
		this.usuario.setAtivo(this.cadastroUsuarioInternoVisao.obterAtivo());		
	}
}