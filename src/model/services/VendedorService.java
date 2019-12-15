package model.services;

import java.util.List;
import java.util.Optional;

import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Vendedor;

public class VendedorService {

	// java variáveis

	private VendedorDao vendedorDao = DaoFactory.createVendedorDao();

	// método novo ou editar unidade

	public void produtoNovoOuEditar(Vendedor vendedor) {

		if (vendedor.getIdVendedor() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja salvar o novo vendedor " + vendedor.getNomeVendedor() + " ?");

			if (result.get() == ButtonType.OK) {

				vendedorDao.inserir(vendedor);

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja salvar a edição do vendedor " + vendedor.getNomeVendedor() + " ?");

			if (result.get() == ButtonType.OK) {

				vendedorDao.atualizar(vendedor);

			}

		}

	}

	public void excluir(int id) {

		vendedorDao.excluir(id);

	}

	public List<Vendedor> buscarTodos() {

		return vendedorDao.buscarTodos();

	}

	public Vendedor buscarPeloId(int codVendedor) {

		return vendedorDao.buscarPeloId(codVendedor);

	}

}
