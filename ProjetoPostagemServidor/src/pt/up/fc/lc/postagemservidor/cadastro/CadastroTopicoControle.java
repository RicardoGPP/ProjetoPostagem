package pt.up.fc.lc.postagemservidor.cadastro;

import java.util.Collections;
import java.util.List;
import pt.up.fc.lc.postagempersistencia.dao.TopicoDAO;
import pt.up.fc.lc.postagempersistencia.entidades.Topico;

public class CadastroTopicoControle
{
	private TopicoDAO topicoDAO;
	private CadastroTopicoVisao cadastroTopicoVisao;
	
	public CadastroTopicoControle(CadastroTopicoVisao cadastroTopicoVisao)
	{
		this.topicoDAO = new TopicoDAO();
		this.cadastroTopicoVisao = cadastroTopicoVisao;
	}
	
	public void carregarLista()
	{
		List<Topico> topicos = this.topicoDAO.obterLista();
		Collections.sort(topicos, (t1, t2) -> t1.getTitulo().compareTo(t2.getTitulo()));
		this.cadastroTopicoVisao.definirTopicos(topicos);
	}
	
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
	
	public void excluir()
	{
		Topico topico = this.cadastroTopicoVisao.obterSelecionado();
		if (this.topicoDAO.deletar(topico))
			this.cadastroTopicoVisao.excluirDaLista(topico);
	}
}