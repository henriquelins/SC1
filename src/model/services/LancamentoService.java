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

	// java vari�veis

	private LancamentoDao lancamentoDao = DaoFactory.createLancamentoDao();

	// m�todo novo ou editar lan�amento

	public void lancamentoSaidaOuEntrada(Lancamento lancamento, ServicoImpressao clienteServico) {

		// try {

		switch (lancamento.getTipoDoLancamento()) {

		case ("Entrada de cr�ditos (+)"):

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja dar entrada de cr�ditos no servi�o " + clienteServico.getNomeDoServico() + " ?");

			if (result.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, clienteServico);

			}

			break;

		case ("Sa�da de cr�ditos (-)"):

			Optional<ButtonType> result1 = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja dar sa�da de cr�ditos no servi�o " + clienteServico.getNomeDoServico() + " ?");

			if (result1.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, clienteServico);

			}

			break;

		case ("Faturado (++)"):

			Optional<ButtonType> result2 = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja somar os cr�ditos para faturar no servi�o " + clienteServico.getNomeDoServico()
							+ " ?");

			if (result2.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, clienteServico);

			}

			break;

		case ("Fatura Paga"):

			Optional<ButtonType> result3 = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja zerar o saldo ap�s o pagamento da Fatura no servi�o "
							+ clienteServico.getNomeDoServico() + " ?");

			if (result3.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, clienteServico);

			}

			break;

		}

	}

	// m�todo ver lan�amentos pela data

	public List<Lancamento> verLancamentos(Date DataInicial, Date DataFinal, int idClienteServico) {

		return lancamentoDao.verLancamentos(DataInicial, DataFinal, idClienteServico);

	}

}
