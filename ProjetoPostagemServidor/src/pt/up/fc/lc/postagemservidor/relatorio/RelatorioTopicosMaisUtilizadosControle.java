package pt.up.fc.lc.postagemservidor.relatorio;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

public class RelatorioTopicosMaisUtilizadosControle extends RelatorioControle
{
	public RelatorioTopicosMaisUtilizadosControle(RelatorioVisao relatorioVisao)
	{
		super(relatorioVisao);
	}
	
	public void carregarTabela()
	{	
		TopicoDAO topicoDAO = new TopicoDAO();
		ComentarioDAO comentarioDAO = new ComentarioDAO();	
		Map<Topico, Integer> mensagens = new HashMap<>();
		List<Topico> topicos = topicoDAO.obterLista();
		for (Topico topico : topicos)
			mensagens.put(topico, comentarioDAO.obterLista(topico).size());		
		Collections.sort(topicos, (t1, t2) -> mensagens.get(t2).compareTo(mensagens.get(t1)));		
		this.relatorioVisao.limparTabela();
		for (Topico topico : topicos)
			this.relatorioVisao.adicionarLinha(topico.getTitulo(), mensagens.get(topico));
	}
}