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
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do cadastro de usuário interno.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
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
	private JComboBox<Usuario.Grupo> comboBoxGrupo;
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
	
	/**
		Cria e inicializa a visão de cadastro de usuário interno no modo
		de inclusão.
	*/
	public CadastroUsuarioInternoVisao()
	{
		this.cadastroUsuarioInternoControle = new CadastroUsuarioInternoControle(this);
		this.construirTela();
		this.vincularEventos();
		this.cadastroUsuarioInternoControle.carregarCampos();
		this.setVisible(true);
	}
	
	/**
		Cria e inicializa a visão de cadastro de usuário interno no modo
		de edição.
	*/
	public CadastroUsuarioInternoVisao(Usuario usuario)
	{
		this.cadastroUsuarioInternoControle = new CadastroUsuarioInternoControle(this, usuario);
		this.construirTela();
		this.vincularEventos();
		this.cadastroUsuarioInternoControle.carregarCampos();
		this.setVisible(true);
	}
	
	/**
		Cria os componentes da visão, define seus respectivos tamanhos e
		posições e relaciona no padrão composite.
	*/
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
		for (Usuario.Grupo grupo : Usuario.Grupo.values())
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
	
	/**
		Vincula eventos aos componentes da visão.
	*/
	private void vincularEventos()
	{
		this.buttonOK.addActionListener(this.aoClicarBotaoOK());
		this.buttonCancelar.addActionListener(this.aoClicarBotaoCancelar());
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
		Define se o componente do nome de usuário será editavel. 
	
		@param Se o componente será editável ou não.
	*/
	public void definirNomeUsuarioEditavel(boolean editavel)
	{
		this.textFieldNomeUsuario.setEditable(editavel);
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
		Obtém o grupo no componente correspondente da visão.
		
		@return O grupo do usuário.
	*/
	public Usuario.Grupo obterGrupo()
	{
		return (Usuario.Grupo) this.comboBoxGrupo.getSelectedItem();
	}
	
	/**
		Define o grupo no componente correspondente da visão. 
	
		@param O grupo do usuário.
	 */
	public void definirGrupo(Usuario.Grupo grupo)
	{
		this.comboBoxGrupo.setSelectedItem(grupo);
	}
	
	/**
		Obtém o nome completo no componente correspondente da visão.
		
		@return O nome completo do usuário.
	*/
	public String obterNomeCompleto()
	{
		return this.textFieldNomeCompleto.getText();
	}
	
	/**
		Define o nome completo no componente correspondente da visão. 
	
		@param O nome completo do usuário.
	 */
	public void definirNomeCompleto(String nomeCompleto)
	{
		this.textFieldNomeCompleto.setText(nomeCompleto);
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
		Obtém o telefone no componente correspondente da visão.
		
		@return O telefone do usuário.
	*/
	public String obterTelefone()
	{
		return this.textFieldTelefone.getText();
	}
	
	/**
		Define o telefone no componente correspondente da visão. 
	
		@param O telefone do usuário.
	 */
	public void definirTelefone(String telefone)
	{
		this.textFieldTelefone.setText(telefone);
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
	public void definirDataNascimento(Date dataNascimento)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAO.FORMATO_DATA);
		this.textFieldDataNascimento.setText(simpleDateFormat.format(dataNascimento));
	}
	
	/**
		Obtém o limite de subscrições no componente correspondente da visão.
		
		@return O limite de subscrições do usuário.
	*/
	public int obterLimiteSubscricoes()
	{
		return Integer.parseInt(this.textFieldLimiteSubscricoes.getText());
	}
	
	/**
		Define o limite de subscrições no componente correspondente da visão. 
	
		@param O limite de subscrições do usuário.
	 */
	public void definirLimiteSubscricoes(int limiteSubscricoes)
	{
		this.textFieldLimiteSubscricoes.setText(Integer.toString(limiteSubscricoes));
	}
	
	/**
		Obtém a flag de ativo no componente correspondente da visão.
		
		@return Se o usuário está ativo ou não.
	*/
	public boolean obterAtivo()
	{
		return this.checkBoxAtivo.isSelected();
	}
	
	/**
		Define a flag de ativo no componente correspondente da visão. 
	
		@param Se o usuário está ativo ou não.
	 */
	public void definirAtivo(boolean ativo)
	{
		this.checkBoxAtivo.setSelected(ativo);
	}
	
	/**
		Obtém o registro gerado pelo cadastro.
		
		@return Um usuário.
	*/
	public Usuario obterRegistro()
	{
		return this.cadastroUsuarioInternoControle.getUsuario();
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no botão "OK".
		
		@return Um handler ao evento.
	*/
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
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no botão "Cancelar".
		
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