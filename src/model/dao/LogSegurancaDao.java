package model.dao;

import java.util.List;

import model.entities.LogSeguranca;


public interface LogSegurancaDao {

	void inserir(LogSeguranca logSeguranca);
	List<LogSeguranca> buscarTodos();

}
