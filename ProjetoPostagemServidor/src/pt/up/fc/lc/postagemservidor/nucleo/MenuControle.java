package pt.up.fc.lc.postagemservidor.nucleo;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;
import pt.up.fc.lc.postagemservidor.arquivo.GerenciarContaVisao;
import pt.up.fc.lc.postagemservidor.cadastro.CadastroTopicoVisao;
import pt.up.fc.lc.postagemservidor.cadastro.CadastroUsuarioVisao;
import pt.up.fc.lc.postagemservidor.movimentacao.GerenciarPedidosUtilizadorVisao;
import pt.up.fc.lc.postagemservidor.relatorio.RelatorioInteracaoPorTopicoVisao;
import pt.up.fc.lc.postagemservidor.relatorio.RelatorioMensagensPorTopicoVisao;
import pt.up.fc.lc.postagemservidor.relatorio.RelatorioSubscricoesUsuarioVisao;
import pt.up.fc.lc.postagemservidor.relatorio.RelatorioTopicosAtivosVisao;
import pt.up.fc.lc.postagemservidor.relatorio.RelatorioTopicosMaisUtilizadosVisao;

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
	
	public boolean usuarioLogadoEMaster()
	{
		return this.logado.getNomeUsuario().equalsIgnoreCase("Master");
	}
	
	public boolean trocarUsuario()
	{
		Usuario usuarioAnterior = this.logado;
		new LoginVisao(this);
		if (this.logado == null)
			this.logado = usuarioAnterior;
		this.menuVisao.definirItemMenuArquivoGerenciarContaVisivel(!this.usuarioLogadoEMaster());
		return (!this.logado.comparar(usuarioAnterior));
	}

	public void abrirGerenciamentoDeConta()
	{
		this.menuVisao.obterPainel().add(new GerenciarContaVisao(this.logado));
	}
	
	public void abrirCadastroUsuarios()
	{
		this.menuVisao.obterPainel().add(new CadastroUsuarioVisao(this.logado));
	}
	
	public void abrirCadastroTopicos()
	{
		this.menuVisao.obterPainel().add(new CadastroTopicoVisao());
	}
	
	public void abrirGerenciamentoPedidosUtilizador()
	{
		this.menuVisao.obterPainel().add(new GerenciarPedidosUtilizadorVisao());
	}
	
	public void abrirRelatorioTopicosAtivos()
	{
		this.menuVisao.obterPainel().add(new RelatorioTopicosAtivosVisao());
	}
	
	public void abrirRelatorioTopicosMaisUtilizados()
	{
		this.menuVisao.obterPainel().add(new RelatorioTopicosMaisUtilizadosVisao());
	}
	
	public void abrirRelatorioMensagensPorTopico()
	{
		this.menuVisao.obterPainel().add(new RelatorioMensagensPorTopicoVisao());
	}
	
	public void abrirRelatorioInteracaoPorTopico()
	{
		this.menuVisao.obterPainel().add(new RelatorioInteracaoPorTopicoVisao());
	}
	
	public void abrirRelatorioSubscricoesUsuario()
	{
		this.menuVisao.obterPainel().add(new RelatorioSubscricoesUsuarioVisao());
	}
}