package pt.up.fc.lc.postagemcliente.nucleo;

import pt.up.fc.lc.postagemcliente.arquivo.GerenciarContaVisao;
import pt.up.fc.lc.postagemcliente.movimentacao.GerenciarSubscricoesVisao;
import pt.up.fc.lc.postagemcliente.movimentacao.PublicarEmUmTopicoVisao;
import pt.up.fc.lc.postagemcliente.movimentacao.SubscricaoEmTopicosVisao;
import pt.up.fc.lc.postagemcliente.movimentacao.VisualizacaoFeedVisao;
import pt.up.fc.lc.postagemcliente.movimentacao.VisualizacaoMensagensTopicoVisao;
import pt.up.fc.lc.postagemcliente.relatorio.RelatorioCurtidasVisao;
import pt.up.fc.lc.postagemcliente.relatorio.RelatorioMensagensPorTopicoVisao;
import pt.up.fc.lc.postagemcliente.relatorio.RelatorioMensagensRecebidasVisao;
import pt.up.fc.lc.postagemcliente.relatorio.RelatorioTopicosMaisAtivosVisao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;
import pt.up.fc.lc.postagemcliente.nucleo.LoginVisao;

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
		this.menuVisao.setTitle("Sistema de postagens | Cliente | [" + this.logado.getNomeUsuario() + "]");
	}

	/**
		Abre a tela de gerenciamento de conta.
	*/
	public void abrirGerenciamentoDeConta()
	{
		this.menuVisao.obterPainel().add(new GerenciarContaVisao(this.logado));
	}

	/**
		Abre a tela de visualiza��o de feed.
	*/
	public void abrirVisualizacaoFeed()
	{
		this.menuVisao.obterPainel().add(new VisualizacaoFeedVisao(this.logado));
	}
	
	/**
		Abre a tela de visualiza��o de mensagens dos t�picos.
	*/
	public void abrirVisualizacaoMensagensTopico()
	{
		this.menuVisao.obterPainel().add(new VisualizacaoMensagensTopicoVisao(this.logado));
	}
	
	/**
		Abre a tela de subscri��o em t�picos.
	*/
	public void abrirSubscricaoEmTopicos()
	{
		this.menuVisao.obterPainel().add(new SubscricaoEmTopicosVisao(this.logado));
	} 
	
	/**
		Abre a tela de publica��o em um t�pico.
	*/
	public void abrirPublicacaoEmUmTopico()
	{
		this.menuVisao.obterPainel().add(new PublicarEmUmTopicoVisao(this.logado));
	}
	
	/**
		Abre a tela de gerenciamento de subscri��es.
	*/
	public void abrirGerenciamentoSubscricoes()
	{
		this.menuVisao.obterPainel().add(new GerenciarSubscricoesVisao(this.logado));
	}
	
	/**
		Abre a tela de relat�rio de t�picos mais ativos.
	*/
	public void abrirRelatorioTopicosMaisAtivos()
	{
		this.menuVisao.obterPainel().add(new RelatorioTopicosMaisAtivosVisao(this.logado));
	}
	
	/**
		Abre a tela de relat�rio de mensagens recebidas.
	*/
	public void abrirRelatorioMensagensRecebidas()
	{
		this.menuVisao.obterPainel().add(new RelatorioMensagensRecebidasVisao(this.logado));
	}
	
	/**
		Abre a tela de relat�rio de mensagens por t�pico.
	*/
	public void abriRelatorioMensagensPorTopico()
	{
		this.menuVisao.obterPainel().add(new RelatorioMensagensPorTopicoVisao(this.logado));
	}
	
	/**
		Abre a tela de relat�rio de curtidas.
	*/
	public void abrirRelatorioCurtidas()
	{
		this.menuVisao.obterPainel().add(new RelatorioCurtidasVisao(this.logado));
	}
}