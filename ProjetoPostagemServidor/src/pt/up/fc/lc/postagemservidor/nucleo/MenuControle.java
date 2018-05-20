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

/**
	Classe da camada de controle do menu do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class MenuControle implements Autenticavel
{
	private MenuVisao menuVisao;
	private Usuario logado;
	
	/**
		Cria e inicializa o controle de menu.
		
		@param A visão do menu.
	*/
	public MenuControle(MenuVisao menuVisao)
	{
		this.menuVisao = menuVisao;
		this.logado = null;
	}
	
	/**
		Define o usuário recuperado pela autenticação.
	
		@param Um usuário.
	*/
	public void definirUsuario(Usuario usuario)
	{
		this.logado = usuario;
	}
	
	/**
		Faz login no sistema.
		
		@return Se a autenticação foi bem-sucedida ou não.
	*/
	public boolean fazerLogin()
	{		
		new LoginVisao(this);
		if (this.logado == null)
			return false;
		else
		{
			this.definirTitulo();
			return true;
		}
	}
	
	/**
		Faz logoff no sistema.
		
		@return Se a autenticação foi bem-sucedida ou não.
	*/
	public boolean fazerLogoff()
	{		
		this.logado = null;
		new LoginVisao(this);
		if (this.logado == null)
			return false;
		else
		{
			this.definirTitulo();
			return true;
		}
	}
	
	/**
		Define o título da visão dependendo do usuário logado.
	*/
	private void definirTitulo()
	{
		this.menuVisao.setTitle("Sistema de postagens | Servidor | [" + this.logado.getNomeUsuario() + "]");
	}
	
	/**
		Verifica se o usuário logado é um usuário master.
		
		@return Se o usuário logado é master ou não.
	*/
	public boolean usuarioLogadoEMaster()
	{
		return this.logado.getNomeUsuario().equalsIgnoreCase("Master");
	}	

	/**
		Abre a tela de gerenciamento de conta.
	*/
	public void abrirGerenciamentoDeConta()
	{
		this.menuVisao.obterPainel().add(new GerenciarContaVisao(this.logado));
	}
	
	/**
		Abre a tela de cadastro de usuários.
	*/
	public void abrirCadastroUsuarios()
	{
		this.menuVisao.obterPainel().add(new CadastroUsuarioVisao(this.logado));
	}
	
	/**
		Abre a tela de cadastro de tópicos.
	*/
	public void abrirCadastroTopicos()
	{
		this.menuVisao.obterPainel().add(new CadastroTopicoVisao());
	}
	
	/**
		Abre a tela de pedidos de utilizador.
	*/
	public void abrirGerenciamentoPedidosUtilizador()
	{
		this.menuVisao.obterPainel().add(new GerenciarPedidosUtilizadorVisao());
	}
	
	/**
		Abre a tela de relatório de tópicos ativos.
	*/
	public void abrirRelatorioTopicosAtivos()
	{
		this.menuVisao.obterPainel().add(new RelatorioTopicosAtivosVisao());
	}
	
	/**
		Abre a tela de relatório de tópicos mais utilizados.
	*/
	public void abrirRelatorioTopicosMaisUtilizados()
	{
		this.menuVisao.obterPainel().add(new RelatorioTopicosMaisUtilizadosVisao());
	}
	
	/**
		Abre a tela de relatório de mensagens por tópico.
	*/
	public void abrirRelatorioMensagensPorTopico()
	{
		this.menuVisao.obterPainel().add(new RelatorioMensagensPorTopicoVisao());
	}
	
	/**
		Abre a tela de relatório de interação por tópico.
	*/
	public void abrirRelatorioInteracaoPorTopico()
	{
		this.menuVisao.obterPainel().add(new RelatorioInteracaoPorTopicoVisao());
	}
	
	/**
		Abre a tela de relatório de subscrições dos usuários.
	*/
	public void abrirRelatorioSubscricoesUsuario()
	{
		this.menuVisao.obterPainel().add(new RelatorioSubscricoesUsuarioVisao());
	}
}