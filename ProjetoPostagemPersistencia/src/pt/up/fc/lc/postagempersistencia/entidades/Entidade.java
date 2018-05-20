package pt.up.fc.lc.postagempersistencia.entidades;

/**
	Interface para a padronização das entidades do sistema.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public interface Entidade<T>
{
	/**
		Compara o objeto de entidade no contexto com outro do mesmo tipo.
		
		@param Um objeto entidade.
		@return Se os objetos são iguais ou não.
	*/
	public boolean comparar(T objeto);
}
