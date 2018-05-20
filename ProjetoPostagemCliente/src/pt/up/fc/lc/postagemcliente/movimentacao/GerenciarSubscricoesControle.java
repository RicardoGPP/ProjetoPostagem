package pt.up.fc.lc.postagemcliente.movimentacao;

import java.util.Collections;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do gerenciamento de subscri��es.

	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class GerenciarSubscricoesControle
{
	private SubscricaoDAO subscricaoDAO;
	private GerenciarSubscricoesVisao gerenciarSubscricoesVisao;
	private Usuario logado;
	
	/**
		Cria e inicializa o controle de gerenciamento de subscri��es.
		
		@param A vis�o do gerenciamento de subscri��es e o usu�rio logado na sess�o.
	*/
	public GerenciarSubscricoesControle(GerenciarSubscricoesVisao gerenciarSubscricoesVisao, Usuario logado)
	{
		this.subscricaoDAO = new SubscricaoDAO();
		this.logado = logado;
		this.gerenciarSubscricoesVisao = gerenciarSubscricoesVisao;
	}
	
	/**
		Carrega a lista de subscri��es do usu�rio logado na vis�o.
	*/
	public void carregarLista()
	{
		List<Subscricao> subscricoes = this.subscricaoDAO.obterLista(this.logado);		
		Collections.sort(subscricoes, (s1, s2) -> s1.getTopico().getTitulo().compareTo(s2.getTopico().getTitulo()));
		this.gerenciarSubscricoesVisao.definirSubscricoes(subscricoes);
	}
	
	/**
		Desinscreve o usu�rio logado de um t�pico removendo a subscri��o
		referente do arquivo.
	*/
	public void desinscrever()
	{
		Subscricao subscricao = this.gerenciarSubscricoesVisao.obterSelecionado();
		if ((subscricao != null) && (this.subscricaoDAO.deletar(subscricao)))
			this.gerenciarSubscricoesVisao.excluirDaLista(subscricao);				
	}
	
	/**
		Favorita ou remove o favorito do usu�rio sobre um t�pico. Se j� houver
		um favorito, este � removido. Se n�o houver, ent�o um novo � inserido.
	*/
	public void favoritarDesfavoritar()
	{
		Subscricao subscricao = this.gerenciarSubscricoesVisao.obterSelecionado();
		if (subscricao != null)
		{
			if (subscricao.isFavorito())
			{	
				subscricao.setFavorito(false);
				if (this.subscricaoDAO.editar(subscricao))
					this.gerenciarSubscricoesVisao.definirTextoBotaoFavorito("Favoritar");				
			} else
			{
				subscricao.setFavorito(true);
				if (this.subscricaoDAO.editar(subscricao))
					this.gerenciarSubscricoesVisao.definirTextoBotaoFavorito("Desfavoritar");
			}
		}
	}
	
	/**
		Atualiza o texto do bot�o entre "favoritar" e "desfavoritar" a depender
		do t�pico j� possuir favorito ou n�o.
	*/
	public void atualizarTextoBotaoFavoritar()
	{
		Subscricao subscricao = this.gerenciarSubscricoesVisao.obterSelecionado();
		if ((subscricao == null) || (!subscricao.isFavorito()))
			this.gerenciarSubscricoesVisao.definirTextoBotaoFavorito("Favoritar");
		else
			this.gerenciarSubscricoesVisao.definirTextoBotaoFavorito("Desfavoritar");
	}
}