package pt.up.fc.lc.postagemservidor.relatorio;

public class RelatorioTopicosAtivosVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	public RelatorioTopicosAtivosVisao()
	{
		super("Relatório de tópicos ativos");
		this.relatorioControle = new RelatorioTopicosAtivosControle(this);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	protected void inicializarTabela()
	{	
		this.adicionarColunas("Tópico", "Mensagens", "Limite", "Restantes");
	}
}