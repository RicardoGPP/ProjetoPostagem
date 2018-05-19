package pt.up.fc.lc.postagemcliente.movimentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
	
	public SubscricaoEmTopicosVisao(Usuario logado)
	{
		this.subscricaoEmTopicosControle = new SubscricaoEmTopicosControle(this, logado);
		this.construirTela();
		this.vincularEventos();
		this.subscricaoEmTopicosControle.carregarLista();
		this.setVisible(true);
	}
	
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
	
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
		this.buttonSubscrever.addActionListener(this.aoClicarButtonSubscrever());
	}
	
	public String obterPadrao()
	{
		return "(.*)" + this.textFieldTopico.getText() + "(.*)";
	}
	
	public void definirPadrao(String padrao)
	{
		this.textFieldTopico.setText(padrao);
	}
	
	public List<Topico> obterTopicos()
	{
		List<Topico> topicos = new ArrayList<>();
		for (int i = 0; i < this.listModelTopicos.size(); i++)
			topicos.add(this.listModelTopicos.get(i));
		return topicos;
	}
	
	public void definirTopicos(List<Topico> topicos)
	{
		this.listModelTopicos.clear();
		for (Topico topico : topicos)
			this.listModelTopicos.addElement(topico);
	}
	
	public Topico obterSelecionado()
	{
		return this.listTopicos.getSelectedValue();
	}
	
	public void excluirDaLista(Topico topico)
	{
		this.listModelTopicos.removeElement(topico);
	}

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
	
	public ActionListener aoClicarButtonSubscrever()
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