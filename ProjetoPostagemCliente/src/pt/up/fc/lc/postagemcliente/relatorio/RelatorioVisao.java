package pt.up.fc.lc.postagemcliente.relatorio;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public abstract class RelatorioVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	protected static final int LARGURA = 700;
	protected static final int ALTURA = 500;
	protected static final int BORDA = 15;
	
	protected RelatorioControle relatorioControle;
	
	private JButton buttonAtualizar;
	private JScrollPane scrollPaneTabela;
	private DefaultTableModel tableModelTabela;
	private JTable tableTabela;
	
	public RelatorioVisao(String titulo)
	{
		this.construirTela(titulo);
		this.inicializarTabela();
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
		this.scrollPaneTabela.setViewportView(this.tableTabela);
	}
	
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(aoClicarButtonAtualizar());
	}
	
	public void adicionarColunas(String ... colunas)
	{
		for (String coluna : colunas)
			this.tableModelTabela.addColumn(coluna);
	}
	
	public void adicionarLinha(Object ... linha)
	{
		this.tableModelTabela.addRow(linha);
	}
	
	public void limparTabela()
	{
		this.tableModelTabela.setRowCount(0);
	}
	
	private ActionListener aoClicarButtonAtualizar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				relatorioControle.carregarTabela();
			}
		};
	}
	
	protected abstract void inicializarTabela();
}