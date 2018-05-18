package pt.up.fc.lc.postagemservidor.cadastro;

import java.util.Date;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Grupo;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class CadastroUsuarioInternoControle extends CadastroInternoControle
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
		super(Modo.INCLUSAO);
		this.usuarioDAO = new UsuarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
		this.cadastroUsuarioInternoVisao = cadastroUsuarioInternoVisao;
		this.usuario = new Usuario();
	}
	
	public CadastroUsuarioInternoControle(CadastroUsuarioInternoVisao cadastroUsuarioInternoVisao, Usuario usuario)
	{
		super(Modo.EDICAO);
		this.usuarioDAO = new UsuarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
		this.cadastroUsuarioInternoVisao = cadastroUsuarioInternoVisao;
		this.usuario = usuario;
	}
	
	public void carregarCampos()
	{
		if (modo == Modo.INCLUSAO)
		{
			this.cadastroUsuarioInternoVisao.setTitle("Incluir usuário");
			this.cadastroUsuarioInternoVisao.definirLimiteSubscricoes(50);
			this.cadastroUsuarioInternoVisao.definirGrupo(Grupo.ADMIN);
			this.cadastroUsuarioInternoVisao.definirAtivo(true);
		} else
		{			
			this.cadastroUsuarioInternoVisao.setTitle("Editar usuário");
			this.cadastroUsuarioInternoVisao.definirNomeUsuarioEditavel(false);			
			this.cadastroUsuarioInternoVisao.definirNomeUsuario(this.usuario.getUtilizador());
			this.cadastroUsuarioInternoVisao.definirSenha(this.usuario.getSenha());
			this.cadastroUsuarioInternoVisao.definirGrupo(this.usuario.getGrupo());
			this.cadastroUsuarioInternoVisao.definirNomeCompleto(this.usuario.getContacto().getNome());
			this.cadastroUsuarioInternoVisao.definirEmail(this.usuario.getContacto().getEmail());
			this.cadastroUsuarioInternoVisao.definirTelefone(this.usuario.getContacto().getTelefone());
			this.cadastroUsuarioInternoVisao.definirDataNascimento(this.usuario.getContacto().getDataNascimento());
			this.cadastroUsuarioInternoVisao.definirLimiteSubscricoes(this.usuario.getLimiteSubscricoes());
			this.cadastroUsuarioInternoVisao.definirAtivo(this.usuario.isAtivo());
		}
	}
	
	public boolean tudoPreenchido()
	{
		String nomeUsuario = this.cadastroUsuarioInternoVisao.obterNomeUsuario().trim();
		String senha = this.cadastroUsuarioInternoVisao.obterSenha();
		Grupo grupo = this.cadastroUsuarioInternoVisao.obterGrupo();
		String nomeCompleto = this.cadastroUsuarioInternoVisao.obterNomeCompleto().trim();
		String email = this.cadastroUsuarioInternoVisao.obterEmail().trim();
		String telefone = this.cadastroUsuarioInternoVisao.obterTelefone().trim();
		Date dataNascimento = this.cadastroUsuarioInternoVisao.obterDataNascimento();
		
		return ((!nomeUsuario.equals("")) && (!senha.equals("")) && (grupo != null) &&
			   (!nomeCompleto.equals("")) && (!email.equals("")) && (!telefone.equals("")) && (dataNascimento != null));		
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
		this.usuario.getContacto().setDataNascimento(this.cadastroUsuarioInternoVisao.obterDataNascimento());		
		this.usuario.setLimiteSubscricoes(this.cadastroUsuarioInternoVisao.obterLimiteSubscricoes());		
		this.usuario.setAtivo(this.cadastroUsuarioInternoVisao.obterAtivo());		
	}
}