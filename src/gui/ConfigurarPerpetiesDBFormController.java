package gui;

import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

import application.SCS1Main;
import db.DB;
import gui.forms.Forms;
import gui.util.Alerts;
import gui.util.Strings;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import properties.PropertiesFile;

public class ConfigurarPerpetiesDBFormController implements Initializable {

	// @FXML variáveis

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

			buttonConfirmar.setDisable(true);
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

			buttonConfirmar.setDisable(false);
			buttonEditarSalvar.setText("EDITAR");

		}

	}

	@FXML
	public void onButtonConfirmarAction(ActionEvent event) {

		Utils.currentStage(event).close();

		boolean conexao = false;

		try {

			conexao = testarConexao();

		} catch (Exception e1) {

			conexao = false;
		}

		if (conexao) {

			try {

				new Forms().splashForm(Strings.getSplashView());

			} catch (Exception e) {

				Alerts.showAlert("Controle de Estoque", "Erro ao abrir a tela", e.getLocalizedMessage(),
						AlertType.ERROR);

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Erro ao abrir o banco de dados",
					"Erro: " + SCS1Main.erro + " .Você deseja configurar as propriedades do banco de dados?");

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

	// testar conexão
	public static boolean testarConexao() throws Exception {

		boolean conexao = false;
		Connection connection = new DB().getConnectionTeste();

		if (connection != null) {

			conexao = true;
		}

		connection.close();

		return conexao;

	}

}
