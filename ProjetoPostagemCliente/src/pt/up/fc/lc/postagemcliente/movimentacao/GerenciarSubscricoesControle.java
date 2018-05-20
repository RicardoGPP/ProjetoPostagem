package pt.up.fc.lc.postagemcliente.movimentacao;

import java.util.Collections;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do gerenciamento de subscrições.

	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class GerenciarSubscricoesControle
{
	private SubscricaoDAO subscricaoDAO;
	private GerenciarSubscricoesVisao gerenciarSubscricoesVisao;
	private Usuario logado;
	
	/**
		Cria e inicializa o controle de gerenciamento de subscrições.
		
		@param A visão do gerenciamento de subscrições e o usuário logado na sessão.
	*/
	public GerenciarSubscricoesControle(GerenciarSubscricoesVisao gerenciarSubscricoesVisao, Usuario logado)
	{
		this.subscricaoDAO = new SubscricaoDAO();
		this.logado = logado;
		this.gerenciarSubscricoesVisao = gerenciarSubscricoesVisao;
	}
	
	/**
		Carrega a lista de subscrições do usuário logado na visão.
	*/
	public void carregarLista()
	{
		List<Subscricao> subscricoes = this.subscricaoDAO.obterLista(this.logado);		
		Collections.sort(subscricoes, (s1, s2) -> s1.getTopico().getTitulo().compareTo(s2.getTopico().getTitulo()));
		this.gerenciarSubscricoesVisao.definirSubscricoes(subscricoes);
	}
	
	/**
		Desinscreve o usuário logado de um tópico removendo a subscrição
		referente do arquivo.
	*/
	public void desinscrever()
	{
		Subscricao subscricao = this.gerenciarSubscricoesVisao.obterSelecionado();
		if ((subscricao != null) && (this.subscricaoDAO.deletar(subscricao)))
			this.gerenciarSubscricoesVisao.excluirDaLista(subscricao);				
	}
	
	/**
		Favorita ou remove o favorito do usuário sobre um tópico. Se já houver
		um favorito, este é removido. Se não houver, então um novo é inserido.
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
		Atualiza o texto do botão entre "favoritar" e "desfavoritar" a depender
		do tópico já possuir favorito ou não.
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