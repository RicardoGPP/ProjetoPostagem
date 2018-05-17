package pt.up.fc.lc.postagemservidor.controle;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagemservidor.visao.CadastroTopicoInternoVisao;
import pt.up.fc.lc.postagemservidor.visao.Modo;

public class CadastroTopicoInternoControle
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
		this.topicoDAO = new TopicoDAO();
		this.comentarioDAO = new ComentarioDAO();
		this.cadastroTopicoInternoVisao = cadastroTopicoInternoVisao;
		this.topico = new Topico();
	}
	
	public CadastroTopicoInternoControle(CadastroTopicoInternoVisao cadastroTopicoInternoVisao, Topico topico)
	{
		this.topicoDAO = new TopicoDAO();
		this.comentarioDAO = new ComentarioDAO();
		this.cadastroTopicoInternoVisao = cadastroTopicoInternoVisao;
		this.topico = topico;
	}
	
	public boolean tudoPreenchido()
	{
		String titulo = this.cadastroTopicoInternoVisao.obterTitulo().trim();
		String descricao = this.cadastroTopicoInternoVisao.obterDescricao().trim();		
		return ((!titulo.equals("")) && (!descricao.equals("")));		
	}
	
	public boolean topicoJaExiste()
	{
		return ((this.cadastroTopicoInternoVisao.obterModo() == Modo.INCLUSAO) &&
			   (this.topicoDAO.obterRegistro(this.cadastroTopicoInternoVisao.obterTitulo().trim()) != null));
	}
	
	public boolean limiteMensagensEValido()
	{
		return ((this.cadastroTopicoInternoVisao.obterModo() == Modo.INCLUSAO) ||
			   (this.comentarioDAO.obterLista(this.topico).size() <= this.cadastroTopicoInternoVisao.obterLimiteMensagens()));
	}
	
	public void definirTopico()
	{		
		this.topico.setIdentificador(this.cadastroTopicoInternoVisao.obterTitulo().trim());
		this.topico.setDescricao(this.cadastroTopicoInternoVisao.obterDescricao().trim());
		this.topico.setLimiteMensagens(this.cadastroTopicoInternoVisao.obterLimiteMensagens());		
	}
}