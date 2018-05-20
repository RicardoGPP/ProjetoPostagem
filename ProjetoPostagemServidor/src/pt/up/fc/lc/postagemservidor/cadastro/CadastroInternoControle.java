package pt.up.fc.lc.postagemservidor.cadastro;

/**
	Classe padr�o para todos os controles de cadastro interno.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class CadastroInternoControle
{
	protected Modo modo;
	
	/**
		Enumera��o que representa o modo da vis�o.
		
		@version 1.0
		@author  Ricardo Giovani Piantavinha Perandr�,
		         Pedro Sobral da Costa
	*/
	public enum Modo
	{
		INCLUSAO,
		EDICAO
	}
	
	/**
		Inicializa o modo com valor passado por par�metro.
		
		@param O modo de intera��o na vis�o.
	*/
	protected CadastroInternoControle(Modo modo)
	{
		this.modo = modo;
	}
}