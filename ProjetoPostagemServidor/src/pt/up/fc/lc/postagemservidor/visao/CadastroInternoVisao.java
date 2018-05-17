package pt.up.fc.lc.postagemservidor.visao;

import javax.swing.JDialog;
import pt.up.fc.lc.postagempersistencia.entidades.Entidade;

public abstract class CadastroInternoVisao<T extends Entidade<T>> extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	protected Modo modo;
	protected boolean processado;
	
	protected CadastroInternoVisao(Modo modo)
	{
		this.modo = modo;
		this.processado = false;
	}
	
	public boolean foiProcessado()
	{
		return processado;
	}
	
	public Modo obterModo()
	{
		return this.modo;
	}
	
	public abstract T obterRegistro();
}