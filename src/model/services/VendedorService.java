package model.services;

import java.util.List;
import java.util.Optional;

import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Vendedor;

public class VendedorService {

	// java vari�veis

	private VendedorDao vendedorDao = DaoFactory.createVendedorDao();

	// m�todo novo ou editar unidade

	public void produtoNovoOuEditar(Vendedor vendedor) {

		if (vendedor.getIdVendedor() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja salvar o novo vendedor " + vendedor.getNomeVendedor() + " ?");

			if (result.get() == ButtonType.OK) {

				vendedorDao.inserir(vendedor);

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja salvar a edi��o do vendedor " + vendedor.getNomeVendedor() + " ?");

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
