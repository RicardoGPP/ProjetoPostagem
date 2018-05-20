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
	         Pedro Sobral da Costa
*/
public class RelatorioSubscricoesUsuarioControle extends RelatorioControle
{	
	private UsuarioDAO usuarioDAO;
	private SubscricaoDAO subscricaoDAO;
	private ComentarioDAO comentarioDAO;
	
	/**
		Cria e inicializa o controle do relatório de subscrições dos usuários.
		
		@param A visão do relatório de subscrições dos usuários.
	*/
	public RelatorioSubscricoesUsuarioControle(RelatorioSubscricoesUsuarioVisao relatorioVisao)
	{
		super(relatorioVisao);
		this.usuarioDAO = new UsuarioDAO();
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
		((RelatorioSubscricoesUsuarioVisao) this.relatorioVisao).definirUsuarios(this.usuarioDAO.obterLista()); 
	}
}