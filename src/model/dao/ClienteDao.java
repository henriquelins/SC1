package model.dao;

import java.util.List;

import model.entities.Cliente;

public interface ClienteDao {
	
	void inserir(Cliente cliente);
	void atualizar(Cliente cliente);
	Cliente buscarPeloCnpj(String cnpj);
	Cliente buscarPeloId(Integer id);
	List<Cliente> buscarTodos();
	List<Cliente> buscarPeloNomeFantasia(String nomeFantasia);
	List<Cliente> buscarPeloVendedor(int codVendedor);
	
}
