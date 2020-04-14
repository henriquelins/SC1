package gui;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import application.SC1Main;
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
import model.services.LogSegurancaService;
import relatorio.Relatorio;

public class LancamentoListFormController implements Initializable, DataChangeListener {

	// Java variáveis

	private int total;

	private Cliente cliente;

	private ServicoImpressao servicoImpressao;

	private LancamentoService lancamentoService;

	private ObservableList<Lancamento> listaVerLancamentos;

	// @FXML variáveis

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

	private Usuario usuario;

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

				new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
						"Pesquisa cliente: " + cliente.getNomeFantasia().toUpperCase() + " - Serviço: "
								+ servicoImpressao.getNomeDoServico().toUpperCase() + " - "
								+ Constraints.setLocalDateToDateSql(datePickerDataInicial.getValue()) + " até "
								+ Constraints.setLocalDateToDateSql(datePickerDataFinal.getValue()));

				tableViewLancamento.setItems(listaVerLancamentos);

				for (Lancamento l : listaVerLancamentos) {

					if (!l.getTipoDoLancamento().equalsIgnoreCase("ENTRADA DE CRÉDITOS (+)")) {

						if (!l.getTipoDoLancamento().equalsIgnoreCase("FATURA PAGA")) {

							total = total + l.getQuantidadeDoPedido();

						}

					}

					labelTotal.setText(String.valueOf(total));

				}

				total = 0;

			}

		}

	}

	@FXML
	public void onBtExportarAction(ActionEvent event) {

		ExportarListaLancamentosXLS exportarXLS = new ExportarListaLancamentosXLS();

		String caminho = "C:/temp/listaVerLancamentos.xls";

		String periodo = "";

		try {

			try {

				if (listaVerLancamentos.isEmpty() != true) {

					try {
						
						new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage016() + servicoImpressao.getNomeDoServico().toUpperCase());

						DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");

						periodo = formatBR.format(Constraints.setLocalDateToDateSql(datePickerDataInicial.getValue()))
								+ " até "
								+ formatBR.format(Constraints.setLocalDateToDateSql(datePickerDataFinal.getValue()));

						exportarXLS.exportarListaLancamentoXLS(caminho, listaVerLancamentos, servicoImpressao, periodo,
								cliente.getNomeFantasia());

					} catch (WriteException e) {

						Alerts.showAlert("Exportar lista", "Erro ao criar o arquivo!", "Exportar para Excel",
								AlertType.ERROR);

					}

				} else {

					Alerts.showAlert("Exportar lista", "Lista vazia", "Exportar para Excel", AlertType.ERROR);
				}

			} catch (HeadlessException | IOException e2) {

				Alerts.showAlert("Exportar lista", "Feche o arquivo primeiro!", "Exportar para Excel", AlertType.ERROR);

			}

		} catch (java.lang.NullPointerException e) {

			Alerts.showAlert("Exportar lista", "Listagem nula", "Exportar para Excel", AlertType.ERROR);

		}

	}

	@FXML
	public void onBtImprimirAction(ActionEvent event) {

		Relatorio relatorio = new Relatorio();

		try {

			if (listaVerLancamentos.isEmpty() != true) {
				
				new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage017() + servicoImpressao.getNomeDoServico().toUpperCase());

				relatorio.relatorioListaLancamentosPDF(listaVerLancamentos, servicoImpressao, total);
				
			} else {

				Alerts.showAlert("Relatório", "Lista vazia", "Exportar Relatório", AlertType.ERROR);

			}

		} catch (java.lang.NullPointerException e) {

			Alerts.showAlert("Relatório", "Listagem nula", "Exportar Relatório", AlertType.ERROR);

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

	// Método com os objetos que devem ser inicializados

	private void initializeNodes() {

		lancamentoService = new LancamentoService();

		Stage stage = (Stage) SC1Main.getMainScene().getWindow();
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

	// Carrega os campos da Classe Lançamento Lista

	public void carregarCampos(Cliente cliente, ServicoImpressao servicoImpressao, Usuario usuario) {

		labelTituloCliente
				.setText(Strings.getTitleLancamentoList() + " - Cliente: " + cliente.getNomeFantasia().toUpperCase()
						+ " - Serviço de Impressão: " + servicoImpressao.getNomeDoServico().toUpperCase());
		labelNomeCliente.setText(cliente.getNomeFantasia().toUpperCase());
		labelNomeServico.setText(servicoImpressao.getNomeDoServico().toUpperCase());
		labelNomeProduto.setText(new Produto().apenasNomeProduto(servicoImpressao.getProdutoDoServico()).toUpperCase());
		labelSaldoAtual.setText(String.valueOf(servicoImpressao.getConta().getSaldo()));

		labelTotal.setText(String.valueOf(total));

		setServicoImpressao(servicoImpressao);

		setCliente(cliente);

		setUsuario(usuario);

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
