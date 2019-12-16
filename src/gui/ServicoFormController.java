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
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Cliente;
import model.entities.Conta;
import model.entities.Produto;
import model.entities.ServicoImpressao;
import model.entities.Usuario;
import model.services.ProdutoService;
import model.services.Servico�mpressaoService;

public class ServicoFormController implements Initializable, DataChangeListener {

	// Java vari�veis

	// Lista de ouvintes para receber alguma modifica��o

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	private ClienteSelecionadoFormController clienteSelecionadoFormController;

	private static Scene servicoFormScene;

	private Usuario usuario;

	private Cliente cliente;

	private ServicoImpressao servicoImpressao;

	private ServicoImpressao compararServicoImpressao;

	private Servico�mpressaoService service;

	// @FXML vari�veis

	@FXML
	private Label labelTituloTela;

	@FXML
	private Label labelNomeFantasia;

	@FXML
	private TextField textFieldNomeDoServico;

	@FXML
	private TextField textFieldCnpjParaCobranca;

	@FXML
	private ComboBox<String> comboBoxProduto;

	@FXML
	private TextField textFieldValorUnitario;

	@FXML
	private TextField textFieldLimiteMinimo;

	@FXML
	private TextArea textAreaObservacoesDoServico;

	@FXML
	private Button buttonSalvarServico;

	// @FXML event

	// Evento bot�o salvar ou editar servi�o

	@FXML
	public void onButtonSalvarServicoAction(ActionEvent event) {

		setServicoImpressao(getFormData());
	
		boolean ok = compararCampos();

		if (ok == false) {

			service.clienteServicoNovoOuEditar(servicoImpressao);
			notifyDataChangeListeners();
			Utils.fecharTelaServicoFormAction();

		} else {

			Alerts.showAlert("Cadastro de servi�os", "Editar servi�os", "N�o houve altera��o no registro",
					AlertType.INFORMATION);

		}

	} 

	// Comparar todos os campos

	private boolean compararCampos() {

		boolean ok = false;

		if (servicoImpressao == null) {

			return ok;

		} else if (servicoImpressao.equals(compararServicoImpressao)) {

			ok = true;
			return ok;

		} else {

			return ok;

		}

	}

	// Lista do comboBox

	private List<String> listaProdutos() {

		List<String> lista = new ArrayList<>();

		for (Produto p : new ProdutoService().buscarTodos()) {

			lista.add(p.getIdProduto() + " - " + p.getNomeProduto());

		}

		return lista;

	}

	// Adiciona a lista um ouvinte, quando h� uma modifica��o

	public void subscribeDataChangeListener(DataChangeListener listener) {

		dataChangeListeners.add(listener);

	}

	// Fun��o que faz a atualiza��o da tabela

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

	// M�todo com os objetos que devem ser inicializados

	private void initializeNodes() {

		setClienteSelecionadoFormController(new ClienteSelecionadoFormController());

		servicoImpressao = new ServicoImpressao();
		service = new Servico�mpressaoService();

		comboBoxProduto.setItems(FXCollections.observableArrayList(listaProdutos()));

		Constraints.mascaraNumeroInteiro(textFieldValorUnitario);
		Constraints.mascaraCNPJ(textFieldCnpjParaCobranca);
		//Constraints.mascaraDinheiro(textFieldValorUnitario);

	}

	// Carregar campos

	public void carregarCampos(Cliente cliente, ServicoImpressao servicoImpressao, Usuario usuario) {

		if (servicoImpressao != null) {
			
			System.out.println(new Produto().nomeProduto(servicoImpressao.getProdutoDoServico()));
			
			labelTituloTela.setText(Strings.getTitleServicos());
			labelNomeFantasia.setText(cliente.getNomeFantasia());
			textFieldNomeDoServico.setText(servicoImpressao.getNomeDoServico());
			textFieldCnpjParaCobranca.setText(servicoImpressao.getConta().getCnpj());
			comboBoxProduto.getSelectionModel()
					.select(new Produto().nomeProduto(servicoImpressao.getProdutoDoServico()));
			//textFieldValorUnitario.setText(String.valueOf(servicoImpressao.getValorUnitario()));
			textFieldValorUnitario.setText(Constraints.dinheiro(servicoImpressao.getValorUnitario()));
			textFieldLimiteMinimo.setText(Constraints.tresDigitos(servicoImpressao.getLimiteMinimo()));
			textAreaObservacoesDoServico.setText(String.valueOf(servicoImpressao.getObservacoesServico()));

		} else {

			labelTituloTela.setText(Strings.getTitleServicos());
			labelNomeFantasia.setText(cliente.getNomeFantasia());

		}

		setCliente(cliente);
		setServicoImpressao(servicoImpressao);
		setCompararServicoImpressao(servicoImpressao);
		setUsuario(usuario);

	}

	// Tratamento dos campos

	private ServicoImpressao getFormData() {

		ServicoImpressao servicoImpressao = new ServicoImpressao();
		Conta conta = new Conta();

		if (textFieldNomeDoServico.getText() == null || textFieldNomeDoServico.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Digite o nome do servi�o",
					AlertType.INFORMATION);

			textFieldNomeDoServico.requestFocus();

			servicoImpressao = null;

		} else if (textFieldCnpjParaCobranca.getText() == null
				|| textFieldCnpjParaCobranca.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Digite o CNPJ do servi�o",
					AlertType.INFORMATION);

			textFieldCnpjParaCobranca.requestFocus();

			servicoImpressao = null;

		} else if (comboBoxProduto.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Selecione o produto", AlertType.INFORMATION);

			comboBoxProduto.requestFocus();

			servicoImpressao = null;

		} else if (textFieldValorUnitario.getText() == null || textFieldValorUnitario.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Digite o valor unit�rio",
					AlertType.INFORMATION);

			textFieldValorUnitario.requestFocus();

			servicoImpressao = null;

		} else if (textFieldLimiteMinimo.getText() == null || textFieldLimiteMinimo.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Digite o limite m�nimo",
					AlertType.INFORMATION);

			textFieldLimiteMinimo.requestFocus();

			servicoImpressao = null;

		} else if (textAreaObservacoesDoServico.getText() == null
				|| textAreaObservacoesDoServico.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Digite as observa��es do servi�o",
					AlertType.INFORMATION);

			textAreaObservacoesDoServico.requestFocus();

			servicoImpressao = null;

		} else {

			if (getServicoImpressao() != null) {

				servicoImpressao.setIdServicoImpressao(getServicoImpressao().getIdServicoImpressao());
				servicoImpressao.setIdCliente(getCliente().getIdCliente());
				servicoImpressao.setNomeDoServico(textFieldNomeDoServico.getText());
				conta.setCnpj(textFieldCnpjParaCobranca.getText());
				servicoImpressao.setProdutoDoServico(
						new Produto().codProduto(comboBoxProduto.getSelectionModel().getSelectedItem()));
				servicoImpressao.setValorUnitario(Double.valueOf(textFieldValorUnitario.getText().replace("R$", "")));
				servicoImpressao.setLimiteMinimo(Integer.valueOf(textFieldLimiteMinimo.getText()));
				servicoImpressao.setObservacoesServico(textAreaObservacoesDoServico.getText());
				conta.setSaldo(getServicoImpressao().getConta().getSaldo());
				
				servicoImpressao.setConta(conta);

			} else {

				servicoImpressao.setIdCliente(getCliente().getIdCliente());
				servicoImpressao.setNomeDoServico(textFieldNomeDoServico.getText());
				conta.setCnpj(textFieldCnpjParaCobranca.getText());
				servicoImpressao.setProdutoDoServico(
						new Produto().codProduto(comboBoxProduto.getSelectionModel().getSelectedItem()));
				servicoImpressao.setValorUnitario(Double.valueOf(textFieldValorUnitario.getText()));
				servicoImpressao.setLimiteMinimo(Integer.valueOf(textFieldLimiteMinimo.getText()));
				servicoImpressao.setObservacoesServico(textAreaObservacoesDoServico.getText());
				conta.setSaldo(0);
				
				servicoImpressao.setConta(conta);

			}

		}

		return servicoImpressao;
	}

	// Getters and Setters

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public ServicoImpressao getCompararServicoImpressao() {
		return compararServicoImpressao;
	}

	public void setCompararServicoImpressao(ServicoImpressao compararServicoImpressao) {
		this.compararServicoImpressao = compararServicoImpressao;
	}

	public static Scene getServicoFormScene() {
		return servicoFormScene;
	}

	public static void setServicoFormScene(Scene servicoFormScene) {
		ServicoFormController.servicoFormScene = servicoFormScene;
	}

	public void setService(ServicoFormController servicoFormController) {

	}

	public ClienteSelecionadoFormController getClienteSelecionadoFormController() {
		return clienteSelecionadoFormController;
	}

	public void setClienteSelecionadoFormController(ClienteSelecionadoFormController clienteSelecionadoFormController) {
		this.clienteSelecionadoFormController = clienteSelecionadoFormController;
	}

}
