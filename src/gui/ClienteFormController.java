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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Cliente;
import model.entities.Contato;
import model.entities.Endereco;
import model.entities.Unidade;
import model.entities.Usuario;
import model.entities.Vendedor;
import model.services.ClienteService;
import model.services.LogSegurancaService;
import model.services.UnidadeService;
import model.services.VendedorService;

public class ClienteFormController implements Initializable, DataChangeListener {

	// Java variáveis

	private int idClienteSelecionado;

	private static Scene clienteFormScene;

	private Cliente cliente;

	private Cliente clienteComparar;

	private ClienteService service;

	private List<Vendedor> listaVendedores = new ArrayList<>();

	private List<Unidade> listaUnidade = new ArrayList<>();

	// Lista de ouvintes para receber alguma modificação

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	// @FXML variáveis

	@FXML
	private Label lableTitulo;

	@FXML
	private TextField textFieldNomeFantasia;

	@FXML
	private TextField textFieldRazaoSocial;

	@FXML
	private TextField textFieldCnpj;

	@FXML
	private TextField textFieldInscEstadual;

	@FXML
	private TextField textFieldInscMunicipal;

	@FXML
	private DatePicker datePickerClienteDesde;

	@FXML
	private TextField textFieldContato;

	@FXML
	private TextField textFieldEmail;

	@FXML
	private TextField textFieldFoneCelular;

	@FXML
	private TextField textFieldFoneFixo;

	@FXML
	private ComboBox<String> comboBoxVendedor;

	@FXML
	private ComboBox<String> comboBoxUnidadeAtendimento;

	@FXML
	private TextField textFieldLogradouro;

	@FXML
	private TextField textFieldBairro;

	@FXML
	private TextField textFieldCidade;

	@FXML
	private TextField textFieldCep;

	@FXML
	private ComboBox<String> comboBoxUnidadeFederal;

	@FXML
	private RadioButton radioButtonEntrega;

	@FXML
	private TextArea textAreaDetalhesDoCliente;

	@FXML
	private Button buttonSalvarCliente;

	private Usuario usuario;

	// @FXML event

	@FXML
	public void onButtonSalvarClienteAction(ActionEvent event) {

		setCliente(getFormData());
		Cliente clienteCnpj = service.buscarPeloCnpj(getCliente().getCnpjCliente());

		if (getCliente() != null) {

			boolean ok = compararCampos();

			if (ok == false) {

				if (getCliente().getIdCliente() == null) {

					if (clienteCnpj.getIdCliente() == null) {

						service.clienteNovoOuEditar(getCliente());
						Utils.fecharTelaClienteFormAction();
						notifyDataChangeListeners();
						new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
								"Cliente cadastrado: " + cliente.getNomeFantasia().toUpperCase());

					} else {

						Alerts.showAlert("Cadastro de clientes", "Novo cliente", "CNPJ já cadastrado para o cliente "
								+ clienteCnpj.getNomeFantasia().toUpperCase() + ",\n verifique o número do CNPJ.",
								AlertType.ERROR);

						textFieldCnpj.requestFocus();
					}

				} else {

					if (!getCliente().getCnpjCliente().equals(clienteCnpj.getCnpjCliente())) {

						service.clienteNovoOuEditar(getCliente());
						Utils.fecharTelaClienteFormAction();
						notifyDataChangeListeners();
						new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
								"Cliente editado: " + cliente.getNomeFantasia().toUpperCase());

					} else if (getCliente().getCnpjCliente().equals(clienteCnpj.getCnpjCliente())
							&& getCliente().getIdCliente() == clienteCnpj.getIdCliente()) {

						service.clienteNovoOuEditar(getCliente());
						Utils.fecharTelaClienteFormAction();
						notifyDataChangeListeners();
						new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
								"Cliente editado: " + cliente.getNomeFantasia().toUpperCase());

					} else {

						Alerts.showAlert("Cadastro de clientes", "Editar cliente", "CNPJ já cadastrado para o cliente "
								+ clienteCnpj.getNomeFantasia().toUpperCase() + ",\n verifique o número do CNPJ.",
								AlertType.ERROR);

						textFieldCnpj.requestFocus();

					}

				}

			} else {

				Alerts.showAlert("Cadastro de clientes", "Editar cliente", "Não houve alteração no registro",
						AlertType.INFORMATION);

			}

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

		service = new ClienteService();
		clienteComparar = new Cliente();
		cliente = new Cliente();

		lableTitulo.setText(Strings.getTitleCliente());
		comboBoxVendedor.setItems(FXCollections.observableArrayList(listaVendedores()));
		comboBoxUnidadeAtendimento.setItems(FXCollections.observableArrayList(listaUnidadesAtendimento()));
		comboBoxUnidadeFederal.setItems(FXCollections.observableArrayList(listaUnidadesFederacao()));

		Constraints.mascaraCNPJ(textFieldCnpj);
		Constraints.mascaraEmail(textFieldEmail);
		Constraints.mascaraTelefoneCelular(textFieldFoneCelular);
		Constraints.mascaraTelefoneFixo(textFieldFoneFixo);
		Constraints.mascaraCEP(textFieldCep);

	}

	// Método carregar campos

	public void carregarCampos(Cliente cliente, Usuario usuario) {

		if (cliente != null) {

			textFieldNomeFantasia.setText(cliente.getNomeFantasia().toUpperCase());
			textFieldRazaoSocial.setText(cliente.getRazaoSocial().toUpperCase());
			textFieldCnpj.setText(cliente.getCnpjCliente());
			textFieldInscEstadual.setText(cliente.getInscricaoEstadual());
			textFieldInscMunicipal.setText(cliente.getInscricaoMunicipal());
			datePickerClienteDesde.setValue(Constraints.setDateToLocalDate(cliente.getDataInicioCliente()));
			textFieldContato.setText(cliente.getContato().contatoCliente);
			textFieldEmail.setText(cliente.getContato().getEmailCliente());
			textFieldFoneCelular.setText(cliente.getContato().getFoneCelular());
			textFieldFoneFixo.setText(cliente.getContato().getFoneFixo());

			comboBoxVendedor.getSelectionModel().select(new Vendedor().nomeVendedor(cliente.getCod_vendedor()));
			comboBoxUnidadeAtendimento.getSelectionModel()
					.select(new Unidade().nomeUnidade(cliente.getUnidadeDeAtendimento()));

			textFieldLogradouro.setText(cliente.getEndereco().getLogradouro().toUpperCase());
			textFieldCidade.setText(cliente.getEndereco().getCidade().toUpperCase());
			textFieldBairro.setText(cliente.getEndereco().getBairro().toUpperCase());
			textFieldCep.setText(cliente.getEndereco().getCep());
			comboBoxUnidadeFederal.setValue(cliente.getEndereco().uf);
			radioButtonEntrega.selectedProperty().setValue(cliente.isEntregaNoCliente());
			textAreaDetalhesDoCliente.setText(cliente.getDetalhesDoCliente().toUpperCase());

			setCliente(cliente);
			clienteComparar = cliente;
			setUsuario(usuario);

		} else {

			textFieldNomeFantasia.setText("");
			textFieldRazaoSocial.setText("");
			textFieldCnpj.setText("");
			textFieldInscEstadual.setText("");
			textFieldInscMunicipal.setText("");
			datePickerClienteDesde.setValue(null);
			textFieldContato.setText("");
			textFieldEmail.setText("");
			textFieldFoneCelular.setText("");
			textFieldFoneFixo.setText("");
			comboBoxVendedor.getSelectionModel().select(null);
			comboBoxUnidadeAtendimento.getSelectionModel().select(null);
			textFieldLogradouro.setText("");
			textFieldCidade.setText("");
			textFieldBairro.setText("");
			textFieldCep.setText("");
			comboBoxUnidadeFederal.setValue(null);
			radioButtonEntrega.selectedProperty().setValue(null);
			textAreaDetalhesDoCliente.setText("");

			setCliente(null);
			clienteComparar = null;
			setUsuario(usuario);

		}

	}

	// Método para testar os textFields

	private Cliente getFormData() {

		Cliente cliente = new Cliente();
		Contato contato = new Contato();
		Endereco endereco = new Endereco();

		if (textFieldNomeFantasia.getText() == null || textFieldNomeFantasia.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite o nome do cliente",
					AlertType.INFORMATION);

			textFieldNomeFantasia.requestFocus();

			cliente = null;

		} else if (textFieldRazaoSocial.getText() == null || textFieldRazaoSocial.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite a razão social do cliente",
					AlertType.INFORMATION);

			textFieldRazaoSocial.requestFocus();

			cliente = null;

		} else if (textFieldCnpj.getText() == null || textFieldCnpj.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite o CNPJ do cliente",
					AlertType.INFORMATION);

			textFieldCnpj.requestFocus();

			cliente = null;

		} else if (datePickerClienteDesde.getValue() == null) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório",
					"Selecione a data do início do cadastro do cliente", AlertType.INFORMATION);

			datePickerClienteDesde.requestFocus();

			cliente = null;

		} else if (textFieldContato.getText() == null || textFieldContato.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite o nome do contato",
					AlertType.INFORMATION);

			textFieldContato.requestFocus();

			cliente = null;

		} else if (textFieldEmail.getText() == null || textFieldEmail.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite o e-mail do contato",
					AlertType.INFORMATION);

			textFieldEmail.requestFocus();

			cliente = null;

		} else if ((textFieldFoneCelular.getText() == null || textFieldFoneCelular.getText().trim().equals(""))
				&& (textFieldFoneFixo.getText() == null || textFieldFoneFixo.getText().trim().equals(""))) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite ao menos 01 número para contato",
					AlertType.INFORMATION);

			textFieldFoneCelular.requestFocus();

			cliente = null;

		} else if (comboBoxVendedor.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Selecione o vendedor",
					AlertType.INFORMATION);

			comboBoxVendedor.requestFocus();

			cliente = null;

		} else if (comboBoxUnidadeAtendimento.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Selecione a unidade de atendimento",
					AlertType.INFORMATION);

			comboBoxUnidadeAtendimento.requestFocus();

			cliente = null;

		} else if (textFieldLogradouro.getText() == null || textFieldLogradouro.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite o logradouro do endereço do cliente",
					AlertType.INFORMATION);

			textFieldLogradouro.requestFocus();

			cliente = null;

		} else if (textFieldBairro.getText() == null || textFieldBairro.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite o bairro do endereço do cliente",
					AlertType.INFORMATION);

			textFieldBairro.requestFocus();

			cliente = null;

		} else if (textFieldCidade.getText() == null || textFieldCidade.getText().trim().equals("")) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Digite a cidade do endereço do cliente",
					AlertType.INFORMATION);

			textFieldCidade.requestFocus();

			cliente = null;

		} else if (comboBoxUnidadeFederal.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "Selecione a unidade federal",
					AlertType.INFORMATION);

			comboBoxUnidadeFederal.requestFocus();

			cliente = null;

		} else {

			if (getCliente() != null) {

				cliente.setIdCliente(getCliente().getIdCliente());
				contato.setIdContato(getCliente().getContato().idContato);
				endereco.setIdEndereco(getCliente().getEndereco().idEndereco);

			}

			if (textFieldInscEstadual.getText().equals("")) {

				cliente.setInscricaoEstadual("ISENTO");

			} else {

				cliente.setInscricaoEstadual(textFieldInscEstadual.getText());

			}

			if (textFieldInscMunicipal.getText().equals("")) {

				cliente.setInscricaoMunicipal("ISENTO");

			} else {

				cliente.setInscricaoMunicipal(textFieldInscMunicipal.getText());
			}

			if (Constraints.isValidEmailAddressRegex(textFieldEmail.getText()) != true) {

				Alerts.showAlert("Cadastro de clientes", "Campo obrigatório", "O e-mail não é válido",
						AlertType.INFORMATION);

				textFieldEmail.setText("");
				textFieldEmail.requestFocus();

				cliente = null;

			}

			cliente.setNomeFantasia(textFieldNomeFantasia.getText().toUpperCase());
			cliente.setRazaoSocial(textFieldRazaoSocial.getText().toUpperCase());
			cliente.setCnpjCliente(textFieldCnpj.getText());
			cliente.setDataInicioCliente(Constraints.setLocalDateToDateSql(datePickerClienteDesde.getValue()));

			contato.setContatoCliente(textFieldContato.getText().toUpperCase());
			contato.setEmailCliente(textFieldEmail.getText());
			contato.setFoneCelular(textFieldFoneCelular.getText());
			contato.setFoneFixo(textFieldFoneFixo.getText());

			for (int i = 0; listaVendedores.size() > i; i++) {

				if (listaVendedores.get(i).getNomeVendedor()
						.equalsIgnoreCase(comboBoxVendedor.getSelectionModel().getSelectedItem())) {

					cliente.setCod_vendedor(listaVendedores.get(i).getIdVendedor());

				}

			}

			for (int i = 0; listaUnidade.size() > i; i++) {

				if (listaUnidade.get(i).getNomeUnidade()
						.equalsIgnoreCase(comboBoxUnidadeAtendimento.getSelectionModel().getSelectedItem())) {

					cliente.setUnidadeDeAtendimento(listaUnidade.get(i).getIdUnidade());

				}

			}

			endereco.setLogradouro(textFieldLogradouro.getText().toUpperCase());
			endereco.setBairro(textFieldBairro.getText().toUpperCase());
			endereco.setCidade(textFieldCidade.getText().toUpperCase());
			endereco.setCep(textFieldCep.getText());
			endereco.setUf(comboBoxUnidadeFederal.getSelectionModel().getSelectedItem());
			cliente.setEntregaNoCliente(radioButtonEntrega.isSelected());
			cliente.setDetalhesDoCliente(textAreaDetalhesDoCliente.getText().toUpperCase());

			cliente.setContato(contato);
			cliente.setEndereco(endereco);

		}

		return cliente;

	}

	// Listas de comboBox
	// lista vendedores

	private List<String> listaVendedores() {

		List<String> lista = new ArrayList<>();

		VendedorService vendedorService = new VendedorService();

		listaVendedores = vendedorService.buscarTodos();

		for (Vendedor v : listaVendedores) {

			lista.add(v.getNomeVendedor());

		}

		return lista;

	}

	// lista unidades

	private List<String> listaUnidadesAtendimento() {

		List<String> lista = new ArrayList<>();

		UnidadeService unidadeService = new UnidadeService();

		listaUnidade = unidadeService.buscarTodos();

		for (Unidade u : new UnidadeService().buscarTodos()) {

			lista.add(u.getNomeUnidade());

		}

		return lista;

	}

	// lista uf

	private List<String> listaUnidadesFederacao() {

		List<String> lista = new ArrayList<>();

		String[] uf = { "ACRE", "ALAGOAS", "AMAPÁ", "AMAZONAS", "BAHIA", "CEARÁ", "DISTRITO FEDERAL", "ESPÍRITO SANTO",
				"GOIÁS", "MARANHÃO", "MATO GROSSO", "MATO GROSSO DO SUL", "MINAS GERAIS", "PARÁ", "PARAÍBA", "PARANÁ",
				"PERNAMBUCO", "PIAUÍ", "RIO JANEIRO", "RIO GRANDE DO NORTE", "RIO GRANDE DO SUL", "RONDÔNIA", "RORAIMA",
				"SANTA CATARINA", "SÃO PAULO", "SERGIPE", "TOCANTINS" };

		for (String e : uf) {

			lista.add(e);

		}

		return lista;

	}

	// Comparar todos os campos

	private boolean compararCampos() {

		boolean ok = false;

		if (getCliente() == null) {

			return ok;

		} else if (getCliente().equals(clienteComparar)) {

			ok = true;
			return ok;

		} else {

			return ok;

		}

	}

	// Getters and Setters

	public Cliente getCliente() {

		return cliente;

	}

	public void setCliente(Cliente cliente) {

		this.cliente = cliente;

	}

	public ClienteService getService() {

		return service;

	}

	public void setService(ClienteService service) {

		this.service = service;

	}

	public static Scene getClienteFormScene() {

		return clienteFormScene;

	}

	public static void setClienteScene(Scene clienteFormScene) {

		ClienteFormController.clienteFormScene = clienteFormScene;

	}

	public int getIdClienteSelecionado() {
		return idClienteSelecionado;
	}

	public void setIdClienteSelecionado(int idClienteSelecionado) {
		this.idClienteSelecionado = idClienteSelecionado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
