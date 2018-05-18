package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public abstract class RelatorioControle
{
	protected RelatorioVisao relatorioVisao;
	protected Usuario logado;
	
	protected RelatorioControle(RelatorioVisao relatorioVisao, Usuario logado)
	{
		this.relatorioVisao = relatorioVisao;
		this.logado = logado;
	}
	
	public abstract void carregarTabela();
}
