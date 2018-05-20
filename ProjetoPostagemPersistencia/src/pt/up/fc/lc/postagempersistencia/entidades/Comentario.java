package pt.up.fc.lc.postagempersistencia.entidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import pt.up.fc.lc.postagempersistencia.dao.CurtidaDAO;
import pt.up.fc.lc.postagempersistencia.dao.DAO;
import pt.up.fc.lc.postagempersistencia.util.Formatador;

/**
	Classe que representa o coment�rio para persist�ncia e uso no sistema.
	Um coment�rio � representado por uma refer�ncia entre usu�rio e t�pico,
	com a men��o de uma mensagem em uma respectiva data.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro
*/
public class Comentario implements Entidade<Comentario>
{	
	private Usuario usuario;
	private Topico topico;
	private Date data;
	private String mensagem;
	
	/**
		M�todo getter para a obten��o do usu�rio do coment�rio.
		
		@return O usu�rio do coment�rio.
	*/
	public Usuario getUsuario()
	{
		return usuario;
	}
	
	/**
		M�todo setter para a defini��o do usu�rio do coment�rio. 
		
		@param O usu�rio do coment�rio.
	*/
	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
	
	/**
		M�todo getter para a obten��o do t�pico do coment�rio.
		
		@return O t�pico do coment�rio.
	*/
	public Topico getTopico()
	{
		return topico;
	}
	
	/**
		M�todo setter para a defini��o do t�pico do coment�rio. 
		
		@param O t�pico do coment�rio.
	*/
	public void setTopico(Topico topico)
	{
		this.topico = topico;
	}
	
	/**
		M�todo getter para a obten��o da data do coment�rio.
		
		@return A data do coment�rio.
	*/
	public Date getData()
	{
		return data;
	}
	
	/**
		M�todo setter para a defini��o da data do coment�rio. 
		
		@param A data do coment�rio.
	*/
	public void setData(Date data)
	{
		this.data = data;
	}
	
	/**
		M�todo getter para a obten��o da mensagem do coment�rio.
		
		@return A mensagem do coment�rio.
	*/
	public String getMensagem()
	{
		return mensagem;
	}
	
	/**
		M�todo setter para a defini��o da mensagem do coment�rio. 
		
		@param A mensagem do coment�rio.
	*/
	public void setMensagem(String mensagem)
	{
		this.mensagem = Formatador.formatar(mensagem);
	}
	
	/**
		Cria o coment�rio com todos os campos vazios.
	*/
	public Comentario()
	{
		this.usuario = null;
		this.topico = null;
		this.data = new Date();
		this.mensagem = "";
	}
	
	/**
	Compara o coment�rio com outro.
	
	@param Um outro coment�rio para compara��o.
	@return Se os coment�rios s�o iguais ou n�o.
*/
	public boolean comparar(Comentario objeto)
	{
		return ((objeto != null) && (objeto.getUsuario() != null) && (objeto.getTopico() != null) &&
			   (objeto.getData() != null) && (this.usuario != null) && (this.topico != null) &&
			   (this.data != null) && (this.data.compareTo(objeto.getData()) == 0));		
	}
	
	/**
		Representa o coment�rio no formato: "[data] usu�rio escreveu no t�pico: mensagem (curtidas)".
		
		@return A representa��o do coment�rio.
	*/
	public String toString()
	{
		CurtidaDAO curtidaDAO = new CurtidaDAO();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAO.FORMATO_DATA_HORA);		
		String representacao = "";
		representacao += "[" + simpleDateFormat.format(this.data) + "] ";
		representacao += this.usuario.getNomeUsuario() + " escreveu no t�pico \"";
		representacao += this.topico.getTitulo() + "\": ";
		representacao += this.mensagem;
		representacao += " (" + curtidaDAO.obterQuantidadeCurtidas(this) + ")";		
		return representacao;		
	}
}