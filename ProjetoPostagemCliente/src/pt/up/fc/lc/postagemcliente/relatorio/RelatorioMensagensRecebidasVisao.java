package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class RelatorioMensagensRecebidasVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	public RelatorioMensagensRecebidasVisao(Usuario logado)
	{
		super("Relatório de mensagens recebidas");
		this.relatorioControle = new RelatorioMensagensRecebidasControle(this, logado);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	protected void inicializarTabela()
	{
		this.adicionarColunas("", "");
	}
}