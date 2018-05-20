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
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class MenuControle implements Autenticavel
{
	private MenuVisao menuVisao;
	private Usuario logado;
	
	/**
		Cria e inicializa o controle de menu.
		
		@param A vis�o do menu.
	*/
	public MenuControle(MenuVisao menuVisao)
	{
		this.menuVisao = menuVisao;
		this.logado = null;
	}
	
	/**
		Define o usu�rio recuperado pela autentica��o.
	
		@param Um usu�rio.
	*/
	public void definirUsuario(Usuario usuario)
	{
		this.logado = usuario;
	}
	
	/**
		Faz login no sistema.
		
		@return Se a autentica��o foi bem-sucedida ou n�o.
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
		
		@return Se a autentica��o foi bem-sucedida ou n�o.
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
		Define o t�tulo da vis�o dependendo do usu�rio logado.
	*/
	private void definirTitulo()
	{
		this.menuVisao.setTitle("Sistema de postagens | Servidor | [" + this.logado.getNomeUsuario() + "]");
	}
	
	/**
		Verifica se o usu�rio logado � um usu�rio master.
		
		@return Se o usu�rio logado � master ou n�o.
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
		Abre a tela de cadastro de usu�rios.
	*/
	public void abrirCadastroUsuarios()
	{
		this.menuVisao.obterPainel().add(new CadastroUsuarioVisao(this.logado));
	}
	
	/**
		Abre a tela de cadastro de t�picos.
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
		Abre a tela de relat�rio de t�picos ativos.
	*/
	public void abrirRelatorioTopicosAtivos()
	{
		this.menuVisao.obterPainel().add(new RelatorioTopicosAtivosVisao());
	}
	
	/**
		Abre a tela de relat�rio de t�picos mais utilizados.
	*/
	public void abrirRelatorioTopicosMaisUtilizados()
	{
		this.menuVisao.obterPainel().add(new RelatorioTopicosMaisUtilizadosVisao());
	}
	
	/**
		Abre a tela de relat�rio de mensagens por t�pico.
	*/
	public void abrirRelatorioMensagensPorTopico()
	{
		this.menuVisao.obterPainel().add(new RelatorioMensagensPorTopicoVisao());
	}
	
	/**
		Abre a tela de relat�rio de intera��o por t�pico.
	*/
	public void abrirRelatorioInteracaoPorTopico()
	{
		this.menuVisao.obterPainel().add(new RelatorioInteracaoPorTopicoVisao());
	}
	
	/**
		Abre a tela de relat�rio de subscri��es dos usu�rios.
	*/
	public void abrirRelatorioSubscricoesUsuario()
	{
		this.menuVisao.obterPainel().add(new RelatorioSubscricoesUsuarioVisao());
	}
}