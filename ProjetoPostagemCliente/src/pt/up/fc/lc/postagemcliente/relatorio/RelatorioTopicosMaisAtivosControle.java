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
	Classe da camada de controle do relat�rio de t�picos mais ativos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class RelatorioTopicosMaisAtivosControle extends RelatorioControle
{	
	private TopicoDAO topicoDAO;
	private SubscricaoDAO subscricaoDAO;
	private ComentarioDAO comentarioDAO;
	
	/**
		Cria e inicializa o controle do relat�rio de t�picos mais ativos.
		
		@param A vis�o do relat�rio de t�picos mais ativos e o usu�rio logado.
	*/
	public RelatorioTopicosMaisAtivosControle(RelatorioTopicosMaisAtivosVisao relatorioVisao, Usuario logado)
	{
		super(relatorioVisao, logado);
		this.topicoDAO = new TopicoDAO();
		this.subscricaoDAO = new SubscricaoDAO();
		this.comentarioDAO = new ComentarioDAO();
	}
	
	/**
		Carrega a tabela do relat�rio com os dados recuperados na pesquisa.
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
				String subscrito = (this.estaSubscritoNoTopico(topico)) ? "Sim" : "N�o";
				int mensagens = comentarios.get(topico);				
				this.relatorioVisao.adicionarLinha(titulo, subscrito, mensagens);
			}
		}
	}
	
	/**
		Recupera a quantidade de coment�rios feitos em um t�pico dentro
		de um determinado per�odo.
		
		@param O per�odo de apura��o
		@return Um HashMap com a quantidade de coment�rios por t�pico
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
		Verifica se o usu�rio logado est� subscrito no t�pico passado
		por par�metro.
		
		@param O t�pico a ser verificado.
		@return Se est� subscrito ou n�o.
	*/
	private boolean estaSubscritoNoTopico(Topico topico)
	{
		for (Subscricao subscricao : this.subscricaoDAO.obterLista(topico))
			if (subscricao.getUsuario().comparar(this.logado))
				return true;
		return false;
	}
	
	/**
		Obt�m o per�odo de pesquisa de acordo com a op��o e as informa��es
		nos campos da vis�o.
		
		@return O per�odo de apura��o.
	*/
	private Periodo obterPeriodo()
	{		
		RelatorioTopicosMaisAtivosVisao relatorioVisao = (RelatorioTopicosMaisAtivosVisao) this.relatorioVisao;
		String opcao = relatorioVisao.obterSelecionado();
		Periodo periodo = null;				
		if (opcao.equals("Per�odo espec�fico"))
			periodo = this.obterPeriodoEspecifico();
		else if (opcao.equals("Na �ltima hora"))
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
		Define visibilidade do per�odo a depender da op��o na vis�o. Se a op��o
		for diferente de "Per�odo espec�fico", os campos de data ficar�o vis�veis.
		Caso contr�rio, eles ficar�o invis�veis.
	*/
	public void definirVisibilidadePeriodo()
	{
		RelatorioTopicosMaisAtivosVisao relatorioVisao = (RelatorioTopicosMaisAtivosVisao) this.relatorioVisao;
		relatorioVisao.definirVisibilidadePeriodo(relatorioVisao.obterSelecionado().equals("Per�odo espec�fico"));
	}
	
	/**
		Obt�m o per�odo para o caso de a op��o selecionada ser "Per�odo espec�fico".
		
		@return O per�odo de apura��o.
	*/
	private Periodo obterPeriodoEspecifico()
	{
		RelatorioTopicosMaisAtivosVisao relatorioVisao = (RelatorioTopicosMaisAtivosVisao) this.relatorioVisao;
		return new Periodo(relatorioVisao.obterDataInicio(), relatorioVisao.obterDataFim());
	}
	
	/**
		Obt�m o per�odo para o caso de a op��o selecionada ser "�ltima hora".
		
		@return O per�odo de apura��o.
	*/
	private Periodo obterPeriodoUltimaHora()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, -60);
		return new Periodo(calendar.getTime(), new Date());
	}
	
	/**
		Obt�m o per�odo para o caso de a op��o selecionada ser "Hoje".
		
		@return O per�odo de apura��o.
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
		Obt�m o per�odo para o caso de a op��o selecionada ser "Esta semana".
		
		@return O per�odo de apura��o.
	*/
	private Periodo obterPeriodoEstaSemana()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -168);
		return new Periodo(calendar.getTime(), new Date());
	}
	
	/**
		Obt�m o per�odo que compreende todos os registros de coment�rio do
		sistema.
		
		@return O per�odo de apura��o.
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
		Classe interna respons�vel por permitir a gera��o de objetos contendo
		duas datas: uma de in�cio e outra de fim.
		
		@version 1.0
		@author  Ricardo Giovani Piantavinha Perandr�,
		         Pedro
	*/
	private class Periodo
	{
		private Date dataInicio;
		private Date dataFim;
		
		/**
			Cria o per�odo com as datas de in�cio e fim passadas por par�metro
			
			@param Data de in�cio e data de fim.
		*/
		private Periodo(Date dataInicio, Date dataFim)
		{
			this.dataInicio = dataInicio;
			this.dataFim = dataFim;
		}
	}
}