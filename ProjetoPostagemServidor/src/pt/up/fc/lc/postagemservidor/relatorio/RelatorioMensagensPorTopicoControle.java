package pt.up.fc.lc.postagemservidor.relatorio;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.DAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

/**
	Classe da camada de controle do relat�rio de mensagens por t�pico.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class RelatorioMensagensPorTopicoControle extends RelatorioControle
{
	private TopicoDAO topicoDAO;
	private ComentarioDAO comentarioDAO;
	
	/**
		Cria e inicializa o controle do relat�rio de mensagens por t�pico.
		
		@param A vis�o do relat�rio de mensagens por t�pico.
	*/
	public RelatorioMensagensPorTopicoControle(RelatorioVisao relatorioVisao)
	{
		super(relatorioVisao);
		this.topicoDAO = new TopicoDAO();
		this.comentarioDAO = new ComentarioDAO();
	}
	
	/**
		Carrega a tabela do relat�rio com os dados recuperados na pesquisa.
	*/
	public void carregarTabela()
	{	
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAO.FORMATO_DATA_HORA);		
		this.relatorioVisao.limparTabela();		
		for (Topico topico : this.topicoDAO.obterLista())
		{
			List<Comentario> comentarios = this.comentarioDAO.obterLista(topico);
			Collections.sort(comentarios, (c1, c2) -> c1.getData().compareTo(c2.getData()));						
			String titulo = topico.getTitulo();
			int mensagens = comentarios.size();
			String maisAntiga = (mensagens == 0) ? "" : simpleDateFormat.format(comentarios.get(0).getData());
			String maisRecente = (mensagens == 0) ? "" : simpleDateFormat.format(comentarios.get(comentarios.size() - 1).getData());
			this.relatorioVisao.adicionarLinha(titulo, mensagens, maisAntiga, maisRecente);
		}		
	}
}
