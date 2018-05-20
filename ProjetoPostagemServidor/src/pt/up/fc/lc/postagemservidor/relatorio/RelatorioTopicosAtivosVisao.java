package pt.up.fc.lc.postagemservidor.relatorio;

/**
	Classe da camada de visão do relatório de tópicos ativos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class RelatorioTopicosAtivosVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	/**
		Cria e inicializa a visão do relatório de tópicos ativos.
	*/
	public RelatorioTopicosAtivosVisao()
	{
		super("Relatório de tópicos ativos");
		this.relatorioControle = new RelatorioTopicosAtivosControle(this);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordenação.
	*/
	protected void inicializarTabela()
	{	
		this.adicionarColunas("Tópico", "Mensagens", "Limite", "Restantes");
	}
}