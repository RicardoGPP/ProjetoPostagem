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
	Classe da camada de vis�o do relat�rio de t�picos mais ativos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
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
		Cria e inicializa a vis�o do relat�rio de t�picos mais ativos.
		
		@param O usu�rio logado.
	*/
	public RelatorioTopicosMaisAtivosVisao(Usuario logado)
	{
		super("Relat�rio de t�picos mais ativos");
		RelatorioTopicosMaisAtivosControle relatorioControle = new RelatorioTopicosMaisAtivosControle(this, logado);
		this.relatorioControle = relatorioControle;
		this.construirTela();
		this.vincularEventos();
		this.relatorioControle.carregarTabela();
		this.setVisible(true);
	}
	
	/**
		Cria os componentes da vis�o, define seus respectivos tamanhos e
		posi��es e relaciona no padr�o composite.
	*/
	private void construirTela()
	{
		this.labelPeriodo = new JLabel();
		this.labelPeriodo.setText("Per�odo:");
		this.labelPeriodo.setBounds(BORDA, (BORDA + 5), 50, 17);
		this.add(this.labelPeriodo);
		
		this.comboBoxPeriodo = new JComboBox<>();
		this.comboBoxPeriodo.setBounds((BORDA + 55), BORDA, 150, 25);
		this.comboBoxPeriodo.addItem("Per�odo espec�fico");
		this.comboBoxPeriodo.addItem("Na �ltima hora");
		this.comboBoxPeriodo.addItem("Hoje");
		this.comboBoxPeriodo.addItem("Esta semana");
		this.comboBoxPeriodo.setSelectedIndex(0);
		this.add(this.comboBoxPeriodo);
		
		this.textFieldDataInicio = new JTextField();
		this.textFieldDataInicio.setBounds((BORDA + 210), BORDA, 150, 25);
		this.add(this.textFieldDataInicio);
		
		this.labelAte = new JLabel();
		this.labelAte.setText("at�");
		this.labelAte.setBounds((BORDA + 365), (BORDA + 5), 20, 17);
		this.add(this.labelAte);
		
		this.textFieldDataFim = new JTextField();
		this.textFieldDataFim.setBounds((BORDA + 390), BORDA, 150, 25);
		this.add(this.textFieldDataFim);
	}
	
	/**
		Vincula eventos aos componentes da vis�o.
	*/
	private void vincularEventos()
	{
		this.comboBoxPeriodo.addItemListener(this.aoMudarOpcaoComboBoxPeriodo());
	}
	
	/**
		Inicializa a tabela, definido colunas, tamanhos e ordena��o.
	*/
	protected void inicializarTabela()
	{	
		this.adicionarColunas("T�pico", "Subscrito", "Mensagens");
	}
	
	/**
		Obt�m a op��o selecionada na combobox de op��es de per�odo.
		
		@return A op��o selecionada.
	*/
	public String obterSelecionado()
	{
		return (String) this.comboBoxPeriodo.getSelectedItem();
	}
	
	/**
		Obt�m a data de in�cio no componente correspondente da vis�o.
		
		@return A descri��o do t�pico.
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
		Obt�m a data de fim no componente correspondente da vis�o.
		
		@return A data de fim do t�pico.
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
		Define se o componente do titulo ser� vis�vel. 
	
		@param Se o componente ser� v�sivel ou n�o.
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
		Define e retorna a a��o aplicada sobre o evento da altera��o na sele��o do combobox
		de "Per�odo".
		
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