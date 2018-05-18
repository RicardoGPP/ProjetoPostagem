package pt.up.fc.lc.postagemcliente.nucleo;

import pt.up.fc.lc.postagemcliente.arquivo.GerenciarContaVisao;
import pt.up.fc.lc.postagemcliente.relatorio.RelatorioCurtidasVisao;
import pt.up.fc.lc.postagemcliente.relatorio.RelatorioMensagensPorTopicoVisao;
import pt.up.fc.lc.postagemcliente.relatorio.RelatorioMensagensRecebidasVisao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class MenuControle implements Autenticavel
{
	private MenuVisao menuVisao;
	private Usuario logado;
	
	public MenuControle(MenuVisao menuVisao)
	{
		this.menuVisao = menuVisao;
		this.logado = null;
	}
	
	public void definirUsuario(Usuario usuario)
	{
		this.logado = usuario;
	}
	
	public void fazerLogin()
	{
		new LoginVisao(this);
	}
	
	public boolean foiAutenticado()
	{
		return (this.logado != null);
	}
	
	public boolean trocarUsuario()
	{
		Usuario usuarioAnterior = this.logado;
		new LoginVisao(this);
		if (this.logado == null)
			this.logado = usuarioAnterior;
		return (!this.logado.comparar(usuarioAnterior));
	}

	public void abrirGerenciamentoDeConta()
	{
		this.menuVisao.obterPainel().add(new GerenciarContaVisao(this.logado));
	}

	public void abrirRelatorioMensagensRecebidas()
	{
		this.menuVisao.obterPainel().add(new RelatorioMensagensRecebidasVisao(this.logado));
	}
	
	public void abriRelatorioMensagensPorTopico()
	{
		this.menuVisao.obterPainel().add(new RelatorioMensagensPorTopicoVisao(this.logado));
	}
	
	public void abrirRelatorioCurtidas()
	{
		this.menuVisao.obterPainel().add(new RelatorioCurtidasVisao(this.logado));
	}
}