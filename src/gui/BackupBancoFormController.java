package gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import backup.BackupBancoPostgres;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.services.LogSegurancaService;
import properties.PropertiesFile;

public class BackupBancoFormController implements Initializable {

	@FXML
	private TextField textFieldFerramentaDeBackup;

	@FXML
	private TextField textFieldSalvarArquivoDeBackup;

	@FXML
	private TextField textFieldPortaBackup;

	@FXML
	private TextField textFieldHostBackup;

	@FXML
	private TextField textFieldNomeDoBancoBackup;

	@FXML
	private PasswordField textFieldUserBackup;

	@FXML
	private PasswordField textFieldPasswordBackup;

	@FXML
	private Button buttonBuscarFerramentaBackup;

	@FXML
	private Button buttonSalvarArquivoDeBackup;

	@FXML
	private Button buttonEditarBackup;

	@FXML
	private Button buttonFazerBackup;

	@FXML
	private TextArea textAreaBackup;

	@FXML
	private TextField textFieldFerramentaDeRestore;

	@FXML
	private TextField textFieldSalvarArquivoDeRestore;

	@FXML
	private TextField textFieldPortaRestore;

	@FXML
	private TextField textFieldHostRestore;

	@FXML
	private TextField textFieldNomeDoBancoRestore;

	@FXML
	private PasswordField textFieldUserRestore;

	@FXML
	private PasswordField textFieldPasswordRestore;

	@FXML
	private Button buttonBuscarFerramentaRestore;

	@FXML
	private Button buttonLocalizarRestore;

	@FXML
	private Button buttonEditarRestore;

	@FXML
	private Button buttonFazerRestore;

	@FXML
	private TextArea textAreaRestore;

	@FXML
	private Button buttonFechar;

	final String MENSAGEM_PASSWORD = "PGPASSWORD";

	@FXML
	public void onButtonFecharAction(ActionEvent event) {

		Utils.currentStage(event).close();

	}

	@FXML
	public void onButtonBuscarFerramentaBackupAction(ActionEvent event) {

		javafx.stage.FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("EXE Files (*.exe)", "*.exe"));
		File arquivo = null;

		fileChooser.setTitle("Escolha a ferramenta para fazer o backup do banco de dados");

		arquivo = fileChooser.showOpenDialog(new Stage());

		if (arquivo != null) {

			textFieldFerramentaDeBackup.setText(arquivo.getAbsolutePath());

		} else {

			Alerts.showAlert("Localizar programa de backup", "Erro ao localizar o programa",
					"Não foi possível salvar o endereço", AlertType.ERROR);

		}

	}

	@FXML
	public void onButtonSalvarArquivoDeBackupAction(ActionEvent event) {

		javafx.stage.FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Backup file (*.backup)", "*.backup"));
		File arquivo = fileChooser.showSaveDialog(new Stage());

		fileChooser.setTitle("Escolha o pasta e digite o nome do arquivo salvar o backup do banco de dados");

		if (arquivo != null) {

			try {

				FileWriter writer = new FileWriter(arquivo, true);
				writer.write("Backup");

				textFieldSalvarArquivoDeBackup.setText(arquivo.getAbsolutePath());

				writer.close();

			} catch (IOException e) {

				Alerts.showAlert("Salvar arquivo de backup", "Erro ao salvar o arquivo", e.getLocalizedMessage(),
						AlertType.ERROR);

			}
		}

	}

	@FXML
	public void onButtonEditarBackupAction(ActionEvent event) {

		if (buttonEditarBackup.getText().equalsIgnoreCase("EDITAR")) {

			textFieldFerramentaDeBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldSalvarArquivoDeBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldPortaBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldHostBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldNomeDoBancoBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldUserBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldPasswordBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");

			textFieldFerramentaDeBackup.setEditable(true);
			textFieldSalvarArquivoDeBackup.setEditable(true);
			textFieldPortaBackup.setEditable(true);
			textFieldHostBackup.setEditable(true);
			textFieldNomeDoBancoBackup.setEditable(true);
			textFieldUserBackup.setEditable(true);
			textFieldPasswordBackup.setEditable(true);
			textAreaBackup.setEditable(true);

			buttonBuscarFerramentaBackup.setDisable(false);
			buttonSalvarArquivoDeBackup.setDisable(false);
			buttonFazerBackup.setDisable(false);

			buttonEditarBackup.setText("CANCELAR");

		} else {

			textFieldFerramentaDeBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldSalvarArquivoDeBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldPortaBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldHostBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldNomeDoBancoBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldUserBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldPasswordBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");

			textFieldFerramentaDeBackup.setEditable(false);
			textFieldSalvarArquivoDeBackup.setEditable(false);
			textFieldPortaBackup.setEditable(false);
			textFieldHostBackup.setEditable(false);
			textFieldNomeDoBancoBackup.setEditable(false);
			textFieldUserBackup.setEditable(false);
			textFieldPasswordBackup.setEditable(false);
			textAreaBackup.setEditable(false);

			buttonBuscarFerramentaBackup.setDisable(true);
			buttonSalvarArquivoDeBackup.setDisable(true);
			buttonFazerBackup.setDisable(true);

			buttonEditarBackup.setText("EDITAR");

		}

	}

	@FXML
	public void onButtonFazerBackupAction(ActionEvent event) {

		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
				"Você deseja fazer o backup do banco de dados ?");

		if (result.get() == ButtonType.OK) {

			String ferramenta = textFieldFerramentaDeBackup.getText();
			String host = textFieldHostBackup.getText();
			String porta = textFieldPortaBackup.getText();
			String user = textFieldUserBackup.getText();
			String endereco = textFieldSalvarArquivoDeBackup.getText();
			String banco = textFieldNomeDoBancoBackup.getText();
			String mensagemSenha = MENSAGEM_PASSWORD;
			String senha = textFieldPasswordBackup.getText();

			List<String> areaTexto = new ArrayList<>();

			areaTexto = BackupBancoPostgres.realizaBackup(ferramenta, host, porta, user, endereco, banco, mensagemSenha,
					senha);

			for (String st : areaTexto) {

				textAreaBackup.appendText(st + "\n");
			}

			backupEditar();

			PropertiesFile.writePropertiesBackup(endereco, host, banco, ferramenta, senha, user, porta);

			new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
					Strings.getLogMessage024());

		}

	}

	@FXML
	public void onButtonBuscarFerramentaRestoreAction(ActionEvent event) {

		javafx.stage.FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("EXE Files (*.exe)", "*.exe"));
		File arquivo = null;

		fileChooser.setTitle("Escolha a ferramenta para fazer o restauração do banco de dados");

		arquivo = fileChooser.showOpenDialog(new Stage());

		if (arquivo != null) {

			textFieldFerramentaDeRestore.setText(arquivo.getAbsolutePath());

		} else {

			Alerts.showAlert("Localizar programa de restauração", "Erro ao localizar o programa",
					"Não foi possível salvar o endereço", AlertType.ERROR);

		}

	}

	@FXML
	public void onButtonbuttonLocalizarRestoreAction(ActionEvent event) {

		javafx.stage.FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Backup file (*.backup)", "*.backup"));
		File arquivo = null;

		fileChooser.setTitle("Escolha o arquivo para restaurar o backup do banco de dados");

		arquivo = fileChooser.showOpenDialog(new Stage());

		if (arquivo != null) {

			textFieldSalvarArquivoDeRestore.setText(arquivo.getAbsolutePath());

		} else {

			Alerts.showAlert("Localizar arquivo de restauração", "Erro ao localizar o arquivo",
					"Não foi possível salvar o endereço", AlertType.ERROR);

		}

	}

	@FXML
	public void onButtonEditarRestoreAction(ActionEvent event) {

		if (buttonEditarRestore.getText().equalsIgnoreCase("Editar")) {

			textFieldFerramentaDeRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldSalvarArquivoDeRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldPortaRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldHostRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldNomeDoBancoRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldUserRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldPasswordRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");

			textFieldFerramentaDeRestore.setEditable(true);
			textFieldSalvarArquivoDeRestore.setEditable(true);
			textFieldPortaRestore.setEditable(true);
			textFieldHostRestore.setEditable(true);
			textFieldNomeDoBancoRestore.setEditable(true);
			textFieldUserRestore.setEditable(true);
			textFieldPasswordRestore.setEditable(true);
			textAreaRestore.setEditable(true);

			buttonBuscarFerramentaRestore.setDisable(false);
			buttonLocalizarRestore.setDisable(false);
			buttonFazerRestore.setDisable(false);

			buttonEditarRestore.setText("CANCELAR");

		} else {

			textFieldFerramentaDeRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldSalvarArquivoDeRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldPortaRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldHostRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldNomeDoBancoRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldUserRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldPasswordRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");

			textFieldFerramentaDeRestore.setEditable(false);
			textFieldSalvarArquivoDeRestore.setEditable(false);
			textFieldPortaRestore.setEditable(false);
			textFieldHostRestore.setEditable(false);
			textFieldNomeDoBancoRestore.setEditable(false);
			textFieldUserRestore.setEditable(false);
			textFieldPasswordRestore.setEditable(false);
			textAreaRestore.setEditable(false);

			buttonBuscarFerramentaRestore.setDisable(true);
			buttonLocalizarRestore.setDisable(true);
			buttonFazerRestore.setDisable(true);

			buttonEditarRestore.setText("EDITAR");

		}

	}

	@FXML
	public void onButtonFazerRestoreAction(ActionEvent event) {

		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
				"Você deseja fazer a restauração do backup do banco de dados ?");

		if (result.get() == ButtonType.OK) {

			String ferramenta = textFieldFerramentaDeRestore.getText();
			String host = textFieldHostRestore.getText();
			String porta = textFieldPortaRestore.getText();
			String user = textFieldUserRestore.getText();
			String endereco = textFieldSalvarArquivoDeRestore.getText();
			String banco = textFieldNomeDoBancoRestore.getText();
			String mensagemSenha = MENSAGEM_PASSWORD;
			String senha = textFieldPasswordRestore.getText();

			List<String> areaTexto = new ArrayList<>();

			areaTexto = BackupBancoPostgres.realizaRestore(ferramenta, host, porta, user, endereco, banco,
					mensagemSenha, senha);

			for (String st : areaTexto) {

				textAreaRestore.appendText(st + "\n");
			}

			restoreEditar();

			PropertiesFile.writePropertiesRestore(endereco, host, banco, ferramenta, senha, user, porta);

			new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
					Strings.getLogMessage025());

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		textFieldFerramentaDeBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldSalvarArquivoDeBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldPortaBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldHostBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldNomeDoBancoBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldUserBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldPasswordBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldFerramentaDeRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldSalvarArquivoDeRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldPortaRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldHostRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldNomeDoBancoRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldUserRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldPasswordRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");

		textFieldFerramentaDeBackup.setText(PropertiesFile.loadPropertiesBackup().getProperty("programaBackup"));
		textFieldSalvarArquivoDeBackup.setText(PropertiesFile.loadPropertiesBackup().getProperty("enderecoBackup"));
		textFieldPortaBackup.setText(PropertiesFile.loadPropertiesBackup().getProperty("porta"));
		textFieldHostBackup.setText(PropertiesFile.loadPropertiesBackup().getProperty("host"));
		textFieldNomeDoBancoBackup.setText(PropertiesFile.loadPropertiesBackup().getProperty("nomeBanco"));
		textFieldUserBackup.setText(PropertiesFile.loadPropertiesBackup().getProperty("user"));
		textFieldPasswordBackup.setText(PropertiesFile.loadPropertiesBackup().getProperty("senha"));
		textAreaBackup.setText("");
		textFieldFerramentaDeRestore.setText(PropertiesFile.loadPropertiesRestore().getProperty("programaRestore"));
		textFieldSalvarArquivoDeRestore.setText(PropertiesFile.loadPropertiesRestore().getProperty("enderecoRestore"));
		textFieldPortaRestore.setText(PropertiesFile.loadPropertiesRestore().getProperty("porta"));
		textFieldHostRestore.setText(PropertiesFile.loadPropertiesRestore().getProperty("host"));
		textFieldNomeDoBancoRestore.setText(PropertiesFile.loadPropertiesRestore().getProperty("nomeBanco"));
		textFieldUserRestore.setText(PropertiesFile.loadPropertiesRestore().getProperty("user"));
		textFieldPasswordRestore.setText(PropertiesFile.loadPropertiesRestore().getProperty("senha"));
		textAreaRestore.setText("");

		textFieldFerramentaDeBackup.setEditable(false);
		textFieldSalvarArquivoDeBackup.setEditable(false);
		textFieldPortaBackup.setEditable(false);
		textFieldHostBackup.setEditable(false);
		textFieldNomeDoBancoBackup.setEditable(false);
		textFieldUserBackup.setEditable(false);
		textFieldPasswordBackup.setEditable(false);
		textAreaBackup.setEditable(false);
		textFieldFerramentaDeRestore.setEditable(false);
		textFieldSalvarArquivoDeRestore.setEditable(false);
		textFieldPortaRestore.setEditable(false);
		textFieldHostRestore.setEditable(false);
		textFieldNomeDoBancoRestore.setEditable(false);
		textFieldUserRestore.setEditable(false);
		textFieldPasswordRestore.setEditable(false);
		textAreaRestore.setEditable(false);

		buttonBuscarFerramentaBackup.setDisable(true);
		buttonSalvarArquivoDeBackup.setDisable(true);
		buttonFazerBackup.setDisable(true);

		buttonBuscarFerramentaRestore.setDisable(true);
		buttonLocalizarRestore.setDisable(true);
		buttonFazerRestore.setDisable(true);

	}

	public void backupCancelar() {

		textFieldFerramentaDeBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
		textFieldSalvarArquivoDeBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
		textFieldPortaBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
		textFieldHostBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
		textFieldNomeDoBancoBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
		textFieldUserBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
		textFieldPasswordBackup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");

		textFieldFerramentaDeBackup.setEditable(true);
		textFieldSalvarArquivoDeBackup.setEditable(true);
		textFieldPortaBackup.setEditable(true);
		textFieldHostBackup.setEditable(true);
		textFieldNomeDoBancoBackup.setEditable(true);
		textFieldUserBackup.setEditable(true);
		textFieldPasswordBackup.setEditable(true);
		textAreaBackup.setEditable(true);

		buttonBuscarFerramentaBackup.setDisable(false);
		buttonSalvarArquivoDeBackup.setDisable(false);
		buttonFazerBackup.setDisable(false);

		buttonEditarBackup.setText("CANCELAR");
	}

	public void backupEditar() {

		textFieldFerramentaDeBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldSalvarArquivoDeBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldPortaBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldHostBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldNomeDoBancoBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldUserBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldPasswordBackup.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");

		textFieldFerramentaDeBackup.setEditable(false);
		textFieldSalvarArquivoDeBackup.setEditable(false);
		textFieldPortaBackup.setEditable(false);
		textFieldHostBackup.setEditable(false);
		textFieldNomeDoBancoBackup.setEditable(false);
		textFieldUserBackup.setEditable(false);
		textFieldPasswordBackup.setEditable(false);
		textAreaBackup.setEditable(false);

		buttonBuscarFerramentaBackup.setDisable(true);
		buttonSalvarArquivoDeBackup.setDisable(true);
		buttonFazerBackup.setDisable(true);

		buttonEditarBackup.setText("EDITAR");
	}

	public void restoreCancelar() {

		textFieldFerramentaDeRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
		textFieldSalvarArquivoDeRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
		textFieldPortaRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
		textFieldHostRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
		textFieldNomeDoBancoRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
		textFieldUserRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
		textFieldPasswordRestore.setStyle("-fx-background-color: white; -fx-background-radius: 20;");

		textFieldFerramentaDeRestore.setEditable(true);
		textFieldSalvarArquivoDeRestore.setEditable(true);
		textFieldPortaRestore.setEditable(true);
		textFieldHostRestore.setEditable(true);
		textFieldNomeDoBancoRestore.setEditable(true);
		textFieldUserRestore.setEditable(true);
		textFieldPasswordRestore.setEditable(true);
		textAreaRestore.setEditable(true);

		buttonBuscarFerramentaRestore.setDisable(false);
		buttonLocalizarRestore.setDisable(false);
		buttonFazerRestore.setDisable(false);

		buttonEditarRestore.setText("CANCELAR");

	}

	public void restoreEditar() {

		textFieldFerramentaDeRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldSalvarArquivoDeRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldPortaRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldHostRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldNomeDoBancoRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldUserRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldPasswordRestore.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");

		textFieldFerramentaDeRestore.setEditable(false);
		textFieldSalvarArquivoDeRestore.setEditable(false);
		textFieldPortaRestore.setEditable(false);
		textFieldHostRestore.setEditable(false);
		textFieldNomeDoBancoRestore.setEditable(false);
		textFieldUserRestore.setEditable(false);
		textFieldPasswordRestore.setEditable(false);
		textAreaRestore.setEditable(false);

		buttonBuscarFerramentaRestore.setDisable(true);
		buttonLocalizarRestore.setDisable(true);
		buttonFazerRestore.setDisable(true);

		buttonEditarRestore.setText("EDITAR");

	}

}
