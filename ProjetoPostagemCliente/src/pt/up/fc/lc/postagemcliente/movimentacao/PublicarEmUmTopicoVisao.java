package pt.up.fc.lc.postagemcliente.movimentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de vis�o da publica��o em um t�pico.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class PublicarEmUmTopicoVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 300;
	private static final int ALTURA = 330;
	private static final int BORDA = 15;
	
	private PublicarEmUmTopicoControle publicarEmUmTopicoControle;
	
	private JLabel labelTopico;
	private JComboBox<Topico> comboBoxTopico;	
	private JLabel labelMensagem;
	private JScrollPane scrollPaneMensagem;
	private JTextArea textAreaMensagem;
	private JButton buttonPublicar;
	
	/**
		Cria e inicializa a vis�o da publica��o em um t�pico.
		
		@param O usu�rio logado.
	*/
	public PublicarEmUmTopicoVisao(Usuario logado)
	{
		this.publicarEmUmTopicoControle = new PublicarEmUmTopicoControle(this, logado);
		this.construirTela();
		this.vincularEventos();
		this.publicarEmUmTopicoControle.carregarTopicos();
		this.setVisible(true);
	}
	
	/**
		Cria os componentes da vis�o, define seus respectivos tamanhos e
		posi��es e relaciona no padr�o composite.
	*/
	private void construirTela()
	{
		this.setTitle("Publicar em um t�pico");
		this.setResizable(false);
		this.setClosable(true);
		this.setSize(LARGURA, ALTURA);
		this.setLayout(null);				
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		this.labelTopico = new JLabel();
		this.labelTopico.setText("T�pico:");
		this.labelTopico.setBounds(BORDA, BORDA, (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelTopico);
		
		this.comboBoxTopico = new JComboBox<>();
		this.comboBoxTopico.setBounds(BORDA, (BORDA + 15), (LARGURA - (BORDA * 2) - 5), 20);
		this.add(this.comboBoxTopico);
		
		this.labelMensagem = new JLabel();
		this.labelMensagem.setText("Mensagem:");
		this.labelMensagem.setBounds(BORDA, (BORDA + 40), (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelMensagem);
		
		this.scrollPaneMensagem = new JScrollPane();
		this.scrollPaneMensagem.setBounds(BORDA, (BORDA + 55), (LARGURA - (BORDA * 2) - 5), 180);
		this.add(this.scrollPaneMensagem);
		
		this.textAreaMensagem = new JTextArea();
		this.textAreaMensagem.setLineWrap(true);
		this.textAreaMensagem.getDocument().putProperty("filterNewLines", Boolean.TRUE);		
		this.scrollPaneMensagem.setViewportView(this.textAreaMensagem);
		
		this.buttonPublicar = new JButton();
		this.buttonPublicar.setText("Publicar");
		this.buttonPublicar.setBounds((LARGURA - BORDA - 95), 260, 90, 25);
		this.add(this.buttonPublicar);
	}
	
	/**
		Vincula eventos aos componentes da vis�o.
	*/
	private void vincularEventos()
	{
		this.buttonPublicar.addActionListener(this.aoClicarButtonPublicar());
	}
	
	/**
		Obt�m o t�pico no componente correspondente da vis�o.
		
		@return O t�pico selecionado ou null caso n�o haja sele��o.
	*/
	public Topico obterTopico()
	{
		return (Topico) this.comboBoxTopico.getSelectedItem();
	}
	
	/**
		Carrega os t�picos no combobox de t�picos.
		
		@param Uma lista de t�picos.
	*/
	public void definirTopicos(List<Topico> topicos)
	{
		this.comboBoxTopico.removeAllItems();
		for (Topico topico : topicos)
			this.comboBoxTopico.addItem(topico);			
	}
	
	/**
		Obt�m a mensagem no componente correspondente da vis�o.
		
		@return O e-mail do usu�rio.
	*/
	public String obterMensagem()
	{
		return this.textAreaMensagem.getText();
	}
	
	/**
		Define a mensagem no componente correspondente da vis�o.
		
		@param A mensagem a ser definida.
	*/
	public void definirMensagem(String mensagem)
	{
		this.textAreaMensagem.setText(mensagem);
	}
	
	/**
		Limpa todos os campos da tela.
	*/
	private void limparCampos()
	{
		this.comboBoxTopico.setSelectedItem(null);
		this.textAreaMensagem.setText("");
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
		"Publicar".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarButtonPublicar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!publicarEmUmTopicoControle.tudoPreenchido())
					JOptionPane.showMessageDialog(null, "H� campo(s) obrigat�rio(s) sem preenchimento.");
				else if (!publicarEmUmTopicoControle.publicar())
					JOptionPane.showMessageDialog(null, "Ocorreu um erro ao publicar sua mensagem.");
				else
				{
					JOptionPane.showMessageDialog(null, "Sua mensagem foi publicada com sucesso!");
					limparCampos();
				}
				comboBoxTopico.requestFocus();
			}
		};
	}
}