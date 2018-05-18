package pt.up.fc.lc.postagemservidor.relatorio;

public class RelatorioInteracaoPorTopicoVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	public RelatorioInteracaoPorTopicoVisao()
	{
		super("Relat�rio de intera��o por t�pico");
		this.relatorioControle = new RelatorioInteracaoPorTopicoControle(this);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	protected void inicializarTabela()
	{	
		this.adicionarColunas("T�pico", "Mensagens", "Subscritos", "Intera��o");
	}
}