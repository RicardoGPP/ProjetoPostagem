package pt.up.fc.lc.postagemcliente.relatorio;

import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.SubscricaoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class RelatorioMensagensPorTopicoControle extends RelatorioControle
{
	private ComentarioDAO comentarioDAO;
	private SubscricaoDAO subscricaoDAO;
	
	public RelatorioMensagensPorTopicoControle(RelatorioVisao relatorioVisao, Usuario logado)
	{
		super(relatorioVisao, logado);
		this.comentarioDAO = new ComentarioDAO();
		this.subscricaoDAO = new SubscricaoDAO();
	}
	
	public void carregarTabela()
	{	
		this.relatorioVisao.limparTabela();				
		for (Subscricao subscricao : this.subscricaoDAO.obterLista(this.logado))
		{
			List<Comentario> comentarios = this.comentarioDAO.obterLista(subscricao.getTopico());
			int suasMensagens = 0;
			for (Comentario comentario : comentarios)
				if (comentario.getUsuario().comparar(this.logado))
					suasMensagens++;			
			String titulo = subscricao.getTopico().getTitulo();
			int mensagens = comentarios.size();
			this.relatorioVisao.adicionarLinha(titulo, mensagens, suasMensagens);			
		}
	}
}