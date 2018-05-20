package pt.up.fc.lc.postagemcliente.nucleo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import pt.up.fc.lc.postagempersistencia.dao.DAO;

/**
	Classe da camada de visão do pedido de utilizador do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class PedidoUtilizadorVisao extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 300;
	private static final int ALTURA = 265;
	private static final int BORDA = 15;
	
	private PedidoUtilizadorControle pedidoUtilizadorControle;
	
	private JLabel labelNomeUsuario;
	private JTextField textFieldNomeUsuario;
	private JLabel labelSenha;
	private JPasswordField passwordFieldSenha;	
	private JLabel labelEmail;
	private JTextField textFieldEmail;
	private JLabel labelDataNascimento;
	private JTextField textFieldDataNascimento;
	private JButton buttonOK;
	
	/**
		Cria e inicializa a visão de gerenciamento de conta.
	*/
	public PedidoUtilizadorVisao()
	{
		this.pedidoUtilizadorControle = new PedidoUtilizadorControle(this);
		this.construirTela();
		this.vincularEventos();
		this.setVisible(true);
	}
	
	/**
		Cria os componentes da visão, define seus respectivos tamanhos e
		posições e relaciona no padrão composite.
	*/
	private void construirTela()
	{
		this.setTitle("Pedido de registro de utilizador");
		this.setResizable(false);
		this.setModal(true);
		this.setSize(LARGURA, ALTURA);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		this.labelNomeUsuario = new JLabel();
		this.labelNomeUsuario.setText("Nome de usuário:");
		this.labelNomeUsuario.setBounds(BORDA, BORDA, (LARGURA - (BORDA * 2) - 10), 17);
		this.add(this.labelNomeUsuario);
		
		this.textFieldNomeUsuario = new JTextField();
		this.textFieldNomeUsuario.setBounds(BORDA, (BORDA + 15), (LARGURA - (BORDA * 2) - 10), 20);
		this.add(this.textFieldNomeUsuario);
		
		this.labelSenha = new JLabel();
		this.labelSenha.setText("Senha:");
		this.labelSenha.setBounds(BORDA, (BORDA + 40), (LARGURA - (BORDA * 2) - 10), 17);
		this.add(this.labelSenha);
		
		this.passwordFieldSenha = new JPasswordField();
		this.passwordFieldSenha.setBounds(BORDA, (BORDA + 55), (LARGURA - (BORDA * 2) - 10), 20);
		this.add(this.passwordFieldSenha);		
		
		this.labelEmail = new JLabel();
		this.labelEmail.setText("E-mail:");
		this.labelEmail.setBounds(BORDA, (BORDA + 80), (LARGURA - (BORDA * 2) - 10), 17);
		this.add(this.labelEmail);
		
		this.textFieldEmail = new JTextField();
		this.textFieldEmail.setBounds(BORDA, (BORDA + 95), (LARGURA - (BORDA * 2) - 10), 20);
		this.add(this.textFieldEmail);
		
		this.labelDataNascimento = new JLabel();
		this.labelDataNascimento.setText("Data de nascimento:");
		this.labelDataNascimento.setBounds(BORDA, (BORDA + 120), (LARGURA - (BORDA * 2) - 10), 17);
		this.add(this.labelDataNascimento);
		
		this.textFieldDataNascimento = new JTextField();
		this.textFieldDataNascimento.setBounds(BORDA, (BORDA + 135), (LARGURA - (BORDA * 2) - 10), 20);
		this.add(this.textFieldDataNascimento);
		
		this.buttonOK = new JButton();
		this.buttonOK.setText("OK");
		this.buttonOK.setBounds((LARGURA - BORDA - 100), (BORDA + 175), 90, 25);
		this.add(this.buttonOK);	
	}
	
	/**
		Vincula eventos aos componentes da visão.
	*/
	private void vincularEventos()
	{
		this.buttonOK.addActionListener(this.aoClicarButtonOK());
	}
	
	/**
		Obtém o nome de usuário no componente correspondente da visão.
		
		@return O nome de usuário.
	*/
	public String obterNomeUsuario()
	{
		return this.textFieldNomeUsuario.getText();
	}
	
	/**
		Define o nome de usuário no componente correspondente da visão. 
	
		@param O nome de usuário.
	 */
	public void definirNomeUsuario(String nomeUsuario)
	{
		this.textFieldNomeUsuario.setText(nomeUsuario);
	}
	
	/**
		Obtém a senha no componente correspondente da visão.
		
		@return A senha do usuário.
	*/
	public String obterSenha()
	{
		return new String(this.passwordFieldSenha.getPassword());
	}
	
	/**
		Define a senha no componente correspondente da visão. 
	
		@param A senha do usuário.
	 */
	public void definirSenha(String senha)
	{
		this.passwordFieldSenha.setText(senha);
	}	
	
	/**
		Obtém o e-mail no componente correspondente da visão.
		
		@return O e-mail do usuário.
	*/
	public String obterEmail()
	{
		return this.textFieldEmail.getText();
	}
	
	/**
		Define o e-mail no componente correspondente da visão. 
	
		@param O e-mail do usuário.
	 */
	public void definirEmail(String email)
	{
		this.textFieldEmail.setText(email);
	}
	
	/**
		Obtém a data de nascimento no componente correspondente da visão.
		
		@return A data de nascimento do usuário.
	*/
	public Date obterDataNascimento()
	{
		try
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAO.FORMATO_DATA);
			return simpleDateFormat.parse(this.textFieldDataNascimento.getText().trim());
		} catch (ParseException e)
		{
			return null;
		}
	}
	
	/**
		Define a data de nascimento no componente correspondente da visão. 
	
		@param A data de nascimento do usuário.
	 */
	public void definirDataDeNascimento(Date dataNascimento)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAO.FORMATO_DATA);
		this.textFieldDataNascimento.setText(simpleDateFormat.format(dataNascimento));
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no botão "OK".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarButtonOK()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (!pedidoUtilizadorControle.tudoPreenchido())
				{
					JOptionPane.showMessageDialog(null, "Há campo(s) obrigatório(s) sem preenchimento.");
					textFieldNomeUsuario.requestFocus();
				} else if (pedidoUtilizadorControle.usuarioOuPedidoJaExiste())
				{
					JOptionPane.showMessageDialog(null, "Já existe um usuário ou um pedido de registro com o mesmo nome informado.");
					textFieldNomeUsuario.requestFocus();
				} else
				{
					if (pedidoUtilizadorControle.registrarPedido())
						JOptionPane.showMessageDialog(null, "O pedido de utilizador foi registrado com sucesso!");
					else
						JOptionPane.showMessageDialog(null, "Ocorreu um erro ao registrar o pedido de utilizador.");
					dispose();
				}					
			}
		};
	}
}