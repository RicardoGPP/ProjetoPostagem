package pt.up.fc.lc.postagemcliente.movimentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de visão da subscrição em tópicos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class SubscricaoEmTopicosVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 340;
	private static final int ALTURA = 400;
	private static final int BORDA = 15;
	
	private SubscricaoEmTopicosControle subscricaoEmTopicosControle;
	
	private JLabel labelTopico;
	private JTextField textFieldTopico;	
	private JButton buttonAtualizar;
	private JScrollPane scrollPaneListTopicos;
	private DefaultListModel<Topico> listModelTopicos;
	private JList<Topico> listTopicos;
	private JButton buttonSubscrever;
	
	/**
		Cria e inicializa a visão de subscrição em tópicos.
		
		@param O usuário logado.
	*/
	public SubscricaoEmTopicosVisao(Usuario logado)
	{
		this.subscricaoEmTopicosControle = new SubscricaoEmTopicosControle(this, logado);
		this.construirTela();
		this.vincularEventos();
		this.subscricaoEmTopicosControle.carregarLista();
		this.setVisible(true);
	}
	
	/**
		Cria os componentes da visão, define seus respectivos tamanhos e
		posições e relaciona no padrão composite.
	*/
	private void construirTela()
	{
		this.setTitle("Subscrição em tópicos");
		this.setResizable(false);
		this.setClosable(true);
		this.setSize(LARGURA, ALTURA);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		
		this.buttonAtualizar = new JButton();
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.setBounds((BORDA + 209), BORDA, 90, 25);
		this.add(this.buttonAtualizar);
		
		this.labelTopico = new JLabel();
		this.labelTopico.setText("Tópico: ");
		this.labelTopico.setBounds(BORDA, (BORDA + 5), 50, 17);
		this.add(this.labelTopico);
		
		this.textFieldTopico = new JTextField();
		this.textFieldTopico.setBounds((BORDA + 45), BORDA, 160, 25);
		this.add(this.textFieldTopico);
		
		this.scrollPaneListTopicos = new JScrollPane();
		this.scrollPaneListTopicos.setBounds(BORDA, (BORDA + 30), (LARGURA - (BORDA * 2) - 10), 270);
		this.add(this.scrollPaneListTopicos);
		
		this.listModelTopicos = new DefaultListModel<>();
		this.listTopicos = new JList<>(this.listModelTopicos);
		this.scrollPaneListTopicos.setViewportView(this.listTopicos);
		
		this.buttonSubscrever = new JButton();
		this.buttonSubscrever.setText("Subscrever");
		this.buttonSubscrever.setBounds((BORDA + 179), (BORDA + 305), 120, 25);
		this.add(this.buttonSubscrever);
	}
	
	/**
		Vincula eventos aos componentes da visão.
	*/
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
		this.buttonSubscrever.addActionListener(this.aoClicarButtonSubscrever());
	}
	
	/**
		Obtém o padrão no componente correspondente da visão. O texto definido
		no campo é seguido de dois modificadores para explicar que o padrão deve
		ser comparado em qualquer parte da string.		
		
		@return Um padrão de pesquisa.
	*/
	public String obterPadrao()
	{
		return "(.*)" + this.textFieldTopico.getText() + "(.*)";
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
	private ActionListener aoClicarButtonAtualizar()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				subscricaoEmTopicosControle.carregarLista();
			}
		};
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no botão
		"Subscrever".
		
		@return Um handler ao evento.
	*/	
	private ActionListener aoClicarButtonSubscrever()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (subscricaoEmTopicosControle.limiteSubscricoesAtingido())
					JOptionPane.showMessageDialog(null, "Seu limite de subscrições foi atingido.");
				else
					subscricaoEmTopicosControle.subscrever();
			}
		};
	}
}