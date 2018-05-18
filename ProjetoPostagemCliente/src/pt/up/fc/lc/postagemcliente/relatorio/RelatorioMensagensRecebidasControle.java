package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class RelatorioMensagensRecebidasControle extends RelatorioControle
{
	private ComentarioDAO comentarioDAO;
	private SubscricaoDAO subscricaoDAO;
	
	public RelatorioMensagensRecebidasControle(RelatorioVisao relatorioVisao, Usuario logado)
	{
		super(relatorioVisao, logado);
		this.comentarioDAO = new ComentarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
	}
	
	public void carregarTabela()
	{	
		this.relatorioVisao.limparTabela();		
		int mensagens = 0;
		for (Subscricao subscricao : this.subscricaoDAO.obterLista(this.logado))
			mensagens += this.comentarioDAO.obterLista(subscricao.getTopico()).size();
		this.relatorioVisao.adicionarLinha("Quantidade total de mensagens recebidas de todos os tópicos subscritos:", mensagens);
	}
}