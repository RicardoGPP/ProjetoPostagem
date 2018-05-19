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

public class PublicarEmUmTopicoControle
{
	private SubscricaoDAO subscricaoDAO;
	private ComentarioDAO comentarioDAO;
	private PublicarEmUmTopicoVisao publicarEmUmTopicoVisao;
	private Usuario logado;
	
	public PublicarEmUmTopicoControle(PublicarEmUmTopicoVisao publicarEmUmTopicoVisao, Usuario logado)
	{		
		this.publicarEmUmTopicoVisao = publicarEmUmTopicoVisao;
		this.comentarioDAO = new ComentarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
		this.logado = logado;
	}
	
	public void carregarTopicos()
	{
		List<Topico> topicos = new ArrayList<>();
		for (Subscricao subscricao : this.subscricaoDAO.obterLista(this.logado))
			topicos.add(subscricao.getTopico());
		Collections.sort(topicos, (t1, t2) -> t1.getTitulo().compareTo(t2.getTitulo()));
		this.publicarEmUmTopicoVisao.definirTopicos(topicos);
	}
	
	public boolean tudoPreenchido()
	{
		Topico topico = this.publicarEmUmTopicoVisao.obterTopico();
		String mensagem = this.publicarEmUmTopicoVisao.obterMensagem().trim();
		return ((topico != null) && (!mensagem.equals("")));
	}
	
	public boolean aindaEstaSubscrito()
	{
		Topico topico = this.publicarEmUmTopicoVisao.obterTopico();
		for (Subscricao subscricao : this.subscricaoDAO.obterLista(topico))
			if (subscricao.getUsuario().comparar(this.logado))
				return true;
		return false;
	}
	
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