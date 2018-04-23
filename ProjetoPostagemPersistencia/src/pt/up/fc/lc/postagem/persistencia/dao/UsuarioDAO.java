package pt.up.fc.lc.postagem.persistencia.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pt.up.fc.lc.postagem.persistencia.entidades.Grupo;
import pt.up.fc.lc.postagem.persistencia.entidades.Usuario;

public class UsuarioDAO extends DAO<Usuario>
{
	private static final String CAMINHO = "usuarios.txt";
	private File arquivo;
	
	public UsuarioDAO()
	{
		this.arquivo = new File(CAMINHO);
	}
	
	protected Usuario deStringParaObjeto(String linha)
	{
		String[] dados = linha.split(";");		
		if (dados.length == 8)
		{
			Usuario usuario = new Usuario();			
			usuario.setUtilizador(dados[0]);
			usuario.setSenha(dados[1]);
			usuario.setGrupo((dados[2].equals("ADMIN") ? Grupo.ADMIN : Grupo.OTHER));
			usuario.getContacto().setNome(dados[3]);
			usuario.getContacto().setEmail(dados[4]);
			usuario.getContacto().setTelefone(dados[5]);
			usuario.setValidado(dados[6].equals("true"));
			usuario.setAtivo(dados[7].equals("true"));
			return usuario;
		}		
		return null;
	}
	
	protected String deObjetoParaString(Usuario objeto)
	{
		if (objeto != null)
		{
			String linha = "";			
			linha += objeto.getUtilizador() + ";";
			linha += objeto.getSenha() + ";";
			linha += objeto.getGrupo() + ";";
			linha += objeto.getContacto().getNome() + ";";
			linha += objeto.getContacto().getEmail() + ";";
			linha += objeto.getContacto().getTelefone() + ";";
			linha += objeto.isValidado() + ";";
			linha += objeto.isAtivo();			
			return linha;			
		}
		return "";
	}
	
	public Usuario obterRegistro(String utilizador)
	{			
		for (Usuario usuario : obterLista())
			if (usuario.getUtilizador().equalsIgnoreCase(utilizador))
				return usuario;
		return null;
	}
	
	public List<Usuario> obterLista()
	{
		List<Usuario> usuarios = new ArrayList<>();
		try
		{
			for (String linha : ler(this.arquivo))
				usuarios.add(deStringParaObjeto(linha));						
		} catch (IOException e)
		{
			usuarios.clear();
		}
		return usuarios;
	}
	
	public boolean inserir(Usuario usuario)
	{
		if ((usuario != null) && (obterRegistro(usuario.getUtilizador()) == null))
		{
			try
			{
				escrever(deObjetoParaString(usuario), this.arquivo, false);
				return true;
			} catch (IOException e)
			{
				return false;
			}
		}
		return false;
	}
	
	public boolean deletar(Usuario usuario)
	{
		if (usuario != null)
		{			
			SubscricaoDAO subscricaoDAO = new SubscricaoDAO();
			ComentarioDAO comentarioDAO = new ComentarioDAO();			
			subscricaoDAO.deletar(usuario);
			comentarioDAO.deletar(usuario);			
			List<String> linhas = new ArrayList<>();
			List<Usuario> usuarios = obterLista();			
			for (Iterator<Usuario> iterator = usuarios.iterator(); iterator.hasNext();)
			{
				Usuario outroUsuario = iterator.next();
				if (usuario.comparar(outroUsuario))
				{
					iterator.remove();
					break;
				}
			}			
			for (Usuario usuarioRestante : usuarios)
				linhas.add(deObjetoParaString(usuarioRestante));			
			try
			{
				escrever(linhas, this.arquivo, true);
				return true;
			} catch (IOException e)
			{
				return false;
			}			
		}
		return false;
	}
	
	public boolean editar(Usuario usuario)
	{
		if (usuario != null)
		{
			List<String> linhas = new ArrayList<>();
			List<Usuario> usuarios = obterLista();
			for (int i = 0; i < usuarios.size(); i++)
				if (usuario.comparar(usuarios.get(i)))
					usuarios.set(i, usuario);			
			for (Usuario novoUsuario : usuarios)
				linhas.add(deObjetoParaString(novoUsuario));			
			try
			{
				escrever(linhas, this.arquivo, true);
				return true;
			} catch (IOException e)
			{
				return false;
			}
		}
		return false;
	}
}