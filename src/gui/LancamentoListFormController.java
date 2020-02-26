package gui;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import exportarXLS.ExportarListaLancamentosXLS;
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
import jxl.write.WriteException;
import model.entities.Cliente;
import model.entities.Lancamento;
import model.entities.Produto;
import model.entities.ServicoImpressao;
import model.entities.Usuario;
import model.services.LancamentoService;
import relatorio.Relatorio;

public class LancamentoListFormController implements Initializable, DataChangeListener {

	// Java vari�veis

	private int total;

	private Cliente cliente;

	private ServicoImpressao servicoImpressao;

	private LancamentoService lancamentoService;

	private static ObservableList<Lancamento> listaVerLancamentos;

	// @FXML vari�veis

	@FXML
	private Label labelTotal;

	@FXML
	private Label labelSaldoAtual;

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

	@FXML
	private Button buttonExportar;

	@FXML
	private Button buttonImprimir;

	// @FXML event

	@FXML
	public void onButtonPesquisarAction(ActionEvent event) {

		if (datePickerDataInicial.getValue() == null || datePickerDataFinal.getValue() == null) {

			Alerts.showAlert("Ver lan�amento", "Campo obrigat�rio", "Digite ou selecione a data", AlertType.ERROR);

		} else if (datePickerDataFinal.getValue().isBefore(datePickerDataInicial.getValue())) {

			Alerts.showAlert("Ver lan�amento", "Campo obrigat�rio", "A data final n�o pode ser maior que a inicial",
					AlertType.ERROR);

		} else {

			listaVerLancamentos = FXCollections.observableArrayList(lancamentoService.verLancamentos(
					Constraints.setLocalDateToDateSql(datePickerDataInicial.getValue()),
					Constraints.setLocalDateToDateSql(datePickerDataFinal.getValue()),
					getServicoImpressao().getIdServicoImpressao()));

			if (listaVerLancamentos.isEmpty() == true) {

				Alerts.showAlert("Ver lan�amento", "Lista vazia", null, AlertType.ERROR);

			} else {

				tableViewLancamento.setItems(listaVerLancamentos);

				for (int i = 0; listaVerLancamentos.size() > i; i++) {

					if (!listaVerLancamentos.get(i).getTipoDoLancamento().equalsIgnoreCase("FATURA PAGA")) {

						total = total + listaVerLancamentos.get(i).getQuantidadeDoPedido();

					}

				}

				labelTotal.setText(String.valueOf(total));

			}

		}

	}

	@FXML
	public void onBtExportarAction(ActionEvent event) {

		ExportarListaLancamentosXLS exportarXLS = new ExportarListaLancamentosXLS();

		String caminho = "C:/temp/listaVerLancamentos.xls";

		try {

			try {

				if (listaVerLancamentos.isEmpty() != true) {

					try {

						exportarXLS.exportarListaLancamentoXLS(caminho, listaVerLancamentos, servicoImpressao, total);

					} catch (WriteException e) {

						Alerts.showAlert("Exportar lista", "Erro ao criar o arquivo!", "Exportar ", AlertType.ERROR);

					}

				} else {

					Alerts.showAlert("Exportar lista", "Lista vazia", "Exportar ", AlertType.ERROR);
				}

			} catch (HeadlessException | IOException e2) {

				Alerts.showAlert("Exportar lista", "Feche o arquivo primeiro!", "Exportar", AlertType.ERROR);

			}

		} catch (java.lang.NullPointerException e) {// TODO: handle exception

			Alerts.showAlert("Exportar lista", "Lista vazia", "Exportar ", AlertType.ERROR);

		}

	}

	@FXML
	public void onBtImprimirAction(ActionEvent event) {

		Relatorio relatorio = new Relatorio();

		try {

			if (listaVerLancamentos.isEmpty() != true) {

				relatorio.relatorioListaLancamentosPDF(listaVerLancamentos, servicoImpressao, total);

			} else {

				Alerts.showAlert("Relat�rio", "Lista vazia", "Exportar Relat�rio", AlertType.ERROR);

			}

		} catch (java.lang.NullPointerException e) {

			Alerts.showAlert("Relat�rio", "Erro ao gerar o arquivo", "Imprimir Relat�rio ", AlertType.ERROR);

		}

	}

	// Listener

	@Override
	public void onDataChanged() {

	}

	// Inicia classe

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();

	}

	// M�todo com os objetos que devem ser inicializados

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

	// Carrega os campos da Classe Lan�amento Lista

	public void carregarCampos(Cliente cliente, ServicoImpressao servicoImpressao, Usuario usuario) {

		labelNomeCliente.setText(cliente.getNomeFantasia());
		labelNomeServico.setText(servicoImpressao.getNomeDoServico());
		labelNomeProduto.setText(new Produto().apenasNomeProduto(servicoImpressao.getProdutoDoServico()));
		labelSaldoAtual.setText(String.valueOf(servicoImpressao.getConta().getSaldo()));

		labelTotal.setText(String.valueOf(total));

		setServicoImpressao(servicoImpressao);

	}

	// Getters e Setters

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
