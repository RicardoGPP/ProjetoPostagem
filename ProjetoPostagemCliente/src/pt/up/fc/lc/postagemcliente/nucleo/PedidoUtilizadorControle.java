package pt.up.fc.lc.postagemcliente.nucleo;

import java.util.Date;
import pt.up.fc.lc.postagempersistencia.dao.PedidoUtilizadorDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.PedidoUtilizador;

public class PedidoUtilizadorControle
{
	private UsuarioDAO usuarioDAO;
	private PedidoUtilizadorDAO pedidoUtilizadorDAO;
	private PedidoUtilizadorVisao pedidoUtilizadorVisao;
	
	public PedidoUtilizadorControle(PedidoUtilizadorVisao pedidoUtilizadorVisao)
	{
		this.usuarioDAO = new UsuarioDAO();
		this.pedidoUtilizadorDAO = new PedidoUtilizadorDAO();
		this.pedidoUtilizadorVisao = pedidoUtilizadorVisao;
	}
	
	public boolean tudoPreenchido()
	{
		String nomeUsuario = this.pedidoUtilizadorVisao.obterNomeUsuario().trim();
		String senha = this.pedidoUtilizadorVisao.obterSenha();
		String email = this.pedidoUtilizadorVisao.obterEmail().trim();
		Date dataNascimento = this.pedidoUtilizadorVisao.obterDataNascimento();		
		return ((!nomeUsuario.equals("")) && (!senha.equals("")) &&
			   (!email.equals("")) && (dataNascimento != null));
	}
	
	public boolean usuarioOuPedidoJaExiste()
	{
		String nomeUsuario = this.pedidoUtilizadorVisao.obterNomeUsuario().trim();
		return ((this.usuarioDAO.obterRegistro(nomeUsuario) != null) ||
			   (this.pedidoUtilizadorDAO.obterRegistro(nomeUsuario) != null));
	}
	
	public boolean registrarPedido()
	{
		PedidoUtilizador pedidoUtilizador = new PedidoUtilizador();		
		pedidoUtilizador.setNomeUsuario(this.pedidoUtilizadorVisao.obterNomeUsuario().trim());
		pedidoUtilizador.setSenha(this.pedidoUtilizadorVisao.obterSenha());
		pedidoUtilizador.setEmail(this.pedidoUtilizadorVisao.obterEmail().trim());
		pedidoUtilizador.setDataNascimento(this.pedidoUtilizadorVisao.obterDataNascimento());		
		return this.pedidoUtilizadorDAO.inserir(pedidoUtilizador);
	}
}