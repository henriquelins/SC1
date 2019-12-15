package model.services;

import java.util.List;
import java.util.Optional;

import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.DaoFactory;
import model.dao.ProdutoDao;
import model.entities.Produto;

public class ProdutoService {
	
	// java vari�veis

		private ProdutoDao produtoDao = DaoFactory.createProdutoDao();

		// m�todo novo ou editar unidade

		public void produtoNovoOuEditar(Produto produto) {

			if (produto.getIdProduto() == null) {

				Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
						"Voc� deseja salvar o novo produto " + produto.getNomeProduto() + " ?");

				if (result.get() == ButtonType.OK) {

					produtoDao.inserir(produto);

				}

			} else {

				Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
						"Voc� deseja salvar a edi��o do produto " + produto.getNomeProduto() + " ?");

				if (result.get() == ButtonType.OK) {

					produtoDao.atualizar(produto);

				}

			}

		}

		public void excluir(int id) {

			produtoDao.excluir(id);

		}

		public List<Produto> buscarTodos() {

			return produtoDao.buscarTodos();

		}

		public Produto buscarPeloId(Integer id) {
			
			return produtoDao.buscarPeloId(id);

		}


}
