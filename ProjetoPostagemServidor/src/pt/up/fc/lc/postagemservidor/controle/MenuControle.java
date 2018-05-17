package pt.up.fc.lc.postagemservidor.controle;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;
import pt.up.fc.lc.postagemservidor.visao.CadastroTopicoVisao;
import pt.up.fc.lc.postagemservidor.visao.CadastroUsuarioVisao;
import pt.up.fc.lc.postagemservidor.visao.GerenciarPedidosUtilizadorVisao;
import pt.up.fc.lc.postagemservidor.visao.LoginVisao;
import pt.up.fc.lc.postagemservidor.visao.MenuVisao;
import pt.up.fc.lc.postagemservidor.visao.RelatorioTopicosAtivosVisao;

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
}