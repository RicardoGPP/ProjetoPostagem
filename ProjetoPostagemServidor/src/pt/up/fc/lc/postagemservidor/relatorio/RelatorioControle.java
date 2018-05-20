package pt.up.fc.lc.postagemservidor.relatorio;

/**
	Classe abstrata padr�o para todos os controles de relat�rio.

	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public abstract class RelatorioControle
{
	protected RelatorioVisao relatorioVisao;
	
	/**
		Inicializa a vis�o de relat�rio com vis�o passada como par�metro.
		
		@param Uma vis�o de relat�rio
	*/
	protected RelatorioControle(RelatorioVisao relatorioVisao)
	{
		this.relatorioVisao = relatorioVisao;
	}
	
	/**
		Carrega a tabela do relat�rio com os dados recuperados na pesquisa.
	*/
	public abstract void carregarTabela();
}
