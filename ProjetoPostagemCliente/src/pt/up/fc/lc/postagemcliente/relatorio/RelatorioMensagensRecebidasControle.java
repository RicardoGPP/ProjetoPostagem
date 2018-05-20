package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do relatório de mensagens recebidas.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class RelatorioMensagensRecebidasControle extends RelatorioControle
{
	private ComentarioDAO comentarioDAO;
	private SubscricaoDAO subscricaoDAO;
	
	/**
		Cria e inicializa o controle do relatório de mensagens recebidas.
		
		@param A visão do relatório de mensagens recebidas.
	*/
	public RelatorioMensagensRecebidasControle(RelatorioMensagensRecebidasVisao relatorioVisao, Usuario logado)
	{
		super(relatorioVisao, logado);
		this.comentarioDAO = new ComentarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
	}
	
	/**
		Carrega a tabela do relatório com os dados recuperados na pesquisa.
	*/
	public void carregarTabela()
	{	
		this.relatorioVisao.limparTabela();		
		int mensagens = 0;
		for (Subscricao subscricao : this.subscricaoDAO.obterLista(this.logado))
			mensagens += this.comentarioDAO.obterLista(subscricao.getTopico()).size();
		this.relatorioVisao.adicionarLinha("Quantidade total de mensagens recebidas de todos os tópicos subscritos:", mensagens);
	}
}