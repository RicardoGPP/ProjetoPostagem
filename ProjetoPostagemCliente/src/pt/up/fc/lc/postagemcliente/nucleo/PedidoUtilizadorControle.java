package pt.up.fc.lc.postagemcliente.nucleo;

import java.util.Date;
import pt.up.fc.lc.postagempersistencia.dao.PedidoUtilizadorDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.PedidoUtilizador;

/**
	Classe da camada de controle do pedido de utilizador do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class PedidoUtilizadorControle
{
	private UsuarioDAO usuarioDAO;
	private PedidoUtilizadorDAO pedidoUtilizadorDAO;
	private PedidoUtilizadorVisao pedidoUtilizadorVisao;
	
	/**
		Cria e inicializa o controle do pedido de utilizador.
	*/
	public PedidoUtilizadorControle(PedidoUtilizadorVisao pedidoUtilizadorVisao)
	{
		this.usuarioDAO = new UsuarioDAO();
		this.pedidoUtilizadorDAO = new PedidoUtilizadorDAO();
		this.pedidoUtilizadorVisao = pedidoUtilizadorVisao;
	}
	
	/**
		Verifica se todos os campos obrigatórios da visão foram
		preenchidos.
	
		@return Se todos os campos foram preenchidos ou não.
	*/
	public boolean tudoPreenchido()
	{
		String nomeUsuario = this.pedidoUtilizadorVisao.obterNomeUsuario().trim();
		String senha = this.pedidoUtilizadorVisao.obterSenha();
		String email = this.pedidoUtilizadorVisao.obterEmail().trim();
		Date dataNascimento = this.pedidoUtilizadorVisao.obterDataNascimento();		
		return ((!nomeUsuario.equals("")) && (!senha.equals("")) &&
			   (!email.equals("")) && (dataNascimento != null));
	}
	
	/**
		Verifica se já existe um usuário validado ou um pedido
		de utilizador com o mesmo nome de usuário informado.
		
		@return Se já existe ou não.
	*/
	public boolean usuarioOuPedidoJaExiste()
	{
		String nomeUsuario = this.pedidoUtilizadorVisao.obterNomeUsuario().trim();
		return ((this.usuarioDAO.obterRegistro(nomeUsuario) != null) ||
			   (this.pedidoUtilizadorDAO.obterRegistro(nomeUsuario) != null));
	}
	
	/**
		Insere um novo pedido de utilizador com os dados informados.
		
		@return Se foi inserido ou não.
	*/
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