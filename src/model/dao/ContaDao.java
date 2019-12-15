package model.dao;

import java.util.List;

import model.entities.Conta;
import model.entities.ServicoImpressao;

public interface ContaDao {
	
	int inserir(ServicoImpressao servicoImpressao);
	void atualizar(ServicoImpressao servicoImpressao);
	void excluirPeloId(Integer id);
	Conta buscarPeloId(Integer id);
	Conta buscarPeloCnpj(Integer id);
    void atualizarSaldo(int saldoAtual, int idServicoImpressao);
	List<Conta> buscarTodos();
	List<Conta> buscarPeloCnpj(String cnpj);
	int buscarConta(String cnpj);
	
}
