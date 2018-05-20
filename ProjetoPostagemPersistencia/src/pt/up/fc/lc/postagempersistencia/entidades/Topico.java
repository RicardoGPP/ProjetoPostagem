package pt.up.fc.lc.postagempersistencia.entidades;

import pt.up.fc.lc.postagempersistencia.util.Formatador;

/**
	Classe que representa o t�pico para persist�ncia e uso no sistema.
		
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class Topico implements Entidade<Topico>
{
	private String titulo;
	private String descricao;
	private int limiteMensagens;
	
	/**
		M�todo getter para a obten��o do t�tulo do t�pico.
		
		@return O t�tulo do t�pico.
	*/
	public String getTitulo()
	{
		return titulo;
	}
	
	/**
		M�todo setter para a defini��o do t�tulo do t�pico. 
		
		@param O t�tulo do t�pico.
	*/
	public void setTitulo(String titulo)
	{
		this.titulo = Formatador.formatar(titulo);
	}
	
	/**
		M�todo getter para a obten��o da descri��o do t�pico.
		
		@return A descri��o do t�pico.
	*/
	public String getDescricao()
	{
		return descricao;
	}
	
	/**
		M�todo setter para a defini��o da descri��o do t�pico. 
		
		@param A descri��o do t�pico.
	*/
	public void setDescricao(String descricao)
	{
		this.descricao = Formatador.formatar(descricao);
	}
	
	/**
		M�todo getter para a obten��o do limite de mensagens do t�pico.
		
		@return O limite de mensagens do t�pico.
	*/
	public int getLimiteMensagens()
	{
		return limiteMensagens;
	}
	
	/**
		M�todo setter para a defini��o do limite de mensagens do t�pico. 
		
		@param O limite de mensagens do t�pico.
	*/
	public void setLimiteMensagens(int limiteMensagens)
	{
		this.limiteMensagens = limiteMensagens;
	}
	
	/**
		Cria o t�pico com todos os campos vazios.
	*/
	public Topico()
	{
		this.titulo = "";
		this.descricao = "";
		this.limiteMensagens = 50;
	}

	/**
		Compara o t�pico com outro.
		
		@param Um outro t�pico para compara��o.
		@return Se os t�picos s�o iguais ou n�o.
	*/
	public boolean comparar(Topico objeto)
	{
		return this.titulo.equalsIgnoreCase(objeto.getTitulo());
	}

	/**
		Representa o t�pico com seu respectivo t�tulo.
		
		@return A representa��o do t�pico.
	*/
	public String toString()
	{
		return this.titulo.trim();
	}
}