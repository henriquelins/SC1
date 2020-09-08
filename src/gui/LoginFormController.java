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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.entities.Usuario;
import model.services.LogSegurancaService;
import model.services.UsuarioService;

public class LoginFormController implements Initializable {

	private static Usuario logado;

	private UsuarioService usuarioService;

	@FXML
	private TextField txtLogin;

	@FXML
	private PasswordField pswSenha;

	@FXML
	private Button buttonLogin;

	@FXML
	private Button buttonFechar;

	@FXML
	private Label labelTitle;
	
	@FXML
	private Hyperlink hyperLinkBancoDados;

	@FXML
	public void onButtonFecharAction(ActionEvent event) {

		System.exit(0);

	}
	
	@FXML
	public void onHyperLinkBancoDadosAction(ActionEvent event) {

		Utils.currentStage(event).close();
		new Forms().ConfigurarPerpetiesDBForm(Strings.getConfigurarPerpetiesDBView());
		
	}

	@FXML
	public void onButtonLoginAction(ActionEvent event) {

		Usuario usuario = new Usuario();
		usuario = getFormData();

		if (usuario.getLogin() != null && usuario.getSenha() != null) {

			try {

				setLogado(usuarioService.login(usuario));

				if (logado != null) {

					Utils.currentStage(event).close();
					new Forms().principalForm(logado, Strings.getPrincipalView());

					new LogSegurancaService().novoLogSeguranca(logado.getNome(), Strings.getLogMessage001());

				} else {

					Alerts.showAlert("Login","Erro de Login", "Login não confirmado", AlertType.ERROR);

					txtLogin.setText("");
					pswSenha.setText("");
					txtLogin.requestFocus();

				}

			} catch (NullPointerException e) {

				Alerts.showAlert("Login","Erro de Login", "Login não confirmado", AlertType.ERROR);

				txtLogin.setText("");
				pswSenha.setText("");
				txtLogin.requestFocus();

			}

		}

	}

	public static void setLogado(Usuario logado) {

		LoginFormController.logado = logado;

	}

	public static Usuario getLogado() {

		return logado;

	}

	public static String usuarioLogado() {

		return logado.toUsuarioLogado();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {

		logado = new Usuario();
		usuarioService = new UsuarioService();

		labelTitle.setText(Strings.getTitleLogin());

	}

	private Usuario getFormData() {

		Usuario usuario = new Usuario();

		if (txtLogin.getText() == null || txtLogin.getText().trim().equals("")) {

			Alerts.showAlert("Login","Campo obrigatório", "Digite seu login", AlertType.INFORMATION);

			txtLogin.requestFocus();

			usuario.setLogin(null);
			usuario.setSenha(null);

		} else if (String.valueOf(pswSenha.getText()) == null || pswSenha.getText().trim().equals("")) {

			Alerts.showAlert("Login","Campo obrigatório", "Digite sua senha", AlertType.INFORMATION);

			pswSenha.requestFocus();

			usuario.setLogin(null);
			usuario.setSenha(null);

		} else {

			usuario.setLogin(txtLogin.getText());
			usuario.setSenha(pswSenha.getText());

		}

		return usuario;

	}

}
