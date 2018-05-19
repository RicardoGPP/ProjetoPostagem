package pt.up.fc.lc.postagemcliente.movimentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class PublicarEmUmTopicoVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 300;
	private static final int ALTURA = 400;
	private static final int BORDA = 15;
	
	private PublicarEmUmTopicoControle publicarEmUmTopicoControle;
	
	private JLabel labelTopico;
	private JComboBox<Topico> comboBoxTopico;	
	private JLabel labelMensagem;
	private JScrollPane scrollPaneMensagem;
	private JTextArea textAreaMensagem;
	private JButton buttonPublicar;
	
	public PublicarEmUmTopicoVisao(Usuario logado)
	{
		this.publicarEmUmTopicoControle = new PublicarEmUmTopicoControle(this, logado);
		this.construirTela();
		this.vincularEventos();
		this.setVisible(true);
	}
	
	private void construirTela()
	{
		this.setResizable(false);
		this.setSize(LARGURA, ALTURA);
		this.setLayout(null);
		this.setModal(true);
		this.setLocationRelativeTo(null);				
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		this.labelTopico = new JLabel();
		this.labelTopico.setText("Título:");
		this.labelTopico.setBounds(BORDA, BORDA, (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelTopico);
		
		this.comboBoxTopico = new JTextField();
		this.comboBoxTopico.setBounds(BORDA, (BORDA + 15), (LARGURA - (BORDA * 2) - 5), 20);
		this.add(this.comboBoxTopico);
		
		this.labelMensagem = new JLabel();
		this.labelMensagem.setText("Descrição:");
		this.labelMensagem.setBounds(BORDA, (BORDA + 40), (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelMensagem);
		
		this.scrollPaneMensagem = new JScrollPane();
		this.scrollPaneMensagem.setBounds(BORDA, (BORDA + 55), (LARGURA - (BORDA * 2) - 5), 180);
		this.add(this.scrollPaneMensagem);
		
		this.textAreaMensagem = new JTextArea();
		this.textAreaMensagem.setLineWrap(true);
		this.textAreaMensagem.getDocument().putProperty("filterNewLines", Boolean.TRUE);		
		this.scrollPaneMensagem.setViewportView(this.textAreaMensagem);
		
		this.labelLimiteMensagens = new JLabel();
		this.labelLimiteMensagens.setText("Limite de mensagens:");
		this.labelLimiteMensagens.setBounds(BORDA, (BORDA + 240), (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelLimiteMensagens);
		
		this.textFieldLimiteMensagens = new JTextField();
		this.textFieldLimiteMensagens.setBounds(BORDA, (BORDA + 255), (LARGURA - (BORDA * 2) - 5), 20);
		this.add(this.textFieldLimiteMensagens);
		
		this.buttonCancelar = new JButton();
		this.buttonCancelar.setText("Cancelar");
		this.buttonCancelar.setBounds((LARGURA - BORDA - 95), 325, 90, 25);
		this.add(this.buttonCancelar);
		
		this.buttonPublicar = new JButton();
		this.buttonPublicar.setText("OK");
		this.buttonPublicar.setBounds((this.buttonCancelar.getX() - 85), 325, 80, 25);
		this.add(this.buttonPublicar);		
	}
	
	private void vincularEventos()
	{
		this.buttonPublicar.addActionListener(this.aoClicarBotaoOK());
		this.buttonCancelar.addActionListener(this.aoClicarBotaoCancelar());
	}
	
	public String obterTopico()
	{
		return this.comboBoxTopico.getText();
	}
	
	public String obterMensagem()
	{
		return this.textAreaMensagem.getText();
	}
	
	public void definirMensagem(String mensagem)
	{
		this.textAreaMensagem.setText(mensagem);
	}
	
	private ActionListener aoClicarBotaoPublicar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
	
			}
		};
	}
}