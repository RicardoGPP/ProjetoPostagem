package pt.up.fc.lc.postagempersistencia.dao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class UsuarioDAO extends DAO<Usuario>
{
	private static final String CAMINHO = "USUARIO";
	
	public UsuarioDAO()
	{
		super(CAMINHO);
	}
	
	protected Usuario deStringParaObjeto(String linha)
	{
		String[] dados = linha.split(";");		
		if (dados.length == 9)
		{
			try
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA);
				Usuario usuario = new Usuario();			
				usuario.setNomeUsuario(dados[0]);
				usuario.setSenha(dados[1]);
				usuario.setGrupo((dados[2].equals("ADMIN") ? Usuario.Grupo.ADMIN : Usuario.Grupo.OTHER));
				usuario.getContato().setNomeCompleto(dados[3]);
				usuario.getContato().setEmail(dados[4]);
				usuario.getContato().setTelefone(dados[5]);
				usuario.getContato().setDataNascimento(simpleDateFormat.parse(dados[6]));
				usuario.setLimiteSubscricoes(Integer.parseInt(dados[7]));
				usuario.setAtivo(dados[8].equals("true"));
				return usuario;
			} catch (ParseException e)
			{
				return null;
			}
		}		
		return null;
	}
	
	protected String deObjetoParaString(Usuario objeto)
	{
		if (objeto != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA);			
			String linha = "";
			linha += objeto.getNomeUsuario() + ";";
			linha += objeto.getSenha() + ";";
			linha += objeto.getGrupo() + ";";
			linha += objeto.getContato().getNomeCompleto() + ";";
			linha += objeto.getContato().getEmail() + ";";
			linha += objeto.getContato().getTelefone() + ";";
			linha += simpleDateFormat.format(objeto.getContato().getDataNascimento()) + ";";
			linha += objeto.getLimiteSubscricoes() + ";";
			linha += objeto.isAtivo();
			return linha;			
		}
		return "";
	}
	
	public Usuario obterRegistro(String nomeUsuario)
	{			
		if (nomeUsuario.equalsIgnoreCase("Master"))
		{
			Usuario usuario = new Usuario();
			usuario.setNomeUsuario("Master");
			usuario.setSenha("master");
			usuario.setGrupo(Usuario.Grupo.ADMIN);
			return usuario;
		}
		for (Usuario usuario : obterLista())
			if (usuario.getNomeUsuario().equalsIgnoreCase(nomeUsuario))
				return usuario;
		return null;
	}
	
	public boolean inserir(Usuario usuario)
	{
		if ((usuario != null) && (obterRegistro(usuario.getNomeUsuario()) == null))
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
			CurtidaDAO curtidaDAO = new CurtidaDAO();
			subscricaoDAO.deletar(usuario);
			comentarioDAO.deletar(usuario);
			curtidaDAO.deletar(usuario);
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