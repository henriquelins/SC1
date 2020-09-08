package model.services;

import java.util.List;

import model.dao.ContaDao;
import model.dao.DaoFactory;
import model.entities.Conta;

public class ContaService {

	// java variáveis

	private ContaDao dao = DaoFactory.createContaDao();

	// inserir conta

	public int inserirConta(Conta conta) {

		int id_conta = dao.inserir(conta);

		// new
		// LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
		// Strings.getLogMessage020() +
		// servicoImpressao.getNomeDoServico().toUpperCase());

		return id_conta;

	}

	// atualizar conta

	public void atualizarConta(Conta conta) {

		dao.atualizar(conta);

		// new
		// LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
		// Strings.getLogMessage021() +
		// servicoImpressao.getNomeDoServico().toUpperCase());

	}

	// atualizar saldo da conta

	public void atualizarSaldoConta(int saldoAtualizado, int idConta) {

		dao.atualizarSaldo(saldoAtualizado, idConta);

		// new
		// LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
		// Strings.getLogMessage021() +
		// servicoImpressao.getNomeDoServico().toUpperCase());

	}

	// listar todas as contas

	public List<Conta> listaContas() {

		// new
		// LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
		// Strings.getLogMessage021() +
		// servicoImpressao.getNomeDoServico().toUpperCase());

		return dao.listaContas();

	}

	// listar contas com o mesmo CNPJ

	public List<Conta> listaCnpj(String cnpj) {

		// new
		// LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
		// Strings.getLogMessage021() +
		// servicoImpressao.getNomeDoServico().toUpperCase());

		return dao.listaCnpj(cnpj);

	}

	public Conta buscarContaCnpj(String cnpj) {

		// new
		// LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
		// Strings.getLogMessage021() +
		// servicoImpressao.getNomeDoServico().toUpperCase());

		return dao.buscarContaCnjp(cnpj);

	}
	
	public Conta buscarContaId(int idConta) {

		// new
		// LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
		// Strings.getLogMessage021() +
		// servicoImpressao.getNomeDoServico().toUpperCase());

		return dao.buscarContaId(idConta);

	}

}
