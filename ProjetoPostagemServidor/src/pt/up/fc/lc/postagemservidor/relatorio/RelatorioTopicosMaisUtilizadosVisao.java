package pt.up.fc.lc.postagemservidor.relatorio;

/**
	Classe da camada de vis�o do relat�rio de t�picos mais utilizados.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class RelatorioTopicosMaisUtilizadosVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	/**
		Cria e inicializa a vis�o do relat�rio de t�picos mais utilizados.
	*/
	public RelatorioTopicosMaisUtilizadosVisao()
	{
		super("Relat�rio de t�picos mais utilizados");
		this.relatorioControle = new RelatorioTopicosMaisUtilizadosControle(this);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordena��o.
	*/
	protected void inicializarTabela()
	{	
		this.adicionarColunas("T�pico", "Mensagens");
	}
}