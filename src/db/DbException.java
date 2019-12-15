package db;

import java.io.IOException;

import gui.util.Alerts;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DbException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DbException(String msg) {

		super(msg);

		Alerts.showAlert("Controle de Estoque", "DbException - Erro ao acessar o Bando de Dados", msg, AlertType.ERROR);
		 
	}

	
	public void DbExceptionBanco() {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ConfigurarPerpetiesDB.fxml"));
			Pane pane = loader.load();

			Stage produtoStage = new Stage();
			produtoStage.setTitle("Propriedades do Banco de Dados");
			produtoStage.setScene(new Scene(pane));
			produtoStage.setResizable(false);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			produtoStage.initOwner(null);
			produtoStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela Propriedades do Banco de Dados", e.getMessage(),
					AlertType.ERROR);

		}

		

	}
}
