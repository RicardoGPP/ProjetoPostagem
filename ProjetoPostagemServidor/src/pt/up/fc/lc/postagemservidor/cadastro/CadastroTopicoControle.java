package pt.up.fc.lc.postagemservidor.cadastro;

import java.util.Collections;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

/**
	Classe da camada de controle do cadastro de t�picos do sistema.
	
	@version 1.0
	@author  Ricardo Giovani Piantavinha Perandr�,
	         Pedro Sobral da Costa
*/
public class CadastroTopicoControle
{
	private TopicoDAO topicoDAO;
	private CadastroTopicoVisao cadastroTopicoVisao;
	
	/**
		Cria e inicializa o controle de cadastro de t�picos.
		
		@param A vis�o do cadastro de t�picos.
	*/
	public CadastroTopicoControle(CadastroTopicoVisao cadastroTopicoVisao)
	{
		this.topicoDAO = new TopicoDAO();
		this.cadastroTopicoVisao = cadastroTopicoVisao;
	}
	
	/**
		Carrega a lista de t�picos.
	*/
	public void carregarLista()
	{
		List<Topico> topicos = this.topicoDAO.obterLista();
		Collections.sort(topicos, (t1, t2) -> t1.getTitulo().compareTo(t2.getTitulo()));
		this.cadastroTopicoVisao.definirTopicos(topicos);
	}
	
	/**
		Chama o cadastro de t�pico interno, inclui o objeto no arquivo e atualiza a
		lista em caso de sucesso.
	*/
	public void incluir()
	{	
		CadastroTopicoInternoVisao cadastroTopicoInterno = new CadastroTopicoInternoVisao();
		if (cadastroTopicoInterno.foiProcessado())
		{
			Topico topico = cadastroTopicoInterno.obterRegistro();
			if (this.topicoDAO.inserir(topico))
				this.cadastroTopicoVisao.incluirNaLista(topico);
		}
	}
	
	/**
		Chama o cadastro de t�pico interno e edita o objeto no arquivo.
	*/
	public void editar()
	{
		Topico topico = this.cadastroTopicoVisao.obterSelecionado();
		if (topico != null)
		{
			CadastroTopicoInternoVisao cadastroTopicoInterno = new CadastroTopicoInternoVisao(topico);
			if (cadastroTopicoInterno.foiProcessado())
				this.topicoDAO.editar(topico);
		}
	}
	
	/**
		Exclui o t�pico no arquivo e atualiza a lista em caso de sucesso.
	*/
	public void excluir()
	{
		Topico topico = this.cadastroTopicoVisao.obterSelecionado();
		if (this.topicoDAO.deletar(topico))
			this.cadastroTopicoVisao.excluirDaLista(topico);
	}
}