package model.services;

import java.util.List;
import java.util.Optional;

import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.DaoFactory;
import model.dao.UnidadeDao;
import model.entities.Unidade;

public class UnidadeService {

	// java vari�veis

	private UnidadeDao unidadeDao = DaoFactory.createUnidadeDao();

	// m�todo novo ou editar unidade

	public void produtoNovoOuEditar(Unidade unidade) {

		if (unidade.getIdUnidade() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja salvar a nova unidade " + unidade.getNomeUnidade() + " ?");

			if (result.get() == ButtonType.OK) {

				unidadeDao.inserir(unidade);

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja salvar a edi��o do produto " + unidade.getNomeUnidade() + " ?");

			if (result.get() == ButtonType.OK) {

				unidadeDao.atualizar(unidade);

			}

		}

	}

	public void excluir(int id) {

		unidadeDao.excluir(id);

	}

	public List<Unidade> buscarTodos() {

		return unidadeDao.buscarTodos();

	}

	public Unidade buscarPeloId(int codUnidade) {

		return unidadeDao.buscarPeloId(codUnidade);
		
	}

}
