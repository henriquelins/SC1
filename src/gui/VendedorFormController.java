package gui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Strings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Vendedor;
import model.services.VendedorService;

public class VendedorFormController implements Initializable, DataChangeListener {

	// Java variáveis

		private Vendedor vendedor;

		private Vendedor compararVendedor;

		private VendedorService vendedorService;

		private static ObservableList<Vendedor> listaVendedor;

		// @FXML variáveis

		@FXML
		private Label labelTituloTela;

		@FXML
		private TextField textFieldId;

		@FXML
		private TextField textFieldNome;

		@FXML
		private TableView<Vendedor> tableViewVendedor;
		
		@FXML
		private TableColumn<Vendedor, String> tableColumnIndex;

		@FXML
		private TableColumn<Vendedor, String> tableColumnId;

		@FXML
		private TableColumn<Vendedor, String> tableColumnNome;

		@FXML
		private Button buttonNovo;

		@FXML
		private Button buttonSalvar;

		@FXML
		private Button buttonExcluir;

		// FXML eventos

		@FXML
		public void onButtonNovoAction(ActionEvent event) {

			textFieldId.setText("");
			textFieldNome.setText("");
			textFieldNome.requestFocus();

			Vendedor vendedor = new Vendedor(null, "");

			setVendedor(vendedor);

		}

		@FXML
		public void onButtonSalvarAction(ActionEvent event) {

			try {

				setVendedor(getFormData());

				boolean ok = compararCampos();

				if (ok == false) {

					vendedorService.produtoNovoOuEditar(vendedor);
					limparCampos();
					onDataChanged();

				} else {

					Alerts.showAlert("Cadastro de Vendedores", "Editar vendedores",
							"Não houve alteração no registro", AlertType.INFORMATION);

				}

			} catch (java.lang.NullPointerException e) {

			}

		}

		@FXML
		public void onButtonExcluirAction(ActionEvent event) {

			if (vendedorService == null) {

				throw new IllegalThreadStateException("vendedorService está nulo");

			}
			try {

				if (vendedor.getIdVendedor() != null) {

					Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
							"Você deseja excluir o vendedor " + vendedor.getNomeVendedor() + " ?");

					if (result.get() == ButtonType.OK) {

						vendedorService.excluir(vendedor.getIdVendedor());
						onDataChanged();

					}

				} else {

					Alerts.showAlert("Cadastro de Vendedores", "Excluir", "Selecione um registro", AlertType.INFORMATION);

				}

			} catch (DbIntegrityException e) {

				Alerts.showAlert("Cadastro de Vendedores", "Excluir", "Erro ao excluir o vendedor",
						AlertType.INFORMATION);
				limparCampos();

			}

		}

		// Inicialização da classe

		@Override
		public void initialize(URL url, ResourceBundle rb) {

			initializeNodes();

		}

		// Método com os objetos que devem ser inicializados

		private void initializeNodes() {
			
			labelTituloTela.setText(Strings.getTitleVendedor());

			vendedor = new Vendedor();
			compararVendedor = new Vendedor();
			vendedorService = new VendedorService();

			
			tableViewVendedor.getSelectionModel().selectedItemProperty()
					.addListener((observable, oldValue, newValue) -> carregarCampos(newValue));
			
			tableColumnIndex.setSortable(false);
			tableColumnIndex.setCellValueFactory( column -> new ReadOnlyObjectWrapper<String>(Constraints.tresDigitos(
					tableViewVendedor.getItems().indexOf(column.getValue()) + 1)));

			tableColumnId.setCellValueFactory((param) -> new SimpleStringProperty(Constraints.quatroDigitos(param.getValue().getIdVendedor())));
			tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeVendedor"));

			atualizarTableView();

		}

		// Listener

		@Override
		public void onDataChanged() {

			atualizarTableView();

		}

		// Comparar todos os campos

		private boolean compararCampos() {

			boolean ok = false;

			if (getVendedor() == null) {

				return ok;

			} else if (getVendedor().equals(compararVendedor)) {

				ok = true;
				return ok;

			} else {

				return ok;

			}

		}

		// Carregar campos

		private void carregarCampos(Vendedor vendedor) {

			labelTituloTela.setText(Strings.getTitleVendedor());

			if (vendedor != null) {

				textFieldId.setText(Constraints.quatroDigitos(vendedor.getIdVendedor()));
				textFieldNome.setText(vendedor.getNomeVendedor());

				setVendedor(vendedor);
				compararVendedor = vendedor;

			} else {

				limparCampos();

			}

		}

		// Atualizar tabela

		private void atualizarTableView() {

			if (vendedorService == null) {

				throw new IllegalStateException("vendedorService está nulo");
			}

			listaVendedor = FXCollections.observableArrayList(vendedorService.buscarTodos());
			tableViewVendedor.setItems(listaVendedor);

		}

		public void limparCampos() {

			textFieldId.setText("");
			textFieldNome.setText("");

			setVendedor(new Vendedor());

		};

		// Tratamento dos campos

		private Vendedor getFormData() {

			Vendedor vendedor = new Vendedor();

			if (textFieldNome.getText() == null || textFieldNome.getText().trim().equals("")) {

				Alerts.showAlert("Cadastro de Vendedores", "Campo obrigatório", "Digite o nome",
						AlertType.INFORMATION);

				textFieldNome.requestFocus();

				vendedor = null;

			} else {

				if (getVendedor() != null) {

					vendedor.setIdVendedor(getVendedor().getIdVendedor());

				}

				vendedor.setNomeVendedor(textFieldNome.getText());

			}

			return vendedor;
		}

		// Get and Setters

		public Vendedor getVendedor() {
			return vendedor;
		}

		public void setVendedor(Vendedor vendedor) {
			this.vendedor = vendedor;
		}

		public Vendedor getCompararVendedor() {
			return compararVendedor;
		}

		public void setCompararVendedor(Vendedor compararVendedor) {
			this.compararVendedor = compararVendedor;
		}

		public VendedorService getVendedorService() {
			return vendedorService;
		}

		public void setVendedorService(VendedorService vendedorService) {
			this.vendedorService = vendedorService;
		}

		
	
}
