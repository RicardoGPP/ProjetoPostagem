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
	Classe da camada de visão da visualização de feed.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
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
		Cria e inicializa a visão de visualização de feed.
		
		@param O usuário logado.
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
		Cria os componentes da visão, define seus respectivos tamanhos e
		posições e relaciona no padrão composite.
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
		Vincula eventos aos componentes da visão.
	*/
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
		this.buttonCurtirDescurtir.addActionListener(this.aoClicarButtonCurtirDescurtir());
		this.listComentarios.addListSelectionListener(this.aoSelecionarItemNaLista());
	}
	
	/**
		Define os comentários na lista da visão.
		
		@param Uma lista de objetos ComentarioFavorito.
	*/
	public void definirComentarios(List<ComentarioFavorito> comentarios)
	{
		this.listModelComentarios.clear();
		for (ComentarioFavorito comentario : comentarios)
			this.listModelComentarios.addElement(comentario);
	}
	
	/**
		Obtém o comentário selecionado na lista da visão.
		
		@return O objeto ComentarioFavorito selecionado ou null se não houver seleção.
	*/
	public ComentarioFavorito obterSelecionado()
	{
		return this.listComentarios.getSelectedValue();
	}
	
	/**
		Define o texto do botão de curtida.
		
		@param O texto a ser definido no botão.
	*/
	public void definirTextoBotaoCurtir(String texto)
	{
		this.buttonCurtirDescurtir.setText(texto);
	}

	/**
		Define e retorna a ação aplicada sobre o evento do clique no botão
		"Atualizar".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarButtonAtualizar()
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
		Define e retorna a ação aplicada sobre o evento do clique no botão
		"Curtir/Descurtir".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarButtonCurtirDescurtir()
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
		Define e retorna a ação aplicada sobre o evento do modificação de seleção
		de um item na lista.
		
		@return Um handler ao evento.
	*/
	private ListSelectionListener aoSelecionarItemNaLista()
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