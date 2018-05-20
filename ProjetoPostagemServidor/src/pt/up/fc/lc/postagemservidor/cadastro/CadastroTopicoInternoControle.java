package pt.up.fc.lc.postagemservidor.cadastro;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

/**
	Classe da camada de controle do cadastro de t�pico interno.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class CadastroTopicoInternoControle extends CadastroInternoControle
{
	private TopicoDAO topicoDAO;
	private ComentarioDAO comentarioDAO;
	private CadastroTopicoInternoVisao cadastroTopicoInternoVisao;
	private Topico topico;
	
	/**
		M�todo getter para obten��o do t�pico gerado pelo cadastro.
		
		@return O t�pico gerado.
	*/
	public Topico getTopico()
	{
		return this.topico;
	}
	
	/**
		Cria e inicializa o controle de cadastro de t�pico interno, definindo o modo
		como inser��o, ou seja, os campos iniciar�o vazios e todos estar�o dispon�veis
		para preenchimento.
		
		@param A vis�o do cadastro de t�pico interno.
	*/
	public CadastroTopicoInternoControle(CadastroTopicoInternoVisao cadastroTopicoInternoVisao)
	{
		super(Modo.INCLUSAO);
		this.topicoDAO = new TopicoDAO();
		this.comentarioDAO = new ComentarioDAO();
		this.cadastroTopicoInternoVisao = cadastroTopicoInternoVisao;
		this.topico = new Topico();
	}
	
	/**
		Cria e inicializa o controle de cadastro de t�pico interno, definindo o modo
		como edi��o, ou seja, os campos ser�o preenchidos com os dados do t�pico do
		par�metro e o componente do t�tulo ficar� indispon�vel para edi��o.
		
		@param A vis�o do cadastro de t�pico interno e o t�pico a ser editado.
	*/
	public CadastroTopicoInternoControle(CadastroTopicoInternoVisao cadastroTopicoInternoVisao, Topico topico)
	{
		super(Modo.EDICAO);
		this.topicoDAO = new TopicoDAO();
		this.comentarioDAO = new ComentarioDAO();
		this.cadastroTopicoInternoVisao = cadastroTopicoInternoVisao;
		this.topico = topico;
	}
	
	/**
		Carrega os campos da vis�o com os dados do usu�rio logado.
	*/
	public void carregarCampos()
	{
		if (this.modo == Modo.INCLUSAO)
		{
			this.cadastroTopicoInternoVisao.setTitle("Incluir t�pico");
			this.cadastroTopicoInternoVisao.definirLimiteMensagens(50);
		} else
		{			
			this.cadastroTopicoInternoVisao.setTitle("Editar usu�rio");
			this.cadastroTopicoInternoVisao.definirTituloEditavel(false);			
			this.cadastroTopicoInternoVisao.definirTitulo(topico.getTitulo());
			this.cadastroTopicoInternoVisao.definirDescricao(topico.getDescricao());
			this.cadastroTopicoInternoVisao.definirLimiteMensagens(topico.getLimiteMensagens());			
		}
	}
	
	/**
		Verifica se todos os campos obrigat�rios da vis�o foram
		preenchidos.
	
		@return Se todos os campos foram preenchidos ou n�o.
	*/
	public boolean tudoPreenchido()
	{
		String titulo = this.cadastroTopicoInternoVisao.obterTitulo().trim();
		String descricao = this.cadastroTopicoInternoVisao.obterDescricao().trim();
		int limiteMensagens = this.cadastroTopicoInternoVisao.obterLimiteMensagens();
		return ((!titulo.equals("")) && (!descricao.equals("") && (limiteMensagens > 0)));		
	}
	
	/**
		Verifica se, no modo de inclus�o, o t�tulo do t�pico informado
		j� existe no sistema.
		
		@return Se o t�pico j� existe ou n�o.
	*/
	public boolean topicoJaExiste()
	{
		return ((this.modo == Modo.INCLUSAO) &&
			   (this.topicoDAO.obterRegistro(this.cadastroTopicoInternoVisao.obterTitulo().trim()) != null));
	}
	
	/**
		Verifica se, no modo de edi��o, o limite de mensagens definido
		ao t�pico n�o � menor do que a quantidade de mensagens j� vinculada
		a ele.
		
		@return Se o limite � v�lido ou n�o.
	*/
	public boolean limiteMensagensEValido()
	{
		return ((this.modo == Modo.INCLUSAO) ||
			   (this.comentarioDAO.obterLista(this.topico).size() <= this.cadastroTopicoInternoVisao.obterLimiteMensagens()));
	}
	
	/**
		Cria t�pico a partir dos dados nos componentes da vis�o.
	*/
	public void definirTopico()
	{		
		this.topico.setTitulo(this.cadastroTopicoInternoVisao.obterTitulo().trim());
		this.topico.setDescricao(this.cadastroTopicoInternoVisao.obterDescricao().trim());
		this.topico.setLimiteMensagens(this.cadastroTopicoInternoVisao.obterLimiteMensagens());		
	}
}