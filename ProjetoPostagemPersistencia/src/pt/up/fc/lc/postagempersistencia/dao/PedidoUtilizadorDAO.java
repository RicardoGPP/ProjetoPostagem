package pt.up.fc.lc.postagempersistencia.dao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.entidades.PedidoUtilizador;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe para acesso a dados da entidade de pedido de utilizador.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro Sobral da Costa
*/
public class PedidoUtilizadorDAO extends DAO<PedidoUtilizador>
{
	private static final String CAMINHO = "PEDIDO_UTILIZADOR";
	
	/**
		Cria o DAO e passa o caminho do arquivo à superclasse.
	*/
	public PedidoUtilizadorDAO()
	{
		super(CAMINHO);
	}

	/**
		Converte uma linha de dados delimitados por ponto e vírgula
		para um objeto de pedido de utilizador.
		
		@param Uma linha de dados.
		@return Um objeto de pedido de utilizador.
	*/
	protected PedidoUtilizador deStringParaObjeto(String linha)
	{
		String dados[] = linha.split(";");
		if (dados.length == 4)
		{
			try
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA);
				PedidoUtilizador pedidoUtilizador = new PedidoUtilizador();
				pedidoUtilizador.setNomeUsuario(dados[0]);
				pedidoUtilizador.setSenha(dados[1]);
				pedidoUtilizador.setEmail(dados[2]);
				pedidoUtilizador.setDataNascimento(simpleDateFormat.parse(dados[3]));
				return pedidoUtilizador;
			} catch (ParseException e)
			{
				return null;
			}
		}		
		return null;
	}

	/**
		Converte um objeto de pedido de utilizador para uma linha de dados
		delimitada por ponto e vírgula.
		
		@param Um objeto de pedido de utilizador.
		@return Uma linha de dados. 
	*/
	protected String deObjetoParaString(PedidoUtilizador pedidoUtilizador)
	{
		if (pedidoUtilizador != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA_HORA);
			String linha = "";
			linha += pedidoUtilizador.getNomeUsuario() + ";";
			linha += pedidoUtilizador.getSenha() + ";";
			linha += pedidoUtilizador.getEmail() + ";";
			linha += simpleDateFormat.format(pedidoUtilizador.getDataNascimento());	
			return linha;
		}		
		return "";
	}
	
	/**
		Verifica se o pedido de utilizador existe no arquivo.
		
		@param Um pedido de utilizador.
		@return Se o pedido de utilizador existe ou não.
	*/
	public boolean existe(PedidoUtilizador pedidoUtilizador)
	{
		return (this.obterRegistro(pedidoUtilizador.getNomeUsuario()) != null);
	}
	
	/**
		Busca um pedido de utilizador no arquivo por meio de um nome de usuário.
		
		@param O nome do usuário a ser recuperado.
		@return O pedido de utilizador se for encontrado ou null se não for. 
	*/
	public PedidoUtilizador obterRegistro(String nomeUsuario)
	{
		for (PedidoUtilizador pedidoUtilizador : obterLista())
			if (pedidoUtilizador.getNomeUsuario().equalsIgnoreCase(nomeUsuario))
				return pedidoUtilizador;
		return null;
	}
	
	/**
		Insere um pedido de utilizador no arquivo.
	
		@param O pedido de utilizador a ser inserido.
		@return Se o pedido de utilizador foi inserido ou não.
	*/
	public boolean inserir(PedidoUtilizador pedidoUtilizador)
	{
		if ((pedidoUtilizador != null) &&
		   (!(new UsuarioDAO()).existe(new Usuario(pedidoUtilizador))) &&
		   (!this.existe(pedidoUtilizador)))
		{
			try
			{
				escrever(deObjetoParaString(pedidoUtilizador), this.arquivo, false);
				return true;
			} catch (IOException e)
			{
				return false;
			}
		}			
		return false;
	}
	
	/**
		Deleta um pedido de utilizador no arquivo.
	
		@param O pedido de utilizador a ser deletado.
		@return Se o pedido de utilizador foi deletado ou não.
	*/
	public boolean deletar(PedidoUtilizador pedidoUtilizador)
	{
		if ((pedidoUtilizador != null) && (this.existe(pedidoUtilizador)))
		{
			List<String> linhas = new ArrayList<>();
			List<PedidoUtilizador> pedidosUtilizador = obterLista();
			for (Iterator<PedidoUtilizador> iterator = pedidosUtilizador.iterator(); iterator.hasNext();)
			{
				PedidoUtilizador outroPedidoUtilizador = iterator.next();
				if (pedidoUtilizador.comparar(outroPedidoUtilizador))
				{
					iterator.remove();
					break;
				}
			}			
			for (PedidoUtilizador pedidoUtilizadorRestante : pedidosUtilizador)
				linhas.add(this.deObjetoParaString(pedidoUtilizadorRestante));
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
}