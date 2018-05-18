package pt.up.fc.lc.postagemservidor.cadastro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

public class CadastroTopicoInternoVisao extends CadastroInternoVisao<Topico>
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 300;
	private static final int ALTURA = 400;
	private static final int BORDA = 15;
	
	private CadastroTopicoInternoControle cadastroTopicoInternoControle;
	
	private JLabel labelTitulo;
	private JTextField textFieldTitulo;	
	private JScrollPane scrollPaneDescricao;	
	private JLabel labelDescricao;
	private JTextArea textAreaDescricao;	
	private JLabel labelLimiteMensagens;
	private JTextField textFieldLimiteMensagens;
	private JButton buttonOK;
	private JButton buttonCancelar;
	
	public CadastroTopicoInternoVisao()
	{
		this.cadastroTopicoInternoControle = new CadastroTopicoInternoControle(this);
		this.construirTela();
		this.vincularEventos();
		this.cadastroTopicoInternoControle.carregarCampos();
		this.setVisible(true);
	}
	
	public CadastroTopicoInternoVisao(Topico topico)
	{
		this.cadastroTopicoInternoControle = new CadastroTopicoInternoControle(this, topico);
		this.construirTela();
		this.vincularEventos();
		this.cadastroTopicoInternoControle.carregarCampos();
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
		
		this.labelTitulo = new JLabel();
		this.labelTitulo.setText("Título:");
		this.labelTitulo.setBounds(BORDA, BORDA, (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelTitulo);
		
		this.textFieldTitulo = new JTextField();
		this.textFieldTitulo.setBounds(BORDA, (BORDA + 15), (LARGURA - (BORDA * 2) - 5), 20);
		this.add(this.textFieldTitulo);
		
		this.labelDescricao = new JLabel();
		this.labelDescricao.setText("Descrição:");
		this.labelDescricao.setBounds(BORDA, (BORDA + 40), (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelDescricao);
		
		this.scrollPaneDescricao = new JScrollPane();
		this.scrollPaneDescricao.setBounds(BORDA, (BORDA + 55), (LARGURA - (BORDA * 2) - 5), 180);
		this.add(this.scrollPaneDescricao);
		
		this.textAreaDescricao = new JTextArea();
		this.textAreaDescricao.setLineWrap(true);
		this.textAreaDescricao.getDocument().putProperty("filterNewLines", Boolean.TRUE);		
		this.scrollPaneDescricao.setViewportView(this.textAreaDescricao);
		
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
		
		this.buttonOK = new JButton();
		this.buttonOK.setText("OK");
		this.buttonOK.setBounds((this.buttonCancelar.getX() - 85), 325, 80, 25);
		this.add(this.buttonOK);		
	}
	
	private void vincularEventos()
	{
		this.buttonOK.addActionListener(this.aoClicarBotaoOK());
		this.buttonCancelar.addActionListener(this.aoClicarBotaoCancelar());
	}
	
	public String obterTitulo()
	{
		return this.textFieldTitulo.getText();
	}
	
	public void definirTitulo(String titulo)
	{
		this.textFieldTitulo.setText(titulo);
	}
	
	public void definirTituloEditavel(boolean editavel)
	{
		this.textFieldTitulo.setEditable(editavel);
	}
	
	public String obterDescricao()
	{
		return this.textAreaDescricao.getText();
	}
	
	public void definirDescricao(String descricao)
	{
		this.textAreaDescricao.setText(descricao);
	}
	
	public int obterLimiteMensagens()
	{
		return Integer.parseInt(this.textFieldLimiteMensagens.getText());
	}
	
	public void definirLimiteMensagens(int limiteMensagens)
	{
		this.textFieldLimiteMensagens.setText(Integer.toString(limiteMensagens));
	}
	
	public Topico obterRegistro()
	{
		return this.cadastroTopicoInternoControle.getTopico();
	}
	
	public boolean foiProcessado()
	{
		return this.processado;
	}
	
	private ActionListener aoClicarBotaoOK()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!cadastroTopicoInternoControle.tudoPreenchido())
				{
					JOptionPane.showMessageDialog(null, "Há campo(s) obrigatório(s) sem preenchimento.");
					textFieldTitulo.requestFocus();
				} else if (cadastroTopicoInternoControle.topicoJaExiste())
				{
					JOptionPane.showMessageDialog(null, "O título informado já está vinculado a um tópico.");
					textFieldTitulo.requestFocus();
				} else if (!cadastroTopicoInternoControle.limiteMensagensEValido())
				{
					JOptionPane.showConfirmDialog(null, "O limite de mensagens definido é menor do que o número de mensagens já existentes no tópico.");
					textFieldLimiteMensagens.requestFocus();
				} else
				{
					cadastroTopicoInternoControle.definirTopico();
					processado = true;
					dispose();
				}
			}
		};
	}
	
	private ActionListener aoClicarBotaoCancelar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				processado = false;
				dispose();
			}
		};
	}
}