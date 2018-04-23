package pt.up.fc.lc.postagem.persistencia.entidades;

public interface Entidade<T>
{
	public boolean comparar(T objeto);
}
