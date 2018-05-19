package pt.up.fc.lc.postagemcliente.movimentacao;

import java.util.Collections;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.PedidoUtilizadorDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.PedidoUtilizador;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class GerenciarSubscricoesControle
{
	private PedidoUtilizadorDAO pedidoUtilizadorDAO;
	private UsuarioDAO usuarioDAO;
	private GerenciarSubscricoesVisao gerenciarSubscricoesVisao;
	
	public GerenciarSubscricoesControle(GerenciarSubscricoesVisao gerenciarSubscricoesVisao)
	{
		this.pedidoUtilizadorDAO = new PedidoUtilizadorDAO();
		this.usuarioDAO = new UsuarioDAO();
		this.gerenciarSubscricoesVisao = gerenciarSubscricoesVisao;
	}
	
	public boolean usuarioJaExiste()
	{
		PedidoUtilizador pedidoUtilizador = this.gerenciarSubscricoesVisao.obterSelecionado();
		return ((pedidoUtilizador != null) && (this.usuarioDAO.obterRegistro(pedidoUtilizador.getNomeUsuario()) != null));
	}
	
	public void carregarLista()
	{
		List<PedidoUtilizador> pedidosUtilizador = this.pedidoUtilizadorDAO.obterLista();		
		Collections.sort(pedidosUtilizador, (p1, p2) -> p1.getNomeUsuario().compareTo(p2.getNomeUsuario()));
		this.gerenciarSubscricoesVisao.definirSubscricoes(pedidosUtilizador);
	}
	
	public void aceitar()
	{
		PedidoUtilizador pedidoUtilizador = this.gerenciarSubscricoesVisao.obterSelecionado();
		if (pedidoUtilizador != null)
		{
			Usuario usuario = new Usuario(pedidoUtilizador);
			usuario.setAtivo(true);
			this.usuarioDAO.inserir(usuario);
			this.pedidoUtilizadorDAO.deletar(pedidoUtilizador);
			this.gerenciarSubscricoesVisao.excluirDaLista(pedidoUtilizador);
		}
	}
	
	public void rejeitar()
	{
		PedidoUtilizador pedidoUtilizador = this.gerenciarSubscricoesVisao.obterSelecionado();
		if (pedidoUtilizador != null)
		{
			this.pedidoUtilizadorDAO.deletar(pedidoUtilizador);
			this.gerenciarSubscricoesVisao.excluirDaLista(pedidoUtilizador);
		}
	}
}