package model.services;

import java.util.List;
import java.util.Optional;

import gui.util.Alerts;
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

		if (servicoImpressao.getIdServicoImpressao() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",

					"Você deseja salvar um novo serviço?");

			if (result.get() == ButtonType.OK) {

				dao.inserir(servicoImpressao);

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja salvar a edição do serviço?");

			if (result.get() == ButtonType.OK) {

				dao.atualizar(servicoImpressao); 

			}

		}

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
