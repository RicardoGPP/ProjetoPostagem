package pt.up.fc.lc.postagemservidor.controle;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;
import pt.up.fc.lc.postagemservidor.visao.CadastroUsuarioInternoVisao;
import pt.up.fc.lc.postagemservidor.visao.CadastroUsuarioVisao;

public class CadastroUsuarioControle
{
	private UsuarioDAO usuarioDAO;
	private CadastroUsuarioVisao cadastroUsuarioVisao;
	private Usuario logado;
	
	public CadastroUsuarioControle(CadastroUsuarioVisao cadastroUsuarioVisao, Usuario logado)
	{
		this.usuarioDAO = new UsuarioDAO();
		this.cadastroUsuarioVisao = cadastroUsuarioVisao;
		this.logado = logado;
	}
	
	public void carregarLista()
	{
		List<Usuario> usuarios = this.usuarioDAO.obterLista();
		for (Iterator<Usuario> iterator = usuarios.iterator(); iterator.hasNext();)
		{
			Usuario usuario = iterator.next();
			if ((usuario.comparar(this.logado)) || (!usuario.isValidado()))
			{
				iterator.remove();
				break;
			}
		}
		Collections.sort(usuarios, (u1, u2) -> u1.getUtilizador().compareTo(u2.getUtilizador()));
		this.cadastroUsuarioVisao.definirUsuarios(usuarios);
	}
	
	public void incluir()
	{	
		Usuario usuario = new Usuario();
		new CadastroUsuarioInternoVisao(usuario);		
		if ((!usuario.getUtilizador().equals("")) && (this.usuarioDAO.inserir(usuario)))
			this.cadastroUsuarioVisao.incluirNaLista(usuario);
	}
	
	public void editar()
	{
		Usuario usuario = this.cadastroUsuarioVisao.obterSelecionado();
		new CadastroUsuarioInternoVisao(usuario);
		if (usuario != null)
			this.usuarioDAO.editar(usuario);
	}
	
	public void excluir()
	{
		Usuario usuario = this.cadastroUsuarioVisao.obterSelecionado();
		if (this.usuarioDAO.deletar(usuario))
			this.cadastroUsuarioVisao.excluirDaLista(usuario);
	}
}