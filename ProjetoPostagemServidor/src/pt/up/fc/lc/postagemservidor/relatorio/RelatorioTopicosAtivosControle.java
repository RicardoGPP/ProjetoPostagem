package pt.up.fc.lc.postagemservidor.relatorio;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

/**
	Classe da camada de controle do relat�rio de t�picos ativos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class RelatorioTopicosAtivosControle extends RelatorioControle
{
	private TopicoDAO topicoDAO;
	private ComentarioDAO comentarioDAO;
	
	/**
		Cria e inicializa o controle do relat�rio de t�picos ativos.
		
		@param A vis�o do relat�rio de t�picos ativos.
	*/
	public RelatorioTopicosAtivosControle(RelatorioTopicosAtivosVisao relatorioVisao)
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