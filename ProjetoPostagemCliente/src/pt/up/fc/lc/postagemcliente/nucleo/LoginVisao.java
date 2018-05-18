package pt.up.fc.lc.postagemcliente.nucleo;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginVisao extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 300;
	private static final int ALTURA = 170;
	private static final int BORDA = 15;	
	
	private LoginControle loginControle;
	private Autenticavel autenticavel;
	
	private JLabel labelUsuario;
	private JLabel labelSenha;
	private JTextField textFieldNomeUsuario;
	private JPasswordField passwordFieldSenha;
	private JButton buttonOK;
	private JButton buttonPedirUtilizador;
	
	public LoginVisao(Autenticavel autenticavel)
	{		
		this.loginControle = new LoginControle(this);
		this.autenticavel = autenticavel;		
		this.construirTela(null);
		this.vincularEventos();		
		this.setVisible(true);
		this.textFieldNomeUsuario.requestFocus();
	}
	
	public LoginVisao(Autenticavel autenticavel, Usuario usuario)
	{
		this.loginControle = new LoginControle(this);
		this.autenticavel = autenticavel;		
		this.construirTela(usuario);
		this.vincularEventos();
		this.buttonPedirUtilizador.setVisible(false);
		this.setVisible(true);
		this.passwordFieldSenha.requestFocus();
	}

	private void construirTela(Usuario usuario)
	{
		this.setTitle("Autenticação");
		this.setSize(LARGURA, ALTURA);
		this.setResizable(false);
		this.setModal(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.labelUsuario = new JLabel();
		this.labelUsuario.setText("Nome de usuário:");
		this.labelUsuario.setBounds(BORDA, BORDA, (LARGURA - (BORDA * 2) - 5), 10);
		this.add(this.labelUsuario);
		
		this.textFieldNomeUsuario = new JTextField();
		this.textFieldNomeUsuario.setBounds(BORDA, (BORDA + 15), (LARGURA - (BORDA * 2) - 5), 20);
		if (usuario != null)
		{
			this.textFieldNomeUsuario.setText(usuario.getNomeUsuario());
			this.textFieldNomeUsuario.setEditable(false);
		}
		this.add(this.textFieldNomeUsuario);
		
		this.labelSenha = new JLabel();
		this.labelSenha.setText("Senha:");
		this.labelSenha.setBounds(BORDA, (BORDA + 40), (LARGURA - (BORDA * 2) - 5), 10);
		this.add(this.labelSenha);
		
		this.passwordFieldSenha = new JPasswordField();
		this.passwordFieldSenha.setBounds(BORDA, (BORDA + 55), (LARGURA - (BORDA * 2) - 5), 20);
		this.add(this.passwordFieldSenha);
		
		this.buttonOK = new JButton();
		this.buttonOK.setText("OK");
		this.buttonOK.setBounds((LARGURA - (BORDA * 2) - 71), (BORDA + 85), 80, 25);
		this.add(this.buttonOK);
		
		this.buttonPedirUtilizador = new JButton();
		this.buttonPedirUtilizador.setText("Pedir utilizador");
		this.buttonPedirUtilizador.setBounds((LARGURA - (BORDA * 2) - 196), (BORDA + 85), 120, 25);
		this.add(this.buttonPedirUtilizador);
	}
	
	private void vincularEventos()
	{
		this.buttonOK.addActionListener(this.aoClicarButtonOK());
		this.buttonPedirUtilizador.addActionListener(this.aoClicarButtonPedirUtilizador());
	}

	public void definirNomeUsuario(String nomeUsuario)
	{
		this.textFieldNomeUsuario.setText(nomeUsuario);
	}
	
	public String obterNomeUsuario()
	{
		return this.textFieldNomeUsuario.getText();
	}
	
	public void definirSenha(String senha)
	{
		this.passwordFieldSenha.setText(senha);
	}
	
	public String obterSenha()
	{
		return new String(this.passwordFieldSenha.getPassword());
	}
	
	private ActionListener aoClicarButtonOK()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				if (!loginControle.tudoPreenchido())
				{
					JOptionPane.showMessageDialog(null, "Há campo(s) obrigatório(s) sem preenchimento.");
					textFieldNomeUsuario.requestFocus();
				} else if (!loginControle.entradaPermitida())
				{
					JOptionPane.showMessageDialog(null, "Nome de usuário e/ou senha inválidos.");
					passwordFieldSenha.setText("");
					textFieldNomeUsuario.requestFocus();
				} else
				{
					loginControle.definirUsuario(autenticavel);
					dispose();
				}
			}
		};
	}
	
	private ActionListener aoClicarButtonPedirUtilizador()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				loginControle.abrirPedidoDeUtilizador();
			}
		};
	}
}