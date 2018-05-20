package pt.up.fc.lc.postagemservidor.cadastro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de visão do cadastro de usuários.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class CadastroUsuarioVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 291;
	private static final int ALTURA = 400;
	private static final int BORDA = 15;
	
	private CadastroUsuarioControle cadastroUsuarioControle;
	
	private JButton buttonAtualizar;
	private JScrollPane scrollPaneListUsuarios;
	private DefaultListModel<Usuario> listModelUsuarios;
	private JList<Usuario> listUsuarios;	
	private JButton buttonIncluir;
	private JButton buttonEditar;
	private JButton buttonExcluir;

	/**
		Cria e inicializa a visão de cadastro de usuários, definindo
		o usuário logado para ser excluído da lista e impedir qualquer
		tipo de edição ou exclusão.
	*/
	public CadastroUsuarioVisao(Usuario logado)
	{
		this.cadastroUsuarioControle = new CadastroUsuarioControle(this, logado);
		this.construirTela();
		this.vincularEventos();
		this.cadastroUsuarioControle.carregarLista();
		this.setVisible(true);
	}
	
	/**
		Cria os componentes da visão, define seus respectivos tamanhos e
		posições e relaciona no padrão composite.
	*/
	private void construirTela()
	{
		this.setTitle("Cadastro de usuários");
		this.setResizable(false);
		this.setClosable(true);
		this.setSize(LARGURA, ALTURA);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		
		this.buttonAtualizar = new JButton();
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.setBounds((BORDA + 160), BORDA, 90, 25);
		this.add(this.buttonAtualizar);
		
		this.scrollPaneListUsuarios = new JScrollPane();
		this.scrollPaneListUsuarios.setBounds(BORDA, (BORDA + 30), (LARGURA - (BORDA * 2) - 10), 270);
		this.add(this.scrollPaneListUsuarios);
		
		this.listModelUsuarios = new DefaultListModel<>();
		this.listUsuarios = new JList<>(this.listModelUsuarios);
		this.scrollPaneListUsuarios.setViewportView(this.listUsuarios);
		
		this.buttonIncluir = new JButton();
		this.buttonIncluir.setText("Incluir");
		this.buttonIncluir.setBounds(BORDA, (BORDA + 305), 80, 25);
		this.add(this.buttonIncluir);
		
		this.buttonEditar = new JButton();
		this.buttonEditar.setText("Editar");
		this.buttonEditar.setBounds((BORDA + 85), (BORDA + 305), 80, 25);
		this.add(this.buttonEditar);
		
		this.buttonExcluir = new JButton();
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.setBounds((BORDA + 170), (BORDA + 305), 80, 25);
		this.add(this.buttonExcluir);
	}
	
	/**
		Vincula eventos aos componentes da visão.
	*/
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
		this.buttonIncluir.addActionListener(this.aoClicarButtonIncluir());
		this.buttonEditar.addActionListener(this.aoClicarButtonEditar());
		this.buttonExcluir.addActionListener(this.aoClicarButtonExcluir());
	}
	
	/**
		Define os usuários na lista da visão.
		
		@param Uma lista de usuários.
	*/
	public void definirUsuarios(List<Usuario> usuarios)
	{
		this.listModelUsuarios.clear();
		for (Usuario usuario : usuarios)
			this.listModelUsuarios.addElement(usuario);
	}

	/**
		Obtém o usuário selecionado na lista da visão.
		
		@return O usuário selecionado ou null se não houver seleção.
	*/
	public Usuario obterSelecionado()
	{
		return this.listUsuarios.getSelectedValue();
	}
	
	/**
		Exclui um usuário da lista.
		
		@param Um usuário.
	*/
	public void excluirDaLista(Usuario usuario)
	{
		this.listModelUsuarios.removeElement(usuario);
	}

	/**
		Define e retorna a ação aplicada sobre o evento do clique no botão
		"Atualizar".
		
		@return Um handler ao evento.
	*/	
	public ActionListener aoClicarButtonAtualizar()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				cadastroUsuarioControle.carregarLista();
			}
		};
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no botão
		"Incluir".
		
		@return Um handler ao evento.
	*/	
	public ActionListener aoClicarButtonIncluir()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cadastroUsuarioControle.incluir();
			}
		};
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no botão
		"Editar".
		
		@return Um handler ao evento.
	*/	
	public ActionListener aoClicarButtonEditar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cadastroUsuarioControle.editar();
			}
		};
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no botão
		"Excluir".
		
		@return Um handler ao evento.
	*/	
	public ActionListener aoClicarButtonExcluir()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if ((obterSelecionado() != null) &&
				   (JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?", "Confirmação", JOptionPane.YES_NO_OPTION) == 0))
					cadastroUsuarioControle.excluir();
			}
		};
	}
}