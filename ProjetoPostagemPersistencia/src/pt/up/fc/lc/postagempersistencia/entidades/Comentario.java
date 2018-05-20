package pt.up.fc.lc.postagempersistencia.entidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import pt.up.fc.lc.postagempersistencia.dao.CurtidaDAO;
import pt.up.fc.lc.postagempersistencia.dao.DAO;
import pt.up.fc.lc.postagempersistencia.util.Formatador;

/**
	Classe que representa o comentário para persistência e uso no sistema.
	Um comentário é representado por uma referência entre usuário e tópico,
	com a menção de uma mensagem em uma respectiva data.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class Comentario implements Entidade<Comentario>
{	
	private Usuario usuario;
	private Topico topico;
	private Date data;
	private String mensagem;
	
	/**
		Método getter para a obtenção do usuário do comentário.
		
		@return O usuário do comentário.
	*/
	public Usuario getUsuario()
	{
		return usuario;
	}
	
	/**
		Método setter para a definição do usuário do comentário. 
		
		@param O usuário do comentário.
	*/
	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
	
	/**
		Método getter para a obtenção do tópico do comentário.
		
		@return O tópico do comentário.
	*/
	public Topico getTopico()
	{
		return topico;
	}
	
	/**
		Método setter para a definição do tópico do comentário. 
		
		@param O tópico do comentário.
	*/
	public void setTopico(Topico topico)
	{
		this.topico = topico;
	}
	
	/**
		Método getter para a obtenção da data do comentário.
		
		@return A data do comentário.
	*/
	public Date getData()
	{
		return data;
	}
	
	/**
		Método setter para a definição da data do comentário. 
		
		@param A data do comentário.
	*/
	public void setData(Date data)
	{
		this.data = data;
	}
	
	/**
		Método getter para a obtenção da mensagem do comentário.
		
		@return A mensagem do comentário.
	*/
	public String getMensagem()
	{
		return mensagem;
	}
	
	/**
		Método setter para a definição da mensagem do comentário. 
		
		@param A mensagem do comentário.
	*/
	public void setMensagem(String mensagem)
	{
		this.mensagem = Formatador.formatar(mensagem);
	}
	
	/**
		Cria o comentário com todos os campos vazios.
	*/
	public Comentario()
	{
		this.usuario = null;
		this.topico = null;
		this.data = new Date();
		this.mensagem = "";
	}
	
	/**
	Compara o comentário com outro.
	
	@param Um outro comentário para comparação.
	@return Se os comentários são iguais ou não.
*/
	public boolean comparar(Comentario objeto)
	{
		return ((objeto != null) && (objeto.getUsuario() != null) && (objeto.getTopico() != null) &&
			   (objeto.getData() != null) && (this.usuario != null) && (this.topico != null) &&
			   (this.data != null) && (this.data.compareTo(objeto.getData()) == 0));		
	}
	
	/**
		Representa o comentário no formato: "[data] usuário escreveu no tópico: mensagem (curtidas)".
		
		@return A representação do comentário.
	*/
	public String toString()
	{
		CurtidaDAO curtidaDAO = new CurtidaDAO();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAO.FORMATO_DATA_HORA);		
		String representacao = "";
		representacao += "[" + simpleDateFormat.format(this.data) + "] ";
		representacao += this.usuario.getNomeUsuario() + " escreveu no tópico \"";
		representacao += this.topico.getTitulo() + "\": ";
		representacao += this.mensagem;
		representacao += " (" + curtidaDAO.obterQuantidadeCurtidas(this) + ")";		
		return representacao;		
	}
}