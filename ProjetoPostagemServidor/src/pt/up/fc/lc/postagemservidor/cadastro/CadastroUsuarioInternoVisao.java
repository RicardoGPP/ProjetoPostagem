package pt.up.fc.lc.postagemservidor.cadastro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import pt.up.fc.lc.postagempersistencia.dao.DAO;
import pt.up.fc.lc.postagempersistencia.entidades.Grupo;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class CadastroUsuarioInternoVisao extends CadastroInternoVisao<Usuario>
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 300;
	private static final int ALTURA = 430;
	private static final int BORDA = 15;
	
	private CadastroUsuarioInternoControle cadastroUsuarioInternoControle;
	
	private JLabel labelNomeUsuario;
	private JTextField textFieldNomeUsuario;
	private JLabel labelSenha;
	private JPasswordField passwordFieldSenha;
	private JLabel labelGrupo;
	private JComboBox<Grupo> comboBoxGrupo;
	private JLabel labelNomeCompleto;
	private JTextField textFieldNomeCompleto;
	private JLabel labelEmail;
	private JTextField textFieldEmail;
	private JLabel labelTelefone;
	private JTextField textFieldTelefone;
	private JLabel labelDataNascimento;
	private JTextField textFieldDataNascimento;
	private JLabel labelLimiteSubscricoes;
	private JTextField textFieldLimiteSubscricoes;
	private JCheckBox checkBoxAtivo;
	private JButton buttonOK;
	private JButton buttonCancelar;
	
	public CadastroUsuarioInternoVisao()
	{
		this.cadastroUsuarioInternoControle = new CadastroUsuarioInternoControle(this);
		this.construirTela();
		this.vincularEventos();
		this.cadastroUsuarioInternoControle.carregarCampos();
		this.setVisible(true);
	}
	
	public CadastroUsuarioInternoVisao(Usuario usuario)
	{
		this.cadastroUsuarioInternoControle = new CadastroUsuarioInternoControle(this, usuario);
		this.construirTela();
		this.vincularEventos();
		this.cadastroUsuarioInternoControle.carregarCampos();
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
		
		this.labelNomeUsuario = new JLabel();
		this.labelNomeUsuario.setText("Nome de usuário:");
		this.labelNomeUsuario.setBounds(BORDA, BORDA, (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelNomeUsuario);
		
		this.textFieldNomeUsuario = new JTextField();
		this.textFieldNomeUsuario.setBounds(BORDA, (BORDA + 15), (LARGURA - (BORDA * 2) - 5), 20);
		this.add(this.textFieldNomeUsuario);
		
		this.labelSenha = new JLabel();
		this.labelSenha.setText("Senha:");
		this.labelSenha.setBounds(BORDA, (BORDA + 40), (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelSenha);
		
		this.passwordFieldSenha = new JPasswordField();
		this.passwordFieldSenha.setBounds(BORDA, (BORDA + 55), (LARGURA - (BORDA * 2) - 5), 20);
		this.add(this.passwordFieldSenha);
		
		this.labelGrupo = new JLabel();
		this.labelGrupo.setText("Grupo:");
		this.labelGrupo.setBounds(BORDA, (BORDA + 80), (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelGrupo);
		
		this.comboBoxGrupo = new JComboBox<>();
		this.comboBoxGrupo.setBounds(BORDA, (BORDA + 95), (LARGURA - (BORDA * 2) - 5), 20);
		for (Grupo grupo : Grupo.values())
			this.comboBoxGrupo.addItem(grupo);
		this.add(this.comboBoxGrupo);
		
		this.labelNomeCompleto = new JLabel();
		this.labelNomeCompleto.setText("Nome completo:");
		this.labelNomeCompleto.setBounds(BORDA, (BORDA + 120), (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelNomeCompleto);
		
		this.textFieldNomeCompleto = new JTextField();
		this.textFieldNomeCompleto.setBounds(BORDA, (BORDA + 135), (LARGURA - (BORDA * 2) - 5), 20);
		this.add(this.textFieldNomeCompleto);
		
		this.labelEmail = new JLabel();
		this.labelEmail.setText("E-mail:");
		this.labelEmail.setBounds(BORDA, (BORDA + 160), (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelEmail);
		
		this.textFieldEmail = new JTextField();
		this.textFieldEmail.setBounds(BORDA, (BORDA + 175), (LARGURA - (BORDA * 2) - 5), 20);
		this.add(this.textFieldEmail);
		
		this.labelTelefone = new JLabel();
		this.labelTelefone.setText("Telefone:");
		this.labelTelefone.setBounds(BORDA, (BORDA + 200), (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelTelefone);
		
		this.textFieldTelefone = new JTextField();
		this.textFieldTelefone.setBounds(BORDA, (BORDA + 215), (LARGURA - (BORDA * 2) - 5), 20);
		this.add(this.textFieldTelefone);
		
		this.labelDataNascimento = new JLabel();
		this.labelDataNascimento.setText("Data de nascimento:");
		this.labelDataNascimento.setBounds(BORDA, (BORDA + 240), (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelDataNascimento);
		
		this.textFieldDataNascimento = new JTextField();
		this.textFieldDataNascimento.setBounds(BORDA, (BORDA + 255), (LARGURA - (BORDA * 2) - 5), 20);
		this.add(this.textFieldDataNascimento);
		
		this.labelLimiteSubscricoes = new JLabel();
		this.labelLimiteSubscricoes.setText("Limite de subscrições:");
		this.labelLimiteSubscricoes.setBounds(BORDA, (BORDA + 280), (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.labelLimiteSubscricoes);
		
		this.textFieldLimiteSubscricoes = new JTextField();
		this.textFieldLimiteSubscricoes.setBounds(BORDA, (BORDA + 295), (LARGURA - (BORDA * 2) - 5), 20);
		this.add(this.textFieldLimiteSubscricoes);
		
		this.checkBoxAtivo = new JCheckBox();
		this.checkBoxAtivo.setText("Ativo");
		this.checkBoxAtivo.setBounds((BORDA - 4), (BORDA + 320), (LARGURA - (BORDA * 2) - 5), 17);
		this.add(this.checkBoxAtivo);
		
		this.buttonCancelar = new JButton();
		this.buttonCancelar.setText("Cancelar");
		this.buttonCancelar.setBounds((LARGURA - BORDA - 95), 360, 90, 25);
		this.add(this.buttonCancelar);
		
		this.buttonOK = new JButton();
		this.buttonOK.setText("OK");
		this.buttonOK.setBounds((this.buttonCancelar.getX() - 85), 360, 80, 25);
		this.add(this.buttonOK);		
	}
	
	private void vincularEventos()
	{
		this.buttonOK.addActionListener(this.aoClicarBotaoOK());
		this.buttonCancelar.addActionListener(this.aoClicarBotaoCancelar());
	}
	
	public String obterNomeUsuario()
	{
		return this.textFieldNomeUsuario.getText();
	}
	
	public void definirNomeUsuario(String nomeUsuario)
	{
		this.textFieldNomeUsuario.setText(nomeUsuario);
	}
	
	public void definirNomeUsuarioEditavel(boolean editavel)
	{
		this.textFieldNomeUsuario.setEditable(editavel);
	}
	
	public String obterSenha()
	{
		return new String(this.passwordFieldSenha.getPassword());
	}
	
	public void definirSenha(String senha)
	{
		this.passwordFieldSenha.setText(senha);
	}
	
	public Grupo obterGrupo()
	{
		return (Grupo) this.comboBoxGrupo.getSelectedItem();
	}
	
	public void definirGrupo(Grupo grupo)
	{
		this.comboBoxGrupo.setSelectedItem(grupo);
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
	
	public void definirDataNascimento(Date dataNascimento)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAO.FORMATO_DATA);
		this.textFieldDataNascimento.setText(simpleDateFormat.format(dataNascimento));
	}
	
	public int obterLimiteSubscricoes()
	{
		return Integer.parseInt(this.textFieldLimiteSubscricoes.getText());
	}
	
	public void definirLimiteSubscricoes(int limiteSubscricoes)
	{
		this.textFieldLimiteSubscricoes.setText(Integer.toString(limiteSubscricoes));
	}
	
	public boolean obterAtivo()
	{
		return this.checkBoxAtivo.isSelected();
	}
	
	public void definirAtivo(boolean ativo)
	{
		this.checkBoxAtivo.setSelected(ativo);
	}
	
	public Usuario obterRegistro()
	{
		return this.cadastroUsuarioInternoControle.getUsuario();
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
				if (!cadastroUsuarioInternoControle.tudoPreenchido())
				{
					JOptionPane.showMessageDialog(null, "Há campo(s) obrigatório(s) sem preenchimento.");
					textFieldNomeUsuario.requestFocus();
				} else if (cadastroUsuarioInternoControle.usuarioJaExiste())
				{
					JOptionPane.showMessageDialog(null, "O nome de usuário informado já existe.");
					textFieldNomeUsuario.requestFocus();
				} else if (!cadastroUsuarioInternoControle.limiteSubscricoesEValido())
				{
					JOptionPane.showConfirmDialog(null, "O limite de subscrições definido é menor do que o número de subscrições já vinculadas ao usuário.");
					textFieldLimiteSubscricoes.requestFocus();
				} else
				{
					cadastroUsuarioInternoControle.definirUsuario();
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