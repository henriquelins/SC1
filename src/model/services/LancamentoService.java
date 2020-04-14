package model.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import gui.LoginFormController;
import gui.util.Alerts;
import gui.util.Strings;
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

		case ("ENTRADA DE CRÉDITOS (+)"):

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja dar entrada de créditos no serviço " + clienteServico.getNomeDoServico().toUpperCase()
							+ " ?");

			if (result.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, clienteServico);
				new LogSegurancaService().novoLogSeguranca(LoginFormController.usuarioLogado().toUpperCase(),
						Strings.getLogMessage019() + clienteServico.getNomeDoServico().toUpperCase()
								+ " - ENTRADA DE CRÉDITOS (+)");

			}

			break;

		case ("SAÍDA DE CRÉDITOS (-)"):

			Optional<ButtonType> result1 = Alerts.showConfirmation("Confirmação",
					"Você deseja dar saída de créditos no serviço " + clienteServico.getNomeDoServico().toUpperCase()
							+ " ?");

			if (result1.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, clienteServico);
				new LogSegurancaService().novoLogSeguranca(LoginFormController.usuarioLogado().toUpperCase(),
						Strings.getLogMessage019() + clienteServico.getNomeDoServico().toUpperCase()
								+ " - SAÍDA DE CRÉDITOS (-)");

			}

			break;

		case ("FATURADO (++)"):

			Optional<ButtonType> result2 = Alerts.showConfirmation("Confirmação",
					"Você deseja somar os créditos para faturar no serviço "
							+ clienteServico.getNomeDoServico().toUpperCase() + " ?");

			if (result2.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, clienteServico);
				new LogSegurancaService().novoLogSeguranca(LoginFormController.usuarioLogado().toUpperCase(),
						Strings.getLogMessage019() + clienteServico.getNomeDoServico().toUpperCase()
								+ " - FATURADO (++)");

			}

			break;

		case ("FATURA PAGA"):

			Optional<ButtonType> result3 = Alerts.showConfirmation("Confirmação",
					"Você deseja zerar o saldo após o pagamento da Fatura no serviço "
							+ clienteServico.getNomeDoServico().toUpperCase() + " ?");

			if (result3.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, clienteServico);
				new LogSegurancaService().novoLogSeguranca(LoginFormController.usuarioLogado().toUpperCase(),
						Strings.getLogMessage019() + clienteServico.getNomeDoServico().toUpperCase()
								+ " - FATURA PAGA");
			}

			break;

		}

	}

	// método ver lançamentos pela data

	public List<Lancamento> verLancamentos(Date DataInicial, Date DataFinal, int idClienteServico) {

		return lancamentoDao.verLancamentos(DataInicial, DataFinal, idClienteServico);

	}

}
