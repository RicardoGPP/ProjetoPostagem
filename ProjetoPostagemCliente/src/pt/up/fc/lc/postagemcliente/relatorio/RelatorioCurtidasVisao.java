package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class RelatorioCurtidasVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	public RelatorioCurtidasVisao(Usuario logado)
	{
		super("Relatório de curtidas");
		this.relatorioControle = new RelatorioCurtidasControle(this, logado);
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	protected void inicializarTabela()
	{
		this.adicionarColunas("Tópico", "Mensagem", "Curtidas");
	}
}