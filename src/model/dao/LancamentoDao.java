package model.dao;

import java.sql.Date;
import java.util.List;

import model.entities.Lancamento;

public interface LancamentoDao {
	
	void inserir(Lancamento lancamento, int id_conta);
	void atualizar(Lancamento lancamento);
	List<Lancamento> buscarTodos(Integer idClienteServico);
	List<Lancamento> verLancamentos(Date dataInicial, Date dataFinal, int idClienteServico);
	
}
