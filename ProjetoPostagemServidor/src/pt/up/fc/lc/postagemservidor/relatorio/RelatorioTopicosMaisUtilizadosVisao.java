package pt.up.fc.lc.postagemservidor.relatorio;

public class RelatorioTopicosMaisUtilizadosVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	public RelatorioTopicosMaisUtilizadosVisao()
	{
		super("Relatório de tópicos mais utilizados");
		this.relatorioControle = new RelatorioTopicosMaisUtilizadosControle(this);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	protected void inicializarTabela()
	{	
		this.adicionarColunas("Tópico", "Mensagens");
	}
}