package pt.up.fc.lc.postagemservidor.cadastro;

import java.util.Date;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do cadastro de usuário interno.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class CadastroUsuarioInternoControle extends CadastroInternoControle
{
	private UsuarioDAO usuarioDAO;
	private SubscricaoDAO subscricaoDAO;
	private CadastroUsuarioInternoVisao cadastroUsuarioInternoVisao;
	private Usuario usuario;
	
	/**
		Método getter para obtenção do usuário gerado pelo cadastro.
		
		@return O tópico gerado.
	*/
	public Usuario getUsuario()
	{
		return this.usuario;
	}
	
	/**
		Cria e inicializa o controle de cadastro de usuário interno, definindo o modo
		como inserção, ou seja, os campos iniciarão vazios e todos estarão disponíveis
		para preenchimento.
		
		@param A visão do cadastro de usuário interno.
	*/
	public CadastroUsuarioInternoControle(CadastroUsuarioInternoVisao cadastroUsuarioInternoVisao)
	{
		super(Modo.INCLUSAO);
		this.usuarioDAO = new UsuarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
		this.cadastroUsuarioInternoVisao = cadastroUsuarioInternoVisao;
		this.usuario = new Usuario();
	}
	
	/**
		Cria e inicializa o controle de cadastro de usuário interno, definindo o modo
		como edição, ou seja, os campos serão preenchidos com os dados do usuário do
		parâmetro e o componente do nome de usuário ficará indisponível para edição.
		
		@param A visão do cadastro de usuário interno e o tópico a ser editado.
	*/
	public CadastroUsuarioInternoControle(CadastroUsuarioInternoVisao cadastroUsuarioInternoVisao, Usuario usuario)
	{
		super(Modo.EDICAO);
		this.usuarioDAO = new UsuarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
		this.cadastroUsuarioInternoVisao = cadastroUsuarioInternoVisao;
		this.usuario = usuario;
	}
	
	/**
		Carrega os campos da visão com os dados do usuário logado.
	*/
	public void carregarCampos()
	{
		if (modo == Modo.INCLUSAO)
		{
			this.cadastroUsuarioInternoVisao.setTitle("Incluir usuário");
			this.cadastroUsuarioInternoVisao.definirLimiteSubscricoes(50);
			this.cadastroUsuarioInternoVisao.definirGrupo(Usuario.Grupo.ADMIN);
			this.cadastroUsuarioInternoVisao.definirAtivo(true);
		} else
		{			
			this.cadastroUsuarioInternoVisao.setTitle("Editar usuário");
			this.cadastroUsuarioInternoVisao.definirNomeUsuarioEditavel(false);			
			this.cadastroUsuarioInternoVisao.definirNomeUsuario(this.usuario.getNomeUsuario());
			this.cadastroUsuarioInternoVisao.definirSenha(this.usuario.getSenha());
			this.cadastroUsuarioInternoVisao.definirGrupo(this.usuario.getGrupo());
			this.cadastroUsuarioInternoVisao.definirNomeCompleto(this.usuario.getContato().getNomeCompleto());
			this.cadastroUsuarioInternoVisao.definirEmail(this.usuario.getContato().getEmail());
			this.cadastroUsuarioInternoVisao.definirTelefone(this.usuario.getContato().getTelefone());
			this.cadastroUsuarioInternoVisao.definirDataNascimento(this.usuario.getContato().getDataNascimento());
			this.cadastroUsuarioInternoVisao.definirLimiteSubscricoes(this.usuario.getLimiteSubscricoes());
			this.cadastroUsuarioInternoVisao.definirAtivo(this.usuario.isAtivo());
		}
	}
	
	/**
		Verifica se todos os campos obrigatórios da visão foram
		preenchidos.
	
		@return Se todos os campos foram preenchidos ou não.
	*/
	public boolean tudoPreenchido()
	{
		String nomeUsuario = this.cadastroUsuarioInternoVisao.obterNomeUsuario().trim();
		String senha = this.cadastroUsuarioInternoVisao.obterSenha();
		Usuario.Grupo grupo = this.cadastroUsuarioInternoVisao.obterGrupo();
		String nomeCompleto = this.cadastroUsuarioInternoVisao.obterNomeCompleto().trim();
		String email = this.cadastroUsuarioInternoVisao.obterEmail().trim();
		String telefone = this.cadastroUsuarioInternoVisao.obterTelefone().trim();
		Date dataNascimento = this.cadastroUsuarioInternoVisao.obterDataNascimento();		
		return ((!nomeUsuario.equals("")) && (!senha.equals("")) && (grupo != null) &&
			   (!nomeCompleto.equals("")) && (!email.equals("")) && (!telefone.equals("")) && (dataNascimento != null));		
	}
	
	/**
		Verifica se, no modo de inclusão, o nome de usuário informado
		já existe no sistema.
		
		@return Se o usuário já existe ou não.
	*/
	public boolean usuarioJaExiste()
	{
		return ((this.usuario.getNomeUsuario().equals("")) &&
			   (this.usuarioDAO.obterRegistro(this.cadastroUsuarioInternoVisao.obterNomeUsuario().trim()) != null));
	}
	
	/**
		Verifica se, no modo de edição, o limite de subscrições definido
		ao usuário não é menor do que a quantidade de subscrições já vinculada
		a ele.
		
		@return Se o limite é válido ou não.
	*/
	public boolean limiteSubscricoesEValido()
	{
		return ((this.usuario.getNomeUsuario().equals("")) ||
			   (this.subscricaoDAO.obterLista(this.usuario).size() <= this.cadastroUsuarioInternoVisao.obterLimiteSubscricoes()));
	}
	
	/**
		Cria usuário a partir dos dados nos componentes da visão.
	*/
	public void definirUsuario()
	{		
		this.usuario.setNomeUsuario(this.cadastroUsuarioInternoVisao.obterNomeUsuario().trim());
		this.usuario.setSenha(this.cadastroUsuarioInternoVisao.obterSenha());
		this.usuario.setGrupo(this.cadastroUsuarioInternoVisao.obterGrupo());
		this.usuario.getContato().setNomeCompleto(this.cadastroUsuarioInternoVisao.obterNomeCompleto().trim());
		this.usuario.getContato().setEmail(this.cadastroUsuarioInternoVisao.obterEmail().trim());
		this.usuario.getContato().setTelefone(this.cadastroUsuarioInternoVisao.obterTelefone().trim());
		this.usuario.getContato().setDataNascimento(this.cadastroUsuarioInternoVisao.obterDataNascimento());		
		this.usuario.setLimiteSubscricoes(this.cadastroUsuarioInternoVisao.obterLimiteSubscricoes());		
		this.usuario.setAtivo(this.cadastroUsuarioInternoVisao.obterAtivo());		
	}
}