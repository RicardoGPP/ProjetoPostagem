package pt.up.fc.lc.postagemservidor.arquivo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import pt.up.fc.lc.postagempersistencia.dao.DAO;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class GerenciarContaVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 300;
	private static final int ALTURA = 305;
	private static final int BORDA = 15;
	
	private GerenciarContaControle gerenciarContaControle;
	
	private JLabel labelSenha;
	private JPasswordField passwordFieldSenha;
	private JLabel labelNomeCompleto;
	private JTextField textFieldNomeCompleto;
	private JLabel labelEmail;
	private JTextField textFieldEmail;
	private JLabel labelTelefone;
	private JTextField textFieldTelefone;
	private JLabel labelDataNascimento;
	private JTextField textFieldDataNascimento;
	private JButton buttonSalvar;
	
	public GerenciarContaVisao(Usuario usuario)
	{
		this.gerenciarContaControle = new GerenciarContaControle(this, usuario);
		this.construirTela();
		this.vincularEventos();
		this.gerenciarContaControle.carregarCampos();
		this.setVisible(true);
	}
	
	private void construirTela()
	{
		this.setTitle("Gerenciamento de conta");
		this.setClosable(true);
		this.setResizable(false);
		this.setSize(LARGURA, ALTURA);
		this.setLayout(null);				
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		this.labelSenha = new JLabel();
		this.labelSenha.setText("Senha:");
		this.labelSenha.setBounds(BORDA, BORDA, (LARGURA - (BORDA * 2) - 10), 17);
		this.add(this.labelSenha);
		
		this.passwordFieldSenha = new JPasswordField();
		this.passwordFieldSenha.setBounds(BORDA, (BORDA + 15), (LARGURA - (BORDA * 2) - 10), 20);
		this.add(this.passwordFieldSenha);
		
		this.labelNomeCompleto = new JLabel();
		this.labelNomeCompleto.setText("Nome completo:");
		this.labelNomeCompleto.setBounds(BORDA, (BORDA + 40), (LARGURA - (BORDA * 2) - 10), 17);
		this.add(this.labelNomeCompleto);
		
		this.textFieldNomeCompleto = new JTextField();
		this.textFieldNomeCompleto.setBounds(BORDA, (BORDA + 55), (LARGURA - (BORDA * 2) - 10), 20);
		this.add(this.textFieldNomeCompleto);
		
		this.labelEmail = new JLabel();
		this.labelEmail.setText("E-mail:");
		this.labelEmail.setBounds(BORDA, (BORDA + 80), (LARGURA - (BORDA * 2) - 10), 17);
		this.add(this.labelEmail);
		
		this.textFieldEmail = new JTextField();
		this.textFieldEmail.setBounds(BORDA, (BORDA + 95), (LARGURA - (BORDA * 2) - 10), 20);
		this.add(this.textFieldEmail);
		
		this.labelTelefone = new JLabel();
		this.labelTelefone.setText("Telefone:");
		this.labelTelefone.setBounds(BORDA, (BORDA + 120), (LARGURA - (BORDA * 2) - 10), 17);
		this.add(this.labelTelefone);
		
		this.textFieldTelefone = new JTextField();
		this.textFieldTelefone.setBounds(BORDA, (BORDA + 135), (LARGURA - (BORDA * 2) - 10), 20);
		this.add(this.textFieldTelefone);
		
		this.labelDataNascimento = new JLabel();
		this.labelDataNascimento.setText("Data de nascimento:");
		this.labelDataNascimento.setBounds(BORDA, (BORDA + 160), (LARGURA - (BORDA * 2) - 10), 17);
		this.add(this.labelDataNascimento);
		
		this.textFieldDataNascimento = new JTextField();
		this.textFieldDataNascimento.setBounds(BORDA, (BORDA + 175), (LARGURA - (BORDA * 2) - 10), 20);
		this.add(this.textFieldDataNascimento);
		
		this.buttonSalvar = new JButton();
		this.buttonSalvar.setText("Salvar");
		this.buttonSalvar.setBounds((LARGURA - BORDA - 100), (BORDA + 215), 90, 25);
		this.add(this.buttonSalvar);	
	}
	
	private void vincularEventos()
	{
		this.buttonSalvar.addActionListener(this.aoClicarButtonSalvar());
	}
	
	public String obterSenha()
	{
		return new String(this.passwordFieldSenha.getPassword());
	}
	
	public void definirSenha(String senha)
	{
		this.passwordFieldSenha.setText(senha);
	}
	
	public String obterNomeCompleto()
	{
		return this.textFieldNomeCompleto.getText();
	}
	
	public void definirNomeCompleto(String nomeCompleto)
	{
		this.textFieldNomeCompleto.setText(nomeCompleto);
	}
	
	public String obterEmail()
	{
		return this.textFieldEmail.getText();
	}
	
	public void definirEmail(String email)
	{
		this.textFieldEmail.setText(email);
	}
	
	public String obterTelefone()
	{
		return this.textFieldTelefone.getText();
	}
	
	public void definirTelefone(String telefone)
	{
		this.textFieldTelefone.setText(telefone);
	}
	
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
	
	public void definirDataDeNascimento(Date dataNascimento)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAO.FORMATO_DATA);
		this.textFieldDataNascimento.setText(simpleDateFormat.format(dataNascimento));
	}
	
	private ActionListener aoClicarButtonSalvar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (!gerenciarContaControle.tudoPreenchido())
				{
					JOptionPane.showMessageDialog(null, "Há campo(s) obrigatório(s) sem preenchimento.");
					textFieldNomeCompleto.requestFocus();
				} else if (gerenciarContaControle.senhaEstaDiferente())
				{
					JOptionPane.showMessageDialog(null, "A senha informada diverge da senha anteriormente cadastrada. É necessária uma nova autenticação para dar prosseguimento ao processo.");					
					if (!gerenciarContaControle.autenticar())
						JOptionPane.showMessageDialog(null, "Não é possível atualizar os dados porque a autenticação falhou.");
					else if (gerenciarContaControle.salvar())
						JOptionPane.showMessageDialog(null, "Os dados foram atualizados com sucesso!");
					else
						JOptionPane.showMessageDialog(null, "Não foi possível atualizar os dados da conta.");					
				} else
				{
					if (gerenciarContaControle.salvar())
						JOptionPane.showMessageDialog(null, "Os dados foram atualizados com sucesso!");
					else
						JOptionPane.showMessageDialog(null, "Não foi possível atualizar os dados da conta.");
				}
				passwordFieldSenha.requestFocus();
			}
		};
	}
}