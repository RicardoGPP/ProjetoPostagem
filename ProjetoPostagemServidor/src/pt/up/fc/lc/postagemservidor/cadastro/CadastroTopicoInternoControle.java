package pt.up.fc.lc.postagemservidor.cadastro;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

public class CadastroTopicoInternoControle extends CadastroInternoControle
{
	private TopicoDAO topicoDAO;
	private ComentarioDAO comentarioDAO;
	private CadastroTopicoInternoVisao cadastroTopicoInternoVisao;
	private Topico topico;
	
	public Topico getTopico()
	{
		return this.topico;
	}
	
	public CadastroTopicoInternoControle(CadastroTopicoInternoVisao cadastroTopicoInternoVisao)
	{
		super(Modo.INCLUSAO);
		this.topicoDAO = new TopicoDAO();
		this.comentarioDAO = new ComentarioDAO();
		this.cadastroTopicoInternoVisao = cadastroTopicoInternoVisao;
		this.topico = new Topico();
	}
	
	public CadastroTopicoInternoControle(CadastroTopicoInternoVisao cadastroTopicoInternoVisao, Topico topico)
	{
		super(Modo.EDICAO);
		this.topicoDAO = new TopicoDAO();
		this.comentarioDAO = new ComentarioDAO();
		this.cadastroTopicoInternoVisao = cadastroTopicoInternoVisao;
		this.topico = topico;
	}
	
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
			this.cadastroTopicoInternoVisao.definirTitulo(topico.getIdentificador());
			this.cadastroTopicoInternoVisao.definirDescricao(topico.getDescricao());
			this.cadastroTopicoInternoVisao.definirLimiteMensagens(topico.getLimiteMensagens());			
		}
	}
	
	public boolean tudoPreenchido()
	{
		String titulo = this.cadastroTopicoInternoVisao.obterTitulo().trim();
		String descricao = this.cadastroTopicoInternoVisao.obterDescricao().trim();		
		return ((!titulo.equals("")) && (!descricao.equals("")));		
	}
	
	public boolean topicoJaExiste()
	{
		return ((this.modo == Modo.INCLUSAO) &&
			   (this.topicoDAO.obterRegistro(this.cadastroTopicoInternoVisao.obterTitulo().trim()) != null));
	}
	
	public boolean limiteMensagensEValido()
	{
		return ((this.modo == Modo.INCLUSAO) ||
			   (this.comentarioDAO.obterLista(this.topico).size() <= this.cadastroTopicoInternoVisao.obterLimiteMensagens()));
	}
	
	public void definirTopico()
	{		
		this.topico.setIdentificador(this.cadastroTopicoInternoVisao.obterTitulo().trim());
		this.topico.setDescricao(this.cadastroTopicoInternoVisao.obterDescricao().trim());
		this.topico.setLimiteMensagens(this.cadastroTopicoInternoVisao.obterLimiteMensagens());		
	}
}