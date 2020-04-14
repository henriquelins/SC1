package model.services;

import java.util.List;
import java.util.Optional;

import gui.LoginFormController;
import gui.util.Alerts;
import gui.util.Strings;
import javafx.scene.control.ButtonType;
import model.dao.ContaDao;
import model.dao.DaoFactory;
import model.dao.ServicoImpressaoDao;
import model.entities.ServicoImpressao;

public class Servico�mpressaoService {

	// java vari�veis

	private ServicoImpressaoDao dao = DaoFactory.createServicoImpressaoDao();
	private ContaDao contaDao = DaoFactory.createContaDao();

	// m�todo novo ou editar cliente servi�o

	public void clienteServicoNovoOuEditar(ServicoImpressao servicoImpressao) {

		servicoImpressao.getConta().setIdConta(contaDao.buscarConta(servicoImpressao.getConta().getCnpj()));

		String tipoDeconta = "";

		if (servicoImpressao.getConta().isTipo() == true) {

			tipoDeconta = "SALDO";

		} else {

			tipoDeconta = "FATURAR";
		} 

		if (servicoImpressao.getIdServicoImpressao() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",

					"Voc� deseja salvar um novo servi�o\n com a conta do tipo " + tipoDeconta + " ?");

			if (result.get() == ButtonType.OK) { 

				dao.inserir(servicoImpressao);
				new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
						Strings.getLogMessage020() + servicoImpressao.getNomeDoServico().toUpperCase());

			}

		} else {

			Servico�mpressaoService impressaoService = new Servico�mpressaoService();

			String nome = impressaoService.buscarServicosDoClientePeloCnpj(servicoImpressao.getConta().getCnpj());

			if (nome.equals("")) {

				Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
						"Voc� deseja salvar a edi��o do servi�o de impress�o "  + servicoImpressao.getNomeDoServico().toUpperCase() + " ?");

				if (result.get() == ButtonType.OK) {

					int id_conta = contaDao.inserir(servicoImpressao);
					servicoImpressao.getConta().setIdConta(id_conta);
					dao.atualizar(servicoImpressao);
					new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
							Strings.getLogMessage021() + servicoImpressao.getNomeDoServico().toUpperCase());

				}

			} else {

				Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
						"Voc� deseja salvar a edi��o do servi�o de impress�o " + servicoImpressao.getNomeDoServico().toUpperCase() + " ?");

				if (result.get() == ButtonType.OK) {

					dao.atualizar(servicoImpressao);
					new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
							Strings.getLogMessage021() + servicoImpressao.getNomeDoServico().toUpperCase());

				}

			}

		}

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

}
