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
	Classe da camada de vis�o do cadastro de usu�rios.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
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
		Cria e inicializa a vis�o de cadastro de usu�rios, definindo
		o usu�rio logado para ser exclu�do da lista e impedir qualquer
		tipo de edi��o ou exclus�o.
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
		Cria os componentes da vis�o, define seus respectivos tamanhos e
		posi��es e relaciona no padr�o composite.
	*/
	private void construirTela()
	{
		this.setTitle("Cadastro de usu�rios");
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
		Vincula eventos aos componentes da vis�o.
	*/
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
		this.buttonIncluir.addActionListener(this.aoClicarButtonIncluir());
		this.buttonEditar.addActionListener(this.aoClicarButtonEditar());
		this.buttonExcluir.addActionListener(this.aoClicarButtonExcluir());
	}
	
	/**
		Define os usu�rios na lista da vis�o.
		
		@param Uma lista de usu�rios.
	*/
	public void definirUsuarios(List<Usuario> usuarios)
	{
		this.listModelUsuarios.clear();
		for (Usuario usuario : usuarios)
			this.listModelUsuarios.addElement(usuario);
	}

	/**
		Obt�m o usu�rio selecionado na lista da vis�o.
		
		@return O usu�rio selecionado ou null se n�o houver sele��o.
	*/
	public Usuario obterSelecionado()
	{
		return this.listUsuarios.getSelectedValue();
	}
	
	/**
		Inclui um usu�rio na lista.
		
		@param Um usu�rio.
	 */
	public void incluirNaLista(Usuario usuario)
	{
		this.listModelUsuarios.addElement(usuario);
	}
	
	/**
		Exclui um usu�rio da lista.
		
		@param Um usu�rio.
	*/
	public void excluirDaLista(Usuario usuario)
	{
		this.listModelUsuarios.removeElement(usuario);
	}

	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
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
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
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
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
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
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
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
				   (JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?", "Confirma��o", JOptionPane.YES_NO_OPTION) == 0))
					cadastroUsuarioControle.excluir();
			}
		};
	}
}