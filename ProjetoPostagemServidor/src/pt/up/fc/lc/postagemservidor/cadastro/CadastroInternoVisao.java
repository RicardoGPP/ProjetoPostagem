package pt.up.fc.lc.postagemservidor.cadastro;

import javax.swing.JDialog;
import pt.up.fc.lc.postagempersistencia.entidades.Entidade;

/**
	Classe padrão para todos as visões de cadastro interno.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public abstract class CadastroInternoVisao<T extends Entidade<T>> extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	protected boolean processado;
	
	/**
		Inicializa a visão com o atributo processado como
		false.
	*/
	protected CadastroInternoVisao()
	{
		this.processado = false;
	}
	
	/**
		Verifica se o cadastro foi concluído ou não.
		
		@return A flag para processado.
	*/
	public boolean foiProcessado()
	{
		return this.processado;
	}
	
	/**
		Obtém o objeto cujo os campos são definidos pelos valores
		informados nos componentes da visão.
		
		@return O objeto gerado por meio da informação dos dados.
	*/
	public abstract T obterRegistro();
}