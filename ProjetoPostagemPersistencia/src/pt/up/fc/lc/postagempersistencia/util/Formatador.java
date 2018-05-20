package pt.up.fc.lc.postagempersistencia.util;

/**
	Classe responsável por formatar os dados em String dos objetos
	a serem persistidos.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class Formatador
{
	/**
		Formata o valor em String removendo o delimitador e a quebra de
		linha dos dados para uma persistência consistente.
	 
		@param O valor a ser formatado.
		@return O valor formatado.
	*/
	public static String formatar(String valor)
	{
		return valor.replace(";", "").replace("\n", " ");
	}
}
