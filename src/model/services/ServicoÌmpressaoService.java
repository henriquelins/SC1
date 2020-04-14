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

public class ServicoÌmpressaoService {

	// java variáveis

	private ServicoImpressaoDao dao = DaoFactory.createServicoImpressaoDao();
	private ContaDao contaDao = DaoFactory.createContaDao();

	// método novo ou editar cliente serviço

	public void clienteServicoNovoOuEditar(ServicoImpressao servicoImpressao) {

		servicoImpressao.getConta().setIdConta(contaDao.buscarConta(servicoImpressao.getConta().getCnpj()));

		String tipoDeconta = "";

		if (servicoImpressao.getConta().isTipo() == true) {

			tipoDeconta = "SALDO";

		} else {

			tipoDeconta = "FATURAR";
		} 

		if (servicoImpressao.getIdServicoImpressao() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",

					"Você deseja salvar um novo serviço\n com a conta do tipo " + tipoDeconta + " ?");

			if (result.get() == ButtonType.OK) { 

				dao.inserir(servicoImpressao);
				new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
						Strings.getLogMessage020() + servicoImpressao.getNomeDoServico().toUpperCase());

			}

		} else {

			ServicoÌmpressaoService impressaoService = new ServicoÌmpressaoService();

			String nome = impressaoService.buscarServicosDoClientePeloCnpj(servicoImpressao.getConta().getCnpj());

			if (nome.equals("")) {

				Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
						"Você deseja salvar a edição do serviço de impressão "  + servicoImpressao.getNomeDoServico().toUpperCase() + " ?");

				if (result.get() == ButtonType.OK) {

					int id_conta = contaDao.inserir(servicoImpressao);
					servicoImpressao.getConta().setIdConta(id_conta);
					dao.atualizar(servicoImpressao);
					new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
							Strings.getLogMessage021() + servicoImpressao.getNomeDoServico().toUpperCase());

				}

			} else {

				Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
						"Você deseja salvar a edição do serviço de impressão " + servicoImpressao.getNomeDoServico().toUpperCase() + " ?");

				if (result.get() == ButtonType.OK) {

					dao.atualizar(servicoImpressao);
					new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
							Strings.getLogMessage021() + servicoImpressao.getNomeDoServico().toUpperCase());

				}

			}

		}

	}
	
	

	// método buscar serviços pelo CNPJ

	public String buscarServicosDoClientePeloCnpj(String ServicoCNPJ) {

		return dao.buscarServicosDoClientePeloCnpj(ServicoCNPJ);

	}

	// método listar serviços do cliente pelo id cliente

	public List<ServicoImpressao> buscarServicosDoCliente(Integer idCliente) {

		return dao.buscarServicosDoCliente(idCliente);

	}

	// método listar cliente serviço pelo id cliente serviço

	public ServicoImpressao buscarPeloIdCliente(Integer id_cliente_servico) {

		return dao.buscarPeloIdCliente(id_cliente_servico);

	}

}
