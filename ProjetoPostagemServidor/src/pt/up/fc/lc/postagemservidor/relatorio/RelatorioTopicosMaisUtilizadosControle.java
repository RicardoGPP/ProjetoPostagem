package pt.up.fc.lc.postagemservidor.relatorio;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

/**
Classe da camada de controle do relatório de tópicos mais utilizados.

@version 1.0
@author  Ricardo Giovani Piantavinha Perandré,
         Pedro
*/
public class RelatorioTopicosMaisUtilizadosControle extends RelatorioControle
{
	private TopicoDAO topicoDAO;
	private ComentarioDAO comentarioDAO;
	
	/**
		Cria e inicializa o controle do relatório de tópicos mais utilizados.
		
		@param A visão do relatório de interação por tópico.
	*/
	public RelatorioTopicosMaisUtilizadosControle(RelatorioVisao relatorioVisao)
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
		Map<Topico, Integer> mensagens = new HashMap<>();
		List<Topico> topicos = this.topicoDAO.obterLista();
		for (Topico topico : topicos)
			mensagens.put(topico, this.comentarioDAO.obterLista(topico).size());		
		Collections.sort(topicos, (t1, t2) -> mensagens.get(t2).compareTo(mensagens.get(t1)));		
		this.relatorioVisao.limparTabela();
		for (Topico topico : topicos)
			this.relatorioVisao.adicionarLinha(topico.getTitulo(), mensagens.get(topico));
	}
}