package pt.up.fc.lc.postagemcliente.movimentacao;

import java.util.Collections;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class GerenciarSubscricoesControle
{
	private SubscricaoDAO subscricaoDAO;
	private GerenciarSubscricoesVisao gerenciarSubscricoesVisao;
	private Usuario logado;
	
	public GerenciarSubscricoesControle(GerenciarSubscricoesVisao gerenciarSubscricoesVisao, Usuario logado)
	{
		this.subscricaoDAO = new SubscricaoDAO();
		this.logado = logado;
		this.gerenciarSubscricoesVisao = gerenciarSubscricoesVisao;
	}
	
	public void carregarLista()
	{
		List<Subscricao> subscricoes = this.subscricaoDAO.obterLista(this.logado);		
		Collections.sort(subscricoes, (s1, s2) -> s1.getTopico().getTitulo().compareTo(s2.getTopico().getTitulo()));
		this.gerenciarSubscricoesVisao.definirSubscricoes(subscricoes);
	}
	
	public void desinscrever()
	{
		Subscricao subscricao = this.gerenciarSubscricoesVisao.obterSelecionado();
		if ((subscricao != null) && (this.subscricaoDAO.deletar(subscricao)))
			this.gerenciarSubscricoesVisao.excluirDaLista(subscricao);				
	}
	
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
	
	public void atualizarTextoBotaoFavoritar()
	{
		Subscricao subscricao = this.gerenciarSubscricoesVisao.obterSelecionado();
		if ((subscricao == null) || (!subscricao.isFavorito()))
			this.gerenciarSubscricoesVisao.definirTextoBotaoFavorito("Favoritar");
		else
			this.gerenciarSubscricoesVisao.definirTextoBotaoFavorito("Desfavoritar");
	}
}