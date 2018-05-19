package pt.up.fc.lc.postagempersistencia.entidades;

import java.text.SimpleDateFormat;
import java.util.Date;

import pt.up.fc.lc.postagempersistencia.dao.CurtidaDAO;
import pt.up.fc.lc.postagempersistencia.dao.DAO;
import pt.up.fc.lc.postagempersistencia.util.Formatador;

public class Comentario implements Entidade<Comentario>
{	
	private Usuario usuario;
	private Topico topico;
	private Date data;
	private String mensagem;
	
	public Usuario getUsuario()
	{
		return usuario;
	}
	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
	public Topico getTopico()
	{
		return topico;
	}
	public void setTopico(Topico topico)
	{
		this.topico = topico;
	}
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public String getMensagem()
	{
		return mensagem;
	}
	public void setMensagem(String mensagem)
	{
		this.mensagem = Formatador.formatar(mensagem);
	}
	
	public Comentario()
	{
		this.usuario = null;
		this.topico = null;
		this.data = new Date();
		this.mensagem = "";
	}
	
	public boolean comparar(Comentario objeto)
	{
		return ((objeto != null) && (objeto.getUsuario() != null) && (objeto.getTopico() != null) &&
			   (objeto.getData() != null) && (this.usuario != null) && (this.topico != null) &&
			   (this.data != null) && (this.data.compareTo(objeto.getData()) == 0));		
	}
	
	public String toString()
	{
		CurtidaDAO curtidaDAO = new CurtidaDAO();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAO.FORMATO_DATA_HORA);		
		String representacao = "";
		representacao += "[" + simpleDateFormat.format(this.data) + "] ";
		representacao += this.usuario.getNomeUsuario() + " escreveu: ";
		representacao += this.mensagem;
		representacao += " (" + curtidaDAO.obterQuantidadeCurtidas(this) + ")";		
		return representacao;		
	}
}