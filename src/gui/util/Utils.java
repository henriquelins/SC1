package gui.util;

import application.Main;
import gui.ClienteFormController;
import gui.LancamentoFormController;
import gui.ServicoFormController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	// Pegar o Stage da tela

	public static Stage currentStage(ActionEvent event) {

		return (Stage) ((Node) event.getSource()).getScene().getWindow();

	}

	// fechar tela principal

	public static void fecharTelaPrincipalFormAction() {

		Stage stage = (Stage) Main.getMainScene().getWindow(); // Obtendo a janela atual
		stage.close();// Fechando o Stage

	}

	// fechar tela cliente

	public static void fecharTelaClienteFormAction() {

		Stage stage = (Stage) ClienteFormController.getClienteFormScene().getWindow(); // Obtendo a janela atual
		stage.close();// Fechando o Stage

	}

	// fechar tela servico

	public static void fecharTelaServicoFormAction() {

		Stage stage = (Stage) ServicoFormController.getServicoFormScene().getWindow();// Obtendo a janela atual
		stage.close();// Fechando o Stage

	}

	// fechar tela lancamento

	public static void fecharTelaLancamentoFormAction() {

		Stage stage = (Stage) LancamentoFormController.getLancamentoFormScene().getWindow();// Obtendo a janela atual
		stage.close();// Fechando o Stage

	}

}
