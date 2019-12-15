package model.dao;

import java.util.List;

import model.entities.Cliente;

public interface ClienteDao {
	
	void inserir(Cliente cliente);
	void atualizar(Cliente cliente);
	void excluirPeloId(Integer id);
	Cliente buscarPeloId(Integer id);
	List<Cliente> buscarTodos();
	List<Cliente> buscarPeloNomeFantasia(String nomeFantasia);
	
}
