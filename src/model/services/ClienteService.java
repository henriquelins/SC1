package model.services;

import java.util.List;
import java.util.Optional;

import gui.LoginFormController;
import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.entities.Cliente;

public class ClienteService {

	// java vari�veis

	private ClienteDao dao = DaoFactory.createClienteDao();

	// m�todo novo ou editar cliente

	public void clienteNovoOuEditar(Cliente cliente) {

		if (cliente.getIdCliente() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja salvar o novo cliente " + cliente.getNomeFantasia().toUpperCase() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.inserir(cliente);
				new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
						"Cliente salvo: " + cliente.getNomeFantasia().toUpperCase());

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja salvar a edi��o do cliente " + cliente.getNomeFantasia().toUpperCase() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.atualizar(cliente);
				new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
						"Cliente editado: " + cliente.getNomeFantasia().toUpperCase());

			}

		}

	}

	// m�todo listar clientes pelo nome fantasia

	public List<Cliente> pesquisarNomeFantasia(String nomeFantasia) {

		return dao.buscarPeloNomeFantasia(nomeFantasia);

	}

	// m�todo lista todos os clientes

	public List<Cliente> findAll() {

		return dao.buscarTodos();

	}

	public Cliente buscarPeloId(Integer idCliente) {

		return dao.buscarPeloId(idCliente);

	}

	public List<Cliente> buscarPeloVendedor(int codVendedor) {

		return dao.buscarPeloVendedor(codVendedor);

	}

	public Cliente buscarPeloCnpj(String cnpjCliente) {

		return dao.buscarPeloCnpj(cnpjCliente);
	}

}
