package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.entities.ServicoImpressao;
import model.entities.Lancamento;
import model.entities.Produto;
import model.entities.Usuario;
import model.services.LancamentoService;

public class LancamentoListFormController implements Initializable, DataChangeListener {

	// Java variáveis

	private Cliente cliente;

	private ServicoImpressao servicoImpressao;

	private LancamentoService lancamentoService;

	private static ObservableList<Lancamento> listaVerLancamentos;

	// @FXML variáveis

	@FXML
	private Label labelTituloCliente;

	@FXML
	private Label labelNomeCliente;

	@FXML
	private Label labelNomeServico;

	@FXML
	private Label labelNomeProduto;

	@FXML
	private DatePicker datePickerDataInicial;

	@FXML
	private DatePicker datePickerDataFinal;

	@FXML
	private TableView<Lancamento> tableViewLancamento;

	@FXML
	private TableColumn<Lancamento, String> tableColumnId;

	@FXML
	private TableColumn<Lancamento, String> tableColumnIndex;

	@FXML
	private TableColumn<Lancamento, String> tableColumnDataLancamento;

	@FXML
	private TableColumn<Lancamento, String> tableColumnSaldoAnterior;

	@FXML
	private TableColumn<Lancamento, String> tableColumnOperador;

	@FXML
	private TableColumn<Lancamento, String> tableColumnQuantidade;

	@FXML
	private TableColumn<Lancamento, String> tableColumnIgualdade;

	@FXML
	private TableColumn<Lancamento, String> tableColumnSaldoAtual;

	@FXML
	private TableColumn<Lancamento, String> tableColumnDetalhamento;

	@FXML
	private Button buttonPesquisar;

	// @FXML event

	@FXML
	public void onButtonPesquisarAction(ActionEvent event) {

		if (datePickerDataInicial.getValue() == null || datePickerDataFinal.getValue() == null) {

			Alerts.showAlert("Ver lançamento", "Campo obrigatório", "Digite ou selecione a data", AlertType.ERROR);

		} else if (datePickerDataFinal.getValue().isBefore(datePickerDataInicial.getValue())) {

			Alerts.showAlert("Ver lançamento", "Campo obrigatório", "A data final não pode ser maior que a inicial",
					AlertType.ERROR);

		} else {

			listaVerLancamentos = FXCollections.observableArrayList(lancamentoService.verLancamentos(
					Constraints.setLocalDateToDateSql(datePickerDataInicial.getValue()),
					Constraints.setLocalDateToDateSql(datePickerDataFinal.getValue()),
					getServicoImpressao().getIdServicoImpressao()));

			if (listaVerLancamentos.isEmpty() == true) {

				Alerts.showAlert("Ver lançamento", "Lista vazia", null, AlertType.ERROR);

			} else {

				tableViewLancamento.setItems(listaVerLancamentos);

			}

		}

	}

	@Override
	public void onDataChanged() {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();

	}

	private void initializeNodes() {

		lancamentoService = new LancamentoService();

		labelTituloCliente.setText(new Strings().getTitleLancamentoList());

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewLancamento.prefHeightProperty().bind(stage.heightProperty());

		tableColumnIndex.setSortable(false);
		tableColumnIndex.setCellValueFactory(column -> new ReadOnlyObjectWrapper<String>(
				Constraints.tresDigitos(tableViewLancamento.getItems().indexOf(column.getValue()) + 1)));

		tableColumnId.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.quatroDigitos(param.getValue().getIdLancamento())));
		tableColumnDataLancamento.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.setDateFormat(param.getValue().getDataDoLancamento())));

		tableColumnSaldoAnterior.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.quatroDigitos(param.getValue().getSaldoAnterior())));

		tableColumnOperador.setCellValueFactory(new PropertyValueFactory<>("tipoDoLancamento"));

		tableColumnQuantidade.setCellValueFactory((param) -> new SimpleStringProperty(
				Constraints.quatroDigitos(param.getValue().getQuantidadeDoPedido())));

		tableColumnIgualdade.setCellValueFactory((param) -> new SimpleStringProperty("="));

		tableColumnSaldoAtual.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.quatroDigitos(param.getValue().getSaldoAtual())));

		tableColumnDetalhamento.setCellValueFactory(new PropertyValueFactory<>("observacoesLancamento"));

	}

	public void carregarCampos(Cliente cliente, ServicoImpressao servicoImpressao, Usuario usuario) {

		labelNomeCliente.setText(cliente.getNomeFantasia());
		labelNomeServico.setText(servicoImpressao.getNomeDoServico());
		labelNomeProduto.setText(new Produto().apenasNomeProduto(servicoImpressao.getProdutoDoServico()));

		setServicoImpressao(servicoImpressao);

	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ServicoImpressao getServicoImpressao() {
		return servicoImpressao;
	}

	public void setServicoImpressao(ServicoImpressao servicoImpressao) {
		this.servicoImpressao = servicoImpressao;
	}

	public LancamentoService getLancamentoService() {
		return lancamentoService;
	}

	public void setLancamentoService(LancamentoService lancamentoService) {
		this.lancamentoService = lancamentoService;
	}

}
