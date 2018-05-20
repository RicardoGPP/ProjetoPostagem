package pt.up.fc.lc.postagemservidor.relatorio;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de visão do relatório de subscrições dos usuários.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class RelatorioSubscricoesUsuarioVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	private JLabel labelUsuario;
	private JComboBox<Usuario> comboBoxUsuario;
	
	/**
		Cria e inicializa a visão do relatório de subscrições dos usuários.
	*/
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
	
	/**
		Define os componentes que diferem da estrutura original da visão.
	*/
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
	
	/**
		Vincula eventos aos componentes extras da visão.
	*/
	private void vincularEventos()
	{
		this.comboBoxUsuario.addItemListener(this.aoMudarOpcaoComboBoxUsuarios());
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordenação.
	*/
	protected void inicializarTabela()
	{	
		this.adicionarColunas("Tópico", "Favorito", "Mensagens");
	}
	
	/**
		Carrega o combobox de usuários da visão com a lista de usuários
		recebida por parâmetro.
	
		@param Lista de usuários.
	*/
	public void definirUsuarios(List<Usuario> usuarios)
	{
		this.comboBoxUsuario.removeAllItems();
		for (Usuario usuario : usuarios)
			this.comboBoxUsuario.addItem(usuario);
	}
	
	/**
		Obtém o usuário selecionado no combobox de usuários da visão.
		
		@return O usuário selecionado ou null se não houver seleção.
	*/
	public Usuario obterSelecionado()
	{
		return (Usuario) this.comboBoxUsuario.getSelectedItem();
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento mudança de seleção
		no combobox de usuários da visão.
		
		@return Um handler ao evento.
	*/
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