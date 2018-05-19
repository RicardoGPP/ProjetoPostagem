package pt.up.fc.lc.postagemservidor.relatorio;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class RelatorioSubscricoesUsuarioVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	private JLabel labelUsuario;
	private JComboBox<Usuario> comboBoxUsuario;
	
	public RelatorioSubscricoesUsuarioVisao()
	{
		super("Relatório de subscrições de usuário");
		RelatorioSubscricoesUsuarioControle relatorioControle = new RelatorioSubscricoesUsuarioControle(this);
		this.relatorioControle = relatorioControle;
		this.construirTela();
		this.vincularEventos();
		relatorioControle.carregarUsuarios();
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	private void construirTela()
	{
		this.labelUsuario = new JLabel();
		this.labelUsuario.setText("Usuário:");
		this.labelUsuario.setBounds((LARGURA - BORDA - 310), (BORDA + 5), 50, 17);
		this.add(this.labelUsuario);
		
		this.comboBoxUsuario = new JComboBox<>();
		this.comboBoxUsuario.setBounds((LARGURA - BORDA - 255), BORDA, 150, 25);
		this.add(this.comboBoxUsuario);
	}
	
	private void vincularEventos()
	{
		this.comboBoxUsuario.addItemListener(this.aoMudarOpcaoComboBoxUsuarios());
	}
	
	protected void inicializarTabela()
	{	
		this.adicionarColunas("Tópico", "Favorito", "Mensagens");
	}
	
	public void adicionarUsuario(Usuario usuario)
	{
		this.comboBoxUsuario.addItem(usuario);
	}
	
	public Usuario obterSelecionado()
	{
		return (Usuario) this.comboBoxUsuario.getSelectedItem();
	}
	
	private ItemListener aoMudarOpcaoComboBoxUsuarios()
	{
		return new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				relatorioControle.carregarTabela();
			}
		};
	}
}