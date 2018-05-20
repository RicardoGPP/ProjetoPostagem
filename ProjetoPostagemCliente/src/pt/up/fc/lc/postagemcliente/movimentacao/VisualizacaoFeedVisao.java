package pt.up.fc.lc.postagemcliente.movimentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import pt.up.fc.lc.postagemcliente.movimentacao.VisualizacaoFeedControle.ComentarioFavorito;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de vis�o da visualiza��o de feed.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class VisualizacaoFeedVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 900;
	private static final int ALTURA = 600;
	private static final int BORDA = 15;
	
	private VisualizacaoFeedControle visualizacaoFeedControle;
	
	private JButton buttonAtualizar;
	private JScrollPane scrollPaneListComentarios;
	private DefaultListModel<ComentarioFavorito> listModelComentarios;
	private JList<ComentarioFavorito> listComentarios;
	private JButton buttonCurtirDescurtir;
	
	/**
		Cria e inicializa a vis�o de visualiza��o de feed.
		
		@param O usu�rio logado.
	*/
	public VisualizacaoFeedVisao(Usuario logado)
	{
		this.visualizacaoFeedControle = new VisualizacaoFeedControle(this, logado);
		this.construirTela();
		this.vincularEventos();
		this.visualizacaoFeedControle.carregarLista();
		this.setVisible(true);
	}
	
	/**
		Cria os componentes da vis�o, define seus respectivos tamanhos e
		posi��es e relaciona no padr�o composite.
	*/
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
	
	/**
		Vincula eventos aos componentes da vis�o.
	*/
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
		this.buttonCurtirDescurtir.addActionListener(this.aoClicarButtonCurtirDescurtir());
		this.listComentarios.addListSelectionListener(this.aoSelecionarItemNaLista());
	}
	
	/**
		Define os coment�rios na lista da vis�o.
		
		@param Uma lista de objetos ComentarioFavorito.
	*/
	public void definirComentarios(List<ComentarioFavorito> comentarios)
	{
		this.listModelComentarios.clear();
		for (ComentarioFavorito comentario : comentarios)
			this.listModelComentarios.addElement(comentario);
	}
	
	/**
		Obt�m o coment�rio selecionado na lista da vis�o.
		
		@return O objeto ComentarioFavorito selecionado ou null se n�o houver sele��o.
	*/
	public ComentarioFavorito obterSelecionado()
	{
		return this.listComentarios.getSelectedValue();
	}
	
	/**
		Define o texto do bot�o de curtida.
		
		@param O texto a ser definido no bot�o.
	*/
	public void definirTextoBotaoCurtir(String texto)
	{
		this.buttonCurtirDescurtir.setText(texto);
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
				visualizacaoFeedControle.carregarLista();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
		"Curtir/Descurtir".
		
		@return Um handler ao evento.
	*/
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
	
	/**
		Define e retorna a a��o aplicada sobre o evento do modifica��o de sele��o
		de um item na lista.
		
		@return Um handler ao evento.
	*/
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