package pt.up.fc.lc.postagemservidor.principal;

import java.util.Date;
import pt.up.fc.lc.postagempersistencia.dao.PedidoUtilizadorDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Grupo;
import pt.up.fc.lc.postagempersistencia.entidades.PedidoUtilizador;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;
import pt.up.fc.lc.postagemservidor.nucleo.MenuVisao;

public class Principal
{
	public static void main(String[] args)
	{
		Usuario usuario = new Usuario();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		usuario.setUtilizador("Ricardo");
		usuario.setSenha("sasuke");
		usuario.setGrupo(Grupo.ADMIN);
		usuario.getContacto().setNome("Ricardo Giovani Piantavinha Perandré");
		usuario.getContacto().setEmail("ricardo.piantavinha@gmail.com");
		usuario.getContacto().setTelefone("(19)98334-4053");
		usuario.getContacto().setDataNascimento(new Date());
		usuario.setLimiteSubscricoes(20);
		usuario.setAtivo(true);
		
		usuarioDAO.inserir(usuario);
		
		
		PedidoUtilizadorDAO pedidoDAO = new PedidoUtilizadorDAO();
		PedidoUtilizador pedido = new PedidoUtilizador();
		
		pedido.setNome("Paulo");
		pedido.setSenha("123");
		pedido.setEmail("paulo@gmail.com");
		pedido.setDataNascimento(new Date());
		
		pedidoDAO.inserir(pedido);
		
		new MenuVisao();
	}

}