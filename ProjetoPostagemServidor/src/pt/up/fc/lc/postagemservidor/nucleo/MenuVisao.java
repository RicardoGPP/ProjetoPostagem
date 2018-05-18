package pt.up.fc.lc.postagemservidor.nucleo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuVisao extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private MenuControle menuControle;
	
	private JDesktopPane desktopPaneTelas;
	private JMenuBar menuBarGeral;
	private JMenu menuArquivo;
	private JMenu menuCadastro;
	private JMenu menuMovimentacao;
	private JMenu menuRelatorios;
	private JMenuItem menuItemArquivoGerenciarConta;
	private JMenuItem menuItemArquivoTrocarUsuario;
	private JMenuItem menuItemArquivoSair;
	private JMenuItem menuItemCadastroUsuarios;
	private JMenuItem menuItemCadastroTopicos;
	private JMenuItem menuItemMovimentacaoGerenciarPedidosUtilizador;
	private JMenuItem menuItemRelatorioTopicosAtivos;
	private JMenuItem menuItemRelatorioTopicosMaisUtilizados;
	private JMenuItem menuItemRelatorioMensagensPorTopico;
	private JMenuItem menuItemRelatorioInteracaoPorTopico;
	private JMenuItem menuItemRelatorioSubscricoesUsuario;
	
	public MenuVisao()
	{
		this.menuControle = new MenuControle(this);
		this.menuControle.fazerLogin();
		if (!this.menuControle.foiAutenticado())
			dispose();
		else
		{
			this.construirTela();			
			this.vincularEventos();
			this.setVisible(true);
		}
	}
	
	private void construirTela()
	{
		this.setTitle("Sistema de postagem");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.desktopPaneTelas = new JDesktopPane();
		this.desktopPaneTelas.setBackground(Color.BLACK);
		this.add(this.desktopPaneTelas);
		
		this.menuBarGeral = new JMenuBar();
		this.setJMenuBar(this.menuBarGeral);
		
		this.menuArquivo = new JMenu();
		this.menuArquivo.setText("Arquivo");
		this.menuBarGeral.add(this.menuArquivo);
		
		this.menuCadastro = new JMenu();
		this.menuCadastro.setText("Cadastro");
		this.menuBarGeral.add(this.menuCadastro);
		
		this.menuMovimentacao = new JMenu();
		this.menuMovimentacao.setText("Movimenta��o");
		this.menuBarGeral.add(this.menuMovimentacao);
		
		this.menuRelatorios = new JMenu();
		this.menuRelatorios.setText("Relat�rios");
		this.menuBarGeral.add(this.menuRelatorios);
		
		this.menuItemArquivoGerenciarConta = new JMenuItem();
		this.menuItemArquivoGerenciarConta.setText("Gerenciar conta");
		this.menuArquivo.add(this.menuItemArquivoGerenciarConta);
		
		this.menuItemArquivoTrocarUsuario = new JMenuItem();
		this.menuItemArquivoTrocarUsuario.setText("Trocar usu�rio");
		this.menuArquivo.add(this.menuItemArquivoTrocarUsuario);
		
		this.menuItemArquivoSair = new JMenuItem();
		this.menuItemArquivoSair.setText("Sair");
		this.menuArquivo.add(this.menuItemArquivoSair);
		
		this.menuItemCadastroUsuarios = new JMenuItem();
		this.menuItemCadastroUsuarios.setText("Usu�rios");
		this.menuCadastro.add(this.menuItemCadastroUsuarios);
		
		this.menuItemCadastroTopicos = new JMenuItem();
		this.menuItemCadastroTopicos.setText("T�picos");
		this.menuCadastro.add(this.menuItemCadastroTopicos);
		
		this.menuItemMovimentacaoGerenciarPedidosUtilizador = new JMenuItem();
		this.menuItemMovimentacaoGerenciarPedidosUtilizador.setText("Gerenciar pedidos de utilizador");
		this.menuMovimentacao.add(this.menuItemMovimentacaoGerenciarPedidosUtilizador);
		
		this.menuItemRelatorioTopicosAtivos = new JMenuItem();
		this.menuItemRelatorioTopicosAtivos.setText("Relat�rio de t�picos ativos");
		this.menuRelatorios.add(this.menuItemRelatorioTopicosAtivos);
		
		this.menuItemRelatorioTopicosMaisUtilizados = new JMenuItem();
		this.menuItemRelatorioTopicosMaisUtilizados.setText("Relat�rio de t�picos mais utilizados");
		this.menuRelatorios.add(this.menuItemRelatorioTopicosMaisUtilizados);
		
		this.menuItemRelatorioMensagensPorTopico = new JMenuItem();
		this.menuItemRelatorioMensagensPorTopico.setText("Relat�rio de mensagens por t�pico");
		this.menuRelatorios.add(this.menuItemRelatorioMensagensPorTopico);
		
		this.menuItemRelatorioInteracaoPorTopico = new JMenuItem();
		this.menuItemRelatorioInteracaoPorTopico.setText("Relat�rio de intera��o por t�pico");
		this.menuRelatorios.add(this.menuItemRelatorioInteracaoPorTopico);
		
		this.menuItemRelatorioSubscricoesUsuario = new JMenuItem();
		this.menuItemRelatorioSubscricoesUsuario.setText("Relat�rio de subscri��es de usu�rios");
		this.menuRelatorios.add(this.menuItemRelatorioSubscricoesUsuario);		
	}

	private void vincularEventos()
	{
		this.menuItemArquivoGerenciarConta.addActionListener(this.aoClicarMenuItemArquivoGerenciarConta());
		this.menuItemArquivoTrocarUsuario.addActionListener(this.aoClicarMenuItemArquivoTrocarUsuario());
		this.menuItemArquivoSair.addActionListener(this.aoClicarMenuItemArquivoSair());
		this.menuItemCadastroUsuarios.addActionListener(this.aoClicarMenuItemCadastroUsuarios());
		this.menuItemCadastroTopicos.addActionListener(this.aoClicarMenuItemCadastroTopicos());
		this.menuItemMovimentacaoGerenciarPedidosUtilizador.addActionListener(this.aoClicarMenuItemMovimentacaoGerenciarPedidosUtilizador());
		this.menuItemRelatorioTopicosAtivos.addActionListener(this.aoClicarMenuItemRelatorioTopicosAtivos());
		this.menuItemRelatorioTopicosMaisUtilizados.addActionListener(this.aoClicarMenuItemRelatorioTopicosMaisUtilizados());
		this.menuItemRelatorioMensagensPorTopico.addActionListener(this.aoClicarMenuItemRelatorioMensagensPorTopico());
		this.menuItemRelatorioInteracaoPorTopico.addActionListener(this.aoClicarMenuItemRelatorioInteracaoPorTopico());
		this.menuItemRelatorioSubscricoesUsuario.addActionListener(this.aoClicarMenuItemRelatorioSubscricoesUsuario());
	}

	public JDesktopPane obterPainel()
	{
		return this.desktopPaneTelas;
	}
	
	private ActionListener aoClicarMenuItemArquivoGerenciarConta()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirGerenciamentoDeConta();
			}
		};
	}
	
	private ActionListener aoClicarMenuItemArquivoTrocarUsuario()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				if (menuControle.trocarUsuario())
					for (JInternalFrame tela : desktopPaneTelas.getAllFrames())
						tela.dispose();
				setVisible(true);
			}
		};
	}
	
	private ActionListener aoClicarMenuItemArquivoSair()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		};
	}
	
	private ActionListener aoClicarMenuItemCadastroUsuarios()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirCadastroUsuarios();				
			}
		};
	}
	
	private ActionListener aoClicarMenuItemCadastroTopicos()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirCadastroTopicos();
			}
		};
	}
	
	private ActionListener aoClicarMenuItemMovimentacaoGerenciarPedidosUtilizador()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirGerenciamentoPedidosUtilizador();
			}
		};
	}

	private ActionListener aoClicarMenuItemRelatorioTopicosAtivos()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirRelatorioTopicosAtivos();
			}
		};
	}

	private ActionListener aoClicarMenuItemRelatorioTopicosMaisUtilizados()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirRelatorioTopicosMaisUtilizados();
			}
		};
	}
	
	private ActionListener aoClicarMenuItemRelatorioMensagensPorTopico()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirRelatorioMensagensPorTopico();
			}
		};
	}

	private ActionListener aoClicarMenuItemRelatorioInteracaoPorTopico()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirRelatorioInteracaoPorTopico();
			}
		};
	}
	
	private ActionListener aoClicarMenuItemRelatorioSubscricoesUsuario()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirRelatorioSubscricoesUsuario();
			}
		};
	}
}