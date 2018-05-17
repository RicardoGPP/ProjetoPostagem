package pt.up.fc.lc.postagemservidor.visao;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public abstract class RelatorioVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	protected static final int LARGURA = 700;
	protected static final int ALTURA = 500;
	protected static final int BORDA = 15;
	
	private JButton buttonAtualizar;
	private JScrollPane scrollPaneTabela;
	protected DefaultTableModel tableModelTabela;
	protected TableColumnModel columnModelTabela;
	private JTable tableTabela;
	
	public RelatorioVisao(String titulo)
	{
		this.construirTela(titulo);
		this.construirTabela();
		this.vincularEventos();
	}
	
	private void construirTela(String titulo)
	{
		this.setTitle(titulo);
		this.setResizable(false);
		this.setClosable(true);
		this.setSize(LARGURA, ALTURA);
		this.setLayout(null);
		
		this.buttonAtualizar = new JButton();
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.setBounds((LARGURA - BORDA - 100), BORDA, 90, 25);
		this.add(this.buttonAtualizar);
		
		this.scrollPaneTabela = new JScrollPane();
		this.scrollPaneTabela.setBounds(BORDA, (BORDA + 30), (LARGURA - (BORDA * 2) - 10), (ALTURA - BORDA - 80));
		this.add(this.scrollPaneTabela);
		
		this.tableModelTabela = new DefaultTableModel();
		this.tableTabela = new JTable(this.tableModelTabela);
		this.columnModelTabela = this.tableTabela.getColumnModel();
		this.scrollPaneTabela.setViewportView(this.tableTabela);
	}
	
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(aoClicarBotaoAtualizar());
	}
	
	protected abstract void construirTabela();
	
	protected abstract ActionListener aoClicarBotaoAtualizar();
}