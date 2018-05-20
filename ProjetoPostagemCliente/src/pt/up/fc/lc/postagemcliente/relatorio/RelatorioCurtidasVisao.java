package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de visão do relatório de curtidas.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class RelatorioCurtidasVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	/**
		Cria e inicializa a visão do relatório de curtidas.
		
		@param O usuário logado.
	*/
	public RelatorioCurtidasVisao(Usuario logado)
	{
		super("Relatório de curtidas");
		this.relatorioControle = new RelatorioCurtidasControle(this, logado);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordenação.
	*/
	protected void inicializarTabela()
	{
		this.adicionarColunas("Tópico", "Mensagem", "Curtidas");
	}
}