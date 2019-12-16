package model.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.DaoFactory;
import model.dao.LancamentoDao;
import model.entities.ServicoImpressao;
import model.entities.Lancamento;

public class LancamentoService {

	// java variáveis

	private LancamentoDao lancamentoDao = DaoFactory.createLancamentoDao();

	// método novo ou editar lançamento

	public void lancamentoSaidaOuEntrada(Lancamento lancamento, ServicoImpressao clienteServico) {

		// try {

		switch (lancamento.getTipoDoLancamento()) {

		case ("Entrada de créditos (+)"):

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja dar entrada de créditos no serviço " + clienteServico.getNomeDoServico() + " ?");

			if (result.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, clienteServico);

			}

			break;

		case ("Saída de créditos (-)"):

			Optional<ButtonType> result1 = Alerts.showConfirmation("Confirmação",
					"Você deseja dar saída de créditos no serviço " + clienteServico.getNomeDoServico() + " ?");

			if (result1.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, clienteServico);

			}

			break;

		}

	}

	// método ver lançamentos pela data

	public List<Lancamento> verLancamentos(Date DataInicial, Date DataFinal, int idClienteServico) {

		return lancamentoDao.verLancamentos(DataInicial, DataFinal, idClienteServico);

	}

}
