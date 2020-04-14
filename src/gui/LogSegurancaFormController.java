package gui;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Constraints;
import gui.util.Strings;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.entities.LogSeguranca;
import model.services.LogSegurancaService;

public class LogSegurancaFormController implements Initializable {

	// @FXML variáveis

	@FXML
	private Label labelTitle;

	@FXML
	private Button buttonFechar;

	@FXML
	private TextArea textAreaLogSeguranca;

	@FXML
	public void onButtonFecharAction(ActionEvent event) {

		Utils.currentStage(event).close();

	}

	// Inicia classe

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		labelTitle.setText(Strings.getTitleLogSeguranca());
		
		List<LogSeguranca> lista = new LogSegurancaService().logBuscarTodos();
		
		textAreaLogSeguranca.appendText("LOG DE SEGURANÇA - DATA: " + Constraints.setDateFormat(new Date(System.currentTimeMillis())));
		textAreaLogSeguranca.appendText("\n");
		textAreaLogSeguranca.appendText("\n");
		
		for (LogSeguranca l : lista) {
			
			textAreaLogSeguranca.appendText("Usuário: " + l.getLogado() + " - Descrição: " + l.getDescricao() + " - Data: " + Constraints.setDateFormat(l.getDataLog()) + " - Hora: " + l.getHoraLog() + "\n"); 
			
		}
		textAreaLogSeguranca.appendText("\n");
		textAreaLogSeguranca.appendText("Dúvidas, entre em contato com o Administrador do sistema");
	
	}

}
