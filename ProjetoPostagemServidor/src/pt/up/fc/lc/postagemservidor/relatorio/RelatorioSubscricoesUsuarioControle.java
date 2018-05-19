package pt.up.fc.lc.postagemservidor.relatorio;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class RelatorioSubscricoesUsuarioControle extends RelatorioControle
{	
	private SubscricaoDAO subscricaoDAO;
	private ComentarioDAO comentarioDAO;
	
	public RelatorioSubscricoesUsuarioControle(RelatorioVisao relatorioVisao)
	{
		super(relatorioVisao);
		this.subscricaoDAO = new SubscricaoDAO();
		this.comentarioDAO = new ComentarioDAO();
	}
	
	public void carregarTabela()
	{	
		Usuario usuario = ((RelatorioSubscricoesUsuarioVisao) this.relatorioVisao).obterSelecionado();
		this.relatorioVisao.limparTabela();
		if (usuario != null)
		{
			for (Subscricao subscricao : this.subscricaoDAO.obterLista(usuario))
			{
				int mensagens = 0;
				for (Comentario comentario : this.comentarioDAO.obterLista(subscricao.getTopico()))
					if (comentario.getUsuario().comparar(usuario))
						mensagens++;				
				String titulo = subscricao.getTopico().getTitulo();
				String favorito = (subscricao.isFavorito()) ? "Sim" : "Não";
				this.relatorioVisao.adicionarLinha(titulo, favorito, mensagens);
			}
		}
	}
	
	public void carregarUsuarios()
	{
		for (Usuario usuario : (new UsuarioDAO()).obterLista())
			((RelatorioSubscricoesUsuarioVisao) this.relatorioVisao).adicionarUsuario(usuario); 
	}
}