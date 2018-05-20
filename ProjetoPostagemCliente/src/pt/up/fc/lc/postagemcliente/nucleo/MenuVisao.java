package pt.up.fc.lc.postagemcliente.nucleo;

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
	Classe da camada de vis�o do menu do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class MenuVisao extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private MenuControle menuControle;
	
	private JDesktopPane desktopPaneTelas;
	private JMenuBar menuBarGeral;
	private JMenu menuArquivo;
	private JMenu menuMovimentacao;
	private JMenu menuRelatorios;
	private JMenuItem menuItemArquivoGerenciarConta;
	private JMenuItem menuItemArquivoTrocarUsuario;
	private JMenuItem menuItemArquivoSair;
	private JMenuItem menuItemMovimentacaoVerFeed;
	private JMenuItem menuItemMovimentacaoVerMensagensDosTopicos;
	private JMenuItem menuItemMovimentacaoSubscreverTopicos;
	private JMenuItem menuItemMovimentacaoPublicarEmUmTopico;
	private JMenuItem menuItemMovimentacaoGerenciarSubscricoes;
	private JMenuItem menuItemRelatorioTopicosMaisAtivos;
	private JMenuItem menuItemRelatorioMensagensRecebidas;
	private JMenuItem menuItemRelatorioMensagensPorTopico;
	private JMenuItem menuItemRelatorioCurtidas;
	
	/**
		Cria e inicializa a vis�o de menu.
	*/
	public MenuVisao()
	{
		this.menuControle = new MenuControle(this);
		if (!this.menuControle.fazerLogin())
			dispose();
		else
		{
			this.construirTela();			
			this.vincularEventos();
			this.setVisible(true);
		}
	}
	
	/**
		Cria os componentes da vis�o, define seus respectivos tamanhos e
		posi��es e relaciona no padr�o composite.
	*/
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
		
		this.menuItemMovimentacaoVerFeed = new JMenuItem();
		this.menuItemMovimentacaoVerFeed.setText("Ver feed");
		this.menuMovimentacao.add(this.menuItemMovimentacaoVerFeed);
		
		this.menuItemMovimentacaoVerMensagensDosTopicos = new JMenuItem();
		this.menuItemMovimentacaoVerMensagensDosTopicos.setText("Ver mensagens dos t�picos");
		this.menuMovimentacao.add(this.menuItemMovimentacaoVerMensagensDosTopicos);
		
		this.menuItemMovimentacaoSubscreverTopicos = new JMenuItem();
		this.menuItemMovimentacaoSubscreverTopicos.setText("Subscrever em t�picos");
		this.menuMovimentacao.add(this.menuItemMovimentacaoSubscreverTopicos);
		
		this.menuItemMovimentacaoPublicarEmUmTopico = new JMenuItem();
		this.menuItemMovimentacaoPublicarEmUmTopico.setText("Publicar em um t�pico");
		this.menuMovimentacao.add(this.menuItemMovimentacaoPublicarEmUmTopico);
		
		this.menuItemMovimentacaoGerenciarSubscricoes = new JMenuItem();
		this.menuItemMovimentacaoGerenciarSubscricoes.setText("Gerenciar subscri��es");
		this.menuMovimentacao.add(this.menuItemMovimentacaoGerenciarSubscricoes);
		
		this.menuItemRelatorioTopicosMaisAtivos = new JMenuItem();
		this.menuItemRelatorioTopicosMaisAtivos.setText("Relat�rio de t�picos mais ativos");
		this.menuRelatorios.add(this.menuItemRelatorioTopicosMaisAtivos);
		
		this.menuItemRelatorioMensagensRecebidas = new JMenuItem();
		this.menuItemRelatorioMensagensRecebidas.setText("Relat�rio de mensagens recebidas");
		this.menuRelatorios.add(this.menuItemRelatorioMensagensRecebidas);
		
		this.menuItemRelatorioMensagensPorTopico = new JMenuItem();
		this.menuItemRelatorioMensagensPorTopico.setText("Relat�rio de mensagens por t�pico");
		this.menuRelatorios.add(this.menuItemRelatorioMensagensPorTopico);
		
		this.menuItemRelatorioCurtidas = new JMenuItem();
		this.menuItemRelatorioCurtidas.setText("Relat�rio de curtidas");
		this.menuRelatorios.add(this.menuItemRelatorioCurtidas);
	}

	/**
		Vincula eventos aos componentes da vis�o.
	*/
	private void vincularEventos()
	{
		this.menuItemArquivoGerenciarConta.addActionListener(this.aoClicarMenuItemArquivoGerenciarConta());
		this.menuItemArquivoTrocarUsuario.addActionListener(this.aoClicarMenuItemArquivoTrocarUsuario());
		this.menuItemArquivoSair.addActionListener(this.aoClicarMenuItemArquivoSair());				
		this.menuItemMovimentacaoVerFeed.addActionListener(this.aoClicarMenuItemMovimentacaoVerFeed());
		this.menuItemMovimentacaoVerMensagensDosTopicos.addActionListener(this.aoClicarMenuItemMovimentacaoVerMensagensDosTopicos());
		this.menuItemMovimentacaoSubscreverTopicos.addActionListener(this.aoClicarMenuItemMovimentacaoSubscreverTopicos());
		this.menuItemMovimentacaoPublicarEmUmTopico.addActionListener(this.aoClicarMenuItemMovimentacaoPublicarEmUmTopico());
		this.menuItemMovimentacaoGerenciarSubscricoes.addActionListener(this.aoClicarMenuItemMovimentacaoGerenciarSubscricoes());
		this.menuItemRelatorioTopicosMaisAtivos.addActionListener(this.aoClicarMenuItemRelatorioTopicosAtivos());
		this.menuItemRelatorioMensagensRecebidas.addActionListener(this.aoClicarMenuItemRelatorioMensagensRecebidas());
		this.menuItemRelatorioMensagensPorTopico.addActionListener(this.aoClicarMenuItemRelatorioMensagensPorTopico());
		this.menuItemRelatorioCurtidas.addActionListener(this.aoClicarMenuItemRelatorioCurtidas());		
	}

	/**
		Obt�m o container geral de todos os frames internos do sistema.
		
		@return O container de telas.
	*/
	public JDesktopPane obterPainel()
	{
		return this.desktopPaneTelas;
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no item de
		menu "Arquivo -> Gerenciar conta".
		
		@return Um handler ao evento.
	*/
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
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no item de
		menu "Arquivo -> Trocar usu�rio".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemArquivoTrocarUsuario()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				if (!menuControle.fazerLogoff())
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
		Define e retorna a a��o aplicada sobre o evento do clique no item de
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
		Define e retorna a a��o aplicada sobre o evento do clique no item de
		menu "Movimenta��o -> Ver feed".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemMovimentacaoVerFeed()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirVisualizacaoFeed();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no item de
		menu "Movimenta��o -> Ver mensagens dos t�picos".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemMovimentacaoVerMensagensDosTopicos()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirVisualizacaoMensagensTopico();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no item de
		menu "Movimenta��o -> Subscrever em t�picos".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemMovimentacaoSubscreverTopicos()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirSubscricaoEmTopicos();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no item de
		menu "Movimenta��o -> Publicar em um t�pico".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemMovimentacaoPublicarEmUmTopico()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirPublicacaoEmUmTopico();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no item de
		menu "Movimenta��o -> Gerenciar subscri��es".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemMovimentacaoGerenciarSubscricoes()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirGerenciamentoSubscricoes();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no item de
		menu "Relat�rios -> Relat�rio de t�picos mais ativos".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemRelatorioTopicosAtivos()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirRelatorioTopicosMaisAtivos();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no item de
		menu "Relat�rios -> Relat�rio de mensagens recebidas".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemRelatorioMensagensRecebidas()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirRelatorioMensagensRecebidas();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no item de
		menu "Relat�rios -> Relat�rio de mensagens por t�pico".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemRelatorioMensagensPorTopico()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abriRelatorioMensagensPorTopico();
			}
		};
	}
	
	/**
		Define e retorna a a��o aplicada sobre o evento do clique no item de
		menu "Relat�rios -> Relat�rio de curtidas".
		
		@return Um handler ao evento.
	*/
	private ActionListener aoClicarMenuItemRelatorioCurtidas()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				menuControle.abrirRelatorioCurtidas();
			}
		};
	}
}