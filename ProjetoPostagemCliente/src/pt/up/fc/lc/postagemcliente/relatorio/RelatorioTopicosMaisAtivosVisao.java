package pt.up.fc.lc.postagemcliente.relatorio;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import pt.up.fc.lc.postagempersistencia.dao.DAO;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de visão do relatório de tópicos mais ativos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class RelatorioTopicosMaisAtivosVisao extends RelatorioVisao
{
	private static final long serialVersionUID = 1L;

	private JLabel labelPeriodo;
	private JComboBox<String> comboBoxPeriodo;
	private JTextField textFieldDataInicio;
	private JLabel labelAte;
	private JTextField textFieldDataFim;
	
	/**
		Cria e inicializa a visão do relatório de tópicos mais ativos.
		
		@param O usuário logado.
	*/
	public RelatorioTopicosMaisAtivosVisao(Usuario logado)
	{
		super("Relatório de tópicos mais ativos");
		RelatorioTopicosMaisAtivosControle relatorioControle = new RelatorioTopicosMaisAtivosControle(this, logado);
		this.relatorioControle = relatorioControle;
		this.construirTela();
		this.vincularEventos();
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Cria os componentes da visão, define seus respectivos tamanhos e
		posições e relaciona no padrão composite.
	*/
	private void construirTela()
	{
		this.labelPeriodo = new JLabel();
		this.labelPeriodo.setText("Período:");
		this.labelPeriodo.setBounds(BORDA, (BORDA + 5), 50, 17);
		this.add(this.labelPeriodo);
		
		this.comboBoxPeriodo = new JComboBox<>();
		this.comboBoxPeriodo.setBounds((BORDA + 55), BORDA, 150, 25);
		this.comboBoxPeriodo.addItem("Período específico");
		this.comboBoxPeriodo.addItem("Na última hora");
		this.comboBoxPeriodo.addItem("Hoje");
		this.comboBoxPeriodo.addItem("Esta semana");
		this.comboBoxPeriodo.setSelectedIndex(0);
		this.add(this.comboBoxPeriodo);
		
		this.textFieldDataInicio = new JTextField();
		this.textFieldDataInicio.setBounds((BORDA + 210), BORDA, 150, 25);
		this.add(this.textFieldDataInicio);
		
		this.labelAte = new JLabel();
		this.labelAte.setText("até");
		this.labelAte.setBounds((BORDA + 365), (BORDA + 5), 20, 17);
		this.add(this.labelAte);
		
		this.textFieldDataFim = new JTextField();
		this.textFieldDataFim.setBounds((BORDA + 390), BORDA, 150, 25);
		this.add(this.textFieldDataFim);
	}
	
	/**
		Vincula eventos aos componentes da visão.
	*/
	private void vincularEventos()
	{
		this.comboBoxPeriodo.addItemListener(this.aoMudarOpcaoComboBoxPeriodo());
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordenação.
	*/
	protected void inicializarTabela()
	{	
		this.adicionarColunas("Tópico", "Subscrito", "Mensagens");
	}
	
	/**
		Obtém a opção selecionada na combobox de opções de período.
		
		@return A opção selecionada.
	*/
	public String obterSelecionado()
	{
		return (String) this.comboBoxPeriodo.getSelectedItem();
	}
	
	/**
		Obtém a data de início no componente correspondente da visão.
		
		@return A descrição do tópico.
	*/
	public Date obterDataInicio()
	{
		try
		{
			return (new SimpleDateFormat(DAO.FORMATO_DATA_HORA)).parse(this.textFieldDataInicio.getText().trim());
		} catch (ParseException e)
		{
			return null;
		}
	}
	
	/**
		Obtém a data de fim no componente correspondente da visão.
		
		@return A data de fim do tópico.
	*/
	public Date obterDataFim()
	{
		try
		{
			return (new SimpleDateFormat(DAO.FORMATO_DATA_HORA)).parse(this.textFieldDataFim.getText().trim());
		} catch (ParseException e)
		{
			return null;
		}
	}
	
	/**
		Define se o componente do titulo será visível. 
	
		@param Se o componente será vísivel ou não.
	*/
	public void definirVisibilidadePeriodo(boolean visivel)
	{
		this.textFieldDataInicio.setVisible(visivel);
		this.labelAte.setVisible(visivel);
		this.textFieldDataFim.setVisible(visivel);
		if (!visivel)
		{
			this.textFieldDataInicio.setText("");
			this.textFieldDataFim.setText("");
		}
	}
	
	/**
		Define e retorna a ação aplicada sobre o evento da alteração na seleção do combobox
		de "Período".
		
		@return Um handler ao evento.
	*/
	private ItemListener aoMudarOpcaoComboBoxPeriodo()
	{
		return new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				((RelatorioTopicosMaisAtivosControle) relatorioControle).definirVisibilidadePeriodo();
			}
		};
	}
}