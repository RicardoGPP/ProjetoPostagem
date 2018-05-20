package pt.up.fc.lc.postagempersistencia.dao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe para acesso a dados da entidade de usu�rio.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class UsuarioDAO extends DAO<Usuario>
{
	private static final String CAMINHO = "USUARIO";
	
	/**
		Cria o DAO e passa o caminho do arquivo � superclasse.
	*/
	public UsuarioDAO()
	{
		super(CAMINHO);
	}
	
	/**
		Converte uma linha de dados delimitados por ponto e v�rgula
		para um objeto de usu�rio.
		
		@param Uma linha de dados.
		@return Um objeto de usu�rio.
	*/
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
	
	/**
		Converte um objeto de usu�rio para uma linha de dados delimitada
		por ponto e v�rgula.
		
		@param Um objeto de usu�rio.
		@return Uma linha de dados. 
	*/
	protected String deObjetoParaString(Usuario usuario)
	{
		if (usuario != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA);			
			String linha = "";
			linha += usuario.getNomeUsuario() + ";";
			linha += usuario.getSenha() + ";";
			linha += usuario.getGrupo() + ";";
			linha += usuario.getContato().getNomeCompleto() + ";";
			linha += usuario.getContato().getEmail() + ";";
			linha += usuario.getContato().getTelefone() + ";";
			linha += simpleDateFormat.format(usuario.getContato().getDataNascimento()) + ";";
			linha += usuario.getLimiteSubscricoes() + ";";
			linha += usuario.isAtivo();
			return linha;			
		}
		return "";
	}
	
	/**
		Verifica se o usu�rio existe no arquivo.
		
		@param Um usu�rio.
		@return Se o usu�rio existe ou n�o.
	*/
	public boolean existe(Usuario usuario)
	{
		return (this.obterRegistro(usuario.getNomeUsuario()) != null);
	}
	
	/**
		Busca um usu�rio no arquivo por meio de um nome de usu�rio.
		
		@param O nome do usu�rio a ser recuperado.
		@return O usu�rio se for encontrado ou null se n�o for. 
	*/
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
	
	/**
		Insere um usu�rio no arquivo.
	
		@param O usu�rio a ser inserido.
		@return Se o usu�rio foi inserido ou n�o.
	*/
	public boolean inserir(Usuario usuario)
	{
		if ((usuario != null) && (!this.existe(usuario)))
		{
			try
			{
				this.escrever(this.deObjetoParaString(usuario), this.arquivo, false);
				return true;
			} catch (IOException e)
			{
				return false;
			}
		}
		return false;
	}
	
	/**
		Deleta um usu�rio no arquivo.
	
		@param O usu�rio a ser deletado.
		@return Se o usu�rio foi deletado ou n�o.
	*/
	public boolean deletar(Usuario usuario)
	{
		if ((usuario != null) && (this.existe(usuario)))
		{			
			(new SubscricaoDAO()).deletar(usuario);
			(new ComentarioDAO()).deletar(usuario);
			(new CurtidaDAO()).deletar(usuario);
			List<String> linhas = new ArrayList<>();
			List<Usuario> usuarios = this.obterLista();			
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
				this.escrever(linhas, this.arquivo, true);
				return true;
			} catch (IOException e)
			{
				return false;
			}			
		}
		return false;
	}
	
	/**
		Edita um usu�rio no arquivo.
	
		@param O usu�rio a ser editado.
		@return Se o usu�rio foi editado ou n�o.
	*/
	public boolean editar(Usuario usuario)
	{
		if ((usuario != null) && (this.existe(usuario)))
		{
			List<String> linhas = new ArrayList<>();
			List<Usuario> usuarios = this.obterLista();
			for (int i = 0; i < usuarios.size(); i++)
				if (usuario.comparar(usuarios.get(i)))
					usuarios.set(i, usuario);			
			for (Usuario novoUsuario : usuarios)
				linhas.add(this.deObjetoParaString(novoUsuario));			
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