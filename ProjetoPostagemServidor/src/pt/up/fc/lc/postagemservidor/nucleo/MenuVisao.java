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

/**
	Classe da camada de visão do menu do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class MenuVisao extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private MenuControle controle;
	
	private JDesktopPane desktopPaneTelas;
	private JMenuBar menuBarGeral;
	private JMenu menuArquivo;
	private JMenu menuCadastro;
	private JMenu menuMovimentacao;
	private JMenu menuRelatorios;
	private JMenuItem menuItemArquivoGerenciarConta;
	private JMenuItem menuItemArquivoFazerLogoff;
	private JMenuItem menuItemArquivoSair;
	private JMenuItem menuItemCadastroUsuarios;
	private JMenuItem menuItemCadastroTopicos;
	private JMenuItem menuItemMovimentacaoGerenciarPedidosUtilizador;
	private JMenuItem menuItemRelatorioTopicosAtivos;
	private JMenuItem menuItemRelatorioTopicosMaisUtilizados;
	private JMenuItem menuItemRelatorioMensagensPorTopico;
	private JMenuItem menuItemRelatorioInteracaoPorTopico;
	private JMenuItem menuItemRelatorioSubscricoesUsuario;
	
	/**
		Cria e inicializa a visão de menu.
	*/
	public MenuVisao()
	{
		this.controle = new MenuControle(this);
		if (!this.controle.fazerLogin())
			dispose();
		else
		{
			this.construirTela();			
			this.vincularEventos();
			this.setVisible(true);
		}
	}
	
	/**
		Cria os componentes da visão, define seus respectivos tamanhos e
		posições e relaciona no padrão composite.
	*/
	private void construirTela()
	{
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
		this.menuMovimentacao.setText("Movimentação");
		this.menuBarGeral.add(this.menuMovimentacao);
		
		this.menuRelatorios = new JMenu();
		this.menuRelatorios.setText("Relatórios");
		this.menuBarGeral.add(this.menuRelatorios);
		
		this.menuItemArquivoGerenciarConta = new JMenuItem();
		this.menuItemArquivoGerenciarConta.setText("Gerenciar conta");
		this.menuItemArquivoGerenciarConta.setVisible(!this.controle.usuarioLogadoEMaster());
		this.menuArquivo.add(this.menuItemArquivoGerenciarConta);
		
		this.menuItemArquivoFazerLogoff = new JMenuItem();
		this.menuItemArquivoFazerLogoff.setText("Fazer logoff");
		this.menuArquivo.add(this.menuItemArquivoFazerLogoff);
		
		this.menuItemArquivoSair = new JMenuItem();
		this.menuItemArquivoSair.setText("Sair");
		this.menuArquivo.add(this.menuItemArquivoSair);
		
		this.menuItemCadastroUsuarios = new JMenuItem();
		this.menuItemCadastroUsuarios.setText("Usuários");
		this.menuCadastro.add(this.menuItemCadastroUsuarios);
		
		this.menuItemCadastroTopicos = new JMenuItem();
		this.menuItemCadastroTopicos.setText("Tópicos");
		this.menuCadastro.add(this.menuItemCadastroTopicos);
		
		this.menuItemMovimentacaoGerenciarPedidosUtilizador = new JMenuItem();
		this.menuItemMovimentacaoGerenciarPedidosUtilizador.setText("Gerenciar pedidos de utilizador");
		this.menuMovimentacao.add(this.menuItemMovimentacaoGerenciarPedidosUtilizador);
		
		this.menuItemRelatorioTopicosAtivos = new JMenuItem();
		this.menuItemRelatorioTopicosAtivos.setText("Relatório de tópicos ativos");
		this.menuRelatorios.add(this.menuItemRelatorioTopicosAtivos);
		
		this.menuItemRelatorioTopicosMaisUtilizados = new JMenuItem();
		this.menuItemRelatorioTopicosMaisUtilizados.setText("Relatório de tópicos mais utilizados");
		this.menuRelatorios.add(this.menuItemRelatorioTopicosMaisUtilizados);
		
		this.menuItemRelatorioMensagensPorTopico = new JMenuItem();
		this.menuItemRelatorioMensagensPorTopico.setText("Relatório de mensagens por tópico");
		this.menuRelatorios.add(this.menuItemRelatorioMensagensPorTopico);
		
		this.menuItemRelatorioInteracaoPorTopico = new JMenuItem();
		this.menuItemRelatorioInteracaoPorTopico.setText("Relatório de interação por tópico");
		this.menuRelatorios.add(this.menuItemRelatorioInteracaoPorTopico);
		
		this.menuItemRelatorioSubscricoesUsuario = new JMenuItem();
		this.menuItemRelatorioSubscricoesUsuario.setText("Relatório de subscrições de usuários");
		this.menuRelatorios.add(this.menuItemRelatorioSubscricoesUsuario);		
	}

	/**
		Vincula eventos aos componentes da visão.
	*/
	private void vincularEventos()
	{
		this.menuItemArquivoGerenciarConta.addActionListener(this.aoClicarMenuItemArquivoGerenciarConta());
		this.menuItemArquivoFazerLogoff.addActionListener(this.aoClicarMenuItemArquivoFazerLogoff());
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

	/**
		Obtém o container geral de todos os frames internos do sistema.
		
		@return O container de telas.
	*/
	public JDesktopPane obterPainel()
	{
		return this.desktopPaneTelas;
	}
	
	/**
		Define a visibilidade do item de menu "Gerenciar conta".
	
		@param Se o item é visível ou não.
	*/
	public void definirItemMenuArquivoGerenciarContaVisivel(boolean visivel)
	{
		this.menuItemArquivoGerenciarConta.setVisible(visivel);
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no item de
		menu "Arquivo -> Gerenciar conta".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemArquivoGerenciarConta()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				controle.abrirGerenciamentoDeConta();
			}
		};
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no item de
		menu "Arquivo -> Fazer logoff".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemArquivoFazerLogoff()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				if (!controle.fazerLogoff())
					dispose();
				else
				{
					for (JInternalFrame tela : desktopPaneTelas.getAllFrames())
						tela.dispose();
					setVisible(true);
				}					
			}
		};
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no item de
		menu "Arquivo -> Sair".
		
		@return Um handler ao evento.
	*/
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
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no item de
		menu "Cadastro -> Usuários".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemCadastroUsuarios()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				controle.abrirCadastroUsuarios();				
			}
		};
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no item de
		menu "Cadastro -> Topicos".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemCadastroTopicos()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				controle.abrirCadastroTopicos();
			}
		};
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no item de
		menu "Movimentação -> Gerenciar pedidos de utilizador".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemMovimentacaoGerenciarPedidosUtilizador()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				controle.abrirGerenciamentoPedidosUtilizador();
			}
		};
	}

	/**
		Define e retorna a ação aplicada sobre o evento do clique no item de
		menu "Relatórios -> Relatório de tópicos mais ativos".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemRelatorioTopicosAtivos()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				controle.abrirRelatorioTopicosAtivos();
			}
		};
	}

	/**
		Define e retorna a ação aplicada sobre o evento do clique no item de
		menu "Relatórios -> Relatório de tópicos mais utilizados".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemRelatorioTopicosMaisUtilizados()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				controle.abrirRelatorioTopicosMaisUtilizados();
			}
		};
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no item de
		menu "Relatórios -> Relatório de mensagens por tópico".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemRelatorioMensagensPorTopico()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				controle.abrirRelatorioMensagensPorTopico();
			}
		};
	}

	/**
		Define e retorna a ação aplicada sobre o evento do clique no item de
		menu "Relatórios -> Relatório de interação por tópico".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemRelatorioInteracaoPorTopico()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				controle.abrirRelatorioInteracaoPorTopico();
			}
		};
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento do clique no item de
		menu "Relatórios -> Relatório de subscrições de usuários".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemRelatorioSubscricoesUsuario()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				controle.abrirRelatorioSubscricoesUsuario();
			}
		};
	}
}