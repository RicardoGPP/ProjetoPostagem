package pt.up.fc.lc.postagemcliente.movimentacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.CurtidaDAO;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Curtida;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class VisualizacaoFeedControle
{
	private SubscricaoDAO subscricaoDAO;
	private ComentarioDAO comentarioDAO;
	private CurtidaDAO curtidaDAO;
	private VisualizacaoFeedVisao visualizacaoFeedVisao;
	private Usuario logado;
	
	public VisualizacaoFeedControle(VisualizacaoFeedVisao visualizacaoFeedVisao, Usuario logado)
	{
		this.subscricaoDAO = new SubscricaoDAO();
		this.comentarioDAO = new ComentarioDAO();
		this.curtidaDAO = new CurtidaDAO();
		this.logado = logado;
		this.visualizacaoFeedVisao = visualizacaoFeedVisao;
	}
	
	public void carregarLista()
	{
		List<Comentario> comentarios = new ArrayList<>();		
		for (Subscricao subscricao : this.subscricaoDAO.obterLista(this.logado))
			for (Comentario comentario : this.comentarioDAO.obterLista(subscricao.getTopico()))
				comentarios.add(comentario);		
		Collections.sort(comentarios, (c1, c2) -> c2.getData().compareTo(c1.getData()));
		this.visualizacaoFeedVisao.definirComentarios(comentarios);
	}
	
	public void curtirDescurtir()
	{
		Comentario comentario = this.visualizacaoFeedVisao.obterSelecionado();
		if (comentario != null)
		{
			Curtida curtida = new Curtida();
			curtida.setUsuario(this.logado);
			curtida.setComentario(comentario);
			if (this.curtidaDAO.curtiu(this.logado, comentario))
			{
				if (this.curtidaDAO.deletar(curtida))
					this.visualizacaoFeedVisao.definirTextoBotaoCurtir("Curtir");
			} else
			{
				if (this.curtidaDAO.inserir(curtida))
					this.visualizacaoFeedVisao.definirTextoBotaoCurtir("Descurtir");
			}
		}
	}
	
	public void atualizarTextoBotaoCurtir()
	{
		Comentario comentario = this.visualizacaoFeedVisao.obterSelecionado();
		if ((comentario == null) || (!this.curtidaDAO.curtiu(this.logado, comentario)))
			this.visualizacaoFeedVisao.definirTextoBotaoCurtir("Curtir");
		else
			this.visualizacaoFeedVisao.definirTextoBotaoCurtir("Descurtir");
	}
}