package pt.up.fc.lc.postagempersistencia.entidades;

import pt.up.fc.lc.postagempersistencia.util.Formatador;

/**
	Classe que representa o tópico para persistência e uso no sistema.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandré,
	         Pedro
*/
public class Topico implements Entidade<Topico>
{
	private String titulo;
	private String descricao;
	private int limiteMensagens;
	
	/**
		Método getter para a obtenção do título do tópico.
		
		@return O título do tópico.
	*/
	public String getTitulo()
	{
		return titulo;
	}
	
	/**
		Método setter para a definição do título do tópico. 
		
		@param O título do tópico.
	*/
	public void setTitulo(String titulo)
	{
		this.titulo = Formatador.formatar(titulo);
	}
	
	/**
		Método getter para a obtenção da descrição do tópico.
		
		@return A descrição do tópico.
	*/
	public String getDescricao()
	{
		return descricao;
	}
	
	/**
		Método setter para a definição da descrição do tópico. 
		
		@param A descrição do tópico.
	*/
	public void setDescricao(String descricao)
	{
		this.descricao = Formatador.formatar(descricao);
	}
	
	/**
		Método getter para a obtenção do limite de mensagens do tópico.
		
		@return O limite de mensagens do tópico.
	*/
	public int getLimiteMensagens()
	{
		return limiteMensagens;
	}
	
	/**
		Método setter para a definição do limite de mensagens do tópico. 
		
		@param O limite de mensagens do tópico.
	*/
	public void setLimiteMensagens(int limiteMensagens)
	{
		this.limiteMensagens = limiteMensagens;
	}
	
	/**
		Cria o tópico com todos os campos vazios.
	*/
	public Topico()
	{
		this.titulo = "";
		this.descricao = "";
		this.limiteMensagens = 50;
	}

	/**
		Compara o tópico com outro.
		
		@param Um outro tópico para comparação.
		@return Se os tópicos são iguais ou não.
	*/
	public boolean comparar(Topico objeto)
	{
		return this.titulo.equalsIgnoreCase(objeto.getTitulo());
	}

	/**
		Representa o tópico com seu respectivo título.
		
		@return A representação do tópico.
	*/
	public String toString()
	{
		return this.titulo.trim();
	}
}