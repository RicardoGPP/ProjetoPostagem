package pt.up.fc.lc.postagemservidor.movimentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import pt.up.fc.lc.postagempersistencia.entidades.PedidoUtilizador;

/**
	Classe da camada de vis�o do gerenciamento de pedidos de utilizador
	do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
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
	
	/**
		Cria e inicializa a vis�o de gerenciamento de pedidos de utilizador.
	*/
	public GerenciarPedidosUtilizadorVisao()
	{
		this.gerenciarPedidosUtilizadorControle = new GerenciarPedidosUtilizadorControle(this);
		this.construirTela();
		this.vincularEventos();
		this.gerenciarPedidosUtilizadorControle.carregarLista();
		this.setVisible(true);
	}
	
	/**
		Cria os componentes da vis�o, define seus respectivos tamanhos e
		posi��es e relaciona no padr�o composite.
	*/
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
	
	/**
		Vincula eventos aos componentes da vis�o.
	*/
	private void vincularEventos()
	{
		this.buttonAtualizar.addActionListener(this.aoClicarButtonAtualizar());
		this.buttonAceitar.addActionListener(this.aoClicarButtonAceitar());
		this.buttonRejeitar.addActionListener(this.aoClicarButtonRejeitar());
	}
	
	/**
		Define os pedidos de utilizador na lista da vis�o.
		
		@param Uma lista de pedidos de utilizador.
	*/
	public void definirPedidosUtilizador(List<PedidoUtilizador> pedidosUtilizador)
	{
		this.listModelPedidosUtilizador.clear();
		for (PedidoUtilizador pedidoUtilizador : pedidosUtilizador)
			this.listModelPedidosUtilizador.addElement(pedidoUtilizador);
	}
	
	/**
		Obt�m o pedido de utilizador selecionado na lista da vis�o.
		
		@return O pedido de utilizador selecionado ou null se n�o houver sele��o.
	*/
	public PedidoUtilizador obterSelecionado()
	{
		return this.listPedidosUtilizador.getSelectedValue();
	}
	
	/**
		Exclui um pedido de utilizador da lista.
		
		@param Um pedido de utilizador.
	*/
	public void excluirDaLista(PedidoUtilizador pedidoUtilizador)
	{
		this.listModelPedidosUtilizador.removeElement(pedidoUtilizador);
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
				gerenciarPedidosUtilizadorControle.carregarLista();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o 
		"Aceitar".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarButtonAceitar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				gerenciarPedidosUtilizadorControle.aceitar();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no bot�o
		"Rejeitar".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarButtonRejeitar()
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