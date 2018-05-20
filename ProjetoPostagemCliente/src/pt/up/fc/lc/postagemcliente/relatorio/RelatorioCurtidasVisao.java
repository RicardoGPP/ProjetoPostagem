package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de vis�o do relat�rio de curtidas.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class RelatorioCurtidasVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	/**
		Cria e inicializa a vis�o do relat�rio de curtidas.
		
		@param O usu�rio logado.
	*/
	public RelatorioCurtidasVisao(Usuario logado)
	{
		super("Relat�rio de curtidas");
		this.relatorioControle = new RelatorioCurtidasControle(this, logado);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordena��o.
	*/
	protected void inicializarTabela()
	{
		this.adicionarColunas("T�pico", "Mensagem", "Curtidas");
	}
}