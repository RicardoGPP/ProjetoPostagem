package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class RelatorioMensagensPorTopicoVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	public RelatorioMensagensPorTopicoVisao(Usuario logado)
	{
		super("Relatório de mensagens por tópico");
		this.relatorioControle = new RelatorioMensagensPorTopicoControle(this, logado);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	protected void inicializarTabela()
	{
		this.adicionarColunas("Tópico", "Mensagens", "Suas mensagens");
	}
}