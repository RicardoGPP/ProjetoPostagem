package pt.up.fc.lc.postagemservidor.visao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.TableModel;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

public class RelatorioTopicosAtivosVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;

	public RelatorioTopicosAtivosVisao()
	{
		super("Relatório de tópicos ativos");
		this.setVisible(true);
	}
	
	protected void construirTabela()
	{
		
		tableModelTabela.addColumn("Tópico");
		tableModelTabela.addColumn("Mensagens");
		tableModelTabela.addColumn("Limite");
		tableModelTabela.addColumn("Restantes");
		
		columnModelTabela.getColumn(0).setMinWidth(400);
	}
	
	protected ActionListener aoClicarBotaoAtualizar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TopicoDAO topicoDAO = new TopicoDAO();
				ComentarioDAO comentarioDAO = new ComentarioDAO();
				
				for (Topico topico : topicoDAO.obterLista())
				{
					String titulo = topico.getIdentificador();
					int mensagens = comentarioDAO.obterLista(topico).size();
					int limite = topico.getLimiteMensagens();
					int restantes = limite - mensagens;					
					if (restantes > 0)
						tableModelTabela.addRow(new Object[] {titulo, mensagens, limite, restantes});
				}
			}
		};
	}
}