package pt.up.fc.lc.postagemcliente.principal;

import pt.up.fc.lc.postagemcliente.nucleo.MenuVisao;

/**
	Classe para inicialização do sistema.

	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class Principal
{
	/**
		Ponto de partida do sistema. Não aceita argumentos em linha de comando.
		
		@param Um array de argumentos.
	*/
	public static void main(String[] args)
	{	
		new MenuVisao();
	}
}