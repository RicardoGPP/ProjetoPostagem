package pt.up.fc.lc.postagemservidor.cadastro;

public class CadastroInternoControle
{
	protected Modo modo;
	
	public enum Modo
	{
		INCLUSAO,
		EDICAO
	}
	
	protected CadastroInternoControle(Modo modo)
	{
		this.modo = modo;
	}
}