package pt.up.fc.lc.postagemservidor.relatorio;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do relatório de subscrições dos usuários.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class RelatorioSubscricoesUsuarioControle extends RelatorioControle
{	
	private SubscricaoDAO subscricaoDAO;
	private ComentarioDAO comentarioDAO;
	
	/**
		Cria e inicializa o controle do relatório de subscrições dos usuários.
		
		@param A visão do relatório de subscrições dos usuários.
	*/
	public RelatorioSubscricoesUsuarioControle(RelatorioVisao relatorioVisao)
	{
		super(relatorioVisao);
		this.subscricaoDAO = new SubscricaoDAO();
		this.comentarioDAO = new ComentarioDAO();
	}
	
	/**
		Carrega a tabela do relatório com os dados recuperados na pesquisa.
	*/
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
	
	/**
		Carrega os usuários do sistema no componente da visão.
	*/
	public void carregarUsuarios()
	{
		for (Usuario usuario : (new UsuarioDAO()).obterLista())
			((RelatorioSubscricoesUsuarioVisao) this.relatorioVisao).definirUsuarios(usuario); 
	}
}