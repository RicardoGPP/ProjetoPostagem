package pt.up.fc.lc.postagemservidor.movimentacao;

import java.util.Collections;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.PedidoUtilizadorDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.PedidoUtilizador;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

/**
	Classe da camada de controle do gerenciamento de pedidos de utilizador
	do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class GerenciarPedidosUtilizadorControle
{
	private PedidoUtilizadorDAO pedidoUtilizadorDAO;
	private UsuarioDAO usuarioDAO;
	private GerenciarPedidosUtilizadorVisao gerenciarPedidosUtilizadorVisao;
	
	/**
		Cria e inicializa o controle de gerenciamento de pedidos de utilizador.
		
		@param A vis�o do gerenciamento de pedidos de utilizador.
	*/
	public GerenciarPedidosUtilizadorControle(GerenciarPedidosUtilizadorVisao gerenciarPedidosUtilizadorVisao)
	{
		this.pedidoUtilizadorDAO = new PedidoUtilizadorDAO();
		this.usuarioDAO = new UsuarioDAO();
		this.gerenciarPedidosUtilizadorVisao = gerenciarPedidosUtilizadorVisao;
	}
	
	/**
		Verifica se o usu�rio a ser criado pelo pedido de utilizador j� existe.
		
		@return Se o usu�rio existe ou n�o.
	*/
	public boolean usuarioJaExiste()
	{
		PedidoUtilizador pedidoUtilizador = this.gerenciarPedidosUtilizadorVisao.obterSelecionado();
		return ((pedidoUtilizador != null) && (this.usuarioDAO.existe(new Usuario(pedidoUtilizador))));
	}
	
	/**
		Carrega a lista de pedidos de utilizador.
	*/
	public void carregarLista()
	{
		List<PedidoUtilizador> pedidosUtilizador = this.pedidoUtilizadorDAO.obterLista();		
		Collections.sort(pedidosUtilizador, (p1, p2) -> p1.getNomeUsuario().compareTo(p2.getNomeUsuario()));
		this.gerenciarPedidosUtilizadorVisao.definirPedidosUtilizador(pedidosUtilizador);
	}
	
	/**
		Aceita um pedido de utilizador, removendo o pedido do arquivo
		e adicionando um usu�rio com as informa��es referentes.
	*/
	public void aceitar()
	{
		PedidoUtilizador pedidoUtilizador = this.gerenciarPedidosUtilizadorVisao.obterSelecionado();
		if (pedidoUtilizador != null)
		{
			Usuario usuario = new Usuario(pedidoUtilizador);
			usuario.setAtivo(true);
			this.usuarioDAO.inserir(usuario);
			this.pedidoUtilizadorDAO.deletar(pedidoUtilizador);
			this.gerenciarPedidosUtilizadorVisao.excluirDaLista(pedidoUtilizador);
		}
	}
	
	/**
		Rejeita um pedido de utilizador, removendo o pedido do arquivo.
	*/
	public void rejeitar()
	{
		PedidoUtilizador pedidoUtilizador = this.gerenciarPedidosUtilizadorVisao.obterSelecionado();
		if (pedidoUtilizador != null)
		{
			this.pedidoUtilizadorDAO.deletar(pedidoUtilizador);
			this.gerenciarPedidosUtilizadorVisao.excluirDaLista(pedidoUtilizador);
		}
	}
}