package pt.up.fc.lc.postagemservidor.relatorio;

public class RelatorioMensagensPorTopicoVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	public RelatorioMensagensPorTopicoVisao()
	{
		super("Relatório de mensagens por tópico");
		this.relatorioControle = new RelatorioMensagensPorTopicoControle(this);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	protected void inicializarTabela()
	{	
		this.adicionarColunas("Tópico", "Mensagens", "Mais antiga", "Mais recente");
	}
}