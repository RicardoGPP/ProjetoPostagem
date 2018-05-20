package pt.up.fc.lc.postagemservidor.cadastro;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

/**
	Classe da camada de controle do cadastro de tópico interno.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class CadastroTopicoInternoControle extends CadastroInternoControle
{
	private TopicoDAO topicoDAO;
	private ComentarioDAO comentarioDAO;
	private CadastroTopicoInternoVisao cadastroTopicoInternoVisao;
	private Topico topico;
	
	/**
		Método getter para obtenção do tópico gerado pelo cadastro.
		
		@return O tópico gerado.
	*/
	public Topico getTopico()
	{
		return this.topico;
	}
	
	/**
		Cria e inicializa o controle de cadastro de tópico interno, definindo o modo
		como inserção, ou seja, os campos iniciarão vazios e todos estarão disponíveis
		para preenchimento.
		
		@param A visão do cadastro de tópico interno.
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
		Cria e inicializa o controle de cadastro de tópico interno, definindo o modo
		como edição, ou seja, os campos serão preenchidos com os dados do tópico do
		parâmetro e o componente do título ficará indisponível para edição.
		
		@param A visão do cadastro de tópico interno e o tópico a ser editado.
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
		Carrega os campos da visão com os dados do usuário logado.
	*/
	public void carregarCampos()
	{
		if (this.modo == Modo.INCLUSAO)
		{
			this.cadastroTopicoInternoVisao.setTitle("Incluir tópico");
			this.cadastroTopicoInternoVisao.definirLimiteMensagens(50);
		} else
		{			
			this.cadastroTopicoInternoVisao.setTitle("Editar usuário");
			this.cadastroTopicoInternoVisao.definirTituloEditavel(false);			
			this.cadastroTopicoInternoVisao.definirTitulo(topico.getTitulo());
			this.cadastroTopicoInternoVisao.definirDescricao(topico.getDescricao());
			this.cadastroTopicoInternoVisao.definirLimiteMensagens(topico.getLimiteMensagens());			
		}
	}
	
	/**
		Verifica se todos os campos obrigatórios da visão foram
		preenchidos.
	
		@return Se todos os campos foram preenchidos ou não.
	*/
	public boolean tudoPreenchido()
	{
		String titulo = this.cadastroTopicoInternoVisao.obterTitulo().trim();
		String descricao = this.cadastroTopicoInternoVisao.obterDescricao().trim();
		int limiteMensagens = this.cadastroTopicoInternoVisao.obterLimiteMensagens();
		return ((!titulo.equals("")) && (!descricao.equals("") && (limiteMensagens > 0)));		
	}
	
	/**
		Verifica se, no modo de inclusão, o título do tópico informado
		já existe no sistema.
		
		@return Se o tópico já existe ou não.
	*/
	public boolean topicoJaExiste()
	{
		return ((this.modo == Modo.INCLUSAO) &&
			   (this.topicoDAO.obterRegistro(this.cadastroTopicoInternoVisao.obterTitulo().trim()) != null));
	}
	
	/**
		Verifica se, no modo de edição, o limite de mensagens definido
		ao tópico não é menor do que a quantidade de mensagens já vinculada
		a ele.
		
		@return Se o limite é válido ou não.
	*/
	public boolean limiteMensagensEValido()
	{
		return ((this.modo == Modo.INCLUSAO) ||
			   (this.comentarioDAO.obterLista(this.topico).size() <= this.cadastroTopicoInternoVisao.obterLimiteMensagens()));
	}
	
	/**
		Cria tópico a partir dos dados nos componentes da visão.
	*/
	public void definirTopico()
	{		
		this.topico.setTitulo(this.cadastroTopicoInternoVisao.obterTitulo().trim());
		this.topico.setDescricao(this.cadastroTopicoInternoVisao.obterDescricao().trim());
		this.topico.setLimiteMensagens(this.cadastroTopicoInternoVisao.obterLimiteMensagens());		
	}
}