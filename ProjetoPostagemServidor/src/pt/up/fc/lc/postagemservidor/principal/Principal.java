package pt.up.fc.lc.postagemservidor.principal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.dao.DAO;
import pt.up.fc.lc.postagempersistencia.dao.PedidoUtilizadorDAO;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Comentario;
import pt.up.fc.lc.postagempersistencia.entidades.Grupo;
import pt.up.fc.lc.postagempersistencia.entidades.PedidoUtilizador;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;
import pt.up.fc.lc.postagemservidor.visao.MenuVisao;

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