package model.dao;

import java.sql.Date;
import java.util.List;

import model.entities.Lancamento;
import model.entities.ServicoImpressao;

public interface LancamentoDao {
	
	void inserir(Lancamento lancamento, ServicoImpressao clienteServico);
	void atualizar(Lancamento lancamento, ServicoImpressao clienteServico);
	List<Lancamento> buscarTodos(Integer idClienteServico);
	List<Lancamento> verLancamentos(Date dataInicial, Date dataFinal, int idClienteServico);
	
}
