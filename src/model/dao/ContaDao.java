package model.dao;

import java.util.List;

import model.entities.Conta;
import model.entities.ServicoImpressao;

public interface ContaDao {
	
	int inserir(ServicoImpressao servicoImpressao);
	void atualizar(ServicoImpressao servicoImpressao);
    void atualizarSaldo(int saldoAtual, int idServicoImpressao);
	List<Conta> buscarPeloCnpj(String cnpj);
	int buscarConta(String cnpj);
	
}
