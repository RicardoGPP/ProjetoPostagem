package pt.up.fc.lc.postagemservidor.movimentacao;

import java.util.Collections;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.PedidoUtilizadorDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.PedidoUtilizador;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class GerenciarPedidosUtilizadorControle
{
	private PedidoUtilizadorDAO pedidoUtilizadorDAO;
	private UsuarioDAO usuarioDAO;
	private GerenciarPedidosUtilizadorVisao gerenciarPedidosUtilizadorVisao;
	
	public GerenciarPedidosUtilizadorControle(GerenciarPedidosUtilizadorVisao gerenciarPedidosUtilizadorVisao)
	{
		this.pedidoUtilizadorDAO = new PedidoUtilizadorDAO();
		this.usuarioDAO = new UsuarioDAO();
		this.gerenciarPedidosUtilizadorVisao = gerenciarPedidosUtilizadorVisao;
	}
	
	public boolean usuarioJaExiste()
	{
		PedidoUtilizador pedidoUtilizador = this.gerenciarPedidosUtilizadorVisao.obterSelecionado();
		return ((pedidoUtilizador != null) && (this.usuarioDAO.obterRegistro(pedidoUtilizador.getNome()) != null));
	}
	
	public void carregarLista()
	{
		List<PedidoUtilizador> pedidosUtilizador = this.pedidoUtilizadorDAO.obterLista();		
		Collections.sort(pedidosUtilizador, (p1, p2) -> p1.getNome().compareTo(p2.getNome()));
		this.gerenciarPedidosUtilizadorVisao.definirPedidosUtilizador(pedidosUtilizador);
	}
	
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