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
	Classe da camada de controle da visualiza��o do feed.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
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
		Cria e inicializa o controle de visualiza��o do feed.
		
		@param A vis�o da visualiza��o do feed e o usu�rio logado na sess�o.
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
		Carrega a lista de coment�rios dos t�picos cujo os quais o usu�rio logado
		est� subscrito.
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
		Curte ou remove a curtida do usu�rio em um coment�rio. Se j� houver
		uma curtida, esta � removida. Se n�o houver, ent�o uma nova � inserida.
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
		Atualiza o texto do bot�o entre "curtir" e "descurtir" a depender
		do coment�rio j� possuir uma curtida ou n�o.
	*/
	public void atualizarTextoBotaoCurtir()
	{
		ComentarioFavorito comentarioFavorito = this.visualizacaoFeedVisao.obterSelecionado();
		if ((comentarioFavorito == null) || (!this.curtidaDAO.curtiu(this.logado, comentarioFavorito.comentario)))
			this.visualizacaoFeedVisao.definirTextoBotaoCurtir("Curtir");
		else
			this.visualizacaoFeedVisao.definirTextoBotaoCurtir("Descurtir");
	}
	
	/**
		Classe interna que promove uma extens�o da entidade coment�rio para a
		exibi��o formatada de sua representa��o mais um aster�sco que determina
		se � favorito ou n�o.
		
		@version 1.0
		@author  Ricardo Giovani Piantavinha Perandr�,
	             Pedro Sobral da Costa	
	*/
	public class ComentarioFavorito
	{
		private Comentario comentario;
		private boolean favorito;
	
		/**
			Cria um coment�rio favorito e define os dados nos atributos
			com os passados por par�metro.
			
			@param Um coment�rio e se � favorito.
		*/
		private ComentarioFavorito(Comentario comentario, boolean favorito)
		{
			this.comentario = comentario;
			this.favorito = favorito;
		}
		
		/**
			Une a representa��o do coment�rio com a men��o de um aster�sco caso
			este coment�rio seja de um t�pico favorito do usu�rio.
		*/
		public String toString()
		{
			return this.comentario.toString() + ((this.favorito) ? " *" : "");
		}
	}
}