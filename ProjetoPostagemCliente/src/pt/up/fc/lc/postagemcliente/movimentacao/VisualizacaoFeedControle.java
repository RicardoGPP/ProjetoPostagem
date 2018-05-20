package pt.up.fc.lc.postagemcliente.movimentacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.CurtidaDAO;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Curtida;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle da visualização do feed.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class VisualizacaoFeedControle
{
	private SubscricaoDAO subscricaoDAO;
	private ComentarioDAO comentarioDAO;
	private CurtidaDAO curtidaDAO;
	private VisualizacaoFeedVisao visualizacaoFeedVisao;
	private Usuario logado;
	
	/**
		Cria e inicializa o controle de visualização do feed.
		
		@param A visão da visualização do feed e o usuário logado na sessão.
	*/
	public VisualizacaoFeedControle(VisualizacaoFeedVisao visualizacaoFeedVisao, Usuario logado)
	{
		this.subscricaoDAO = new SubscricaoDAO();
		this.comentarioDAO = new ComentarioDAO();
		this.curtidaDAO = new CurtidaDAO();
		this.logado = logado;
		this.visualizacaoFeedVisao = visualizacaoFeedVisao;
	}
	
	/**
		Carrega a lista de comentários dos tópicos cujo os quais o usuário logado
		está subscrito.
	*/
	public void carregarLista()
	{
		List<ComentarioFavorito> comentarios = new ArrayList<>();		
		for (Subscricao subscricao : this.subscricaoDAO.obterLista(this.logado))
			for (Comentario comentario : this.comentarioDAO.obterLista(subscricao.getTopico()))
				comentarios.add(new ComentarioFavorito(comentario, subscricao.isFavorito()));		
		Collections.sort(comentarios, (c1, c2) -> c2.comentario.getData().compareTo(c1.comentario.getData()));
		this.visualizacaoFeedVisao.definirComentarios(comentarios);
	}
	
	/**
		Curte ou remove a curtida do usuário em um comentário. Se já houver
		uma curtida, esta é removida. Se não houver, então uma nova é inserida.
	*/
	public void curtirDescurtir()
	{
		Comentario comentario = this.visualizacaoFeedVisao.obterSelecionado().comentario;
		if (comentario != null)
		{
			Curtida curtida = new Curtida();
			curtida.setUsuario(this.logado);
			curtida.setComentario(comentario);
			if (this.curtidaDAO.curtiu(this.logado, comentario))
			{
				if (this.curtidaDAO.deletar(curtida))
					this.visualizacaoFeedVisao.definirTextoBotaoCurtir("Curtir");
			} else
			{
				if (this.curtidaDAO.inserir(curtida))
					this.visualizacaoFeedVisao.definirTextoBotaoCurtir("Descurtir");
			}
		}
	}
	
	/**
		Atualiza o texto do botão entre "curtir" e "descurtir" a depender
		do comentário já possuir uma curtida ou não.
	*/
	public void atualizarTextoBotaoCurtir()
	{
		Comentario comentario = this.visualizacaoFeedVisao.obterSelecionado().comentario;
		if ((comentario == null) || (!this.curtidaDAO.curtiu(this.logado, comentario)))
			this.visualizacaoFeedVisao.definirTextoBotaoCurtir("Curtir");
		else
			this.visualizacaoFeedVisao.definirTextoBotaoCurtir("Descurtir");
	}
	
	/**
		Classe interna que promove uma extensão da entidade comentário para a
		exibição formatada de sua representação mais um asterísco que determina
		se é favorito ou não.
		
		@version 1.0
		@author  Ricardo Giovani Piantavinha Perandré,
	             Pedro Sobral da Costa	
	*/
	public class ComentarioFavorito
	{
		private Comentario comentario;
		private boolean favorito;
		
		/**
			Cria um comentário favorito e define os dados nos atributos
			com os passados por parâmetro.
			
			@param Um comentário e se é favorito.
		*/
		private ComentarioFavorito(Comentario comentario, boolean favorito)
		{
			this.comentario = comentario;
			this.favorito = favorito;
		}
		
		/**
			Une a representação do comentário com a menção de um asterísco caso
			este comentário seja de um tópico favorito do usuário.
		*/
		public String toString()
		{
			return this.comentario.toString() + ((this.favorito) ? " *" : "");
		}
	}
}