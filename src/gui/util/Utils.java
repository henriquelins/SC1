package gui.util;

import application.SCS1Main;
import gui.ClienteFormController;
import gui.LancamentoFormController;
import gui.ServicoFormController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Utils {

	// Pegar o Stage da tela

	public static Stage currentStage(ActionEvent event) {

		return (Stage) ((Node) event.getSource()).getScene().getWindow();

	}

	// fechar tela principal

	public static void fecharTelaPrincipalFormAction() {

		Stage stage = (Stage) SCS1Main.getMainScene().getWindow(); // Obtendo a janela atual
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

	public static void fecharTela(Scene scene) {
		Stage stage = (Stage) scene.getWindow();// Obtendo a janela atual
		stage.close();// Fechando o Stage	
	}

}
