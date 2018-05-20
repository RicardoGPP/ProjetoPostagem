package pt.up.fc.lc.postagemservidor.cadastro;

import java.util.Date;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do cadastro de usu�rio interno.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class CadastroUsuarioInternoControle extends CadastroInternoControle
{
	private UsuarioDAO usuarioDAO;
	private SubscricaoDAO subscricaoDAO;
	private CadastroUsuarioInternoVisao cadastroUsuarioInternoVisao;
	private Usuario usuario;
	
	/**
		M�todo getter para obten��o do usu�rio gerado pelo cadastro.
		
		@return O t�pico gerado.
	*/
	public Usuario getUsuario()
	{
		return this.usuario;
	}
	
	/**
		Cria e inicializa o controle de cadastro de usu�rio interno, definindo o modo
		como inser��o, ou seja, os campos iniciar�o vazios e todos estar�o dispon�veis
		para preenchimento.
		
		@param A vis�o do cadastro de usu�rio interno.
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
		Cria e inicializa o controle de cadastro de usu�rio interno, definindo o modo
		como edi��o, ou seja, os campos ser�o preenchidos com os dados do usu�rio do
		par�metro e o componente do nome de usu�rio ficar� indispon�vel para edi��o.
		
		@param A vis�o do cadastro de usu�rio interno e o t�pico a ser editado.
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
		Carrega os campos da vis�o com os dados do usu�rio logado.
	*/
	public void carregarCampos()
	{
		if (modo == Modo.INCLUSAO)
		{
			this.cadastroUsuarioInternoVisao.setTitle("Incluir usu�rio");
			this.cadastroUsuarioInternoVisao.definirLimiteSubscricoes(50);
			this.cadastroUsuarioInternoVisao.definirGrupo(Usuario.Grupo.ADMIN);
			this.cadastroUsuarioInternoVisao.definirAtivo(true);
		} else
		{			
			this.cadastroUsuarioInternoVisao.setTitle("Editar usu�rio");
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
		Verifica se todos os campos obrigat�rios da vis�o foram
		preenchidos.
	
		@return Se todos os campos foram preenchidos ou n�o.
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
		Verifica se, no modo de inclus�o, o nome de usu�rio informado
		j� existe no sistema.
		
		@return Se o usu�rio j� existe ou n�o.
	*/
	public boolean usuarioJaExiste()
	{
		return ((this.usuario.getNomeUsuario().equals("")) &&
			   (this.usuarioDAO.obterRegistro(this.cadastroUsuarioInternoVisao.obterNomeUsuario().trim()) != null));
	}
	
	/**
		Verifica se, no modo de edi��o, o limite de subscri��es definido
		ao usu�rio n�o � menor do que a quantidade de subscri��es j� vinculada
		a ele.
		
		@return Se o limite � v�lido ou n�o.
	*/
	public boolean limiteSubscricoesEValido()
	{
		return ((this.usuario.getNomeUsuario().equals("")) ||
			   (this.subscricaoDAO.obterLista(this.usuario).size() <= this.cadastroUsuarioInternoVisao.obterLimiteSubscricoes()));
	}
	
	/**
		Cria usu�rio a partir dos dados nos componentes da vis�o.
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