package pt.up.fc.lc.postagemservidor.cadastro;

import javax.swing.JDialog;
import pt.up.fc.lc.postagempersistencia.entidades.Entidade;

public abstract class CadastroInternoVisao<T extends Entidade<T>> extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	protected boolean processado;
	
	protected CadastroInternoVisao()
	{
		this.processado = false;
	}
	
	public boolean foiProcessado()
	{
		return this.processado;
	}
	
	public abstract T obterRegistro();
}