package pt.up.fc.lc.postagemcliente.relatorio;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do relat�rio de mensagens recebidas.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class RelatorioMensagensRecebidasControle extends RelatorioControle
{
	private ComentarioDAO comentarioDAO;
	private SubscricaoDAO subscricaoDAO;
	
	/**
		Cria e inicializa o controle do relat�rio de mensagens recebidas.
		
		@param A vis�o do relat�rio de mensagens recebidas.
	*/
	public RelatorioMensagensRecebidasControle(RelatorioMensagensRecebidasVisao relatorioVisao, Usuario logado)
	{
		super(relatorioVisao, logado);
		this.comentarioDAO = new ComentarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
	}
	
	/**
		Carrega a tabela do relat�rio com os dados recuperados na pesquisa.
	*/
	public void carregarTabela()
	{	
		this.relatorioVisao.limparTabela();		
		int mensagens = 0;
		for (Subscricao subscricao : this.subscricaoDAO.obterLista(this.logado))
			mensagens += this.comentarioDAO.obterLista(subscricao.getTopico()).size();
		this.relatorioVisao.adicionarLinha("Quantidade total de mensagens recebidas de todos os t�picos subscritos:", mensagens);
	}
}