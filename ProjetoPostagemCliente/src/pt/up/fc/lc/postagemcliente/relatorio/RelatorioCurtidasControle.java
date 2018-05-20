package pt.up.fc.lc.postagemcliente.relatorio;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.CurtidaDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do relat�rio de curtidass.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class RelatorioCurtidasControle extends RelatorioControle
{
	private ComentarioDAO comentarioDAO;
	private CurtidaDAO curtidaDAO;
	
	/**
		Cria e inicializa o controle do relat�rio de curtidas.
		
		@param A vis�o do relat�rio de curtidas e o usu�rio logado.
	*/
	public RelatorioCurtidasControle(RelatorioCurtidasVisao relatorioVisao, Usuario logado)
	{
		super(relatorioVisao, logado);
		this.comentarioDAO = new ComentarioDAO();
		this.curtidaDAO = new CurtidaDAO();
	}
	
	/**
		Carrega a tabela do relat�rio com os dados recuperados na pesquisa.
	*/
	public void carregarTabela()
	{	
		Map<Comentario, Integer> curtidas = new HashMap<>();
		List<Comentario> comentarios = this.comentarioDAO.obterLista(this.logado);		
		for (Comentario comentario : comentarios)			
			curtidas.put(comentario, this.curtidaDAO.obterQuantidadeCurtidas(comentario));		
		Collections.sort(comentarios, (c1, c2) -> curtidas.get(c2).compareTo(curtidas.get(c1)));				
		this.relatorioVisao.limparTabela();
		for (Comentario comentario : comentarios)
		{
			if (curtidas.get(comentario) > 0)
			{
				String titulo = comentario.getTopico().getTitulo();
				String mensagem = comentario.getMensagem();
				int quantidadeCurtidas = curtidas.get(comentario);
				this.relatorioVisao.adicionarLinha(titulo, mensagem, quantidadeCurtidas);
			}
		}		
	}
}