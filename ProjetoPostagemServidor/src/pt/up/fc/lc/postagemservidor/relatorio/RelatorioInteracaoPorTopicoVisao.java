package pt.up.fc.lc.postagemservidor.relatorio;

/**
	Classe da camada de vis�o do relat�rio de intera��o por t�pico.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class RelatorioInteracaoPorTopicoVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	/**
		Cria e inicializa a vis�o do relat�rio de intera��o por t�pico.
	*/
	public RelatorioInteracaoPorTopicoVisao()
	{
		super("Relat�rio de intera��o por t�pico");
		this.relatorioControle = new RelatorioInteracaoPorTopicoControle(this);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordena��o.
	*/
	protected void inicializarTabela()
	{	
		this.adicionarColunas("T�pico", "Mensagens", "Subscritos", "Intera��o");
	}
}