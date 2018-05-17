package pt.up.fc.lc.postagemservidor.principal;

import pt.up.fc.lc.postagempersistencia.dao.UsuarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Grupo;
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
		usuario.setAtivo(true);
		usuario.setValidado(false);
		
		usuarioDAO.inserir(usuario);
		
		new MenuVisao();
	}

}