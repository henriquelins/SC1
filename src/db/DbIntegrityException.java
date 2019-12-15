package db;

import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;

public class DbIntegrityException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DbIntegrityException(String msg) {
		
		super(msg);
		
		Alerts.showAlert("Controle de Estoque", " DbIntegrityException", msg, AlertType.ERROR);
		
	}
}
