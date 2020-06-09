package model.services;

import java.util.List;

import gui.LoginFormController;
import gui.util.Strings;
import model.dao.ContaDao;
import model.dao.DaoFactory;
import model.dao.ServicoImpressaoDao;
import model.entities.ServicoImpressao;

public class Servico�mpressaoService {

	// java vari�veis

	private ServicoImpressaoDao dao = DaoFactory.createServicoImpressaoDao();
	private ContaDao contaDao = DaoFactory.createContaDao();

	// m�todo novo ou editar cliente servi�o

	public void novoServico(ServicoImpressao servicoImpressao) {

		int id_conta = contaDao.inserir(servicoImpressao);
		servicoImpressao.getConta().setIdConta(id_conta);
		dao.inserir(servicoImpressao);
		new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
				Strings.getLogMessage020() + servicoImpressao.getNomeDoServico().toUpperCase());

	}

	public void editarServico(ServicoImpressao servicoImpressao) {

		int id_conta = contaDao.inserir(servicoImpressao);
		servicoImpressao.getConta().setIdConta(id_conta);
		dao.atualizar(servicoImpressao);
		new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
				Strings.getLogMessage021() + servicoImpressao.getNomeDoServico().toUpperCase());

	}

	// m�todo buscar servi�os pelo CNPJ

	public String buscarServicosDoClientePeloCnpj(String ServicoCNPJ) {

		return dao.buscarServicosDoClientePeloCnpj(ServicoCNPJ);

	}

	// m�todo listar servi�os do cliente pelo id cliente

	public List<ServicoImpressao> buscarServicosDoCliente(Integer idCliente) {

		return dao.buscarServicosDoCliente(idCliente);

	}

	// m�todo listar cliente servi�o pelo id cliente servi�o

	public ServicoImpressao buscarPeloIdCliente(Integer id_cliente_servico) {

		return dao.buscarPeloIdCliente(id_cliente_servico);

	}

	public ServicoImpressao buscarServicoImpressaoCnpj(String cnpj) {

		return dao.buscarServicoImpressaoCnpj(cnpj);

	}

}
