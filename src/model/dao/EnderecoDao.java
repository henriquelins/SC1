package model.dao;

import model.entities.Cliente;

public interface EnderecoDao {
	
	void inserir(Cliente cliente);
	void atualizar(Cliente cliente);
	
}
