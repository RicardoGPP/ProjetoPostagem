package pt.up.fc.lc.postagemservidor.relatorio;

public abstract class RelatorioControle
{
	protected RelatorioVisao relatorioVisao;
	
	protected RelatorioControle(RelatorioVisao relatorioVisao)
	{
		this.relatorioVisao = relatorioVisao;
	}
	
	public abstract void carregarTabela();
}
