package pt.up.fc.lc.postagemservidor.relatorio;

/**
	Classe abstrata padrão para todos os controles de relatório.

	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public abstract class RelatorioControle
{
	protected RelatorioVisao relatorioVisao;
	
	/**
		Inicializa a visão de relatório com visão passada como parâmetro.
		
		@param Uma visão de relatório
	*/
	protected RelatorioControle(RelatorioVisao relatorioVisao)
	{
		this.relatorioVisao = relatorioVisao;
	}
	
	/**
		Carrega a tabela do relatório com os dados recuperados na pesquisa.
	*/
	public abstract void carregarTabela();
}
