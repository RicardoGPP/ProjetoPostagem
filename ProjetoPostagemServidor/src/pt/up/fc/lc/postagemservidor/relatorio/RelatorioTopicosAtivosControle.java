package pt.up.fc.lc.postagemservidor.relatorio;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

/**
	Classe da camada de controle do relatório de tópicos ativos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class RelatorioTopicosAtivosControle extends RelatorioControle
{
	private TopicoDAO topicoDAO;
	private ComentarioDAO comentarioDAO;
	
	/**
		Cria e inicializa o controle do relatório de tópicos ativos.
		
		@param A visão do relatório de tópicos ativos.
	*/
	public RelatorioTopicosAtivosControle(RelatorioTopicosAtivosVisao relatorioVisao)
	{
		super(relatorioVisao);
		this.topicoDAO = new TopicoDAO();
		this.comentarioDAO = new ComentarioDAO();
	}
	
	/**
		Carrega a tabela do relatório com os dados recuperados na pesquisa.
	*/
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