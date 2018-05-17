package pt.up.fc.lc.postagemservidor.visao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagemservidor.controle.CadastroTopicoControle;

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
	
	public CadastroTopicoVisao()
	{
		this.cadastroTopicoControle = new CadastroTopicoControle(this);
		this.construirTela();
		this.vincularEventos();
		this.cadastroTopicoControle.carregarLista();
		this.setVisible(true);
	}
	
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
	
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
		this.buttonIncluir.addActionListener(this.aoClicarButtonIncluir());
		this.buttonEditar.addActionListener(this.aoClicarButtonEditar());
		this.buttonExcluir.addActionListener(this.aoClicarButtonExcluir());
	}
	
	public List<Topico> obterTopicos()
	{
		List<Topico> topicos = new ArrayList<>();
		for (int i = 0; i < this.listModelTopicos.size(); i++)
			topicos.add(this.listModelTopicos.get(i));
		return topicos;
	}
	
	public void definirTopicos(List<Topico> topicos)
	{
		this.listModelTopicos.clear();
		for (Topico topico : topicos)
			this.listModelTopicos.addElement(topico);
	}
	
	public Topico obterSelecionado()
	{
		return this.listTopicos.getSelectedValue();
	}
	
	public void incluirNaLista(Topico topico)
	{
		this.listModelTopicos.addElement(topico);
	}
	
	public void excluirDaLista(Topico topico)
	{
		this.listModelTopicos.removeElement(topico);
	}

	public ActionListener aoClicarButtonAtualizar()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				cadastroTopicoControle.carregarLista();
			}
		};
	}
	
	public ActionListener aoClicarButtonIncluir()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cadastroTopicoControle.incluir();
			}
		};
	}
	
	public ActionListener aoClicarButtonEditar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cadastroTopicoControle.editar();
			}
		};
	}
	
	public ActionListener aoClicarButtonExcluir()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if ((obterSelecionado() != null) &&
				   (JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?", "Confirmação", JOptionPane.YES_NO_OPTION) == 0))
					cadastroTopicoControle.excluir();
			}
		};
	}
}