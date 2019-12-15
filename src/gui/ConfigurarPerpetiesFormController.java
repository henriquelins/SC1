package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import properties.PropertiesFile;

public class ConfigurarPerpetiesFormController implements Initializable {

	@FXML
	private TextField txtUrl;

	@FXML
	private TextField txtPassword;

	@FXML
	private TextField txtUser;

	@FXML
	private PasswordField pswSenha;

	@FXML
	private Button btEditarSalvar;

	@FXML
	public void onBtEditarSalvarAction(ActionEvent event) {
		
		if (btEditarSalvar.getText().equals("Salvar")) {
			
			System.out.println("salvar");
			
			btEditarSalvar.setText("Editar");
			
			txtUrl.setEditable(false);
			txtPassword.setEditable(false);
			txtUser.setEditable(false);
			
			//PropertiesFile.writePropertiesDB(txtUrl.getText(), txtPassword.getText(), txtUser.getText());
			
			PropertiesFile.salvarPropertiesDB(txtUrl.getText(), txtPassword.getText(), txtUser.getText());
			
		} else {
			
			System.out.println("salvar");
			
			btEditarSalvar.setText("Editar");
			
			txtUrl.setEditable(true);
			txtPassword.setEditable(true);
			txtUser.setEditable(true);
			
		}
	
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		initializeNodes();
		
	}

	private void initializeNodes() {
		
		
	}

	

}
