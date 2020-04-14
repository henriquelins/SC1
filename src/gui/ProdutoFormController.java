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
import model.entities.Produto;
import model.entities.Usuario;
import model.services.LogSegurancaService;
import model.services.ProdutoService;

public class ProdutoFormController implements Initializable, DataChangeListener {

	// Java variáveis

	private Produto produto;

	private Produto compararProduto;

	private ProdutoService produtoService;

	private static ObservableList<Produto> listaProduto;

	// @FXML variáveis

	@FXML
	private Label labelTituloTela;

	@FXML
	private TextField textFieldId;

	@FXML
	private TextField textFieldNome;

	@FXML
	private TableView<Produto> tableViewProduto;

	@FXML
	private TableColumn<Produto, String> tableColumnIndex;

	@FXML
	private TableColumn<Produto, String> tableColumnId;

	@FXML
	private TableColumn<Produto, String> tableColumnNome;

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

		Produto produto = new Produto(null, "");

		setProduto(produto);

	}

	@FXML
	public void onButtonSalvarAction(ActionEvent event) {

		try {

			setProduto(getFormData());

			boolean ok = compararCampos();

			if (ok == false) {

				produtoService.produtoNovoOuEditar(produto);
				limparCampos();
				onDataChanged();
				new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
						"Produto cadastrado ou Editado: " + usuario.getNome().toUpperCase());


			} else {

				Alerts.showAlert("Cadastro de Produtos", "Editar produtos", "Não houve alteração no registro",
						AlertType.INFORMATION);

			}

		} catch (java.lang.NullPointerException e) {

		}

	}

	@FXML
	public void onButtonExcluirAction(ActionEvent event) {

		if (produtoService == null) {

			throw new IllegalThreadStateException("produtoService está nulo");

		}
		try {

			if (produto.getIdProduto() != null) {

				Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
						"Você deseja excluir o produto " + produto.getNomeProduto() + " ?");

				if (result.get() == ButtonType.OK) {

					produtoService.excluir(produto.getIdProduto());
					onDataChanged();

				}

			} else {

				Alerts.showAlert("Cadastro de Produtos", "Excluir", "Selecione um registro", AlertType.INFORMATION);

			}

		} catch (DbIntegrityException e) {

			Alerts.showAlert("Cadastro de Produtos", "Excluir", "Erro ao excluir o produto", AlertType.INFORMATION);
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

		labelTituloTela.setText(Strings.getTitleProduto());

		produto = new Produto();
		compararProduto = new Produto();
		produtoService = new ProdutoService();

		tableViewProduto.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> carregarCampos(newValue));

		tableColumnIndex.setSortable(false);
		tableColumnIndex.setCellValueFactory(column -> new ReadOnlyObjectWrapper<String>(
				Constraints.tresDigitos(tableViewProduto.getItems().indexOf(column.getValue()) + 1)));

		tableColumnId.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.quatroDigitos(param.getValue().getIdProduto())));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));

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

		if (getProduto() == null) {

			return ok;

		} else if (getProduto().equals(compararProduto)) {

			ok = true;
			return ok;

		} else {

			return ok;

		}

	}

	// Carregar campos

	private void carregarCampos(Produto produto) {

		labelTituloTela.setText(Strings.getTitleProduto());

		if (produto != null) {

			textFieldId.setText(Constraints.quatroDigitos(produto.getIdProduto()));
			textFieldNome.setText(produto.getNomeProduto());

			setProduto(produto);
			compararProduto = produto;

		} else {

			limparCampos();

		}

	}

	// Atualizar tabela

	private void atualizarTableView() {

		if (produtoService == null) {

			throw new IllegalStateException("produtoService está nulo");
		}

		listaProduto = FXCollections.observableArrayList(produtoService.buscarTodos());
		tableViewProduto.setItems(listaProduto);

	}

	public void limparCampos() {

		textFieldId.setText("");
		textFieldNome.setText("");

		setProduto(new Produto());

	};

	// Tratamento dos campos

	private Produto getFormData() {

		Produto produto = new Produto();

		if (textFieldNome.getText() == null || textFieldNome.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de unidade de atendimento", "Campo obrigatório", "Digite o nome",
					AlertType.INFORMATION);

			textFieldNome.requestFocus();

			produto = null;

		} else {

			if (getProduto() != null) {

				produto.setIdProduto(getProduto().getIdProduto());

			}

			produto.setNomeProduto(textFieldNome.getText());

		}

		return produto;
	}

	// Get and Setters

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getCompararProduto() {
		return compararProduto;
	}

	public void setCompararProduto(Produto compararProduto) {
		this.compararProduto = compararProduto;
	}

	public ProdutoService getProdutoService() {
		return produtoService;
	}

	public void setProdutoService(ProdutoService produtoService) {
		this.produtoService = produtoService;
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
