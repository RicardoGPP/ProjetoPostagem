package pt.up.fc.lc.postagemservidor.relatorio;

/**
	Classe da camada de visão do relatório de tópicos mais utilizados.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class RelatorioTopicosMaisUtilizadosVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	/**
		Cria e inicializa a visão do relatório de tópicos mais utilizados.
	*/
	public RelatorioTopicosMaisUtilizadosVisao()
	{
		super("Relatório de tópicos mais utilizados");
		this.relatorioControle = new RelatorioTopicosMaisUtilizadosControle(this);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordenação.
	*/
	protected void inicializarTabela()
	{	
		this.adicionarColunas("Tópico", "Mensagens");
	}
}