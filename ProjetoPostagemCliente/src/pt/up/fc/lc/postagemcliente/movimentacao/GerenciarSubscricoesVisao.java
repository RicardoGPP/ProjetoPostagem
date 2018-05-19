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
import pt.up.fc.lc.postagempersistencia.entidades.PedidoUtilizador;

public class GerenciarSubscricoesVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 291;
	private static final int ALTURA = 400;
	private static final int BORDA = 15;
	
	private GerenciarSubscricoesControle gerenciarSubscricoesControle;
	
	private JButton buttonAtualizar;
	private JScrollPane scrollPaneListSubscricoes;
	private DefaultListModel<PedidoUtilizador> listModelSubscricoes;
	private JList<PedidoUtilizador> listSubscricoes;
	private JButton buttonAceitar;
	private JButton buttonRejeitar;
	
	public GerenciarSubscricoesVisao()
	{
		this.gerenciarSubscricoesControle = new GerenciarSubscricoesControle(this);
		this.construirTela();
		this.vincularEventos();
		this.gerenciarSubscricoesControle.carregarLista();
		this.setVisible(true);
	}
	
	private void construirTela()
	{
		this.setTitle("Gerenciamento de pedidos de utilizador");
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
		
		this.buttonAceitar = new JButton();
		this.buttonAceitar.setText("Aceitar");
		this.buttonAceitar.setBounds((BORDA + 85), (BORDA + 305), 80, 25);
		this.add(this.buttonAceitar);
		
		this.buttonRejeitar = new JButton();
		this.buttonRejeitar.setText("Rejeitar");
		this.buttonRejeitar.setBounds((BORDA + 170), (BORDA + 305), 80, 25);
		this.add(this.buttonRejeitar);
	}
	
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
		this.buttonAceitar.addActionListener(this.aoClicarButtonAceitar());
		this.buttonRejeitar.addActionListener(this.aoClicarButtonRejeitar());
	}
	
	public List<PedidoUtilizador> obterSubscricoes()
	{
		List<PedidoUtilizador> pedidosUtilizadors = new ArrayList<>();
		for (int i = 0; i < this.listModelSubscricoes.size(); i++)
			pedidosUtilizadors.add(this.listModelSubscricoes.get(i));
		return pedidosUtilizadors;
	}
	
	public void definirSubscricoes(List<PedidoUtilizador> pedidosUtilizador)
	{
		this.listModelSubscricoes.clear();
		for (PedidoUtilizador pedidoUtilizador : pedidosUtilizador)
			this.listModelSubscricoes.addElement(pedidoUtilizador);
	}
	
	public PedidoUtilizador obterSelecionado()
	{
		return this.listSubscricoes.getSelectedValue();
	}
	
	public void incluirNaLista(PedidoUtilizador pedidoUtilizador)
	{
		this.listModelSubscricoes.addElement(pedidoUtilizador);
	}
	
	public void excluirDaLista(PedidoUtilizador pedidoUtilizador)
	{
		this.listModelSubscricoes.removeElement(pedidoUtilizador);
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
	
	public ActionListener aoClicarButtonAceitar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				gerenciarSubscricoesControle.aceitar();
			}
		};
	}
	
	public ActionListener aoClicarButtonRejeitar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				gerenciarSubscricoesControle.rejeitar();
			}
		};
	}
}