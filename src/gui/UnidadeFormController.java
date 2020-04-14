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
import model.entities.Unidade;
import model.entities.Usuario;
import model.services.LogSegurancaService;
import model.services.UnidadeService;

public class UnidadeFormController implements Initializable, DataChangeListener {

	// Java variáveis

	private Unidade unidade;

	private Unidade compararUnidade;

	private UnidadeService unidadeService;

	private static ObservableList<Unidade> listaUnidade;

	// @FXML variáveis

	@FXML
	private Label labelTituloTela;

	@FXML
	private TextField textFieldId;

	@FXML
	private TextField textFieldNome;

	@FXML
	private TableView<Unidade> tableViewUnidade;

	@FXML
	private TableColumn<Unidade, String> tableColumnIndex;

	@FXML
	private TableColumn<Unidade, String> tableColumnId;

	@FXML
	private TableColumn<Unidade, String> tableColumnNome;

	@FXML
	private Button buttonNovo;

	@FXML
	private Button buttonSalvar;

	@FXML
	private Button buttonExcluir;

	private Usuario usuario;

	// FXML eventos

	@FXML
	public void onButtonNovoAction(ActionEvent event) {

		textFieldId.setText("");
		textFieldNome.setText("");
		textFieldNome.requestFocus();

		Unidade unidade = new Unidade(null, "");

		setUnidade(unidade);

	}

	@FXML
	public void onButtonSalvarAction(ActionEvent event) {

		try {

			setUnidade(getFormData());

			boolean ok = compararCampos();

			if (ok == false) {

				unidadeService.produtoNovoOuEditar(unidade);
				limparCampos();
				onDataChanged();
				new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
						"Unidade cadastrada ou Editada: " + usuario.getNome().toUpperCase());

			} else {

				Alerts.showAlert("Unidade de Atendimento", "Editar Unidade de Atendimento",
						"Não houve alteração no registro", AlertType.INFORMATION);

			}

		} catch (java.lang.NullPointerException e) {

		}

	}

	@FXML
	public void onButtonExcluirAction(ActionEvent event) {

		if (unidadeService == null) {

			throw new IllegalThreadStateException("unidadeService está nulo");

		}
		try {

			if (unidade.getIdUnidade() != null) {

				Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
						"Você deseja excluir a unidade de atendimento " + unidade.getNomeUnidade() + " ?");

				if (result.get() == ButtonType.OK) {

					unidadeService.excluir(unidade.getIdUnidade());
					onDataChanged();

				}

			} else {

				Alerts.showAlert("Unidade de Atendimento", "Excluir", "Selecione um registro", AlertType.INFORMATION);

			}

		} catch (DbIntegrityException e) {

			Alerts.showAlert("Unidade de Atendimento", "Excluir", "Erro ao excluir a unidade de atendimento",
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

		labelTituloTela.setText(Strings.getTitleUnidade());

		unidade = new Unidade();
		compararUnidade = new Unidade();
		unidadeService = new UnidadeService();

		tableViewUnidade.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> carregarCampos(newValue));

		tableColumnIndex.setSortable(false);
		tableColumnIndex.setCellValueFactory(column -> new ReadOnlyObjectWrapper<String>(
				Constraints.tresDigitos(tableViewUnidade.getItems().indexOf(column.getValue()) + 1)));

		tableColumnId.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.quatroDigitos(param.getValue().getIdUnidade())));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeUnidade"));

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

		if (getUnidade() == null) {

			return ok;

		} else if (getUnidade().equals(compararUnidade)) {

			ok = true;
			return ok;

		} else {

			return ok;

		}

	}

	// Carregar campos

	private void carregarCampos(Unidade unidade) {

		labelTituloTela.setText(Strings.getTitleUnidade());

		if (unidade != null) {

			textFieldId.setText(Constraints.quatroDigitos(unidade.getIdUnidade()));
			textFieldNome.setText(unidade.getNomeUnidade());

			setUnidade(unidade);
			compararUnidade = unidade;

		} else {

			limparCampos();

		}

	}

	// Atualizar tabela

	private void atualizarTableView() {

		if (unidadeService == null) {

			throw new IllegalStateException("UnidadeService está nulo");
		}

		listaUnidade = FXCollections.observableArrayList(unidadeService.buscarTodos());
		tableViewUnidade.setItems(listaUnidade);

	}

	public void limparCampos() {

		textFieldId.setText("");
		textFieldNome.setText("");

		setUnidade(new Unidade());

	};

	// Tratamento dos campos

	private Unidade getFormData() {

		Unidade unidade = new Unidade();

		if (textFieldNome.getText() == null || textFieldNome.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de unidade de atendimento", "Campo obrigatório", "Digite o nome",
					AlertType.INFORMATION);

			textFieldNome.requestFocus();

			unidade = null;

		} else {

			if (getUnidade() != null) {

				unidade.setIdUnidade(getUnidade().getIdUnidade());

			}

			unidade.setNomeUnidade(textFieldNome.getText());

		}

		return unidade;
	}

	// Get and Setters

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Unidade getCompararUnidade() {
		return compararUnidade;
	}

	public void setCompararUnidade(Unidade compararUnidade) {
		this.compararUnidade = compararUnidade;
	}

	public UnidadeService getUnidadeService() {
		return unidadeService;
	}

	public void setUnidadeService(UnidadeService unidadeService) {
		this.unidadeService = unidadeService;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void carregarCampos(Usuario usuario) {

		setUsuario(usuario);

	}

}
