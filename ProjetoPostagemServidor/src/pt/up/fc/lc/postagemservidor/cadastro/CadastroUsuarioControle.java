package pt.up.fc.lc.postagemservidor.cadastro;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do cadastro de usu�rios do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class CadastroUsuarioControle
{
	private UsuarioDAO usuarioDAO;
	private CadastroUsuarioVisao cadastroUsuarioVisao;
	private Usuario logado;
	
	/**
		Cria e inicializa o controle de cadastro de usu�rios.
		
		@param A vis�o do cadastro de usu�rios.
	*/
	public CadastroUsuarioControle(CadastroUsuarioVisao cadastroUsuarioVisao, Usuario logado)
	{
		this.usuarioDAO = new UsuarioDAO();
		this.cadastroUsuarioVisao = cadastroUsuarioVisao;
		this.logado = logado;
	}
	
	/**
		Carrega a lista de usu�rios.
	*/
	public void carregarLista()
	{
		List<Usuario> usuarios = this.usuarioDAO.obterLista();
		for (Iterator<Usuario> iterator = usuarios.iterator(); iterator.hasNext();)
		{
			Usuario usuario = iterator.next();
			if (usuario.comparar(this.logado))
			{
				iterator.remove();
				break;
			}
		}
		Collections.sort(usuarios, (u1, u2) -> u1.getNomeUsuario().compareTo(u2.getNomeUsuario()));
		this.cadastroUsuarioVisao.definirUsuarios(usuarios);
	}
	
	/**
		Chama o cadastro de t�pico interno, inclui o objeto no arquivo e atualiza a
		lista em caso de sucesso.
	*/
	public void incluir()
	{	
		CadastroUsuarioInternoVisao cadastroUsuarioInterno = new CadastroUsuarioInternoVisao();
		if (cadastroUsuarioInterno.foiProcessado())
		{
			Usuario usuario = cadastroUsuarioInterno.obterRegistro();
			if (this.usuarioDAO.inserir(usuario))
				this.cadastroUsuarioVisao.incluirNaLista(usuario);
		}
	}
	
	/**
		Chama o cadastro de usu�rio interno e inclui o objeto no arquivo.
	*/
	public void editar()
	{
		Usuario usuario = this.cadastroUsuarioVisao.obterSelecionado();
		if (usuario != null)
		{
			CadastroUsuarioInternoVisao cadastroUsuarioInterno = new CadastroUsuarioInternoVisao(usuario);
			if (cadastroUsuarioInterno.foiProcessado())
				this.usuarioDAO.editar(usuario);
		}
	}
	
	/**
		Exclui o usu�rio no arquivo e atualiza a lista em caso de sucesso.
	*/
	public void excluir()
	{
		Usuario usuario = this.cadastroUsuarioVisao.obterSelecionado();
		if (this.usuarioDAO.deletar(usuario))
			this.cadastroUsuarioVisao.excluirDaLista(usuario);
	}
}