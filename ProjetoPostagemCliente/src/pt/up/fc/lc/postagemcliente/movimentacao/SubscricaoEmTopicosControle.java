package pt.up.fc.lc.postagemcliente.movimentacao;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle da subscri��o em t�picos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class SubscricaoEmTopicosControle
{
	private SubscricaoDAO subscricaoDAO;
	private TopicoDAO topicoDAO;
	private SubscricaoEmTopicosVisao subscricaoEmTopicosVisao;
	private Usuario logado;
	
	/**
		Cria e inicializa o controle de subscri��o em t�picos.
		
		@param A vis�o da subscri��o em t�picos e o usu�rio logado na sess�o.
	*/
	public SubscricaoEmTopicosControle(SubscricaoEmTopicosVisao subscricaoEmTopicosVisao, Usuario logado)
	{
		this.subscricaoDAO = new SubscricaoDAO();
		this.topicoDAO = new TopicoDAO();
		this.logado = logado;
		this.subscricaoEmTopicosVisao = subscricaoEmTopicosVisao;
	}
	
	/**
		Carrega a lista de t�picos n�o subscritos do usu�rio logado na vis�o.
	*/
	public void carregarLista()
	{
		List<Topico> topicos = this.obterTopicosNaoSubscritos();		
		Pattern padrao = Pattern.compile(this.subscricaoEmTopicosVisao.obterPadrao());		
		for (Iterator<Topico> iterator = topicos.iterator(); iterator.hasNext();)			
		{
			Topico topico = iterator.next();			
			if ((!padrao.pattern().equals("")) && (!padrao.matcher(topico.getTitulo()).matches()))
				iterator.remove();
		}		
		Collections.sort(topicos, (t1, t2) -> t1.getTitulo().compareTo(t2.getTitulo()));
		this.subscricaoEmTopicosVisao.definirTopicos(topicos);
	}
	
	/**
		Obt�m a lista de t�picos n�o subscritos pelo usu�rio logado.
		
		@return Uma lista de t�picos.
	*/
	private List<Topico> obterTopicosNaoSubscritos()
	{
		List<Topico> topicos = this.topicoDAO.obterLista();		
		for (Iterator<Topico> iterator = topicos.iterator(); iterator.hasNext();)
		{
			Topico topico = iterator.next();
			for (Subscricao subscricao : this.subscricaoDAO.obterLista(this.logado))
			{
				if (subscricao.getTopico().comparar(topico))
				{
					iterator.remove();
					break;
				}
			}
		}
		return topicos;
	}
	
	/**
		Subscreve o usu�rio no t�pico, incluindo a subscri��o no arquivo.
	*/
	public void subscrever()
	{
		Topico topico = this.subscricaoEmTopicosVisao.obterSelecionado();
		if (topico != null)
		{
			Subscricao subscricao = new Subscricao();
			subscricao.setTopico(topico);
			subscricao.setUsuario(this.logado);
			subscricao.setFavorito(false);
			if (this.subscricaoDAO.inserir(subscricao))
				this.subscricaoEmTopicosVisao.excluirDaLista(topico);
		}
	}
	
	/**
		Verifica se o limite de subscri��es do usu�rio logado � igual ao n�mero de
		subscri��es que ele possui no momento.
		
		@return Se o limite foi atingido ou n�o.
	*/
	public boolean limiteSubscricoesAtingido()
	{
		return (this.logado.getLimiteSubscricoes() <= this.subscricaoDAO.obterLista(this.logado).size());
	}
}