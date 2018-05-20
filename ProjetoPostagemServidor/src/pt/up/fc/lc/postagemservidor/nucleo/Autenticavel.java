package pt.up.fc.lc.postagemservidor.nucleo;

import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Interface que permite aos controles do sistema fazer chamadas
	de autenticação.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public interface Autenticavel
{
	/**
		Define o usuário recuperado pela autenticação.
	
		@param Um usuário.
	*/
	public void definirUsuario(Usuario usuario);
}
