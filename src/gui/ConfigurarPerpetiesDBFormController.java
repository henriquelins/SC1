package gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

import application.SC1Main;
import db.DB;
import gui.forms.Forms;
import gui.util.Alerts;
import gui.util.Strings;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import properties.PropertiesFile;

public class ConfigurarPerpetiesDBFormController implements Initializable {

	// @FXML vari�veis

	@FXML
	private TextField textFieldUrl;

	@FXML
	private PasswordField textFieldPassword;

	@FXML
	private PasswordField textFieldUser;

	@FXML
	private Button buttonEditarSalvar;

	@FXML
	private Button buttonConfirmar;

	// Inicia evento

	@FXML
	public void onButtonEditarSalvarAction(ActionEvent event) {

	 	if (buttonEditarSalvar.getText().equalsIgnoreCase("EDITAR")) {
	 		
	 		textFieldUrl.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldPassword.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldUser.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
	 		
			textFieldUrl.setEditable(true);
			textFieldPassword.setEditable(true);
			textFieldUser.setEditable(true);

			buttonEditarSalvar.setText("SALVAR");

		} else {
			
			textFieldUrl.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldPassword.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldUser.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");

			textFieldUrl.setEditable(false);
			textFieldPassword.setEditable(false);
			textFieldUser.setEditable(false);

			PropertiesFile.writePropertiesDB(textFieldUrl.getText(), textFieldPassword.getText(),
					textFieldUser.getText());

			buttonEditarSalvar.setText("EDITAR");
		}

	}

	@FXML
	public void onButtonConfirmarAction(ActionEvent event) {

		Utils.currentStage(event).close();

		Connection conn = null;
		conn = DB.getConnectionTeste();

		if (conn != null) {

			try {

				SC1Main.setPortSocket(Integer.parseInt(
						PropertiesFile.loadPropertiesSocket().getProperty(Strings.getPropertiessocketPort())));
				SC1Main.setServerSocket(new ServerSocket(SC1Main.getPortSocket()));
				SC1Main.setSocket(new Socket(InetAddress.getLocalHost().getHostAddress(), SC1Main.getPortSocket()));

				try {

					new Forms().splashForm(Strings.getSplashView());

				} catch (IOException e) {

					e.printStackTrace();

				}

			} catch (IOException e) {

				Alerts.showAlert("Controle de Estoque", "Erro ao abrir o programa",
						"J� existe uma inst�ncia do programa aberta!", AlertType.ERROR);

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Erro ao abrir o banco de dados",
					"Voc� deseja configurar as propriedades do banco de dados ?");

			if (result.get() == ButtonType.OK) {

				new Forms().ConfigurarPerpetiesDBForm(Strings.getConfigurarPerpetiesDBView());

			}

		}

	}

	// Inicia classe

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void carregarCampos() {
				
		textFieldUrl.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldPassword.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldUser.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		
		textFieldUrl.setText(PropertiesFile.loadPropertiesDB().getProperty("dburl"));
		textFieldPassword.setText(PropertiesFile.loadPropertiesDB().getProperty("password"));
		textFieldUser.setText(PropertiesFile.loadPropertiesDB().getProperty("user"));

		textFieldUrl.setEditable(false);
		textFieldPassword.setEditable(false);
		textFieldUser.setEditable(false);

	}

}
