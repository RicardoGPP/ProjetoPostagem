package pt.up.fc.lc.postagemservidor.relatorio;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de vis�o do relat�rio de subscri��es dos usu�rios.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class RelatorioSubscricoesUsuarioVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;
	
	private JLabel labelUsuario;
	private JComboBox<Usuario> comboBoxUsuario;
	
	/**
		Cria e inicializa a vis�o do relat�rio de subscri��es dos usu�rios.
	*/
	public RelatorioSubscricoesUsuarioVisao()
	{
		super("Relat�rio de subscri��es de usu�rio");
		RelatorioSubscricoesUsuarioControle relatorioControle = new RelatorioSubscricoesUsuarioControle(this);
		this.relatorioControle = relatorioControle;
		this.construirTela();
		this.vincularEventos();
		relatorioControle.carregarUsuarios();
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Define os componentes que diferem da estrutura original da vis�o.
	*/
	private void construirTela()
	{
		this.labelUsuario = new JLabel();
		this.labelUsuario.setText("Usu�rio:");
		this.labelUsuario.setBounds((LARGURA - BORDA - 310), (BORDA + 5), 50, 17);
		this.add(this.labelUsuario);
		
		this.comboBoxUsuario = new JComboBox<>();
		this.comboBoxUsuario.setBounds((LARGURA - BORDA - 255), BORDA, 150, 25);
		this.add(this.comboBoxUsuario);
	}
	
	/**
		Vincula eventos aos componentes extras da vis�o.
	*/
	private void vincularEventos()
	{
		this.comboBoxUsuario.addItemListener(this.aoMudarOpcaoComboBoxUsuarios());
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordena��o.
	*/
	protected void inicializarTabela()
	{	
		this.adicionarColunas("T�pico", "Favorito", "Mensagens");
	}
	
	/**
		Carrega o combobox de usu�rios da vis�o com a lista de usu�rios
		recebida por par�metro.
	
		@param Lista de usu�rios.
	*/
	public void definirUsuarios(List<Usuario> usuarios)
	{
		this.comboBoxUsuario.removeAllItems();
		for (Usuario usuario : usuarios)
			this.comboBoxUsuario.addItem(usuario);
	}
	
	/**
		Obt�m o usu�rio selecionado no combobox de usu�rios da vis�o.
		
		@return O usu�rio selecionado ou null se n�o houver sele��o.
	*/
	public Usuario obterSelecionado()
	{
		return (Usuario) this.comboBoxUsuario.getSelectedItem();
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento mudan�a de sele��o
		no combobox de usu�rios da vis�o.
		
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