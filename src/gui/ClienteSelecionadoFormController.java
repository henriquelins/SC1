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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.entities.Produto;
import model.entities.ServicoImpressao;
import model.entities.Unidade;
import model.entities.Usuario;
import model.entities.Vendedor;
import model.services.ClienteService;
import model.services.Servico�mpressaoService;

public class ClienteSelecionadoFormController implements Initializable, DataChangeListener {

	// Java vari�veis

	// Lista de ouvintes para receber alguma modifica��o

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	private static int idClienteSelecionado;

	private Usuario usuario;

	private Cliente cliente;

	private Servico�mpressaoService servico�mpressaoService;

	private ClienteService clienteService;

	private ServicoImpressao servicoImpressao;

	private PrincipalFormController principalFormController;

	private static int id_cliente_servico;

	// @FXML vari�veis

	@FXML
	private Label labelTituloCliente;

	@FXML
	private Label labelNomeFantasia;

	@FXML
	private Label labelRazaoSocial;

	@FXML
	private Label labelCnpj;

	@FXML
	private Label labelInscricaoEstadual;

	@FXML
	private Label labelInscricaoMuncipal;

	@FXML
	private Label labelClienteDesde;

	@FXML
	private Label labelContato;

	@FXML
	private Label labelEmail;

	@FXML
	private Label labelFoneCelular;

	@FXML
	private Label labelFoneFixo;

	@FXML
	private Label labelVendedor;

	@FXML
	private Label labelUnidadeDeAtendimento;

	@FXML
	private Label labelLogradouro;

	@FXML
	private Label labelBairro;

	@FXML
	private Label labelCidade;

	@FXML
	private Label labelCep;

	@FXML
	private Label labelUf;

	@FXML
	private Label labelEntrega;

	@FXML
	private TextArea textAreaDetalhesDoCliente;

	@FXML
	private ComboBox<String> comboBoxServico;

	@FXML
	private TextField textFieldServico;

	@FXML
	private TextField textFieldProdutoDoServico;

	@FXML
	private TextField textFieldSaldo;

	@FXML
	private TextField textFieldValorUnitario;

	@FXML
	private TextField textFieldLimiteMinimo;

	@FXML
	private TextField textFieldCnpj;

	@FXML
	private TextArea textAreaDetalhesDoServico;

	@FXML
	private Button buttonNovoServico;

	@FXML
	private Button buttonEditarServico;

	@FXML
	private Button buttonFazerLancamento;

	@FXML
	private Button buttonVerLancamentos;

	@FXML
	private Button buttonEditarCliente;

	// @FXML event

	// Evento bot�o novo servi�o

	@FXML
	public void onButtonNovoServicoAction(ActionEvent event) {

		servicoForm(usuario, cliente, null, Strings.getServicoView());

	}

	// Evento bot�o editar servi�o

	@FXML
	public void onButtonEditarServicoAction(ActionEvent event) {

		if (listaServicos().isEmpty() == true) {

			Alerts.showAlert("Servi�os do Cliente", "Servi�os de impress�o", "Selecione o servi�o de impress�o",
					AlertType.ERROR);

		} else {

			try {

				servicoForm(usuario, cliente, servicoImpressao, Strings.getServicoView());

			} catch (java.lang.NullPointerException e) {

				Alerts.showAlert("Servi�os do Cliente", "Servi�os de impress�o", "Selecione um servi�o",
						AlertType.ERROR);
			}

		}

	}

	// Evento bot�o fazer lan�amento

	@FXML
	public void onButtonFazerLancamentoAction(ActionEvent event) {

		if (listaServicos().isEmpty() == true) {

			Alerts.showAlert("Servi�os do Cliente", "Servi�os de impress�o", "Selecione o servi�o de impress�o",
					AlertType.ERROR);

		} else {

			try {

				lancamentoForm(usuario, cliente, servicoImpressao, Strings.getLancamentoView());

			} catch (java.lang.NullPointerException e) {

				Alerts.showAlert("Servi�os do Cliente", "Servi�os de impress�o", "Selecione um servi�o",
						AlertType.ERROR);
			}

		}

	}

	// Evento bot�o ver lan�amento

	@FXML
	public void onButtonVerLancamentoAction(ActionEvent event) {

		if (listaServicos().isEmpty() == true) {

			Alerts.showAlert("Servi�os do Cliente", "Servi�os de impress�o", "Selecione o servi�o de impress�o",
					AlertType.ERROR);

		} else {

			try {

				new Forms().lancamentoListForm(usuario, cliente, servicoImpressao, Strings.getLancamentoListView());

			} catch (java.lang.NullPointerException e) {

				Alerts.showAlert("Servi�os do Cliente", "Servi�os de impress�o", "Selecione um servi�o",
						AlertType.ERROR);
			}

		}

	}

	// Evento bot�o editar cliente servi�o

	@FXML
	public void onButtoEditarClienteServicoAction(ActionEvent event) {

		clienteForm(usuario, cliente, Strings.getClienteView());

	}

	// Evento selecionar o servi�o do comboBox

	@FXML
	public void onComboBoxServicoAction(ActionEvent event) {

		try {

			if (listaServicos().isEmpty() == true) {

				Alerts.showAlert("Servi�os do Cliente", "Servi�os de impress�o", "Selecione o servi�o de impress�o",
						AlertType.ERROR);

			} else {

				id_cliente_servico = servico_para_id_cliente_servico(
						comboBoxServico.getSelectionModel().getSelectedItem());
				setServicoImpressao(servico�mpressaoService.buscarPeloIdCliente(id_cliente_servico));
				carregarCamposClienteServico();

			}

		} catch (java.lang.NullPointerException e) {

		}

	}

	// Lista de servi�os

	private List<String> listaServicos() {

		Servico�mpressaoService impressaoService = new Servico�mpressaoService();
		List<String> listaServicoImpressao = new ArrayList<>();

		for (ServicoImpressao cs : impressaoService.buscarServicosDoCliente(idClienteSelecionado)) {

			listaServicoImpressao.add(cs.getIdServicoImpressao() + " - " + cs.getNomeDoServico());

		}

		System.out.println(listaServicoImpressao.toString());

		return listaServicoImpressao;

	}

	// M�todo para atualizar comboBox servi�os

	public void updateDadosServicos() {

		principalFormController.updateTableView();

		setServicoImpressao(servico�mpressaoService.buscarPeloIdCliente(id_cliente_servico));

		carregarCamposClienteServico();

	}

	public void atualizarSalvoEditado() {

		if (comboBoxServico.getSelectionModel().getSelectedItem() == null) {

			comboBoxServico.setItems(FXCollections.observableArrayList(listaServicos()));
			comboBoxServico.getSelectionModel().selectLast();

		} else {

			comboBoxServico.setItems(FXCollections.observableArrayList(listaServicos()));
		}

		textFieldServico.setText(servicoImpressao.getNomeDoServico());
		textFieldProdutoDoServico.setText(new Produto().apenasNomeProduto(servicoImpressao.getProdutoDoServico()));
		textFieldSaldo.setText(Constraints.tresDigitos(servicoImpressao.getConta().getSaldo()) + " Unidades");
		textFieldValorUnitario.setText(Constraints.dinheiro(servicoImpressao.getValorUnitario()));
		textFieldLimiteMinimo.setText(Constraints.tresDigitos(servicoImpressao.getLimiteMinimo()) + " Unidades");
		textFieldCnpj.setText(servicoImpressao.getConta().getCnpj());
		textAreaDetalhesDoServico.setText(servicoImpressao.getObservacoesServico());

	}

	public void AtualizarCliente() {

		Cliente clienteAtualizado = new Cliente();
		clienteAtualizado = clienteService.buscarPeloId(idClienteSelecionado);

		System.out.println(idClienteSelecionado);

		System.out.println("1 " + clienteAtualizado.toString());

		labelTituloCliente.setText("Cliente selecionado:  " + clienteAtualizado.getNomeFantasia());
		labelNomeFantasia.setText(clienteAtualizado.getNomeFantasia());
		labelRazaoSocial.setText(clienteAtualizado.getRazaoSocial());
		labelCnpj.setText(clienteAtualizado.getCnpjCliente());
		labelInscricaoEstadual.setText(clienteAtualizado.getInscricaoEstadual());
		labelInscricaoMuncipal.setText(clienteAtualizado.getInscricaoMunicipal());
		labelClienteDesde.setText(Constraints.setDateFormat(clienteAtualizado.getDataInicioCliente()));
		labelContato.setText(clienteAtualizado.getContato().getContatoCliente());
		labelEmail.setText(clienteAtualizado.getContato().getEmailCliente());
		labelFoneCelular.setText(clienteAtualizado.getContato().getFoneCelular());
		labelFoneFixo.setText(clienteAtualizado.getContato().getFoneFixo());
		labelVendedor.setText(new Vendedor().nomeVendedor((clienteAtualizado.getCod_vendedor())));
		labelUnidadeDeAtendimento.setText(new Unidade().nomeUnidade(clienteAtualizado.getUnidadeDeAtendimento()));
		labelLogradouro.setText(clienteAtualizado.getEndereco().getLogradouro());
		labelBairro.setText(clienteAtualizado.getEndereco().getBairro());
		labelCidade.setText(clienteAtualizado.getEndereco().getCidade());
		labelCep.setText(clienteAtualizado.getEndereco().getCep());
		labelUf.setText(clienteAtualizado.getEndereco().getUf());
		labelEntrega.setText(String.valueOf(Constraints.setBooleanEntrega(clienteAtualizado.isEntregaNoCliente())));
		textAreaDetalhesDoCliente.setText(clienteAtualizado.getDetalhesDoCliente());

		setCliente(clienteAtualizado);

	}

	// Adiciona a lista um ouvinte, quando h� uma modifica��o

	public void subscribeDataChangeListener(DataChangeListener listener) {

		dataChangeListeners.add(listener);

	}

	// Listener

	@Override
	public void onDataChanged() {

		try {

			atualizarSalvoEditado();
			AtualizarCliente();

		} catch (java.lang.NullPointerException e) {

			AtualizarCliente();
		}

	}

	// Inicia classe

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();

	}

	// M�todo com os objetos que devem ser inicializados

	private void initializeNodes() {

		// servicoFormController = new ServicoFormController ();

		setClienteService(new ClienteService());

		servico�mpressaoService = new Servico�mpressaoService();

		principalFormController = new PrincipalFormController();

	}

	// Carregar campos

	public void carregarCampos(Cliente cliente, Usuario usuario) {

		try {

			labelTituloCliente.setText("Cliente selecionado:  " + cliente.getNomeFantasia());
			labelNomeFantasia.setText(cliente.getNomeFantasia());
			labelRazaoSocial.setText(cliente.getRazaoSocial());
			labelCnpj.setText(cliente.getCnpjCliente());
			labelInscricaoEstadual.setText(cliente.getInscricaoEstadual());
			labelInscricaoMuncipal.setText(cliente.getInscricaoMunicipal());
			labelClienteDesde.setText(Constraints.setDateFormat(cliente.getDataInicioCliente()));
			labelContato.setText(cliente.getContato().getContatoCliente());
			labelEmail.setText(cliente.getContato().getEmailCliente());
			labelFoneCelular.setText(cliente.getContato().getFoneCelular());
			labelFoneFixo.setText(cliente.getContato().getFoneFixo());
			labelVendedor.setText(new Vendedor().nomeVendedor(cliente.getCod_vendedor()));
			labelUnidadeDeAtendimento.setText(new Unidade().nomeUnidade(cliente.getUnidadeDeAtendimento()));
			labelLogradouro.setText(cliente.getEndereco().getLogradouro());
			labelBairro.setText(cliente.getEndereco().getBairro());
			labelCidade.setText(cliente.getEndereco().getCidade());
			labelCep.setText(cliente.getEndereco().getCep());
			labelUf.setText(cliente.getEndereco().getUf());
			labelEntrega.setText(String.valueOf(Constraints.setBooleanEntrega(cliente.isEntregaNoCliente())));
			textAreaDetalhesDoCliente.setText(cliente.getDetalhesDoCliente());

			setCliente(cliente);
			setUsuario(usuario);

			setIdClienteSelecionado(cliente.getIdCliente());

			System.out.println(idClienteSelecionado);

			System.out.println("3 " + getCliente());

			comboBoxServico.setItems(FXCollections.observableArrayList(listaServicos()));

			// comboBoxServico.setItems(FXCollections.observableArrayList(listaServicos2()));

		} catch (java.lang.NullPointerException e) {

		}

	}

	public void atualizarCampos() {

		ClienteService clienteService = new ClienteService();
		setCliente(clienteService.buscarPeloId(idClienteSelecionado));

		labelTituloCliente.setText("Cliente selecionado:  " + cliente.getNomeFantasia());
		labelNomeFantasia.setText(cliente.getNomeFantasia());
		labelRazaoSocial.setText(cliente.getRazaoSocial());
		labelCnpj.setText(cliente.getCnpjCliente());
		labelInscricaoEstadual.setText(cliente.getInscricaoEstadual());
		labelInscricaoMuncipal.setText(cliente.getInscricaoMunicipal());
		labelClienteDesde.setText(Constraints.setDateFormat(cliente.getDataInicioCliente()));
		labelContato.setText(cliente.getContato().getContatoCliente());
		labelEmail.setText(cliente.getContato().getEmailCliente());
		labelFoneCelular.setText(cliente.getContato().getFoneCelular());
		labelFoneFixo.setText(cliente.getContato().getFoneFixo());
		labelVendedor.setText(new Vendedor().nomeVendedor((cliente.getCod_vendedor())));
		labelUnidadeDeAtendimento.setText(new Unidade().nomeUnidade(cliente.getUnidadeDeAtendimento()));
		labelLogradouro.setText(cliente.getEndereco().getLogradouro());
		labelBairro.setText(cliente.getEndereco().getBairro());
		labelCidade.setText(cliente.getEndereco().getCidade());
		labelCep.setText(cliente.getEndereco().getCep());
		labelUf.setText(cliente.getEndereco().getUf());
		labelEntrega.setText(String.valueOf(Constraints.setBooleanEntrega(cliente.isEntregaNoCliente())));
		textAreaDetalhesDoCliente.setText(cliente.getDetalhesDoCliente());

	}

	// Converter String para inteiro

	public int servico_para_id_cliente_servico(String servico) {

		int index = servico.indexOf("-");
		String id_cliente_servico = servico.substring(0, index - 1);

		return Integer.valueOf(id_cliente_servico);

	}

	// Carregar os campos ServicoImpressao

	public void carregarCamposClienteServico() {

		textFieldServico.setText(servicoImpressao.getNomeDoServico());
		textFieldProdutoDoServico.setText(new Produto().apenasNomeProduto(servicoImpressao.getProdutoDoServico()));
		textFieldSaldo.setText(Constraints.tresDigitos(servicoImpressao.getConta().getSaldo()) + " Unidades");
		textFieldValorUnitario.setText(Constraints.dinheiro(servicoImpressao.getValorUnitario()));
		textFieldLimiteMinimo.setText(Constraints.tresDigitos(servicoImpressao.getLimiteMinimo()) + " Unidades");
		textFieldCnpj.setText(servicoImpressao.getConta().getCnpj());
		textAreaDetalhesDoServico.setText(servicoImpressao.getObservacoesServico());

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

			Alerts.showAlert("Acesso negado", "Acesso n�o concedido ao usu�rio logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	public void servicoForm(Usuario usuario, Cliente cliente, ServicoImpressao clienteServico, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				GridPane pane = loader.load();

				ServicoFormController controller = loader.getController();
				controller.setService(new ServicoFormController());
				controller.subscribeDataChangeListener(this);
				controller.carregarCampos(cliente, clienteServico, usuario);

				ServicoFormController.setServicoFormScene(new Scene(pane));

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(ServicoFormController.getServicoFormScene());
				primaryStage.setResizable(false);
				primaryStage.initModality(Modality.APPLICATION_MODAL);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				primaryStage.getIcons().add(applicationIcon);

				primaryStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", Strings.erroCarregarTela(), e.getMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso n�o concedido ao usu�rio logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	// forms tela lan�amento

	public void lancamentoForm(Usuario usuario, Cliente cliente, ServicoImpressao servicoImpressao, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				GridPane pane = loader.load();

				LancamentoFormController controller = loader.getController();
				controller.setService(new LancamentoFormController());
				controller.subscribeDataChangeListener(this);
				controller.carregarCampos(cliente, servicoImpressao, usuario);

				LancamentoFormController.setLancamentoFormScene(new Scene(pane));

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(LancamentoFormController.getLancamentoFormScene());
				primaryStage.setResizable(false);
				primaryStage.initModality(Modality.APPLICATION_MODAL);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				primaryStage.getIcons().add(applicationIcon);

				primaryStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", Strings.erroCarregarTela(), e.getMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso n�o concedido ao usu�rio logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

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

	public ClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public static int getIdClienteSelecionado() {
		return idClienteSelecionado;
	}

	public static void setIdClienteSelecionado(int idClienteSelecionado) {
		ClienteSelecionadoFormController.idClienteSelecionado = idClienteSelecionado;
	}

	public void subscribeDataChangeListener(PrincipalFormController principalFormController2) {

	}

}
