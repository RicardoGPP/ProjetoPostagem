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
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

/**
	Classe da camada de vis�o do cadastro de t�picos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class CadastroTopicoVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 291;
	private static final int ALTURA = 400;
	private static final int BORDA = 15;
	
	private CadastroTopicoControle cadastroTopicoControle;
	
	private JButton buttonAtualizar;
	private JScrollPane scrollPaneListTopicos;
	private DefaultListModel<Topico> listModelTopicos;
	private JList<Topico> listTopicos;	
	private JButton buttonIncluir;
	private JButton buttonEditar;
	private JButton buttonExcluir;
	
	/**
		Cria e inicializa a vis�o de cadastro de t�picos.
	*/
	public CadastroTopicoVisao()
	{
		this.cadastroTopicoControle = new CadastroTopicoControle(this);
		this.construirTela();
		this.vincularEventos();
		this.cadastroTopicoControle.carregarLista();
		this.setVisible(true);
	}
	
	/**
		Cria os componentes da vis�o, define seus respectivos tamanhos e
		posi��es e relaciona no padr�o composite.
	*/
	private void construirTela()
	{
		this.setTitle("Cadastro de t�picos");
		this.setResizable(false);
		this.setClosable(true);
		this.setSize(LARGURA, ALTURA);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		
		this.buttonAtualizar = new JButton();
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.setBounds((BORDA + 160), BORDA, 90, 25);
		this.add(this.buttonAtualizar);
		
		this.scrollPaneListTopicos = new JScrollPane();
		this.scrollPaneListTopicos.setBounds(BORDA, (BORDA + 30), (LARGURA - (BORDA * 2) - 10), 270);
		this.add(this.scrollPaneListTopicos);
		
		this.listModelTopicos = new DefaultListModel<>();
		this.listTopicos = new JList<>(this.listModelTopicos);
		this.scrollPaneListTopicos.setViewportView(this.listTopicos);
		
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
		Define os t�picos na lista da vis�o.
		
		@param Uma lista de t�picos.
	*/
	public void definirTopicos(List<Topico> topicos)
	{
		this.listModelTopicos.clear();
		for (Topico topico : topicos)
			this.listModelTopicos.addElement(topico);
	}
	
	/**
		Obt�m o t�pico selecionado na lista da vis�o.
		
		@return O t�pico selecionado ou null se n�o houver sele��o.
	*/
	public Topico obterSelecionado()
	{
		return this.listTopicos.getSelectedValue();
	}
	
	/**
		Inclui um t�pico na lista.
		
		@param Um t�pico.
	*/
	public void incluirNaLista(Topico topico)
	{
		this.listModelTopicos.addElement(topico);
	}
	
	/**
		Exclui um t�pico da lista.
		
		@param Um t�pico.
	*/
	public void excluirDaLista(Topico topico)
	{
		this.listModelTopicos.removeElement(topico);
	}

	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
		"Atualizar".
		
		@return Um handler ao evento.
	*/	
	private ActionListener aoClicarButtonAtualizar()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				cadastroTopicoControle.carregarLista();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
		"Incluir".
		
		@return Um handler ao evento.
	*/	
	private ActionListener aoClicarButtonIncluir()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cadastroTopicoControle.incluir();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
		"Editar".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarButtonEditar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cadastroTopicoControle.editar();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
		"Excluir".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarButtonExcluir()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if ((obterSelecionado() != null) &&
				   (JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?", "Confirma��o", JOptionPane.YES_NO_OPTION) == 0))
					cadastroTopicoControle.excluir();
			}
		};
	}
}