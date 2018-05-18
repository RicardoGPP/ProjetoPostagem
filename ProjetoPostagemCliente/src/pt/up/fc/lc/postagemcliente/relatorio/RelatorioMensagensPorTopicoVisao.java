package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class RelatorioMensagensPorTopicoVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	public RelatorioMensagensPorTopicoVisao(Usuario logado)
	{
		super("Relat�rio de mensagens por t�pico");
		this.relatorioControle = new RelatorioMensagensPorTopicoControle(this, logado);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	protected void inicializarTabela()
	{
		this.adicionarColunas("T�pico", "Mensagens", "Suas mensagens");
	}
}