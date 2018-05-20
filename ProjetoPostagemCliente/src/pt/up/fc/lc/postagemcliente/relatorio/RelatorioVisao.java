package pt.up.fc.lc.postagemcliente.relatorio;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
	Classe abstrata padr�o para todos as vis�es de relat�rio.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
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
	
	/**
		Cria e inicializa a vis�o de relat�rio.
	*/
	public RelatorioVisao(String titulo)
	{
		this.construirTela(titulo);
		this.inicializarTabela();
		this.vincularEventos();
	}
	
	/**
		Cria os componentes da vis�o, define seus respectivos tamanhos e
		posi��es e relaciona no padr�o composite. Define o t�tulo passado
		no par�metro.
		
		@param O t�tulo do relat�rio.
	*/
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
	
	/**
		Vincula eventos aos componentes da vis�o.
	*/
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(aoClicarButtonAtualizar());
	}
	
	/**
		Adiciona colunas na tabela da vis�o.
		
		@param Um array de nomes de coluna.
	*/
	public void adicionarColunas(String ... colunas)
	{
		for (String coluna : colunas)
			this.tableModelTabela.addColumn(coluna);
	}
	
	/**
		Adiciona uma linha na tabela da vis�o.
	
		@param Um array de objetos de valor.
	*/
	public void adicionarLinha(Object ... linha)
	{
		this.tableModelTabela.addRow(linha);
	}
	
	/**
		Remove todas as linhas da tabela da vis�o.
	*/
	public void limparTabela()
	{
		this.tableModelTabela.setRowCount(0);
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
		"Atualizar".
		
		@return Um handler ao evento.
	*/
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
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordena��o.
	*/
	protected abstract void inicializarTabela();
}