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
	Classe da camada de controle da subscrição em tópicos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class SubscricaoEmTopicosControle
{
	private SubscricaoDAO subscricaoDAO;
	private TopicoDAO topicoDAO;
	private SubscricaoEmTopicosVisao subscricaoEmTopicosVisao;
	private Usuario logado;
	
	/**
		Cria e inicializa o controle de subscrição em tópicos.
		
		@param A visão da subscrição em tópicos e o usuário logado na sessão.
	*/
	public SubscricaoEmTopicosControle(SubscricaoEmTopicosVisao subscricaoEmTopicosVisao, Usuario logado)
	{
		this.subscricaoDAO = new SubscricaoDAO();
		this.topicoDAO = new TopicoDAO();
		this.logado = logado;
		this.subscricaoEmTopicosVisao = subscricaoEmTopicosVisao;
	}
	
	/**
		Carrega a lista de tópicos não subscritos do usuário logado na visão.
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
		Obtém a lista de tópicos não subscritos pelo usuário logado.
		
		@return Uma lista de tópicos.
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
		Subscreve o usuário no tópico, incluindo a subscrição no arquivo.
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
		Verifica se o limite de subscrições do usuário logado é igual ao número de
		subscrições que ele possui no momento.
		
		@return Se o limite foi atingido ou não.
	*/
	public boolean limiteSubscricoesAtingido()
	{
		return (this.logado.getLimiteSubscricoes() <= this.subscricaoDAO.obterLista(this.logado).size());
	}
}