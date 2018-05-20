package pt.up.fc.lc.postagempersistencia.entidades;

/**
	Interface para a padroniza��o das entidades do sistema.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public interface Entidade<T>
{
	/**
		Compara o objeto de entidade no contexto com outro do mesmo tipo.
		
		@param Um objeto entidade.
		@return Se os objetos s�o iguais ou n�o.
	*/
	public boolean comparar(T objeto);
}
