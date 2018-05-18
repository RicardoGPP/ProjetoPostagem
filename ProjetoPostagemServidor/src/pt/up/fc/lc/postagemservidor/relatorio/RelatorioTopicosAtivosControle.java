package pt.up.fc.lc.postagemservidor.relatorio;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

public class RelatorioTopicosAtivosControle extends RelatorioControle
{
	private TopicoDAO topicoDAO;
	private ComentarioDAO comentarioDAO;
	
	public RelatorioTopicosAtivosControle(RelatorioVisao relatorioVisao)
	{
		super(relatorioVisao);
		this.topicoDAO = new TopicoDAO();
		this.comentarioDAO = new ComentarioDAO();
	}
	
	public void carregarTabela()
	{	
		this.relatorioVisao.limparTabela();
		for (Topico topico : this.topicoDAO.obterLista())
		{
			String titulo = topico.getTitulo();
			int mensagens = this.comentarioDAO.obterLista(topico).size();
			int limite = topico.getLimiteMensagens();
			int restantes = limite - mensagens;								
			if (restantes > 0)
				this.relatorioVisao.adicionarLinha(titulo, mensagens, limite, restantes);
		}	
	}
}