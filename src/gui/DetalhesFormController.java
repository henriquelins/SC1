package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import javafx.fxml.Initializable;
import model.entities.Cliente;

public class DetalhesFormController implements Initializable, DataChangeListener {

	private Cliente cliente;

	@Override
	public void onDataChanged() {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
