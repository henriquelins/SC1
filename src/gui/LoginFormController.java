package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.forms.Forms;
import gui.util.Alerts;
import gui.util.Strings;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.entities.Usuario;
import model.services.UsuarioService;

public class LoginFormController implements Initializable {
	
	// Java variáveis
	
	private UsuarioService usuarioService;

	private Usuario usuario;
	
	// @FXML variáveis

	@FXML
	private TextField txtLogin;

	@FXML
	private PasswordField pswSenha;

	@FXML
	private Button btOK;

	@FXML
	private Label labelTitle;
	
	// @FXML event

	@FXML
	public void onBtOKAction(ActionEvent event) {

		Usuario user = new Usuario();
		user = getFormData();

		if (user.getLogin() != null && user.getSenha() != null) {

			setUsuario(usuarioService.login(user));

			try {

				if (usuario != null) {

					Utils.currentStage(event).close();
					new Forms().principalForm(usuario, Strings.getPrincipalView());

				} else {

					Alerts.showAlert("Login", null, "Login não confirmado", AlertType.ERROR);

					txtLogin.setText("");
					pswSenha.setText("");
					txtLogin.requestFocus();

				}

			} catch (NullPointerException e) {

				Alerts.showAlert("Login", "NullPointerException", e.getMessage(), AlertType.ERROR);

			}

		}

	}
	
	// Inicia classe

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}
	
	// Método com os objetos que devem ser inicializados

	private void initializeNodes() {

		usuario = new Usuario();
		usuarioService = new UsuarioService();
		labelTitle.setText(Strings.getTitleLogin());

	}
	
	// Método para testar os textFields

	private Usuario getFormData() {

		Usuario usuario = new Usuario();

		if (txtLogin.getText() == null || txtLogin.getText().trim().equals("")) {

			Alerts.showAlert("Login", null, "Digite seu login", AlertType.INFORMATION);

			txtLogin.requestFocus();

			usuario.setLogin(null);
			usuario.setSenha(null);

		} else if (String.valueOf(pswSenha.getText()) == null || pswSenha.getText().trim().equals("")) {

			Alerts.showAlert("Login", null, "Digite sua senha", AlertType.INFORMATION);

			pswSenha.requestFocus();

			usuario.setLogin(null);
			usuario.setSenha(null);

		} else {

			usuario.setLogin(txtLogin.getText());
			usuario.setSenha(pswSenha.getText());

		}

		return usuario;

	}
	
	// Getters and Setters

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
