package model.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import email.Email;
import gui.ClienteSelecionadoFormController;
import gui.LoginFormController;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Strings;
import javafx.scene.control.ButtonType;
import model.dao.DaoFactory;
import model.dao.LancamentoDao;
import model.entities.ServicoImpressao;
import model.entities.Conta;
import model.entities.Lancamento;

public class LancamentoService {

	// java vari�veis

	private LancamentoDao lancamentoDao = DaoFactory.createLancamentoDao();

	// m�todo novo ou editar lan�amento

	public void lancamentoSaidaOuEntrada(Lancamento lancamento, ServicoImpressao servicoImpressao) {

		// try {

		Conta conta = new ContaService().buscarContaId(servicoImpressao.getIdConta());

		switch (lancamento.getTipoDoLancamento()) {

		case ("ENTRADA DE CR�DITOS (+)"):

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
					"Entrada no servi�o " + servicoImpressao.getNomeDoServico().toUpperCase() + "? "
							+ "Com a entrada de " + Constraints.tresDigitos(lancamento.getQuantidadeDoPedido())
							+ " unid(s) adicionando-se ao saldo atual de "
							+ Constraints.tresDigitos(lancamento.getSaldoAnterior()) + " unid(s), o novo saldo ser� de "
							+ Constraints.tresDigitos(lancamento.getSaldoAtual()) + " unid(s).");

			String status = new ClienteSelecionadoFormController().statusServicoImpressao(conta.isTipo(),
					servicoImpressao.getLimiteMinimo(), conta.getSaldo());

			String texto = "Com a entrada de " + Constraints.tresDigitos(lancamento.getQuantidadeDoPedido())
					+ " unid(s) adicionando-se ao saldo atual de "
					+ Constraints.tresDigitos(lancamento.getSaldoAnterior()) + " unid(s), o novo saldo ser� de "
					+ Constraints.tresDigitos(lancamento.getSaldoAtual()) + " unid(s).";

			if (result.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, conta.getIdConta());
				new LogSegurancaService().novoLogSeguranca(LoginFormController.usuarioLogado().toUpperCase(),
						Strings.getLogMessage019() + servicoImpressao.getNomeDoServico().toUpperCase()
								+ " - ENTRADA DE CR�DITOS (+)");

				Optional<ButtonType> result_a = Alerts.showConfirmation("E-mail de confirma��o",
						"Deseja enviar um e-mail de confirma��o do lan�amento?");

				if (result_a.get() == ButtonType.OK) {

					new Email().enviarEmailLancamento(servicoImpressao, status, texto);

				}

			}

			break;

		case ("SA�DA DE CR�DITOS (-)"):

			Optional<ButtonType> result1 = Alerts.showConfirmation("Confirma��o",
					"Sa�da no servi�o " + servicoImpressao.getNomeDoServico().toUpperCase() + "? " + "Com a sa�da de "
							+ Constraints.tresDigitos(lancamento.getQuantidadeDoPedido())
							+ " unid(s) subtraindo-se do saldo atual de "
							+ Constraints.tresDigitos(lancamento.getSaldoAnterior()) + " unid(s), o novo saldo ser� de "
							+ Constraints.tresDigitos(lancamento.getSaldoAtual()) + " unid(s).");

			String status1 = new ClienteSelecionadoFormController().statusServicoImpressao(conta.isTipo(),
					servicoImpressao.getLimiteMinimo(), conta.getSaldo());

			String texto1 = "Com a sa�da de " + Constraints.tresDigitos(lancamento.getQuantidadeDoPedido())
					+ " unid(s) subtraindo-se do saldo atual de "
					+ Constraints.tresDigitos(lancamento.getSaldoAnterior()) + " unid(s), o novo saldo ser� de "
					+ Constraints.tresDigitos(lancamento.getSaldoAtual()) + " unid(s).";

			if (result1.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, conta.getIdConta());
				new LogSegurancaService().novoLogSeguranca(LoginFormController.usuarioLogado().toUpperCase(),
						Strings.getLogMessage019() + servicoImpressao.getNomeDoServico().toUpperCase()
								+ " - SA�DA DE CR�DITOS (-)");

				Optional<ButtonType> result_b = Alerts.showConfirmation("E-mail de confirma��o",
						"Deseja enviar um e-mail de confirma��o do lan�amento?");

				if (result_b.get() == ButtonType.OK) {

					new Email().enviarEmailLancamento(servicoImpressao, status1, texto1);

				}

			}

			break;

		case ("FATURADO (++)"):

			Optional<ButtonType> result2 = Alerts.showConfirmation("Confirma��o",
					"Entrada na fatura do servi�o " + servicoImpressao.getNomeDoServico().toUpperCase() + "? "
							+ " Com a entrada de " + Constraints.tresDigitos(lancamento.getQuantidadeDoPedido())
							+ " unid(s) subtraindo-se ao saldo atual de "
							+ Constraints.tresDigitos(lancamento.getSaldoAnterior()) + " unid(s), o novo saldo ser� de "
							+ Constraints.tresDigitos(lancamento.getSaldoAtual()) + " unid(s).");

			String status2 = new ClienteSelecionadoFormController().statusServicoImpressao(conta.isTipo(),
					servicoImpressao.getLimiteMinimo(), conta.getSaldo());

			String texto2 = " Com a entrada de " + Constraints.tresDigitos(lancamento.getQuantidadeDoPedido())
					+ " unid(s) subtraindo-se ao saldo atual de "
					+ Constraints.tresDigitos(lancamento.getSaldoAnterior()) + " unid(s), o novo saldo ser� de "
					+ Constraints.tresDigitos(lancamento.getSaldoAtual()) + " unid(s).";

			if (result2.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, conta.getIdConta());
				new LogSegurancaService().novoLogSeguranca(LoginFormController.usuarioLogado().toUpperCase(),
						Strings.getLogMessage019() + servicoImpressao.getNomeDoServico().toUpperCase()
								+ " - FATURADO (++)");

				Optional<ButtonType> result_c = Alerts.showConfirmation("E-mail de confirma��o",
						"Deseja enviar um e-mail de confirma��o do lan�amento?");

				if (result_c.get() == ButtonType.OK) {

					new Email().enviarEmailLancamento(servicoImpressao, status2, texto2);

				}

			}

			break;

		case ("FATURA PAGA"):

			Optional<ButtonType> result3 = Alerts.showConfirmation("Confirma��o",
					"A fatura do servi�o " + servicoImpressao.getNomeDoServico().toUpperCase() + " no total de "
							+ Constraints.tresDigitos(lancamento.getSaldoAnterior())
							+ " unid(s) foi paga? O novo saldo ser� de "
							+ Constraints.tresDigitos(lancamento.getSaldoAtual()) + " unid(s).");

			String status3 = new ClienteSelecionadoFormController().statusServicoImpressao(conta.isTipo(),
					servicoImpressao.getLimiteMinimo(), conta.getSaldo());

			String texto3 = "A fatura do servi�o " + servicoImpressao.getNomeDoServico().toUpperCase() + " no total de "
					+ Constraints.tresDigitos(lancamento.getSaldoAnterior())
					+ " unid(s) foi paga? O novo saldo ser� de " + Constraints.tresDigitos(lancamento.getSaldoAtual())
					+ " unid(s).";

			if (result3.get() == ButtonType.OK) {

				lancamentoDao.inserir(lancamento, conta.getIdConta());
				new LogSegurancaService().novoLogSeguranca(LoginFormController.usuarioLogado().toUpperCase(),
						Strings.getLogMessage019() + servicoImpressao.getNomeDoServico().toUpperCase()
								+ " - FATURA PAGA");

				Optional<ButtonType> result_d = Alerts.showConfirmation("E-mail de confirma��o",
						"Deseja enviar um e-mail de confirma��o do lan�amento?");

				if (result_d.get() == ButtonType.OK) {

					new Email().enviarEmailLancamento(servicoImpressao, status3, texto3);

				}

			}

			break;

		}

	}

	// m�todo ver lan�amentos pela data

	public List<Lancamento> verLancamentos(Date DataInicial, Date DataFinal, int idClienteServico) {

		return lancamentoDao.verLancamentos(DataInicial, DataFinal, idClienteServico);

	}

}
