package pt.up.fc.lc.postagemcliente.nucleo;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Interface que permite aos controles do sistema fazer chamadas
	de autentica��o.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public interface Autenticavel
{
	/**
		Define o usu�rio recuperado pela autentica��o.
	
		@param Um usu�rio.
	*/
	public void definirUsuario(Usuario usuario);
}
