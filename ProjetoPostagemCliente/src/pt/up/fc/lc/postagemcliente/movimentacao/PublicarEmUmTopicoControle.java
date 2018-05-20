package pt.up.fc.lc.postagemcliente.movimentacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle da publicação em um tópico.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class PublicarEmUmTopicoControle
{
	private SubscricaoDAO subscricaoDAO;
	private ComentarioDAO comentarioDAO;
	private PublicarEmUmTopicoVisao publicarEmUmTopicoVisao;
	private Usuario logado;
	
	/**
		Cria e inicializa o controle de publicação em um tópico.
		
		@param A visão do gerenciamento de publicação em um tópico e o
		       usuário logado na sessão.
	*/
	public PublicarEmUmTopicoControle(PublicarEmUmTopicoVisao publicarEmUmTopicoVisao, Usuario logado)
	{		
		this.publicarEmUmTopicoVisao = publicarEmUmTopicoVisao;
		this.comentarioDAO = new ComentarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
		this.logado = logado;
	}
	
	/**
		Carrega a combobox de tópicos com os tópicos subscritos pelo usuário
		logado.
	*/
	public void carregarTopicos()
	{
		List<Topico> topicos = new ArrayList<>();
		for (Subscricao subscricao : this.subscricaoDAO.obterLista(this.logado))
			topicos.add(subscricao.getTopico());
		Collections.sort(topicos, (t1, t2) -> t1.getTitulo().compareTo(t2.getTitulo()));
		this.publicarEmUmTopicoVisao.definirTopicos(topicos);
	}
	
	/**
		Verifica se todos os campos obrigatórios da visão foram
		preenchidos.
	
		@return Se todos os campos foram preenchidos ou não.
	*/
	public boolean tudoPreenchido()
	{
		Topico topico = this.publicarEmUmTopicoVisao.obterTopico();
		String mensagem = this.publicarEmUmTopicoVisao.obterMensagem().trim();
		return ((topico != null) && (!mensagem.equals("")));
	}
	
	/**
		Cria um objeto de comentário, define os dados nos campos da visão e inclui
		no arquivo de comentários.
		
		@return Se inclui o comentário ou não.
	*/
	public boolean publicar()
	{
		Comentario comentario = new Comentario();
		comentario.setUsuario(this.logado);
		comentario.setTopico(this.publicarEmUmTopicoVisao.obterTopico());
		comentario.setMensagem(this.publicarEmUmTopicoVisao.obterMensagem().trim());
		comentario.setData(new Date());
		return (this.comentarioDAO.inserir(comentario));
	}
}