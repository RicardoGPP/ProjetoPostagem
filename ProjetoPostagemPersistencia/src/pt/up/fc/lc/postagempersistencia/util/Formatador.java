package pt.up.fc.lc.postagempersistencia.util;

public class Formatador
{
	public static String formatar(String valor)
	{
		return valor.replace(";", "").replace("\n", " ");
	}
}
