package gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import email.Email;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Strings;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.entities.Usuario;
import model.services.EmailService;

public class EmailFormController implements Initializable {

	private static byte[] bytes;

	private Email email;

	private Email emailComparar;

	@FXML
	private TextField textFieldEmail;

	@FXML
	private TextField textFieldDescricao;

	@FXML
	private TextField textFieldNomeServidorEmail;

	@FXML
	private TextField textFieldHost;

	@FXML
	private TextField textFieldPorta;

	@FXML
	private PasswordField textFieldAutenticacao;

	@FXML
	private TextField textFieldMensagemAssinatura;

	@FXML
	private Button buttonImagemEmail;

	@FXML
	private Button buttonNovoEmail;

	@FXML
	private Button buttonSalvarEmail;

	@FXML
	private Button buttonEditarEmail;

	@FXML
	private Button buttonFechar;

	@FXML
	private Label labelTituloTela;

	@FXML
	private ImageView ImageViewAssinaturaEmail;

	private EmailService emailService;

	// Métodos dos botões

	public void ontButtonImagemEmailAction(ActionEvent event) {

		FileChooser chooser = new FileChooser();
		File arquivo = null;
		String local = "";

		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));

		chooser.setTitle("Escolha a imagem da assinatura do email");

		arquivo = chooser.showOpenDialog(new Stage());

		if (arquivo != null) {

			local = arquivo.getAbsolutePath();
			InputStream converter = null;

			setBytes(new byte[(int) arquivo.length()]);

			try {

				converter = new FileInputStream(local);

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

			int offset = 0;
			int numRead = 0;

			try {

				while (offset < bytes.length && (numRead = converter.read(bytes, offset, bytes.length - offset)) >= 0) {

					offset += numRead;
				}

			} catch (IOException e) {

				e.printStackTrace();

			}

			if (getBytes() != null) {

				ImageViewAssinaturaEmail.setImage(byteToImage(getBytes()));

			}

		} else {

			local = "";
			setBytes(null);

		}

	}

	public void onButtonSalvarEmailAction(ActionEvent event) {

		setEmail(getFormDataEmail());

		if (getEmail() != null) {

			boolean ok = compararCamposEmail();

			if (ok == false) {

				new EmailService().emailNovoOuEditar(getEmail());

				setBytes(null);
				setEmail(null);

			} else {

				Alerts.showAlert("Cadastro de email", "Editar email", "Não houve alteração no registro",
						AlertType.INFORMATION);

			}

		}

		atualizarDados();

	}

	public void onButtonEditarEmailAction(ActionEvent event) {

		if (buttonEditarEmail.getText().equals("EDITAR")) {

			textFieldEmail.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldDescricao.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldNomeServidorEmail.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldHost.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldPorta.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
			textFieldAutenticacao.setStyle("-fx-background-color: white; -fx-background-radius: 20;");

			textFieldEmail.setEditable(true);
			textFieldEmail.setEditable(true);
			textFieldDescricao.setEditable(true);
			textFieldNomeServidorEmail.setEditable(true);
			textFieldHost.setEditable(true);
			textFieldPorta.setEditable(true);
			textFieldAutenticacao.setEditable(true);

			buttonImagemEmail.setDisable(false);
			buttonSalvarEmail.setDisable(false);

			buttonEditarEmail.setText("CANCELAR");

		} else {

			textFieldEmail.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldDescricao.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldNomeServidorEmail.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldHost.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldPorta.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
			textFieldAutenticacao.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");

			textFieldEmail.setEditable(false);
			textFieldEmail.setEditable(false);
			textFieldDescricao.setEditable(false);
			textFieldNomeServidorEmail.setEditable(false);
			textFieldHost.setEditable(false);
			textFieldPorta.setEditable(false);
			textFieldAutenticacao.setEditable(false);

			buttonImagemEmail.setDisable(true);
			buttonSalvarEmail.setDisable(true);

			buttonEditarEmail.setText("EDITAR");

		}

	}

	public void atualizarDados() {

		setEmail(new EmailService().buscarEmail());

		textFieldEmail.setText(getEmail().getNomeEmail());
		textFieldDescricao.setText(getEmail().getDescricaoEmail());
		textFieldNomeServidorEmail.setText(getEmail().getNomeServidor());
		textFieldHost.setText(getEmail().getHostName());
		textFieldPorta.setText(String.valueOf(getEmail().getSmtpPort()));
		textFieldAutenticacao.setText(getEmail().getAuthentication());

		if (getEmail().getImagemAssinaturaEmail() == null) {

			ImageViewAssinaturaEmail.setImage(new Image(Strings.getSemFoto()));

		} else {

			try {

				ImageViewAssinaturaEmail.setImage(byteToImage(getEmail().getImagemAssinaturaEmail()));

			} catch (java.lang.NullPointerException e1) {

				ImageViewAssinaturaEmail.setImage(new Image(Strings.getSemFoto()));

			}

		}

		textFieldEmail.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldDescricao.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldNomeServidorEmail.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldHost.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldPorta.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldAutenticacao.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");

		textFieldEmail.setEditable(false);
		textFieldEmail.setEditable(false);
		textFieldDescricao.setEditable(false);
		textFieldNomeServidorEmail.setEditable(false);
		textFieldHost.setEditable(false);
		textFieldPorta.setEditable(false);
		textFieldAutenticacao.setEditable(false);

		buttonImagemEmail.setDisable(true);
		buttonSalvarEmail.setDisable(true);

		buttonEditarEmail.setText("EDITAR");

	}

	public void onButtonFecharAction(ActionEvent event) {

		Stage stage = (Stage) buttonFechar.getScene().getWindow(); // Obtendo a janela atual
		stage.close(); // Fechando o Stage

	}

	// inicializar o app

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initializeNodes();

	}

	private void initializeNodes() {

		emailService = new EmailService();

		Constraints.mascaraNumeroInteiro(textFieldPorta);
		Constraints.limitTextField(textFieldPorta, 4);

		carregarEmail();

	}

	private void carregarEmail() {

		setEmail(new EmailService().buscarEmail());

		textFieldEmail.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldDescricao.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldNomeServidorEmail.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldHost.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldPorta.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");
		textFieldAutenticacao.setStyle("-fx-background-color: yellow; -fx-background-radius: 20;");

		if (getEmail() != null) {

			textFieldEmail.setText(getEmail().getNomeEmail());
			textFieldDescricao.setText(getEmail().getDescricaoEmail());
			textFieldNomeServidorEmail.setText(getEmail().getNomeServidor());
			textFieldHost.setText(getEmail().getHostName());
			textFieldPorta.setText(String.valueOf(getEmail().getSmtpPort()));
			textFieldAutenticacao.setText(getEmail().getAuthentication());

			if (getEmail().getImagemAssinaturaEmail() == null) {

				ImageViewAssinaturaEmail.setImage(new Image(Strings.getSemFoto()));

			} else {

				try {

					ImageViewAssinaturaEmail.setImage(byteToImage(getEmail().getImagemAssinaturaEmail()));

				} catch (java.lang.NullPointerException e1) {

					ImageViewAssinaturaEmail.setImage(new Image(Strings.getSemFoto()));

				}

			}

			emailComparar = getEmail();

		} else {

			textFieldEmail.setText("");
			textFieldDescricao.setText("");
			textFieldNomeServidorEmail.setText("");
			textFieldHost.setText("");
			textFieldPorta.setText("");
			textFieldAutenticacao.setText("");

			ImageViewAssinaturaEmail.setImage(new Image(Strings.getSemFoto()));

		}

	}

	// Confirmar dados

	private Email getFormDataEmail() {

		Email email = new Email();

		if (textFieldEmail.getText() == null || textFieldEmail.getText().trim().equals("")) {

			Alerts.showAlert("Novo Email", "Campo obrigatório", "Digite o email", AlertType.INFORMATION);

			textFieldEmail.requestFocus();

			email = null;

		} else if (Constraints.isValidEmailAddressRegex(textFieldEmail.getText()) != true) {

			Alerts.showAlert("Novo Email", "Campo obrigatório", "O e-mail não é válido", AlertType.INFORMATION);

			textFieldEmail.setText("");
			textFieldEmail.requestFocus();

			email = null;

		} else if (textFieldDescricao.getText() == null || textFieldDescricao.getText().trim().equals("")) {

			Alerts.showAlert("Novo Email", "Campo obrigatório", "Digite a descrição", AlertType.INFORMATION);

			textFieldDescricao.requestFocus();

			email = null;

		} else if (textFieldNomeServidorEmail.getText() == null
				|| textFieldNomeServidorEmail.getText().trim().equals("")) {

			Alerts.showAlert("Servidor Email", "Campo obrigatório", "Digite o nome do servidor de email",
					AlertType.INFORMATION);

			textFieldNomeServidorEmail.requestFocus();

			email = null;

		} else if (textFieldHost.getText() == null || textFieldHost.getText().trim().equals("")) {

			Alerts.showAlert("Servidor Email", "Campo obrigatório", "Digite o Host", AlertType.INFORMATION);

			textFieldHost.requestFocus();

			email = null;

		} else if (textFieldPorta.getText() == null || textFieldPorta.getText().trim().equals("")) {

			Alerts.showAlert("Servidor Email", "Campo obrigatório", "Digite a Porta", AlertType.INFORMATION);

			textFieldPorta.requestFocus();

			email = null;

		} else if (textFieldAutenticacao.getText() == null || textFieldAutenticacao.getText().trim().equals("")) {

			Alerts.showAlert("Servidor Email", "Campo obrigatório", "Digite a Autenticação", AlertType.INFORMATION);

			textFieldAutenticacao.requestFocus();

			email = null;

		} else {

			if (getEmail() != null) {

				email.setIdEmail(getEmail().getIdEmail());

				if (getBytes() != null) {

					email.setImagemAssinaturaEmail(getBytes());

				} else {

					email.setImagemAssinaturaEmail(getEmail().getImagemAssinaturaEmail());
				}

			} else {

				email.setIdEmail(null);

				if (getBytes() != null) {

					email.setImagemAssinaturaEmail(getBytes());

				} else {

					email.setImagemAssinaturaEmail(null);
				}

			}

			email.setNomeEmail(textFieldEmail.getText().toLowerCase());
			email.setDescricaoEmail(textFieldDescricao.getText().toUpperCase());
			email.setNomeServidor(textFieldNomeServidorEmail.getText().toLowerCase());
			email.setHostName(textFieldHost.getText());
			email.setSmtpPort(Integer.parseInt(textFieldPorta.getText()));
			email.setAuthentication(textFieldAutenticacao.getText());

		}

		return email;
	}

	// Getters e setters

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public static byte[] getBytes() {
		return bytes;
	}

	public static void setBytes(byte[] bytes) {
		EmailFormController.bytes = bytes;
	}

	public void carregarCampos(Usuario usuario) {

		labelTituloTela.setText(Strings.getTitleEmail());

	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public static Image byteToImage(byte[] img) {

		BufferedImage bi = null;
		try {

			bi = ImageIO.read(new ByteArrayInputStream(img));

		} catch (IOException e) {

			e.printStackTrace();
		}

		Image image = SwingFXUtils.toFXImage(bi, null);

		return image;
	}

	// Comparar todos os campos email

	private boolean compararCamposEmail() {

		boolean ok = false;

		if (getEmail() == null) {

			return ok;

		} else if (getEmail().equals(emailComparar)) {

			ok = true;
			return ok;

		} else {

			return ok;

		}

	}

	// Comparar todos os campos servidor

}
