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
	Classe da camada de vis�o da subscri��o em t�picos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
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
		Cria e inicializa a vis�o de subscri��o em t�picos.
		
		@param O usu�rio logado.
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
		Cria os componentes da vis�o, define seus respectivos tamanhos e
		posi��es e relaciona no padr�o composite.
	*/
	private void construirTela()
	{
		this.setTitle("Subscri��o em t�picos");
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
		this.labelTopico.setText("T�pico: ");
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
		Vincula eventos aos componentes da vis�o.
	*/
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
		this.buttonSubscrever.addActionListener(this.aoClicarButtonSubscrever());
	}
	
	/**
		Obt�m o padr�o no componente correspondente da vis�o. O texto definido
		no campo � seguido de dois modificadores para explicar que o padr�o deve
		ser comparado em qualquer parte da string.		
		
		@return Um padr�o de pesquisa.
	*/
	public String obterPadrao()
	{
		return "(.*)" + this.textFieldTopico.getText() + "(.*)";
	}
	
	/**
		Define os t�picos na lista da vis�o.
		
		@param Uma lista de t�picos.
	*/
	public void definirTopicos(List<Topico> topicos)
	{
		this.listModelTopicos.clear();
		for (Topico topico : topicos)
			this.listModelTopicos.addElement(topico);
	}
	
	/**
		Obt�m o t�pico selecionado na lista da vis�o.
		
		@return O t�pico selecionado ou null se n�o houver sele��o.
	*/
	public Topico obterSelecionado()
	{
		return this.listTopicos.getSelectedValue();
	}
	
	/**
		Exclui um t�pico da lista.
		
		@param Um t�pico.
	*/
	public void excluirDaLista(Topico topico)
	{
		this.listModelTopicos.removeElement(topico);
	}

	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
		"Atualizar".
		
		@return Um handler ao evento.
	*/	
	public ActionListener aoClicarButtonAtualizar()
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
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
		"Subscrever".
		
		@return Um handler ao evento.
	*/	
	public ActionListener aoClicarButtonSubscrever()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (subscricaoEmTopicosControle.limiteSubscricoesAtingido())
					JOptionPane.showMessageDialog(null, "Seu limite de subscri��es foi atingido.");
				else
					subscricaoEmTopicosControle.subscrever();
			}
		};
	}
}