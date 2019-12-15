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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Usuario;
import model.services.UsuarioService;

public class UsuarioFormController implements Initializable, DataChangeListener {

	// Java variáveis

	private UsuarioService service;

	private static Usuario usuario;

	private Usuario usuarioTabela;

	private Usuario usuarioComparar;

	private static ObservableList<Usuario> listaUsuarios;

	// @FXML variáveis

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private Label labelTitle;

	@FXML
	private TextField txtIdUsuario;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtLogin;

	@FXML
	private PasswordField pswSenha;

	@FXML
	private PasswordField pswRepetirSenha;

	@FXML
	private Button btNovoUsuario;

	@FXML
	private Button btSalvarUsuario;

	@FXML
	private Button btCancelarEditarUsuario;

	@FXML
	private ComboBox<String> comboBoxAcesso;

	@FXML
	public TableView<Usuario> tableViewUsuario;

	@FXML
	private TableColumn<Usuario, String> tableColumnIndex;

	@FXML
	private TableColumn<Usuario, String> tableColumnId;

	@FXML
	private TableColumn<Usuario, String> tableColumnNome;

	@FXML
	private TableColumn<Usuario, String> tableColumnLogin;

	@FXML
	private TableColumn<Usuario, String> tableColumnAcesso;

	@FXML
	private TableColumn<Usuario, Usuario> tableColumnEDIT;

	@FXML
	private TableColumn<Usuario, Usuario> tableColumnREMOVE;

	// @FXML event

	// Evento botão novo usuário

	@FXML
	public void onBtNovoUsuarioAction(ActionEvent event) {

		limparCampos();
		editarCamposFalso();
		novoCamposDoUsuario();
		tableViewUsuario.setDisable(true);

	}

	// Evento botão salvar usuário

	@FXML
	public void onBtSalvarUsuarioAction(ActionEvent event) {

		setUsuario(getFormData());

		if (usuario != null) {

			editarCamposFalso();

			boolean ok = false;

			ok = compararCampos();

			if (ok == false) {

				service.usuarioNovoOuEditar(usuario);
				botoesFalso();
				onDataChanged();

			} else {

				Alerts.showAlert("Usuário", "Editar Usuário", "Não houve alteração no registro", AlertType.INFORMATION);

				botoesFalso();

			}

			tableViewUsuario.setDisable(false);

		}
	}

	// Evento botão cancelar usuário

	@FXML
	public void onBtCancelarEditarUsuarioAction(ActionEvent event) {

		limparCampos();
		botoesFalso();
		editarCamposFalso();

		tableViewUsuario.setDisable(false);

		if (usuarioTabela.getIdUsuario() != null) {

			showUsuarioDetails(usuarioTabela);

		} else {

			limparCampos();

		}

	}

	// método editar campos

	protected void editarCamposDoUsuario() {

		if (usuarioTabela.getIdUsuario() != null) {

			txtNome.setEditable(true);
			txtLogin.setEditable(true);
			pswSenha.setEditable(true);
			pswRepetirSenha.setEditable(true);

			btNovoUsuario.setDisable(false);
			btCancelarEditarUsuario.setVisible(true);
			btSalvarUsuario.setVisible(true);

			tableViewUsuario.setDisable(true);

			btNovoUsuario.setDisable(true);

		} else {

			Alerts.showAlert("Usuários", "Editar Usuário", "Selecione um registro", AlertType.INFORMATION);

		}

	}

	// método novo campos

	protected void novoCamposDoUsuario() {

		txtNome.setEditable(true);
		txtLogin.setEditable(true);
		pswSenha.setEditable(true);
		pswRepetirSenha.setEditable(true);

		btNovoUsuario.setDisable(true);
		btCancelarEditarUsuario.setVisible(true);
		btSalvarUsuario.setVisible(true);

		tableViewUsuario.setDisable(true);

	}

	// método limpar campos

	protected void limparCampos() {

		txtIdUsuario.setText("");
		txtNome.setText("");
		txtLogin.setText("");
		pswSenha.setText("");
		pswRepetirSenha.setText("");
		comboBoxAcesso.setValue("Selecione o tipo de acesso...");

		setUsuario(new Usuario());

	}

	// método editar campos falso

	protected void editarCamposFalso() {

		txtIdUsuario.setEditable(false);
		txtNome.setEditable(false);
		txtLogin.setEditable(false);
		pswSenha.setEditable(false);
		pswRepetirSenha.setEditable(false);

	}

	// método botões falso

	protected void botoesFalso() {

		btCancelarEditarUsuario.setVisible(false);
		btSalvarUsuario.setVisible(false);
		btNovoUsuario.setDisable(false);

	}

	// Mostrar detalhes do usuário

	public void showUsuarioDetails(Usuario usuario) {

		if (btCancelarEditarUsuario.isVisible() != true) {

			if (usuario != null) {
				txtIdUsuario.setText(Constraints.quatroDigitos(usuario.getIdUsuario()));
				txtNome.setText(usuario.getNome());
				txtLogin.setText(usuario.getLogin());
				pswSenha.setText(usuario.getSenha());
				pswRepetirSenha.setText(usuario.getSenha());
				comboBoxAcesso.setValue(selectChoiceBox(usuario.getAcesso()));

				setUsuarioTabela(usuario);

				setUsuario(usuario);
				usuarioComparar = usuario;

			} else {

				setUsuarioTabela(null);
				limparCampos();

			}
		}
	}

	// Lista comboBox

	private ObservableList<String> listaAcesso() {

		ObservableList<String> listaChoiceBox = FXCollections.observableArrayList(Strings.getCase1choiceBoxAcesso1(),
				Strings.getCase1choiceBoxAcesso2());
		return listaChoiceBox;

	}

	// Listener

	@Override
	public void onDataChanged() {

		this.updateTableView();

	}

	// Inicia classe

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	// Método com os objetos que devem ser inicializados

	private void initializeNodes() {

		labelTitle.setText(Strings.getTitleUsuario());

		showUsuarioDetails(null);

		btCancelarEditarUsuario.setVisible(false);
		btSalvarUsuario.setVisible(false);

		tableViewUsuario.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showUsuarioDetails(newValue));

		tableColumnIndex.setSortable(false);
		tableColumnIndex.setCellValueFactory(column -> new ReadOnlyObjectWrapper<String>(
				Constraints.tresDigitos(tableViewUsuario.getItems().indexOf(column.getValue()) + 1)));

		tableColumnId.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.quatroDigitos(param.getValue().getIdUsuario())));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tableColumnLogin.setCellValueFactory(new PropertyValueFactory<>("Login"));

		tableColumnAcesso.setCellValueFactory(
				(param) -> new SimpleStringProperty(selectChoiceBox(param.getValue().getAcesso()).substring(3)));

		service = new UsuarioService();
		usuarioTabela = new Usuario();

		comboBoxAcesso.setItems(listaAcesso());

		updateTableView();

	}

	// Método atualizar Table

	public void updateTableView() {

		if (service == null) {

			throw new IllegalStateException("Serviço está nulo");

		}

		listaUsuarios = FXCollections.observableArrayList(service.findAll());
		tableViewUsuario.setItems(listaUsuarios);

		tableViewUsuario.setDisable(false);
		limparCampos();

		initEditButton();
		initRemoveButton();

	}

	// Método para testar os textFields

	private Usuario getFormData() {

		Usuario usuario = new Usuario();

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usuário", "Campo obrigatório", "Digite seu nome!", AlertType.INFORMATION);
			txtNome.requestFocus();
			usuario = null;

		} else if (txtLogin.getText() == null || txtLogin.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usuário", "Campo obrigatório", "Digite seu login!", AlertType.INFORMATION);
			txtLogin.requestFocus();
			usuario = null;

		} else if (pswSenha.getText() == null || pswSenha.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usuário", "Campo obrigatório", "Digite sua senha!", AlertType.INFORMATION);

			pswSenha.requestFocus();
			usuario = null;

		} else if (pswRepetirSenha.getText() == null || pswRepetirSenha.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usuário", "Campo obrigatório", "Digite a confirmação da senha!",
					AlertType.INFORMATION);

			pswRepetirSenha.requestFocus();
			usuario = null;

		} else if (!pswSenha.getText().equals(pswRepetirSenha.getText())
				|| !pswRepetirSenha.getText().equals(pswSenha.getText())) {

			Alerts.showAlert("Novo Usuário", "Campo obrigatório", "A confirmação da senha não está igual a senha!",
					AlertType.INFORMATION);

			pswRepetirSenha.requestFocus();
			usuario = null;

		} else if (comboBoxAcesso.getSelectionModel().getSelectedItem().equals("Selecione o tipo de acesso...")) {

			Alerts.showAlert("Novo Usuário", "Campo obrigatório", "Selecione o acesso!", AlertType.INFORMATION);

			comboBoxAcesso.requestFocus();
			usuario = null;

		} else {

			if (txtIdUsuario.getText().equals("")) {

				usuario.setIdUsuario(null);

			} else {

				usuario.setIdUsuario(Integer.valueOf(txtIdUsuario.getText()));

			}

			usuario.setNome(txtNome.getText());
			usuario.setLogin(txtLogin.getText());
			usuario.setSenha(pswSenha.getText());
			usuario.setAcesso(selectAcesso(comboBoxAcesso.getSelectionModel().getSelectedItem()));

		}

		return usuario;

	}

	// Método iniciar o botão editar da table

	private void initEditButton() {

		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Usuario, Usuario>() {
			private final Button btEditar = new Button("Editar");

			@Override
			protected void updateItem(Usuario usuario, boolean empty) {

				super.updateItem(usuario, empty);

				if (usuario == null) {

					setGraphic(null);
					return;

				}

				setGraphic(btEditar);
				btEditar.setOnAction(event -> editarCamposDoUsuario());

			}
		});
	}

	// Método iniciar o botão remover da table

	private void initRemoveButton() {

		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Usuario, Usuario>() {
			private final Button btExcluir = new Button("Excluir");

			@Override
			protected void updateItem(Usuario usuario, boolean empty) {

				super.updateItem(usuario, empty);

				if (usuario == null) {

					setGraphic(null);
					return;

				}

				setGraphic(btExcluir);
				btExcluir.setOnAction(event -> removeEntity(usuario));

			}
		});
	}

	// Método remover entidade

	private void removeEntity(Usuario usuario) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
				"Você deseja deletar o usuário " + usuario.getNome() + " ?");

		if (result.get() == ButtonType.OK) {

			if (service == null) {

				throw new IllegalThreadStateException("Service está nulo");

			}

			try {

				service.remove(usuario);
				onDataChanged();

			} catch (DbIntegrityException e) {

				Alerts.showAlert("Erro ao remover o usuário " + usuario.getNome() + " !", null, e.getMessage(),
						AlertType.ERROR);

			}
		}
	}

	// Método comparar campos

	public boolean compararCampos() {

		boolean ok = false;

		if (usuario == null) {

			return ok;

		} else if (usuario.equals(usuarioComparar)) {

			ok = true;
			return ok;

		} else {

			return ok;

		}

	};

	// Método selecionar o tipo de acesso

	public String selectChoiceBox(int acesso) {

		String selectComboBoxAcesso = "";

		switch (acesso) {

		case 1:

			selectComboBoxAcesso = Strings.getCase1choiceBoxAcesso1();
			break;

		case 2:

			selectComboBoxAcesso = Strings.getCase1choiceBoxAcesso2();
			break;

		}

		return selectComboBoxAcesso;

	}

	// Getters and Setters

	public int selectAcesso(String selectChoiceBox) {

		int acesso = Integer.valueOf(selectChoiceBox.substring(0, 1));

		return acesso;
	}

	public void setUsuarioService(UsuarioService service) {

		this.service = service;

	}

	public void setUsuario(Usuario usuario) {

		UsuarioFormController.usuario = usuario;

	}

	public static Usuario getUsuario() {

		return usuario;

	}

	public void setUsuarioTabela(Usuario usuario) {

		this.usuarioTabela = usuario;

	}

}
