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
import pt.up.fc.lc.postagempersistencia.entidades.Subscricao;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class GerenciarSubscricoesVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 291;
	private static final int ALTURA = 400;
	private static final int BORDA = 15;
	
	private GerenciarSubscricoesControle gerenciarSubscricoesControle;
	
	private JButton buttonAtualizar;
	private JScrollPane scrollPaneListSubscricoes;
	private DefaultListModel<Subscricao> listModelSubscricoes;
	private JList<Subscricao> listSubscricoes;
	private JButton buttonDesinscrever;
	private JButton buttonFavoritarDesfavoritar;
	
	public GerenciarSubscricoesVisao(Usuario logado)
	{
		this.gerenciarSubscricoesControle = new GerenciarSubscricoesControle(this, logado);
		this.construirTela();
		this.vincularEventos();
		this.gerenciarSubscricoesControle.carregarLista();
		this.setVisible(true);
	}
	
	private void construirTela()
	{
		this.setTitle("Gerenciamento de subscrições");
		this.setResizable(false);
		this.setClosable(true);
		this.setSize(LARGURA, ALTURA);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		
		this.buttonAtualizar = new JButton();
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.setBounds((BORDA + 160), BORDA, 90, 25);
		this.add(this.buttonAtualizar);
		
		this.scrollPaneListSubscricoes = new JScrollPane();
		this.scrollPaneListSubscricoes.setBounds(BORDA, (BORDA + 30), (LARGURA - (BORDA * 2) - 10), 270);
		this.add(this.scrollPaneListSubscricoes);
		
		this.listModelSubscricoes = new DefaultListModel<>();
		this.listSubscricoes = new JList<>(this.listModelSubscricoes);
		this.scrollPaneListSubscricoes.setViewportView(this.listSubscricoes);
		
		this.buttonDesinscrever = new JButton();
		this.buttonDesinscrever.setText("Desinscrever");
		this.buttonDesinscrever.setBounds((BORDA + 5), (BORDA + 305), 120, 25);
		this.add(this.buttonDesinscrever);
		
		this.buttonFavoritarDesfavoritar = new JButton();
		this.buttonFavoritarDesfavoritar.setText("Favoritar");
		this.buttonFavoritarDesfavoritar.setBounds((BORDA + 130), (BORDA + 305), 120, 25);
		this.add(this.buttonFavoritarDesfavoritar);
	}
	
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
		this.buttonDesinscrever.addActionListener(this.aoClicarButtonDesinscrever());
		this.buttonFavoritarDesfavoritar.addActionListener(this.aoClicarButtonFavoritarDesfavoritar());
		this.listSubscricoes.addListSelectionListener(this.aoSelecionarItemNaLista());
	}
	
	public List<Subscricao> obterSubscricoes()
	{
		List<Subscricao> subscricoes = new ArrayList<>();
		for (int i = 0; i < this.listModelSubscricoes.size(); i++)
			subscricoes.add(this.listModelSubscricoes.get(i));
		return subscricoes;
	}
	
	public void definirSubscricoes(List<Subscricao> subscricoes)
	{
		this.listModelSubscricoes.clear();
		for (Subscricao subscricao : subscricoes)
			this.listModelSubscricoes.addElement(subscricao);
	}
	
	public Subscricao obterSelecionado()
	{
		return this.listSubscricoes.getSelectedValue();
	}
	
	public void excluirDaLista(Subscricao subscricao)
	{
		this.listModelSubscricoes.removeElement(subscricao);
	}
	
	public void definirTextoBotaoFavorito(String texto)
	{
		this.buttonFavoritarDesfavoritar.setText(texto);
	}

	public ActionListener aoClicarButtonAtualizar()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				gerenciarSubscricoesControle.carregarLista();
			}
		};
	}
	
	public ActionListener aoClicarButtonDesinscrever()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				gerenciarSubscricoesControle.desinscrever();
			}
		};
	}
	
	public ActionListener aoClicarButtonFavoritarDesfavoritar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				gerenciarSubscricoesControle.favoritarDesfavoritar();
				listSubscricoes.requestFocus();
			}
		};
	}
	
	public ListSelectionListener aoSelecionarItemNaLista()
	{
		return new ListSelectionListener()
		{	
			public void valueChanged(ListSelectionEvent e)
			{
				gerenciarSubscricoesControle.atualizarTextoBotaoFavoritar();	
			}
		};
	}
}