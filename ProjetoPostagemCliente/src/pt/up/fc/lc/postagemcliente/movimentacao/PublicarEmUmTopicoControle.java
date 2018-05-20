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
	Classe da camada de controle da publica��o em um t�pico.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class PublicarEmUmTopicoControle
{
	private SubscricaoDAO subscricaoDAO;
	private ComentarioDAO comentarioDAO;
	private PublicarEmUmTopicoVisao publicarEmUmTopicoVisao;
	private Usuario logado;
	
	/**
		Cria e inicializa o controle de publica��o em um t�pico.
		
		@param A vis�o do gerenciamento de publica��o em um t�pico e o
		       usu�rio logado na sess�o.
	*/
	public PublicarEmUmTopicoControle(PublicarEmUmTopicoVisao publicarEmUmTopicoVisao, Usuario logado)
	{		
		this.publicarEmUmTopicoVisao = publicarEmUmTopicoVisao;
		this.comentarioDAO = new ComentarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
		this.logado = logado;
	}
	
	/**
		Carrega a combobox de t�picos com os t�picos subscritos pelo usu�rio
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
		Verifica se todos os campos obrigat�rios da vis�o foram
		preenchidos.
	
		@return Se todos os campos foram preenchidos ou n�o.
	*/
	public boolean tudoPreenchido()
	{
		Topico topico = this.publicarEmUmTopicoVisao.obterTopico();
		String mensagem = this.publicarEmUmTopicoVisao.obterMensagem().trim();
		return ((topico != null) && (!mensagem.equals("")));
	}
	
	/**
		Cria um objeto de coment�rio, define os dados nos campos da vis�o e inclui
		no arquivo de coment�rios.
		
		@return Se inclui o coment�rio ou n�o.
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