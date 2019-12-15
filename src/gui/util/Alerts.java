package gui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class Alerts {

	public static void showAlert(String title, String header, String content, AlertType type) {

		Alert alert = new Alert(type);

		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.initStyle(StageStyle.UTILITY);

		alert.show();
	}
	
	public static void closeAlert(Alert alert) {

		alert.close();
		
	}


	public static Optional<ButtonType> showConfirmation(String title, String content) {

		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);

		alert.initStyle(StageStyle.UTILITY);

		return alert.showAndWait();

	}

}