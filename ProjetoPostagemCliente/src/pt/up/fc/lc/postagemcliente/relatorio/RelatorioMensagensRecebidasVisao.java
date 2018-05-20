package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de vis�o do relat�rio de mensagens recebidas.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class RelatorioMensagensRecebidasVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	/**
		Cria e inicializa a vis�o do relat�rio de mensagens recebidas.
		
		@param O usu�rio logado.
	*/
	public RelatorioMensagensRecebidasVisao(Usuario logado)
	{
		super("Relat�rio de mensagens recebidas");
		this.relatorioControle = new RelatorioMensagensRecebidasControle(this, logado);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordena��o.
	*/
	protected void inicializarTabela()
	{
		this.adicionarColunas("", "");
	}
}