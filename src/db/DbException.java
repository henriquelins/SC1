package db;

import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;

public class DbException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DbException(String msg) {

		super(msg);

		Alerts.showAlert("Controle de Saldo", "DbException - Erro ao acessar o Bando de Dados", msg, AlertType.ERROR);
		 
	}

}
