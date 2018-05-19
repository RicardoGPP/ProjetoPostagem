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
		
		this.menuMovimentacao = new JMenu();
		this.menuMovimentacao.setText("Movimentação");
		this.menuBarGeral.add(this.menuMovimentacao);
		
		this.menuRelatorios = new JMenu();
		this.menuRelatorios.setText("Relatórios");
		this.menuBarGeral.add(this.menuRelatorios);
		
		this.menuItemArquivoGerenciarConta = new JMenuItem();
		this.menuItemArquivoGerenciarConta.setText("Gerenciar conta");
		this.menuArquivo.add(this.menuItemArquivoGerenciarConta);
		
		this.menuItemArquivoTrocarUsuario = new JMenuItem();
		this.menuItemArquivoTrocarUsuario.setText("Trocar usuário");
		this.menuArquivo.add(this.menuItemArquivoTrocarUsuario);
		
		this.menuItemArquivoSair = new JMenuItem();
		this.menuItemArquivoSair.setText("Sair");
		this.menuArquivo.add(this.menuItemArquivoSair);
		
		this.menuItemMovimentacaoVerFeed = new JMenuItem();
		this.menuItemMovimentacaoVerFeed.setText("Ver feed");
		this.menuMovimentacao.add(this.menuItemMovimentacaoVerFeed);
		
		this.menuItemMovimentacaoVerMensagensDosTopicos = new JMenuItem();
		this.menuItemMovimentacaoVerMensagensDosTopicos.setText("Ver mensagens dos tópicos");
		this.menuMovimentacao.add(this.menuItemMovimentacaoVerMensagensDosTopicos);
		
		this.menuItemMovimentacaoSubscreverTopicos = new JMenuItem();
		this.menuItemMovimentacaoSubscreverTopicos.setText("Subscrever em tópicos");
		this.menuMovimentacao.add(this.menuItemMovimentacaoSubscreverTopicos);
		
		this.menuItemMovimentacaoPublicarEmUmTopico = new JMenuItem();
		this.menuItemMovimentacaoPublicarEmUmTopico.setText("Publicar em um tópico");
		this.menuMovimentacao.add(this.menuItemMovimentacaoPublicarEmUmTopico);
		
		this.menuItemMovimentacaoGerenciarSubscricoes = new JMenuItem();
		this.menuItemMovimentacaoGerenciarSubscricoes.setText("Gerenciar subscrições");
		this.menuMovimentacao.add(this.menuItemMovimentacaoGerenciarSubscricoes);
		
		this.menuItemRelatorioTopicosMaisAtivos = new JMenuItem();
		this.menuItemRelatorioTopicosMaisAtivos.setText("Relatório de tópicos mais ativos");
		this.menuRelatorios.add(this.menuItemRelatorioTopicosMaisAtivos);
		
		this.menuItemRelatorioMensagensRecebidas = new JMenuItem();
		this.menuItemRelatorioMensagensRecebidas.setText("Relatório de mensagens recebidas");
		this.menuRelatorios.add(this.menuItemRelatorioMensagensRecebidas);
		
		this.menuItemRelatorioMensagensPorTopico = new JMenuItem();
		this.menuItemRelatorioMensagensPorTopico.setText("Relatório de mensagens por tópico");
		this.menuRelatorios.add(this.menuItemRelatorioMensagensPorTopico);
		
		this.menuItemRelatorioCurtidas = new JMenuItem();
		this.menuItemRelatorioCurtidas.setText("Relatório de curtidas");
		this.menuRelatorios.add(this.menuItemRelatorioCurtidas);
	}

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

	public JDesktopPane obterPainel()
	{
		return this.desktopPaneTelas;
	}
	
	public void definirItemMenuArquivoGerenciarContaVisivel(boolean visivel)
	{
		this.menuItemArquivoGerenciarConta.setVisible(visivel);
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
	
	private ActionListener aoClicarMenuItemMovimentacaoPublicarEmUmTopico()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{

			}
		};
	}
	
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