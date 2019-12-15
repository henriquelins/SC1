package model.dao;

import java.util.List;

import model.entities.Unidade;

public interface UnidadeDao {

	void inserir(Unidade unidade);
	void atualizar(Unidade unidade);
	void excluir(Integer id);
	List<Unidade> buscarTodos();
	Unidade buscarPeloId (Integer id);

}
