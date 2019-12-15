package model.dao;

import model.entities.Cliente;

public interface ContatoDao {

	void inserir(Cliente cliente);
	void atualizar(Cliente cliente);

}
