package pt.up.fc.lc.postagemservidor.relatorio;

/**
	Classe da camada de visão do relatório de mensagens por tópico.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class RelatorioMensagensPorTopicoVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	/**
		Cria e inicializa a visão do relatório de mensagens por tópico.
	*/
	public RelatorioMensagensPorTopicoVisao()
	{
		super("Relatório de mensagens por tópico");
		this.relatorioControle = new RelatorioMensagensPorTopicoControle(this);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordenação.
	*/
	protected void inicializarTabela()
	{	
		this.adicionarColunas("Tópico", "Mensagens", "Mais antiga", "Mais recente");
	}
}