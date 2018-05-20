package pt.up.fc.lc.postagemservidor.relatorio;

/**
	Classe da camada de vis�o do relat�rio de mensagens por t�pico.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class RelatorioMensagensPorTopicoVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	/**
		Cria e inicializa a vis�o do relat�rio de mensagens por t�pico.
	*/
	public RelatorioMensagensPorTopicoVisao()
	{
		super("Relat�rio de mensagens por t�pico");
		this.relatorioControle = new RelatorioMensagensPorTopicoControle(this);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordena��o.
	*/
	protected void inicializarTabela()
	{	
		this.adicionarColunas("T�pico", "Mensagens", "Mais antiga", "Mais recente");
	}
}