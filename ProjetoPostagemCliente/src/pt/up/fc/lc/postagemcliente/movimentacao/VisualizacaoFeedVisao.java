package pt.up.fc.lc.postagemcliente.movimentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class VisualizacaoFeedVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 900;
	private static final int ALTURA = 600;
	private static final int BORDA = 15;
	
	private VisualizacaoFeedControle visualizacaoFeedControle;
	
	private JButton buttonAtualizar;
	private JScrollPane scrollPaneListComentarios;
	private DefaultListModel<Comentario> listModelComentarios;
	private JList<Comentario> listComentarios;
	private JButton buttonCurtirDescurtir;
	
	public VisualizacaoFeedVisao(Usuario logado)
	{
		this.visualizacaoFeedControle = new VisualizacaoFeedControle(this, logado);
		this.construirTela();
		this.vincularEventos();
		this.visualizacaoFeedControle.carregarLista();
		this.setVisible(true);
	}
	
	private void construirTela()
	{
		this.setTitle("Feed de mensagens");
		this.setResizable(false);
		this.setClosable(true);
		this.setSize(LARGURA, ALTURA);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		
		this.buttonAtualizar = new JButton();
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.setBounds((BORDA + 769), BORDA, 90, 25);
		this.add(this.buttonAtualizar);
		
		this.scrollPaneListComentarios = new JScrollPane();
		this.scrollPaneListComentarios.setBounds(BORDA, (BORDA + 30), (LARGURA - (BORDA * 2) - 10), 470);
		this.add(this.scrollPaneListComentarios);
		
		this.listModelComentarios = new DefaultListModel<>();
		this.listComentarios = new JList<>(this.listModelComentarios);
		this.scrollPaneListComentarios.setViewportView(this.listComentarios);
		
		this.buttonCurtirDescurtir = new JButton();
		this.buttonCurtirDescurtir.setText("Curtir");
		this.buttonCurtirDescurtir.setBounds((BORDA + 769), (BORDA + 505), 90, 25);
		this.add(this.buttonCurtirDescurtir);
	}
	
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
		this.buttonCurtirDescurtir.addActionListener(this.aoClicarButtonCurtirDescurtir());
		this.listComentarios.addListSelectionListener(this.aoSelecionarItemNaLista());
	}
	
	public List<Comentario> obterComentarios()
	{
		List<Comentario> comentarios = new ArrayList<>();
		for (int i = 0; i < this.listModelComentarios.size(); i++)
			comentarios.add(this.listModelComentarios.get(i));
		return comentarios;
	}
	
	public void definirComentarios(List<Comentario> comentarios)
	{
		this.listModelComentarios.clear();
		for (Comentario comentario : comentarios)
			this.listModelComentarios.addElement(comentario);
	}
	
	public Comentario obterSelecionado()
	{
		return this.listComentarios.getSelectedValue();
	}
	
	public void definirTextoBotaoCurtir(String texto)
	{
		this.buttonCurtirDescurtir.setText(texto);
	}

	public ActionListener aoClicarButtonAtualizar()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				visualizacaoFeedControle.carregarLista();
			}
		};
	}
	
	public ActionListener aoClicarButtonCurtirDescurtir()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				visualizacaoFeedControle.curtirDescurtir();
				listComentarios.requestFocus();
			}
		};
	}
	
	public ListSelectionListener aoSelecionarItemNaLista()
	{
		return new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				visualizacaoFeedControle.atualizarTextoBotaoCurtir();
			}
		};
	}
}