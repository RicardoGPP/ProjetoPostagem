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

/**
	Classe da camada de vis�o do cadastro de t�pico interno.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
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
	
	/**
		Cria e inicializa a vis�o de cadastro de t�pico interno no modo
		de inclus�o.
	*/
	public CadastroTopicoInternoVisao()
	{
		this.cadastroTopicoInternoControle = new CadastroTopicoInternoControle(this);
		this.construirTela();
		this.vincularEventos();
		this.cadastroTopicoInternoControle.carregarCampos();
		this.setVisible(true);
	}
	
	/**
		Cria e inicializa a vis�o de cadastro de t�pico interno no modo
		de edi��o.
	*/
	public CadastroTopicoInternoVisao(Topico topico)
	{
		this.cadastroTopicoInternoControle = new CadastroTopicoInternoControle(this, topico);
		this.construirTela();
		this.vincularEventos();
		this.cadastroTopicoInternoControle.carregarCampos();
		this.setVisible(true);
	}
	
	/**
		Cria os componentes da vis�o, define seus respectivos tamanhos e
		posi��es e relaciona no padr�o composite.
	*/
	private void construirTela()
	{
		this.setResizable(false);
		this.setSize(LARGURA, ALTURA);
		this.setLayout(null);
		this.setModal(true);
		this.setLocationRelativeTo(null);				
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		this.labelTitulo = new JLabel();
		this.labelTitulo.setText("T�tulo:");
		this.labelTitulo.setBounds(BORDA, BORDA, (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelTitulo);
		
		this.textFieldTitulo = new JTextField();
		this.textFieldTitulo.setBounds(BORDA, (BORDA + 15), (LARGURA - (BORDA * 2) - 5), 20);
		this.add(this.textFieldTitulo);
		
		this.labelDescricao = new JLabel();
		this.labelDescricao.setText("Descri��o:");
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
	
	/**
		Vincula eventos aos componentes da vis�o.
	*/
	private void vincularEventos()
	{
		this.buttonOK.addActionListener(this.aoClicarBotaoOK());
		this.buttonCancelar.addActionListener(this.aoClicarBotaoCancelar());
	}
	
	/**
		Obt�m o t�tulo no componente correspondente da vis�o.
		
		@return O t�tulo do t�pico.
	*/
	public String obterTitulo()
	{
		return this.textFieldTitulo.getText();
	}
	
	/**
		Define o t�tulo no componente correspondente da vis�o. 
	
		@param O t�tulo do t�pico.
	 */
	public void definirTitulo(String titulo)
	{
		this.textFieldTitulo.setText(titulo);
	}
	
	/**
		Define se o componente do titulo ser� editavel. 
	
		@param Se o componente ser� edit�vel ou n�o.
	*/
	public void definirTituloEditavel(boolean editavel)
	{
		this.textFieldTitulo.setEditable(editavel);
	}
	
	/**
		Obt�m a descri��o no componente correspondente da vis�o.
		
		@return A descri��o do t�pico.
	*/
	public String obterDescricao()
	{
		return this.textAreaDescricao.getText();
	}
	
	/**
		Define a descri��o no componente correspondente da vis�o. 
	
		@param A descri��o do t�pico.
	 */
	public void definirDescricao(String descricao)
	{
		this.textAreaDescricao.setText(descricao);
	}
	
	/**
		Obt�m o limite de mensagens no componente correspondente da vis�o.
		
		@return O limite de mensagens do t�pico.
	*/
	public int obterLimiteMensagens()
	{
		try
		{
			return Integer.parseInt(this.textFieldLimiteMensagens.getText());
		} catch (Exception e)
		{
			return 0;
		}
	}
	
	/**
		Define o limite de mensagens no componente correspondente da vis�o. 
	
		@param O limite de mensagens do t�pico.
	 */
	public void definirLimiteMensagens(int limiteMensagens)
	{
		this.textFieldLimiteMensagens.setText(Integer.toString(limiteMensagens));
	}
	
	/**
		Obt�m o registro gerado pelo cadastro.
		
		@return Um t�pico.
	*/
	public Topico obterRegistro()
	{
		return this.cadastroTopicoInternoControle.getTopico();
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o "OK".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarBotaoOK()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!cadastroTopicoInternoControle.tudoPreenchido())
				{
					JOptionPane.showMessageDialog(null, "H� campo(s) obrigat�rio(s) sem preenchimento.");
					textFieldTitulo.requestFocus();
				} else if (cadastroTopicoInternoControle.topicoJaExiste())
				{
					JOptionPane.showMessageDialog(null, "O t�tulo informado j� est� vinculado a um t�pico.");
					textFieldTitulo.requestFocus();
				} else if (!cadastroTopicoInternoControle.limiteMensagensEValido())
				{
					JOptionPane.showConfirmDialog(null, "O limite de mensagens definido � menor do que o n�mero de mensagens j� existentes no t�pico.");
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
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o "Cancelar".
		
		@return Um handler ao evento.
	*/
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