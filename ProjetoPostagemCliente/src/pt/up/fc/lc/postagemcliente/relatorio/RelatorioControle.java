package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe abstrata padr�o para todos os controles de relat�rio.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public abstract class RelatorioControle
{
	protected RelatorioVisao relatorioVisao;
	protected Usuario logado;
	
	/**
		Inicializa a vis�o de relat�rio com vis�o passada como par�metro e
		define o usu�rio logado para uso nas pesquisas.
		
		@param Uma vis�o de relat�rio
	*/
	protected RelatorioControle(RelatorioVisao relatorioVisao, Usuario logado)
	{
		this.relatorioVisao = relatorioVisao;
		this.logado = logado;
	}
	
	/**
		Carrega a tabela do relat�rio com os dados recuperados na pesquisa.
	*/
	public abstract void carregarTabela();
}