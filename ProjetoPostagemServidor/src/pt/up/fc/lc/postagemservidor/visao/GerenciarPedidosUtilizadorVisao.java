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
import javax.swing.JScrollPane;
import pt.up.fc.lc.postagempersistencia.entidades.PedidoUtilizador;
import pt.up.fc.lc.postagemservidor.controle.GerenciarPedidosUtilizadorControle;

public class GerenciarPedidosUtilizadorVisao extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int LARGURA = 291;
	private static final int ALTURA = 400;
	private static final int BORDA = 15;
	
	private GerenciarPedidosUtilizadorControle gerenciarPedidosUtilizadorControle;
	
	private JButton buttonAtualizar;
	private JScrollPane scrollPaneListPedidosUtilizador;
	private DefaultListModel<PedidoUtilizador> listModelPedidosUtilizador;
	private JList<PedidoUtilizador> listPedidosUtilizador;
	private JButton buttonAceitar;
	private JButton buttonRejeitar;
	
	public GerenciarPedidosUtilizadorVisao()
	{
		this.gerenciarPedidosUtilizadorControle = new GerenciarPedidosUtilizadorControle(this);
		this.construirTela();
		this.vincularEventos();
		this.gerenciarPedidosUtilizadorControle.carregarLista();
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
		
		this.scrollPaneListPedidosUtilizador = new JScrollPane();
		this.scrollPaneListPedidosUtilizador.setBounds(BORDA, (BORDA + 30), (LARGURA - (BORDA * 2) - 10), 270);
		this.add(this.scrollPaneListPedidosUtilizador);
		
		this.listModelPedidosUtilizador = new DefaultListModel<>();
		this.listPedidosUtilizador = new JList<>(this.listModelPedidosUtilizador);
		this.scrollPaneListPedidosUtilizador.setViewportView(this.listPedidosUtilizador);
		
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
	
	public List<PedidoUtilizador> obterPedidosUtilizador()
	{
		List<PedidoUtilizador> pedidosUtilizadors = new ArrayList<>();
		for (int i = 0; i < this.listModelPedidosUtilizador.size(); i++)
			pedidosUtilizadors.add(this.listModelPedidosUtilizador.get(i));
		return pedidosUtilizadors;
	}
	
	public void definirPedidosUtilizador(List<PedidoUtilizador> pedidosUtilizador)
	{
		this.listModelPedidosUtilizador.clear();
		for (PedidoUtilizador pedidoUtilizador : pedidosUtilizador)
			this.listModelPedidosUtilizador.addElement(pedidoUtilizador);
	}
	
	public PedidoUtilizador obterSelecionado()
	{
		return this.listPedidosUtilizador.getSelectedValue();
	}
	
	public void incluirNaLista(PedidoUtilizador pedidoUtilizador)
	{
		this.listModelPedidosUtilizador.addElement(pedidoUtilizador);
	}
	
	public void excluirDaLista(PedidoUtilizador pedidoUtilizador)
	{
		this.listModelPedidosUtilizador.removeElement(pedidoUtilizador);
	}

	public ActionListener aoClicarButtonAtualizar()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				gerenciarPedidosUtilizadorControle.carregarLista();
			}
		};
	}
	
	public ActionListener aoClicarButtonAceitar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				gerenciarPedidosUtilizadorControle.aceitar();
			}
		};
	}
	
	public ActionListener aoClicarButtonRejeitar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				gerenciarPedidosUtilizadorControle.rejeitar();
			}
		};
	}
}