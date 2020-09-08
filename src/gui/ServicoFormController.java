package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Strings;
import gui.util.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import model.entities.Cliente;
import model.entities.Conta;
import model.entities.Produto;
import model.entities.ServicoImpressao;
import model.entities.Usuario;
import model.services.ClienteService;
import model.services.ContaService;
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

	private Conta conta;

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

	@FXML
	private RadioButton radiobuttonFaturamento;

	@FXML
	private RadioButton radiobuttonSaldo;

	private ToggleGroup groupLancamento;

	private boolean tipoLancamento = true;

	@FXML
	private Label limite;

	// @FXML event

	@FXML
	public void onRadioAction(ActionEvent event) {

		if (radiobuttonFaturamento.isSelected()) {

			limite.setText("Limite M�ximo");

		} else if (radiobuttonSaldo.isSelected()) {

			limite.setText("Limite M�nimo");

		}
	}

	// Evento bot�o salvar ou editar servi�o

	@FXML
	public void onButtonSalvarServicoAction(ActionEvent event) {

		setServicoImpressao(getFormData());
	
		boolean ok = compararCampos();
	
		if (ok == false) {

			if (getServicoImpressao().getIdServicoImpressao() == null) {

				novoServicoImpresao();

			} else {

				editarServicoImpressao();

			}

		} else {

			Alerts.showAlert("Cadastro de servi�os", "Editar servi�os", "N�o houve altera��o no registro",
					AlertType.INFORMATION);

		}

	}

	private void editarServicoImpressao() {

		Optional<ButtonType> result = Alerts.showConfirmation("Cadastro de servi�os",

				"Deseja editar o servi�o de impress�o " + getServicoImpressao().getNomeDoServico() + "?");

		if (result.get() == ButtonType.OK) {

			service.editarServico(servicoImpressao);
			notifyDataChangeListeners();
			Utils.fecharTelaServicoFormAction();

		} else {

			Utils.fecharTelaServicoFormAction();

		}

	}

	private void novoServicoImpresao() {

		Cliente clienteCnpjPesquisado = new ClienteService().buscarPeloCnpj(getConta().getCnpj());
	
		 if ((getCliente().getIdCliente().equals(clienteCnpjPesquisado.getIdCliente())) || (clienteCnpjPesquisado.getIdCliente() == null)) {

			Conta contaPesquisado = new ContaService().buscarContaCnpj(getCliente().getCnpjCliente());
		
			if (contaPesquisado.getCnpj() != clienteCnpjPesquisado.getCnpjCliente()) {

				contaPesquisado = new ContaService().buscarContaCnpj(getConta().getCnpj());
			

				if (contaPesquisado.getIdConta() == null) {

					int idConta = new ContaService().inserirConta(getConta());
					getServicoImpressao().setIdConta(idConta);
				
				} else {

					getServicoImpressao().setIdConta(getConta().getIdConta());
				
				}

				if (!getConta().getCnpj().equals(contaPesquisado.getCnpj())) {

					Optional<ButtonType> result = Alerts.showConfirmation("Cadastro de servi�os",
							"Voc� deseja salvar o novo servi�o de impress�o "
									+ getServicoImpressao().getNomeDoServico().toUpperCase() + " no CNPJ "
									+ getConta().getCnpj() + " ?");

					if (result.get() == ButtonType.OK) {

						service.novoServico(getServicoImpressao());
						notifyDataChangeListeners();
						Utils.fecharTelaServicoFormAction();

					} else {

						Utils.fecharTelaServicoFormAction();

					}

				} else {

					if (getConta().isTipo() == contaPesquisado.isTipo()) {

						Optional<ButtonType> result = Alerts.showConfirmation("Cadastro de servi�os", "O CNPJ "
								+ conta.getCnpj() + " j� foi cadastrado para outro(s) servico(s) do mesmo cliente"
								+ ", ao compartilhar um CNPJ com outro servi�o de impress�o , o saldo e os demais"
								+ " dados tamb�m ser�o compartlhados. Desejar compartilhar o mesmo CNPJ com o"
								+ " outro(s) servi�o(s) de impress�o" + "?");

						if (result.get() == ButtonType.OK) {

							ServicoImpressao servicoImpressaoPesquisado = new Servico�mpressaoService()
									.buscarServicoImpressaoIdConta(contaPesquisado.getIdConta());

							getServicoImpressao().setIdConta(contaPesquisado.getIdConta());
							getServicoImpressao().setValorUnitario(servicoImpressaoPesquisado.getValorUnitario());
							getServicoImpressao().setLimiteMinimo(servicoImpressaoPesquisado.getLimiteMinimo());
							getServicoImpressao().setIdProdutoDoServico(servicoImpressaoPesquisado.getIdProdutoDoServico());
							
							service.novoServico(getServicoImpressao());
							notifyDataChangeListeners();
							Utils.fecharTelaServicoFormAction();

						} else {

							textFieldCnpjParaCobranca.setText("");
							textFieldCnpjParaCobranca.requestFocus();

						}

					} else {

						Alerts.showAlert("Servi�o de Impress�o", "Tipo de conta diferentes",
								"Para compartilhar o CNPJ entre servi�os de impress�o ambos devem ter o mesmo tipo de conta. Verifique o tipo de conta!",
								AlertType.ERROR);
					}

				}

			} else {

				Alerts.showAlert("Servi�o de Impress�o", "CNPJ de outro cliente",
						"O CNPJ do servi�o est� cadastrado para o cliente "
								+ clienteCnpjPesquisado.getNomeFantasia().toUpperCase()
								+ ", verifique o n�mero do CNPJ.",
						AlertType.ERROR);

				textFieldCnpjParaCobranca.setText("");
				textFieldCnpjParaCobranca.requestFocus();
			}

		} else {

			Alerts.showAlert(
					"Servi�o de Impress�o", "CNPJ de outro cliente", "O CNPJ est� cadastrado para o cliente "
							+ clienteCnpjPesquisado.getNomeFantasia().toUpperCase() + ", verifique o n�mero do CNPJ.",
					AlertType.ERROR);

			textFieldCnpjParaCobranca.setText("");
			textFieldCnpjParaCobranca.requestFocus();
		}

	}

	// Comparar todos os campos

	private boolean compararCampos() {

		boolean ok = false;

		if (getServicoImpressao() == null || compararServicoImpressao == null ) {

			ok = false;

		} else if (servicoImpressao.equals(compararServicoImpressao)) {

			ok = true;
			
		} 
		
		return ok;

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

		Constraints.mascaraNumero(textFieldLimiteMinimo);
		Constraints.mascaraCNPJ(textFieldCnpjParaCobranca);
		Constraints.monetaryField(textFieldValorUnitario);

		groupLancamento = new ToggleGroup();
		radiobuttonFaturamento.setToggleGroup(groupLancamento);
		radiobuttonSaldo.setToggleGroup(groupLancamento);

		groupLancamento.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

				// Quando selecionar.
				if (groupLancamento.getSelectedToggle() != null) {

					tipoLancamento = groupLancamento.getSelectedToggle().equals(radiobuttonSaldo);

				}
			}
		});

	}

	// Carregar campos

	public void carregarCampos(Cliente cliente, ServicoImpressao servicoImpressao, Usuario usuario, boolean editar) {		
		
		if (editar == true) {

			radiobuttonFaturamento.setDisable(true);
			radiobuttonSaldo.setDisable(true);

			textFieldCnpjParaCobranca.setDisable(true);

		} else {

			radiobuttonFaturamento.setDisable(false);
			radiobuttonSaldo.setDisable(false);

			textFieldCnpjParaCobranca.setDisable(false);

		}

		if (servicoImpressao != null) {
			
			Conta conta = new ContaService().buscarContaId(servicoImpressao.getIdConta());
			
			setConta(conta);

			labelTituloTela.setText(Strings.getTitleServicos());
			labelNomeFantasia.setText(cliente.getNomeFantasia().toUpperCase());
			textFieldNomeDoServico.setText(servicoImpressao.getNomeDoServico().toUpperCase());
			textFieldCnpjParaCobranca.setText(conta.getCnpj());
			comboBoxProduto.getSelectionModel()
					.select(new Produto().nomeProduto(servicoImpressao.getIdProdutoDoServico()));
			textFieldValorUnitario.setText(Constraints.dinheiro(servicoImpressao.getValorUnitario()));
			textFieldLimiteMinimo.setText(Constraints.tresDigitos(servicoImpressao.getLimiteMinimo()));
			textAreaObservacoesDoServico
					.setText(String.valueOf(servicoImpressao.getObservacoesServico()).toUpperCase());

			if (conta.isTipo() == true) {

				radiobuttonSaldo.setSelected(true);
				limite.setText("Limite M�nimo");

			} else {

				radiobuttonFaturamento.setSelected(true);
				limite.setText("Limite M�ximo");
			}

		} else {

			labelTituloTela.setText(Strings.getTitleServicos());
			labelNomeFantasia.setText(cliente.getNomeFantasia().toUpperCase());
			textFieldCnpjParaCobranca.setText(cliente.getCnpjCliente());
			limite.setText("Limite M�nimo");

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

		} else if (comboBoxProduto.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Selecione o produto", AlertType.INFORMATION);

			comboBoxProduto.requestFocus();

			servicoImpressao = null;

		} else if (textAreaObservacoesDoServico.getText() == null
				|| textAreaObservacoesDoServico.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de servi�os", "Campo obrigat�rio", "Digite as observa��es do servi�o",
					AlertType.INFORMATION);

			textAreaObservacoesDoServico.requestFocus();

			servicoImpressao = null;

		} else {

			if (textFieldCnpjParaCobranca.getText() != null || !textFieldCnpjParaCobranca.getText().trim().equals("")) {

				//if (getServicoImpressao() != null && getConta().equals(null)) {
					
				if (getServicoImpressao() != null) {

					servicoImpressao.setIdServicoImpressao(getServicoImpressao().getIdServicoImpressao());
					servicoImpressao.setIdCliente(getCliente().getIdCliente());
					servicoImpressao.setIdConta(getConta().getIdConta());
					servicoImpressao.setNomeDoServico(textFieldNomeDoServico.getText().toUpperCase());
					servicoImpressao.setIdProdutoDoServico(
							new Produto().codProduto(comboBoxProduto.getSelectionModel().getSelectedItem()));
					servicoImpressao
							.setValorUnitario(Double.valueOf(textFieldValorUnitario.getText().replace(",", ".")));
					servicoImpressao.setLimiteMinimo(Integer.valueOf(textFieldLimiteMinimo.getText()));
					servicoImpressao.setObservacoesServico(textAreaObservacoesDoServico.getText().toUpperCase());
					
					conta.setCnpj(textFieldCnpjParaCobranca.getText());
					conta.setSaldo(getConta().getSaldo());
					conta.setTipo(tipoLancamento);

					setConta(conta);

				} else {

					servicoImpressao.setIdCliente(getCliente().getIdCliente());
					servicoImpressao.setNomeDoServico(textFieldNomeDoServico.getText().toUpperCase());
					servicoImpressao.setIdProdutoDoServico(
							new Produto().codProduto(comboBoxProduto.getSelectionModel().getSelectedItem()));
					servicoImpressao
							.setValorUnitario(Double.valueOf(textFieldValorUnitario.getText().replace(",", ".")));
					servicoImpressao.setLimiteMinimo(Integer.valueOf(textFieldLimiteMinimo.getText()));
					servicoImpressao.setObservacoesServico(textAreaObservacoesDoServico.getText().toUpperCase());
					
					conta.setCnpj(textFieldCnpjParaCobranca.getText());
					conta.setSaldo(0);
					conta.setTipo(tipoLancamento);

					setConta(conta);

				}

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

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public ServicoImpressao finalizar(ServicoImpressao servicoImpressao, Conta conta) {

		if (getServicoImpressao() != null) {

			servicoImpressao.setIdServicoImpressao(getServicoImpressao().getIdServicoImpressao());
			servicoImpressao.setIdCliente(getCliente().getIdCliente());
			servicoImpressao.setNomeDoServico(textFieldNomeDoServico.getText().toUpperCase());
			conta.setCnpj(textFieldCnpjParaCobranca.getText());
			servicoImpressao.setIdProdutoDoServico(
					new Produto().codProduto(comboBoxProduto.getSelectionModel().getSelectedItem()));
			servicoImpressao.setValorUnitario(Double.valueOf(textFieldValorUnitario.getText().replace("R$", "")));
			servicoImpressao.setLimiteMinimo(Integer.valueOf(textFieldLimiteMinimo.getText()));
			servicoImpressao.setObservacoesServico(textAreaObservacoesDoServico.getText().toUpperCase());
			conta.setSaldo(getConta().getSaldo());
			conta.setTipo(tipoLancamento);

			servicoImpressao.setIdConta(conta.getIdConta());

		} else {

			servicoImpressao.setIdCliente(getCliente().getIdCliente());
			servicoImpressao.setNomeDoServico(textFieldNomeDoServico.getText().toUpperCase());
			conta.setCnpj(textFieldCnpjParaCobranca.getText());
			servicoImpressao.setIdProdutoDoServico(
					new Produto().codProduto(comboBoxProduto.getSelectionModel().getSelectedItem()));
			servicoImpressao.setValorUnitario(Double.valueOf(textFieldValorUnitario.getText()));
			servicoImpressao.setLimiteMinimo(Integer.valueOf(textFieldLimiteMinimo.getText()));
			servicoImpressao.setObservacoesServico(textAreaObservacoesDoServico.getText().toUpperCase());
			conta.setSaldo(0);
			conta.setTipo(tipoLancamento);

			servicoImpressao.setIdConta(conta.getIdConta());

		}

		return servicoImpressao;

	}

}
