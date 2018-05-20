package pt.up.fc.lc.postagemcliente.movimentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de vis�o da visualiza��o de mensagens dos t�picos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class VisualizacaoMensagensTopicoVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 900;
	private static final int ALTURA = 600;
	private static final int BORDA = 15;
	
	private VisualizacaoMensagensTopicoControle visualizacaoMensagensTopicoControle;
	
	private JLabel labelTopicos;
	private JComboBox<Topico> comboBoxTopicos;
	private JButton buttonAtualizar;
	private JScrollPane scrollPaneListComentarios;
	private DefaultListModel<Comentario> listModelComentarios;
	private JList<Comentario> listComentarios;
	
	/**
		Cria e inicializa a vis�o da visualiza��o de mensagens dos t�picos.
		
		@param O usu�rio logado.
	*/
	public VisualizacaoMensagensTopicoVisao(Usuario logado)
	{
		this.visualizacaoMensagensTopicoControle = new VisualizacaoMensagensTopicoControle(this, logado);
		this.construirTela();
		this.vincularEventos();
		this.visualizacaoMensagensTopicoControle.carregarTopicos();
		this.visualizacaoMensagensTopicoControle.carregarLista();
		this.setVisible(true);
	}
	
	/**
		Cria os componentes da vis�o, define seus respectivos tamanhos e
		posi��es e relaciona no padr�o composite.
	*/
	private void construirTela()
	{
		this.setTitle("Visualiza��o de mensagens por t�pico");
		this.setResizable(false);
		this.setClosable(true);
		this.setSize(LARGURA, ALTURA);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		
		this.labelTopicos = new JLabel();
		this.labelTopicos.setText("T�pico: ");
		this.labelTopicos.setBounds(BORDA, (BORDA + 5), 50, 17);
		this.add(this.labelTopicos);
		
		this.comboBoxTopicos = new JComboBox<>();
		this.comboBoxTopicos.setBounds((BORDA + 45), BORDA, 719, 25);
		this.add(this.comboBoxTopicos);
		
		this.buttonAtualizar = new JButton();
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.setBounds((BORDA + 769), BORDA, 90, 25);
		this.add(this.buttonAtualizar);
		
		this.scrollPaneListComentarios = new JScrollPane();
		this.scrollPaneListComentarios.setBounds(BORDA, (BORDA + 30), (LARGURA - (BORDA * 2) - 10), 500);
		this.add(this.scrollPaneListComentarios);
		
		this.listModelComentarios = new DefaultListModel<>();
		this.listComentarios = new JList<>(this.listModelComentarios);
		this.scrollPaneListComentarios.setViewportView(this.listComentarios);
	}
	
	/**
		Vincula eventos aos componentes da vis�o.
	*/
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
	}
	
	/**
		Define os coment�rios na lista da vis�o.
		
		@param Uma lista de coment�rios.
	*/
	public void definirComentarios(List<Comentario> comentarios)
	{
		this.listModelComentarios.clear();
		for (Comentario comentario : comentarios)
			this.listModelComentarios.addElement(comentario);
	}
	
	/**
		Define os t�picos no combobox de t�picos.
		
		@param Uma lista de subscri��es.
	*/
	public void definirTopicos(List<Topico> topicos)
	{
		this.comboBoxTopicos.removeAllItems();
		for (Topico topico : topicos)
			this.comboBoxTopicos.addItem(topico);
	}
	
	/**
		Obt�m o t�pico selecionado no combobox.
		
		@return Um t�pico ou null caso n�o haja sele��o.
	*/
	public Topico obterTopico()
	{
		return (Topico) this.comboBoxTopicos.getSelectedItem();
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
				visualizacaoMensagensTopicoControle.carregarLista();
			}
		};
	}
}