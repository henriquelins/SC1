package model.dao;

import java.util.List;
import model.entities.Produto;


public interface ProdutoDao {

	void inserir(Produto produto);
	void atualizar(Produto produto);
	void excluir(Integer id);
	List<Produto> buscarTodos();
	Produto buscarPeloId(Integer id);

}
