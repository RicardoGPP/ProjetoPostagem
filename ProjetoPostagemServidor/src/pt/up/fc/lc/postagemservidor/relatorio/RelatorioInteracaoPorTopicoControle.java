package pt.up.fc.lc.postagemservidor.relatorio;

import java.text.DecimalFormat;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

public class RelatorioInteracaoPorTopicoControle extends RelatorioControle
{
	private TopicoDAO topicoDAO;
	private ComentarioDAO comentarioDAO;
	private SubscricaoDAO subscricaoDAO;
	
	public RelatorioInteracaoPorTopicoControle(RelatorioVisao relatorioVisao)
	{
		super(relatorioVisao);
		this.topicoDAO = new TopicoDAO();
		this.comentarioDAO = new ComentarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
	}
	
	public void carregarTabela()
	{	
		this.relatorioVisao.limparTabela();
		for (Topico topico : this.topicoDAO.obterLista())
		{
			List<Subscricao> subscricoes = this.subscricaoDAO.obterLista(topico);
			List<Comentario> comentarios = this.comentarioDAO.obterLista(topico);			
			int interacao = 0;			
			for (Subscricao subscricao : subscricoes)
				for (Comentario comentario : comentarios)
					if (comentario.getUsuario().comparar(subscricao.getUsuario()))
					{
						interacao++;
						break;
					}
			String titulo = topico.getTitulo();
			int mensagens = comentarios.size();
			int subscritos = subscricoes.size();
			String percentual = ((subscritos == 0) ? "0,00" : (new DecimalFormat("0.00")).format((interacao / subscritos) * 100)) + "%";			
			this.relatorioVisao.adicionarLinha(titulo, mensagens, subscritos, (interacao + " (" + percentual + ")"));
		}	
	}
}
