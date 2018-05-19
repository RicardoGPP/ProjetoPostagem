package pt.up.fc.lc.postagemcliente.movimentacao;

import pt.up.fc.lc.postagempersistencia.dao.ComentarioDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Usuario;

public class PublicarEmUmTopicoControle
{
	private ComentarioDAO comentarioDAO;
	private PublicarEmUmTopicoVisao publicarEmUmTopicoVisao;
	
	public PublicarEmUmTopicoControle(PublicarEmUmTopicoVisao publicarEmUmTopicoVisao, Usuario logado)
	{
		this.publicarEmUmTopicoVisao = publicarEmUmTopicoVisao;
		this.comentarioDAO = new ComentarioDAO();		
	}
	
	public boolean tudoPreenchido()
	{
		String titulo = this.publicarEmUmTopicoVisao.obterTopico().trim();
		String descricao = this.publicarEmUmTopicoVisao.obterMensagem().trim();		
		return ((!titulo.equals("")) && (!descricao.equals("")));		
	}
	
	
}