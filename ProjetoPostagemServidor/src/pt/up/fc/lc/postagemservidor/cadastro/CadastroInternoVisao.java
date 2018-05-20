package pt.up.fc.lc.postagemservidor.cadastro;

import javax.swing.JDialog;
import pt.up.fc.lc.postagempersistencia.entidades.Entidade;

/**
	Classe padr�o para todos as vis�es de cadastro interno.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public abstract class CadastroInternoVisao<T extends Entidade<T>> extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	protected boolean processado;
	
	/**
		Inicializa a vis�o com o atributo processado como
		false.
	*/
	protected CadastroInternoVisao()
	{
		this.processado = false;
	}
	
	/**
		Verifica se o cadastro foi conclu�do ou n�o.
		
		@return A flag para processado.
	*/
	public boolean foiProcessado()
	{
		return this.processado;
	}
	
	/**
		Obt�m o objeto cujo os campos s�o definidos pelos valores
		informados nos componentes da vis�o.
		
		@return O objeto gerado por meio da informa��o dos dados.
	*/
	public abstract T obterRegistro();
}