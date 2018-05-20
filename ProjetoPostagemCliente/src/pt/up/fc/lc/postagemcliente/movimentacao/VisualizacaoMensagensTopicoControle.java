package pt.up.fc.lc.postagemcliente.movimentacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle da visualização das mensagens por tópico.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class VisualizacaoMensagensTopicoControle
{
	private SubscricaoDAO subscricaoDAO;
	private ComentarioDAO comentarioDAO;
	private VisualizacaoMensagensTopicoVisao visualizacaoMensagensTopicoVisao;
	private Usuario logado;
	
	/**
		Cria e inicializa o controle de visualização de mensagens por tópico.
		
		@param A visão da visualização de mensagens por tópico e o usuário logado na sessão.
	*/
	public VisualizacaoMensagensTopicoControle(VisualizacaoMensagensTopicoVisao visualizacaoMensagensTopicoVisao, Usuario logado)
	{
		this.subscricaoDAO = new SubscricaoDAO();
		this.comentarioDAO = new ComentarioDAO();
		this.logado = logado;
		this.visualizacaoMensagensTopicoVisao = visualizacaoMensagensTopicoVisao;
	}
	
	/**
		Carrega a lista de comentários do tópico selecionado.
	*/
	public void carregarLista()
	{
		Topico topico = this.visualizacaoMensagensTopicoVisao.obterTopico();
		if (topico == null)
			this.visualizacaoMensagensTopicoVisao.definirTopicos(new ArrayList<>());
		else
		{
			List<Comentario> comentarios = this.comentarioDAO.obterLista(topico);
			Collections.sort(comentarios, (c1, c2) -> c2.getData().compareTo(c1.getData()));
			this.visualizacaoMensagensTopicoVisao.definirComentarios(comentarios);
		}
	}
	
	/**
		Carrega o combobox de tópicos em que o usuário está subscrito.
	*/
	public void carregarTopicos()
	{
		List<Topico> topicos = new ArrayList<>();
		for (Subscricao subscricao : this.subscricaoDAO.obterLista(this.logado))
			topicos.add(subscricao.getTopico());
		Collections.sort(topicos, (t1, t2) -> t1.getTitulo().compareTo(t2.getTitulo()));
		this.visualizacaoMensagensTopicoVisao.definirTopicos(topicos);
	}
}