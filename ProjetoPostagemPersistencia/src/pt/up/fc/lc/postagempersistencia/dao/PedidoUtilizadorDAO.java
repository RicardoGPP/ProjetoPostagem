package pt.up.fc.lc.postagempersistencia.dao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pt.up.fc.lc.postagempersistencia.entidades.PedidoUtilizador;

public class PedidoUtilizadorDAO extends DAO<PedidoUtilizador>
{
	private static final String CAMINHO = "PEDIDO_UTILIZADOR";
	
	public PedidoUtilizadorDAO()
	{
		super(CAMINHO);
	}

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

	protected String deObjetoParaString(PedidoUtilizador objeto)
	{
		if (objeto != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_DATA_HORA);
			String linha = "";
			linha += objeto.getNomeUsuario() + ";";
			linha += objeto.getSenha() + ";";
			linha += objeto.getEmail() + ";";
			linha += simpleDateFormat.format(objeto.getDataNascimento());	
			return linha;
		}		
		return "";
	}
	
	public PedidoUtilizador obterRegistro(String nomeUsuario)
	{
		for (PedidoUtilizador pedidoUtilizador : obterLista())
			if (pedidoUtilizador.getNomeUsuario().equalsIgnoreCase(nomeUsuario))
				return pedidoUtilizador;
		return null;
	}
	
	public boolean inserir(PedidoUtilizador pedidoUtilizador)
	{
		if ((pedidoUtilizador != null) &&
		   ((new UsuarioDAO()).obterRegistro(pedidoUtilizador.getNomeUsuario()) == null) &&
		   (obterRegistro(pedidoUtilizador.getNomeUsuario()) == null))
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
	
	public boolean deletar(PedidoUtilizador pedidoUtilizador)
	{
		if (pedidoUtilizador != null)
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
				linhas.add(deObjetoParaString(pedidoUtilizadorRestante));
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