package pt.up.fc.lc.postagemservidor.cadastro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

/**
	Classe da camada de visão do cadastro de tópicos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class CadastroTopicoVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 291;
	private static final int ALTURA = 400;
	private static final int BORDA = 15;
	
	private CadastroTopicoControle cadastroTopicoControle;
	
	private JButton buttonAtualizar;
	private JScrollPane scrollPaneListTopicos;
	private DefaultListModel<Topico> listModelTopicos;
	private JList<Topico> listTopicos;	
	private JButton buttonIncluir;
	private JButton buttonEditar;
	private JButton buttonExcluir;
	
	/**
		Cria e inicializa a visão de cadastro de tópicos.
	*/
	public CadastroTopicoVisao()
	{
		this.cadastroTopicoControle = new CadastroTopicoControle(this);
		this.construirTela();
		this.vincularEventos();
		this.cadastroTopicoControle.carregarLista();
		this.setVisible(true);
	}
	
	/**
		Cria os componentes da visão, define seus respectivos tamanhos e
		posições e relaciona no padrão composite.
	*/
	private void construirTela()
	{
		this.setTitle("Cadastro de tópicos");
		this.setResizable(false);
		this.setClosable(true);
		this.setSize(LARGURA, ALTURA);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		
		this.buttonAtualizar = new JButton();
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.setBounds((BORDA + 160), BORDA, 90, 25);
		this.add(this.buttonAtualizar);
		
		this.scrollPaneListTopicos = new JScrollPane();
		this.scrollPaneListTopicos.setBounds(BORDA, (BORDA + 30), (LARGURA - (BORDA * 2) - 10), 270);
		this.add(this.scrollPaneListTopicos);
		
		this.listModelTopicos = new DefaultListModel<>();
		this.listTopicos = new JList<>(this.listModelTopicos);
		this.scrollPaneListTopicos.setViewportView(this.listTopicos);
		
		this.buttonIncluir = new JButton();
		this.buttonIncluir.setText("Incluir");
		this.buttonIncluir.setBounds(BORDA, (BORDA + 305), 80, 25);
		this.add(this.buttonIncluir);
		
		this.buttonEditar = new JButton();
		this.buttonEditar.setText("Editar");
		this.buttonEditar.setBounds((BORDA + 85), (BORDA + 305), 80, 25);
		this.add(this.buttonEditar);
		
		this.buttonExcluir = new JButton();
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.setBounds((BORDA + 170), (BORDA + 305), 80, 25);
		this.add(this.buttonExcluir);
	}
	
	/**
		Vincula eventos aos componentes da visão.
	*/
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
		this.buttonIncluir.addActionListener(this.aoClicarButtonIncluir());
		this.buttonEditar.addActionListener(this.aoClicarButtonEditar());
		this.buttonExcluir.addActionListener(this.aoClicarButtonExcluir());
	}
	
	/**
		Define os tópicos na lista da visão.
		
		@param Uma lista de tópicos.
	*/
	public void definirTopicos(List<Topico> topicos)
	{
		this.listModelTopicos.clear();
		for (Topico topico : topicos)
			this.listModelTopicos.addElement(topico);
	}
	
	/**
		Obtém o tópico selecionado na lista da visão.
		
		@return O tópico selecionado ou null se não houver seleção.
	*/
	public Topico obterSelecionado()
	{
		return this.listTopicos.getSelectedValue();
	}
	
	/**
		Inclui um tópico na lista.
		
		@param Um tópico.
	*/
	public void incluirNaLista(Topico topico)
	{
		this.listModelTopicos.addElement(topico);
	}
	
	/**
		Exclui um tópico da lista.
		
		@param Um tópico.
	*/
	public void excluirDaLista(Topico topico)
	{
		this.listModelTopicos.removeElement(topico);
	}

	/**
		Define e retorna a ação aplicada sobre o evento do clique no botão
		"Atualizar".
		
		@return Um handler ao evento.
	*/	
	public ActionListener aoClicarButtonAtualizar()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				cadastroTopicoControle.carregarLista();
			}
		};
	}
	
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no botão
		"Incluir".
		
		@return Um handler ao evento.
	*/
	
	public ActionListener aoClicarButtonIncluir()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cadastroTopicoControle.incluir();
			}
		};
	}
	
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no botão
		"Editar".
		
		@return Um handler ao evento.
	*/
	public ActionListener aoClicarButtonEditar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cadastroTopicoControle.editar();
			}
		};
	}
	
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no botão
		"Excluir".
		
		@return Um handler ao evento.
	*/
	public ActionListener aoClicarButtonExcluir()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if ((obterSelecionado() != null) &&
				   (JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?", "Confirmação", JOptionPane.YES_NO_OPTION) == 0))
					cadastroTopicoControle.excluir();
			}
		};
	}
}