package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de vis�o do relat�rio de mensagens por t�pico.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class RelatorioMensagensPorTopicoVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	/**
		Cria e inicializa a vis�o do relat�rio de mensagens por t�pico.
		
		@param O usu�rio logado.
	*/
	public RelatorioMensagensPorTopicoVisao(Usuario logado)
	{
		super("Relat�rio de mensagens por t�pico");
		this.relatorioControle = new RelatorioMensagensPorTopicoControle(this, logado);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordena��o.
	*/
	protected void inicializarTabela()
	{
		this.adicionarColunas("T�pico", "Mensagens", "Suas mensagens");
	}
}