package gui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alerts {

	public static void showAlert(String title, String header, String content, AlertType type) {

		Alert alert = new Alert(type);

		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.initModality(Modality.APPLICATION_MODAL);

		Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
		dialogStage.getIcons().add(new Image(Strings.getIcone()));

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
		
		alert.initModality(Modality.APPLICATION_MODAL);

		Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
		dialogStage.getIcons().add(new Image(Strings.getIcone()));

		return alert.showAndWait();

	}

}