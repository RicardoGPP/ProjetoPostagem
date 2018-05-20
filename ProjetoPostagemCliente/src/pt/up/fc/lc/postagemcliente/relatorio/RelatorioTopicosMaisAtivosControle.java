package pt.up.fc.lc.postagemcliente.relatorio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.DAO;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do relatório de tópicos mais ativos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class RelatorioTopicosMaisAtivosControle extends RelatorioControle
{	
	private TopicoDAO topicoDAO;
	private SubscricaoDAO subscricaoDAO;
	private ComentarioDAO comentarioDAO;
	
	/**
		Cria e inicializa o controle do relatório de tópicos mais ativos.
		
		@param A visão do relatório de tópicos mais ativos e o usuário logado.
	*/
	public RelatorioTopicosMaisAtivosControle(RelatorioTopicosMaisAtivosVisao relatorioVisao, Usuario logado)
	{
		super(relatorioVisao, logado);
		this.topicoDAO = new TopicoDAO();
		this.subscricaoDAO = new SubscricaoDAO();
		this.comentarioDAO = new ComentarioDAO();
	}
	
	/**
		Carrega a tabela do relatório com os dados recuperados na pesquisa.
	*/
	public void carregarTabela()
	{
		Periodo periodo = this.obterPeriodo();
		Map<Topico, Integer> comentarios = this.obterComentariosNoPeriodo(periodo);
		List<Topico> topicos = new ArrayList<>(comentarios.keySet());
		Collections.sort(topicos, (t1, t2) -> comentarios.get(t2).compareTo(comentarios.get(t1)));
		this.relatorioVisao.limparTabela();
		for (Topico topico : topicos)
		{
			if (comentarios.get(topico) > 0)
			{
				String titulo = topico.getTitulo();
				String subscrito = (this.estaSubscritoNoTopico(topico)) ? "Sim" : "Não";
				int mensagens = comentarios.get(topico);				
				this.relatorioVisao.adicionarLinha(titulo, subscrito, mensagens);
			}
		}
	}
	
	/**
		Recupera a quantidade de comentários feitos em um tópico dentro
		de um determinado período.
		
		@param O período de apuração
		@return Um HashMap com a quantidade de comentários por tópico
	*/
	private Map<Topico, Integer> obterComentariosNoPeriodo(Periodo periodo)
	{
		Map<Topico, Integer> comentarios = new HashMap<>();
		for (Topico topico : this.topicoDAO.obterLista())
			for (Comentario comentario : this.comentarioDAO.obterLista(topico))
				if ((comentario.getData().compareTo(periodo.dataInicio) >= 0) &&
				   (comentario.getData().compareTo(periodo.dataFim) <= 0))
					comentarios.compute(topico, (k, v) -> (v == null) ? 1 : v + 1);
		return comentarios;
	}
	
	/**
		Verifica se o usuário logado está subscrito no tópico passado
		por parâmetro.
		
		@param O tópico a ser verificado.
		@return Se está subscrito ou não.
	*/
	private boolean estaSubscritoNoTopico(Topico topico)
	{
		for (Subscricao subscricao : this.subscricaoDAO.obterLista(topico))
			if (subscricao.getUsuario().comparar(this.logado))
				return true;
		return false;
	}
	
	/**
		Obtém o período de pesquisa de acordo com a opção e as informações
		nos campos da visão.
		
		@return O período de apuração.
	*/
	private Periodo obterPeriodo()
	{		
		RelatorioTopicosMaisAtivosVisao relatorioVisao = (RelatorioTopicosMaisAtivosVisao) this.relatorioVisao;
		String opcao = relatorioVisao.obterSelecionado();
		Periodo periodo = null;				
		if (opcao.equals("Período específico"))
			periodo = this.obterPeriodoEspecifico();
		else if (opcao.equals("Na última hora"))
			periodo = this.obterPeriodoUltimaHora();				
		else if (opcao.equals("Hoje"))
			periodo = this.obterPeriodoHoje();
		else
			periodo = this.obterPeriodoEstaSemana();		
		if ((periodo.dataInicio == null) || (periodo.dataFim == null))
			periodo = obterPeriodoCompleto();
		return periodo;
	}
	
	/**
		Define visibilidade do período a depender da opção na visão. Se a opção
		for diferente de "Período específico", os campos de data ficarão visíveis.
		Caso contrário, eles ficarão invisíveis.
	*/
	public void definirVisibilidadePeriodo()
	{
		RelatorioTopicosMaisAtivosVisao relatorioVisao = (RelatorioTopicosMaisAtivosVisao) this.relatorioVisao;
		relatorioVisao.definirVisibilidadePeriodo(relatorioVisao.obterSelecionado().equals("Período específico"));
	}
	
	/**
		Obtém o período para o caso de a opção selecionada ser "Período específico".
		
		@return O período de apuração.
	*/
	private Periodo obterPeriodoEspecifico()
	{
		RelatorioTopicosMaisAtivosVisao relatorioVisao = (RelatorioTopicosMaisAtivosVisao) this.relatorioVisao;
		return new Periodo(relatorioVisao.obterDataInicio(), relatorioVisao.obterDataFim());
	}
	
	/**
		Obtém o período para o caso de a opção selecionada ser "Última hora".
		
		@return O período de apuração.
	*/
	private Periodo obterPeriodoUltimaHora()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, -60);
		return new Periodo(calendar.getTime(), new Date());
	}
	
	/**
		Obtém o período para o caso de a opção selecionada ser "Hoje".
		
		@return O período de apuração.
	*/
	private Periodo obterPeriodoHoje()
	{
		try
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAO.FORMATO_DATA);
			return new Periodo(simpleDateFormat.parse(simpleDateFormat.format(new Date())), new Date());
		} catch (ParseException e)
		{
			return new Periodo(null, new Date());
		}
	}
	
	/**
		Obtém o período para o caso de a opção selecionada ser "Esta semana".
		
		@return O período de apuração.
	*/
	private Periodo obterPeriodoEstaSemana()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -168);
		return new Periodo(calendar.getTime(), new Date());
	}
	
	/**
		Obtém o período que compreende todos os registros de comentário do
		sistema.
		
		@return O período de apuração.
	*/
	private Periodo obterPeriodoCompleto()
	{
		Date maisAntiga = new Date();
		for (Comentario comentario : this.comentarioDAO.obterLista())
			if ((maisAntiga == null) || (comentario.getData().compareTo(maisAntiga) < 0))
				maisAntiga = comentario.getData();
		return new Periodo(maisAntiga, new Date());
	}
	
	/**
		Classe interna responsável por permitir a geração de objetos contendo
		duas datas: uma de início e outra de fim.
		
		@version 1.0
		@author  Ricardo Giovani Piantavinha Perandré,
		         Pedro Sobral da Costa
	*/
	private class Periodo
	{
		private Date dataInicio;
		private Date dataFim;
		
		/**
			Cria o período com as datas de início e fim passadas por parâmetro
			
			@param Data de início e data de fim.
		*/
		private Periodo(Date dataInicio, Date dataFim)
		{
			this.dataInicio = dataInicio;
			this.dataFim = dataFim;
		}
	}
}