package pt.up.fc.lc.postagemservidor.relatorio;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

/**
Classe da camada de controle do relat�rio de t�picos mais utilizados.

@version 1.0
@author  Ricardo Giovani Piantavinha Perandr�,
         Pedro
*/
public class RelatorioTopicosMaisUtilizadosControle extends RelatorioControle
{
	private TopicoDAO topicoDAO;
	private ComentarioDAO comentarioDAO;
	
	/**
		Cria e inicializa o controle do relat�rio de t�picos mais utilizados.
		
		@param A vis�o do relat�rio de t�picos mais utilizados.
	*/
	public RelatorioTopicosMaisUtilizadosControle(RelatorioTopicosMaisUtilizadosVisao relatorioVisao)
	{
		super(relatorioVisao);
		this.topicoDAO = new TopicoDAO();
		this.comentarioDAO = new ComentarioDAO();
	}
	
	/**
		Carrega a tabela do relat�rio com os dados recuperados na pesquisa.
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