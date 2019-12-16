package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Strings;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Cliente;
import model.entities.ServicoImpressao;
import model.entities.Lancamento;
import model.entities.Produto;
import model.entities.Usuario;
import model.services.LancamentoService;

public class LancamentoFormController implements Initializable, DataChangeListener { 

	// Java variáveis

	private static Scene lancamentoFormScene;

	private Cliente cliente;

	private ServicoImpressao servicoImpressao;

	private Lancamento lancamento;

	private LancamentoService lancamentoService;

	private ClienteSelecionadoFormController clienteSelecionadoFormController;

	// Lista de ouvintes para receber alguma modificação

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	// @FXML variáveis

	@FXML
	private Label labelTituloTela;

	@FXML
	private Label labelNomeDoCliente;

	@FXML
	private TextField textFieldNomeDoServico;

	@FXML
	private TextField textFieldProdutoDoServico;

	@FXML
	private TextField textFieldCnpjDoLancamento;

	@FXML
	private TextField textFieldSaldoDoServico;

	@FXML
	private TextField textFieldQuantidadeDoLancamento;

	@FXML
	private DatePicker datePickerDataDoLancamento;

	@FXML
	private ComboBox<String> comboBoxTipoDoLancamento;

	@FXML
	private TextArea textAreaObservacoesDoLancamento;

	@FXML
	private Button buttonSalvarLancamento;	

	// @FXML event

	@FXML
	public void onButtonSalvarLancamentoAction(ActionEvent event) {

		setLancamento(getFormData());
	
		if (lancamento != null) {
			
			lancamentoService.lancamentoSaidaOuEntrada(getLancamento(), getServicoImpressao());
			notifyDataChangeListeners();
			Utils.fecharTelaLancamentoFormAction();

		}
	}

	// Adiciona a lista um ouvinte, quando há uma modificação

	public void subscribeDataChangeListener(DataChangeListener listener) {

		dataChangeListeners.add(listener);

	}

	// Função que faz a atualização da tabela

	private void notifyDataChangeListeners() {

		for (DataChangeListener listener : dataChangeListeners) {

			listener.onDataChanged();

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

		servicoImpressao = new ServicoImpressao();

		setClienteSelecionadoFormController(new ClienteSelecionadoFormController());

		lancamento = new Lancamento();
		lancamentoService = new LancamentoService();
		labelTituloTela.setText(Strings.getTitleLancamento());
		comboBoxTipoDoLancamento.setItems(FXCollections.observableArrayList(listarTiposLancamento()));
		
		Constraints.mascaraNumeroInteiro(textFieldQuantidadeDoLancamento);

	}

	// Lista do comboBox

	private List<String> listarTiposLancamento() {

		List<String> lista = new ArrayList<>();
		lista.add("Entrada de créditos (+)");
		lista.add("Saída de créditos (-)");
		
		return lista;

	}

	// Carrega os campos da Classe Lançamento

	public void carregarCampos(Cliente cliente, ServicoImpressao servicoImpressao, Usuario usuario) {

		labelNomeDoCliente.setText(cliente.getNomeFantasia());
		textFieldNomeDoServico.setText(servicoImpressao.getNomeDoServico());
		textFieldProdutoDoServico.setText(new Produto().apenasNomeProduto(servicoImpressao.getProdutoDoServico()));
		textFieldCnpjDoLancamento.setText(servicoImpressao.getConta().getCnpj());
		textFieldSaldoDoServico.setText(Constraints.tresDigitos(servicoImpressao.getConta().getSaldo()) + " Unidades");

		setServicoImpressao(servicoImpressao);

	}

	// Método para testar os textFields

	private Lancamento getFormData() {

		Lancamento lancamento = new Lancamento();

		if (textFieldQuantidadeDoLancamento.getText() == null
				|| textFieldQuantidadeDoLancamento.getText().trim().equals("")) {

			Alerts.showAlert("Lançamentos", "Campo obrigatório", "Digite a quantidade do lançamento",
					AlertType.INFORMATION);

			textFieldQuantidadeDoLancamento.requestFocus();

			lancamento = null;

		} else if (datePickerDataDoLancamento.getValue() == null) {

			Alerts.showAlert("Cadastro de clientes", "Campos obrigatório",
					"Selecione a data do início do cadastro do cliente", AlertType.INFORMATION);

			datePickerDataDoLancamento.requestFocus();

			lancamento = null;

		} else if (comboBoxTipoDoLancamento.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Selecione o tipo do lançamento",
					AlertType.INFORMATION);

			comboBoxTipoDoLancamento.requestFocus();

			lancamento = null;

		} else if (textAreaObservacoesDoLancamento.getText() == null
				|| textAreaObservacoesDoLancamento.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite as observações do lançamento",
					AlertType.INFORMATION);

			textAreaObservacoesDoLancamento.requestFocus();

			lancamento = null;

		} else {

			lancamento.setIdServicoImpressao(servicoImpressao.getIdServicoImpressao());
			lancamento.setQuantidadeDoPedido(Integer.valueOf(textFieldQuantidadeDoLancamento.getText()));
			lancamento.setDataDoLancamento(Constraints.setLocalDateToDateSql(datePickerDataDoLancamento.getValue()));
			lancamento.setTipoDoLancamento(comboBoxTipoDoLancamento.getSelectionModel().getSelectedItem());
			lancamento.setObservacoesLancamento(textAreaObservacoesDoLancamento.getText());
			lancamento.setSaldoAnterior(servicoImpressao.getConta().getSaldo());
			lancamento.setSaldoAtual(saltoAtual(servicoImpressao.getConta().getSaldo(),
					Integer.valueOf(textFieldQuantidadeDoLancamento.getText()),
					comboBoxTipoDoLancamento.getSelectionModel().getSelectedItem()));

		}

		return lancamento;

	}

	// Função saldo atual

	public int saltoAtual(int saldoAnterior, int saldo, String tipoLancamento) {

		int saldoAtual = 0;

		switch (tipoLancamento) {
		case "Entrada de créditos (+)":

			saldoAtual = saldoAnterior + saldo;

			break;

		case "Saída de créditos (-)":

			saldoAtual = saldoAnterior - saldo;

			break;

		}

		return saldoAtual;
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

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public static Scene getLancamentoFormScene() {
		return lancamentoFormScene;
	}

	public static void setLancamentoFormScene(Scene lancamentoFormScene) {
		LancamentoFormController.lancamentoFormScene = lancamentoFormScene;
	}

	public void setService(LancamentoFormController lancamentoFormController) {

	}

	public ClienteSelecionadoFormController getClienteSelecionadoFormController() {
		return clienteSelecionadoFormController;
	}

	public void setClienteSelecionadoFormController(ClienteSelecionadoFormController clienteSelecionadoFormController) {
		this.clienteSelecionadoFormController = clienteSelecionadoFormController;
	}

}
