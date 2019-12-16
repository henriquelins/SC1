package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.forms.Forms;
import gui.listeners.DataChangeListener;
import gui.util.Acesso;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Strings;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.entities.ServicoImpressao;
import model.entities.Unidade;
import model.entities.Usuario;
import model.services.ClienteService;

public class PrincipalFormController implements Initializable, DataChangeListener {

	// Java variáveis

	private static int id_cliente;

	private Usuario usuario;

	private Cliente cliente;

	private ClienteService service;

	private static ObservableList<Cliente> listaClientes;

	// @FXML variáveis

	@FXML
	private Label labelLogado;

	@FXML
	private Button buttonNovoCliente;

	@FXML
	private Button buttonPesquisar;

	@FXML
	private TextField textFieldPesquisar;

	@FXML
	private ComboBox<String> comboBoxListarClientes;

	@FXML
	private MenuItem menuItemLogout;

	@FXML
	private MenuItem menuItemSair;

	@FXML
	private MenuItem menuItemUsuario;

	@FXML
	private MenuItem menuItemCliente;

	@FXML
	private MenuItem menuItemVendedor;

	@FXML
	private MenuItem menuItemUnidade;

	@FXML
	private MenuItem menuItemProduto;

	@FXML
	private MenuItem menuItemLancamentoList;

	@FXML
	private MenuItem menuItemAjuda;

	@FXML
	public TableView<Cliente> tableViewCliente;

	@FXML
	private TableColumn<Cliente, String> tableColumnIndex;

	@FXML
	private TableColumn<Cliente, String> tableColumnId;

	@FXML
	private TableColumn<Cliente, String> tableColumnNomeFantasia;

	@FXML
	private TableColumn<Cliente, String> tableColumnRazaoSocial;

	@FXML
	private TableColumn<Cliente, String> tableColumnCnpj;

	@FXML
	private TableColumn<Cliente, String> tableColumnAtendimento;

	@FXML
	private TableColumn<Cliente, String> tableColumnEmail;

	@FXML
	private TableColumn<Cliente, String> tableColumnFone;

	@FXML
	private TableColumn<Cliente, Cliente> tableColumnClienteSelecionado;

	// @FXML event

	@FXML
	public void onMouseClickedAction(MouseEvent event) {

		if (event.getClickCount() == 2) {

			event.consume();

			cliente = tableViewCliente.getSelectionModel().getSelectedItem();

			if (cliente != null) {

				clearTableView();
				comboBoxListarClientes.setValue(null);
				clienteSelecionadoForm(usuario, cliente, Strings.getClienteSelecionadoView());

			} else {

				// Alerts.showAlert("Pesquisar Clientes", "Selecionar o cliente ", "Dê dois
				// cliques na linha para selecionar o cliente", AlertType.ERROR);

			}

		}

	}

	// evento botão cliente

	@FXML
	public void onBtClienteAction(ActionEvent event) {

		clienteForm(usuario, null, Strings.getClienteView());

	}

	// evento botão pesquisar

	@FXML
	public void onBtPesquisarClienteAction(ActionEvent event) {

		if (textFieldPesquisar.getText().equals("")) {

			Alerts.showAlert("Pesquisar Clientes", "Campo obrigatório", "Digite no campo de pesquisa", AlertType.ERROR);

		} else {

			List<Cliente> listaCliente = new ArrayList<Cliente>();

			listaCliente = service.pesquisarNomeFantasia(textFieldPesquisar.getText());

			if (listaCliente.isEmpty() == true) {

				Alerts.showAlert("Pesquisar Clientes", "Lista vazia", "A pesquisa não retornou resultado!",
						AlertType.ERROR);

				textFieldPesquisar.setText("");
				textFieldPesquisar.requestFocus();

				clearTableView();

			} else {

				listaClientes = FXCollections.observableArrayList(listaCliente);
				listarTableView();

				textFieldPesquisar.setText("");
				textFieldPesquisar.requestFocus();

			}

		}

	}

	// evento menu item logout

	@FXML
	public void onMenuItemLogoutAction(ActionEvent event) {

		Utils.fecharTelaPrincipalFormAction();
		new Forms().loginForm(Strings.getLoginView());

	}

	// evento menu item sair

	@FXML
	public void onMenuItemSairAction(ActionEvent event) {

		System.exit(0);

	}

	// evento menu item usuario

	@FXML
	public void onMenuItemUsuarioAction(ActionEvent event) {

		new Forms().usuarioForm(usuario, Strings.getUsuarioView());

	}

	// evento menu item cliente

	@FXML
	public void onMenuItemClienteAction(ActionEvent event) {

		clienteForm(usuario, null, Strings.getClienteView());

	}

	// evento menu item vendedor

	@FXML
	public void onMenuItemVendedorAction(ActionEvent event) {

		new Forms().vendedorForm(usuario, Strings.getVendedorView());

	}

	// evento menu item cliente

	@FXML
	public void onMenuItemUnidadeAction(ActionEvent event) {

		new Forms().unidadeForm(usuario, Strings.getUnidadeView());

	}

	@FXML
	public void onMenuItemProdutoAction(ActionEvent event) {

		new Forms().produtoForm(usuario, Strings.getProdutoView());

	}

	// evento menu item sobre

	@FXML
	public void onMenuItemAjudaAction(ActionEvent event) {

		new Forms().sobreForm(Strings.getSobreView());

	}

	// evento comboBox listar

	@FXML
	public void onComboBoxListarClientesAction(ActionEvent event) {

		try {

			if (comboBoxListarClientes.getSelectionModel().getSelectedItem().equalsIgnoreCase("Todos")) {

				updateTableView();

			} else if (comboBoxListarClientes.getSelectionModel().getSelectedItem().equalsIgnoreCase("Limpar")) {

				clearTableView();

			}

		} catch (java.lang.NullPointerException e) {

			// Alerts.showAlert("Lista Clientes", "Lista Vazia",
			// "java.lang.NullPointerException", AlertType.ERROR);

		}

	}

	// Listener

	@Override
	public void onDataChanged() {

		updateTableView();

	}

	// Inicia classe

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();

	}

	// Método com os objetos que devem ser inicializados

	private void initializeNodes() {

		service = new ClienteService();

		comboBoxListarClientes.setItems(FXCollections.observableArrayList(listarClientes()));

		tableColumnIndex.setSortable(false);
		tableColumnIndex.setCellValueFactory(column -> new ReadOnlyObjectWrapper<String>(
				Constraints.tresDigitos(tableViewCliente.getItems().indexOf(column.getValue()) + 1)));

		tableColumnId.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.quatroDigitos(param.getValue().getIdCliente())));
		tableColumnNomeFantasia.setCellValueFactory(new PropertyValueFactory<>("nomeFantasia"));
		tableColumnRazaoSocial.setCellValueFactory(new PropertyValueFactory<>("razaoSocial"));
		tableColumnCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpjCliente"));

		tableColumnAtendimento.setCellValueFactory((param) -> new SimpleStringProperty(
				new Unidade().nomeUnidade(param.getValue().getUnidadeDeAtendimento())));

		tableColumnEmail.setCellValueFactory(
				(param) -> new SimpleStringProperty(param.getValue().getContato().getEmailCliente()));
		tableColumnFone.setCellValueFactory(
				(param) -> new SimpleStringProperty(param.getValue().getContato().getFoneCelular()));

	}

	// Lista todos os clientes

	private void listaTodos() {

		listaClientes = FXCollections.observableArrayList(service.findAll());

	}

	// Atualizar Table com listar todos

	public void updateTableView() {

		listaTodos();
		tableViewCliente.setItems(listaClientes);

	}

	// Atualizar Table

	public void listarTableView() {

		tableViewCliente.setItems(listaClientes);

	}

	// Limpar Table

	public void clearTableView() {

		if (service == null) {

			throw new IllegalStateException("clienteService está nulo");

		}

		tableViewCliente.setItems(null);

	}

	// Lista do comboBox

	private List<String> listarClientes() {

		List<String> lista = new ArrayList<>();
		lista.add("Todos");
		lista.add("Limpar");

		return lista;

	}

	// clienteForm com DataChangeList

	public void clienteForm(Usuario usuario, Cliente cliente, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				ScrollPane pane = loader.load();

				ClienteFormController controller = loader.getController();
				controller.setService(new ClienteService());
				controller.subscribeDataChangeListener(this);
				controller.carregarCampos(cliente, usuario);

				ClienteFormController.setClienteScene(new Scene(pane));

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(ClienteFormController.getClienteFormScene());
				primaryStage.setResizable(false);
				primaryStage.initModality(Modality.APPLICATION_MODAL);

				pane.setFitToHeight(true);
				pane.setFitToWidth(true);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				primaryStage.getIcons().add(applicationIcon);

				primaryStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	// clienteSelecionadoForm com DataChangeList

	public void clienteSelecionadoForm(Usuario usuario, Cliente cliente, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				VBox pane = loader.load();

				ClienteSelecionadoFormController controller = loader.getController();
				controller.setServicoImpressao(new ServicoImpressao());
				controller.carregarCampos(cliente, usuario);

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(new Scene(pane));
				primaryStage.initModality(Modality.APPLICATION_MODAL);

				primaryStage.setMaximized(true);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				primaryStage.getIcons().add(applicationIcon);

				primaryStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", Strings.erroCarregarTela(), e.getMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	// Getters and Setters

	public Label getLabelLogado() {
		return labelLogado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setLabelLogado(String logado) {
		this.labelLogado.setText(logado);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public static int getId_cliente() {
		return id_cliente;
	}

	public static void setId_cliente(int id_cliente) {
		PrincipalFormController.id_cliente = id_cliente;
	}

}
