package pt.up.fc.lc.postagemservidor.relatorio;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do relat�rio de subscri��es dos usu�rios.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class RelatorioSubscricoesUsuarioControle extends RelatorioControle
{	
	private UsuarioDAO usuarioDAO;
	private SubscricaoDAO subscricaoDAO;
	private ComentarioDAO comentarioDAO;
	
	/**
		Cria e inicializa o controle do relat�rio de subscri��es dos usu�rios.
		
		@param A vis�o do relat�rio de subscri��es dos usu�rios.
	*/
	public RelatorioSubscricoesUsuarioControle(RelatorioSubscricoesUsuarioVisao relatorioVisao)
	{
		super(relatorioVisao);
		this.usuarioDAO = new UsuarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
		this.comentarioDAO = new ComentarioDAO();
	}
	
	/**
		Carrega a tabela do relat�rio com os dados recuperados na pesquisa.
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
				String favorito = (subscricao.isFavorito()) ? "Sim" : "N�o";
				this.relatorioVisao.adicionarLinha(titulo, favorito, mensagens);
			}
		}
	}
	
	/**
		Carrega os usu�rios do sistema no componente da vis�o.
	*/
	public void carregarUsuarios()
	{
		((RelatorioSubscricoesUsuarioVisao) this.relatorioVisao).definirUsuarios(this.usuarioDAO.obterLista()); 
	}
}