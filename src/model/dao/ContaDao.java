package model.dao;

import java.util.List;

import model.entities.Conta;

public interface ContaDao {
	
	int inserir(Conta conta);
	void atualizar(Conta conta);
    void atualizarSaldo(int saldoAtualizado, int idConta);
    Conta buscarContaCnjp(String cnpj);
    Conta buscarContaId(int idconta);
    List<Conta> listaContas();
	List<Conta> listaCnpj(String cnpj);
		
}
