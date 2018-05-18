package pt.up.fc.lc.postagemservidor.relatorio;

public class RelatorioTopicosAtivosVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	public RelatorioTopicosAtivosVisao()
	{
		super("Relat�rio de t�picos ativos");
		this.relatorioControle = new RelatorioTopicosAtivosControle(this);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	protected void inicializarTabela()
	{	
		this.adicionarColunas("T�pico", "Mensagens", "Limite", "Restantes");
	}
}