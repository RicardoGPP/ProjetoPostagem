package pt.up.fc.lc.postagemcliente.principal;

import pt.up.fc.lc.postagemcliente.nucleo.MenuVisao;

/**
	Classe para inicializa��o do sistema.

	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class Principal
{
	/**
		Ponto de partida do sistema. N�o aceita argumentos em linha de comando.
		
		@param Um array de argumentos.
	*/
	public static void main(String[] args)
	{	
		new MenuVisao();
	}
}