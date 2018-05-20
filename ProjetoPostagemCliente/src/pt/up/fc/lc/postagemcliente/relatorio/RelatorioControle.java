package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe abstrata padrão para todos os controles de relatório.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public abstract class RelatorioControle
{
	protected RelatorioVisao relatorioVisao;
	protected Usuario logado;
	
	/**
		Inicializa a visão de relatório com visão passada como parâmetro e
		define o usuário logado para uso nas pesquisas.
		
		@param Uma visão de relatório
	*/
	protected RelatorioControle(RelatorioVisao relatorioVisao, Usuario logado)
	{
		this.relatorioVisao = relatorioVisao;
		this.logado = logado;
	}
	
	/**
		Carrega a tabela do relatório com os dados recuperados na pesquisa.
	*/
	public abstract void carregarTabela();
}