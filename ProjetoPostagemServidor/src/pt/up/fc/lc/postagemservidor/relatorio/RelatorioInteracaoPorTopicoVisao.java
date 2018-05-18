package pt.up.fc.lc.postagemservidor.relatorio;

public class RelatorioInteracaoPorTopicoVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	public RelatorioInteracaoPorTopicoVisao()
	{
		super("Relatório de interação por tópico");
		this.relatorioControle = new RelatorioInteracaoPorTopicoControle(this);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	protected void inicializarTabela()
	{	
		this.adicionarColunas("Tópico", "Mensagens", "Subscritos", "Interação");
	}
}