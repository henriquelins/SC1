package model.dao;

import java.util.List;
import model.entities.Vendedor;

public interface VendedorDao {

	void inserir(Vendedor vendedor);
	void atualizar(Vendedor vendedor);
	void excluir(Integer id);
	List<Vendedor> buscarTodos();
	Vendedor buscarPeloId (Integer id);

}
