package pt.up.fc.lc.postagemservidor.cadastro;

/**
	Classe padrão para todos os controles de cadastro interno.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class CadastroInternoControle
{
	protected Modo modo;
	
	/**
		Enumeração que representa o modo da visão.
		
		@version 1.0
		@author  Ricardo Giovani Piantavinha Perandré,
		         Pedro
	*/
	public enum Modo
	{
		INCLUSAO,
		EDICAO
	}
	
	/**
		Inicializa o modo com valor passado por parâmetro.
		
		@param O modo de interação na visão.
	*/
	protected CadastroInternoControle(Modo modo)
	{
		this.modo = modo;
	}
}