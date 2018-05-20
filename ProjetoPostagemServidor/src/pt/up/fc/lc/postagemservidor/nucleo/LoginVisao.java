package pt.up.fc.lc.postagemservidor.nucleo;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
	Classe da camada de vis�o do login do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class LoginVisao extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 300;
	private static final int ALTURA = 170;
	private static final int BORDA = 15;	
	
	private LoginControle loginControle;
	
	private JLabel labelUsuario;
	private JLabel labelSenha;
	private JTextField textFieldNomeUsuario;
	private JPasswordField passwordFieldSenha;
	private JButton buttonOK;	
	
	/**
		Cria e inicializa a vis�o de login do sistema no modo de
		inser��o completa, isto �, o usu�rio ter� de fornecer um nome
		e uma senha para se autenticar. No fim do processo de
		autentica��o, o usu�rio � definido no objeto autentic�vel. 
		
		@param Um objeto que pode requisitar autentica��o.
	*/
	public LoginVisao(Autenticavel autenticavel)
	{		
		this.loginControle = new LoginControle(this, autenticavel);		
		this.construirTela(null);
		this.vincularEventos();		
		this.setVisible(true);
	}
	
	/**
		Cria e inicializa a vis�o de login do sistema no modo de
		inser��o parcial, isto �, o usu�rio ter� de fornecer somente
		a senha, pois o nome de usu�rio ser� carregado atrav�s do usu�rio
		fornecido no par�metro. No fim do processo de autentica��o, o usu�rio
		� definido no objeto autentic�vel. 
		
		@param Um objeto que pode requisitar autentica��o.
	*/
	public LoginVisao(Autenticavel autenticavel, Usuario usuario)
	{
		this.loginControle = new LoginControle(this, autenticavel);
		this.construirTela(usuario);
		this.vincularEventos();		
		this.setVisible(true);
		this.passwordFieldSenha.requestFocus();
	}

	/**
		Cria os componentes da vis�o, define seus respectivos tamanhos e
		posi��es e relaciona no padr�o composite.
	*/
	private void construirTela(Usuario usuario)
	{
		this.setTitle("Autentica��o");
		this.setSize(LARGURA, ALTURA);
		this.setResizable(false);
		this.setModal(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.labelUsuario = new JLabel();
		this.labelUsuario.setText("Nome de usu�rio:");
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
	}
	
	/**
		Vincula eventos aos componentes da vis�o.
	*/
	private void vincularEventos()
	{
		this.buttonOK.addActionListener(this.aoClicarButtonOK());
	}

	/**
		Define o nome de usu�rio no componente correspondente da vis�o. 
		
		@param O nome de usu�rio.
	*/
	public void definirNomeUsuario(String nomeUsuario)
	{
		this.textFieldNomeUsuario.setText(nomeUsuario);
	}
	
	/**
		Obt�m o nome de usu�rio no componente correspondente da vis�o.
		
		@return O nome de usu�rio.
	*/
	public String obterNomeUsuario()
	{
		return this.textFieldNomeUsuario.getText();
	}
	
	/**
		Define a senha no componente correspondente da vis�o. 
	
		@param O nome de usu�rio.
	 */
	public void definirSenha(String senha)
	{
		this.passwordFieldSenha.setText(senha);
	}
	
	/**
		Obt�m a senha no componente correspondente da vis�o.
		
		@return A senha do usu�rio.
	*/
	public String obterSenha()
	{
		return new String(this.passwordFieldSenha.getPassword());
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o "OK".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarButtonOK()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				if (!loginControle.tudoPreenchido())
				{
					JOptionPane.showMessageDialog(null, "H� campo(s) obrigat�rio(s) sem preenchimento.");
					textFieldNomeUsuario.requestFocus();
				} else if (!loginControle.usuarioFoiAutenticado())
				{
					JOptionPane.showMessageDialog(null, "Nome de usu�rio e/ou senha inv�lidos.");
					passwordFieldSenha.setText("");
					textFieldNomeUsuario.requestFocus();
				} else
				{
					loginControle.definirUsuario();
					dispose();
				}
			}
		};
	}
}