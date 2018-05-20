package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de visão do relatório de mensagens recebidas.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class RelatorioMensagensRecebidasVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	/**
		Cria e inicializa a visão do relatório de mensagens recebidas.
		
		@param O usuário logado.
	*/
	public RelatorioMensagensRecebidasVisao(Usuario logado)
	{
		super("Relatório de mensagens recebidas");
		this.relatorioControle = new RelatorioMensagensRecebidasControle(this, logado);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordenação.
	*/
	protected void inicializarTabela()
	{
		this.adicionarColunas("", "");
	}
}