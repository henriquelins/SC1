package gui;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import exportarXLS.ExportarListaClientesXLS;
import exportarXLS.ExportarListaSaldoClienteXLS;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jxl.write.WriteException;
import model.entities.Cliente;
import model.entities.ServicoImpressao;
import model.entities.Unidade;
import model.entities.Usuario;
import model.entities.Vendedor;
import model.services.ClienteService;
import model.services.LogSegurancaService;
import model.services.ServicoÌmpressaoService;
import model.services.VendedorService;
import relatorio.Relatorio;

public class PrincipalFormController implements Initializable, DataChangeListener {

	// Java variáveis

	private static int id_cliente;

	private String vendedor;

	private Usuario usuario;

	private Cliente cliente;

	private ClienteService service;

	private static ObservableList<Cliente> listaClientes;

	private List<String> listaSaldoCliente = new ArrayList<>();

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
	private MenuItem menuItemBackup;

	@FXML
	private MenuItem menuItemLog;

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
	private TableColumn<Cliente, String> tableColumnDetalhes;

	@FXML
	private TableColumn<Cliente, Cliente> tableColumnClienteSelecionado;

	@FXML
	private TextArea textAreaDetalhes;

	@FXML
	private TextArea textAreaSaldoCnpj;

	@FXML
	private Button buttonExportar;

	@FXML
	private Button buttonImprimir;

	@FXML
	private Button buttonExportarSaldo;

	private List<Vendedor> ListaVendedores = new ArrayList<>();

	private VendedorService vendedorService = new VendedorService();

	private StringBuffer servicos;

	private StringBuffer saldoCnpj;

	// @FXML event

	// Evento clique na linha da tabela
	@FXML
	public void onMouseClickedAction(MouseEvent event) {

		if (event.getClickCount() == 1) {

			event.consume();

			String serv = "";
			String saldo = "";

			servicos = new StringBuffer();
			saldoCnpj = new StringBuffer();

			listaSaldoCliente.clear();

			cliente = tableViewCliente.getSelectionModel().getSelectedItem();

			if (cliente != null) {

				new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
						"seleção do cliente " + cliente.getNomeFantasia().toUpperCase());

				String cnpj = "";

				saldoCnpj.append("Cliente: " + cliente.getNomeFantasia().toUpperCase() + " - Lista por CNPJ.\n");

				saldo = "Cliente: " + cliente.getNomeFantasia().toUpperCase() + " - Lista por CNPJ.\n";
				listaSaldoCliente.add(saldo);

				int index = 1;
				int index2 = 1;

				ServicoÌmpressaoService impressaoService = new ServicoÌmpressaoService();

				List<ServicoImpressao> ListaSi = new ArrayList<>();

				ListaSi = impressaoService.buscarServicosDoCliente(cliente.getIdCliente());

				if (!ListaSi.isEmpty() == true) {

					String tipo = "";

					for (ServicoImpressao si : ListaSi) {

						if (!si.getConta().getCnpj().equals(cnpj)) {

							if (si.getConta().isTipo()) {

								tipo = "SALDO";

							} else {

								tipo = "FATURADO";
							}

							saldoCnpj.append(index + " - Tipo de conta: " + tipo + " - CNPJ: " + si.getConta().getCnpj()
									+ " - total: " + String.valueOf(si.getConta().getSaldo()) + " Unidades.\n");

							saldo = index + " - Tipo de conta: " + tipo + " - CNPJ: " + si.getConta().getCnpj() + " - total: "
									+ String.valueOf(si.getConta().getSaldo()) + " Unidades.\n";

							listaSaldoCliente.add(saldo.toString());

							index++;
						}

						cnpj = si.getConta().getCnpj();

				

					}

					servicos.append("Cliente: " + cliente.getNomeFantasia().toUpperCase()
							+ " - Lista por Serviços do cliente - Mesmo CNPJ, compartilham o mesmo total.\n");

					serv = "Cliente: " + cliente.getNomeFantasia().toUpperCase()
							+ " - Lista por Serviços do cliente - Mesmo CNPJ, compartilham o mesmo total.\n";

					listaSaldoCliente.add("\n");
					listaSaldoCliente.add(serv);

				} else {

					servicos.append("Não existem serviços cadastrados para o cliente.");
					textAreaDetalhes.setText(String.valueOf(servicos).toUpperCase());
					saldoCnpj.append("Não existem serviços cadastrados para o cliente.");
					textAreaSaldoCnpj.setText(String.valueOf(saldoCnpj).toUpperCase());

					servicos = new StringBuffer();
					saldoCnpj = new StringBuffer();

					listaSaldoCliente.clear();
				}

				if (!ListaSi.isEmpty() == true) {

					String tipo = "";

					for (ServicoImpressao si : ListaSi) {

						if (si.getConta().isTipo()) {

							tipo = "SALDO";

						} else {

							tipo = "FATURADO";
						}

						servicos.append(index2 + "- Tipo: " + tipo + " - CNPJ: " + si.getConta().getCnpj()
								+ " - Serviço: " + si.getNomeDoServico().toUpperCase() + " - total: "
								+ String.valueOf(si.getConta().getSaldo()) + " Unidades.");

						serv = index2 + "- Tipo: " + tipo + " - CNPJ: " + si.getConta().getCnpj() + " - Serviço: "
								+ si.getNomeDoServico().toUpperCase() + " - total: "
								+ String.valueOf(si.getConta().getSaldo()) + " Unidades.";

						listaSaldoCliente.add(serv);

						if (ListaSi.size() != index2) {

							servicos.append("\n");

						}

						cnpj = si.getConta().getCnpj();

						index2++;

					}

					textAreaDetalhes.setText(String.valueOf(servicos).toUpperCase());
					textAreaSaldoCnpj.setText(String.valueOf(saldoCnpj).toUpperCase());

				} else {

					servicos.append("Não existem serviços cadastrados para o cliente.");
					textAreaDetalhes.setText(String.valueOf(servicos).toUpperCase());
					saldoCnpj.append("Não existem serviços cadastrados para o cliente.");
					textAreaSaldoCnpj.setText(String.valueOf(saldoCnpj).toUpperCase());

					servicos = new StringBuffer();
					saldoCnpj = new StringBuffer();

					listaSaldoCliente.clear();
				}

			} else {

				textAreaDetalhes.setText("");
				textAreaSaldoCnpj.setText("");

				servicos = new StringBuffer();
				saldoCnpj = new StringBuffer();

				listaSaldoCliente.clear();
			}

		}
		if (event.getClickCount() == 2) {

			event.consume();

			cliente = tableViewCliente.getSelectionModel().getSelectedItem();

			if (cliente != null) {

				listaClientes.clear();
				clearTableView();
				comboBoxListarClientes.setValue(null);
				textAreaDetalhes.setText("");
				textAreaSaldoCnpj.setText("");
				clienteSelecionadoForm(usuario, cliente, Strings.getClienteSelecionadoView());

				new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
						"Cliente selecionado " + cliente.getNomeFantasia().toUpperCase());

			} else {

				listaClientes.clear();
				textAreaDetalhes.setText("");
				textAreaSaldoCnpj.setText("");
				listaSaldoCliente.clear();

			}

		}

	}

	// evento botão cliente

	@FXML
	public void onBtClienteAction(ActionEvent event) {

		clienteForm(usuario, null, Strings.getClienteView());
		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage003());

	}

	@FXML
	public void onBtExportarAction(ActionEvent event) {

		ExportarListaClientesXLS exportarXLS = new ExportarListaClientesXLS();

		String caminho = "C:/temp/listaCliente.xls";

		vendedor = comboBoxListarClientes.getSelectionModel().getSelectedItem();

		try {

			try {

				if (listaClientes.isEmpty() != true) {

					try {

						exportarXLS.exportarListaClientesXLS(caminho, listaClientes, vendedor);
						new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage004());

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

			Alerts.showAlert("Exportar lista", "Listagem vazia", "Exportar para Excel", AlertType.ERROR);

		}

	}

	@FXML
	public void onBtImprimirAction(ActionEvent event) {

		Relatorio relatorio = new Relatorio();

		vendedor = comboBoxListarClientes.getSelectionModel().getSelectedItem();

		try {

			if (listaClientes.isEmpty() != true) {

				relatorio.relatorioListaClientesPDF(listaClientes, vendedor);
				new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage005());

			} else {

				Alerts.showAlert("Relatório", "Lista vazia", "Exportar Relatório", AlertType.ERROR);

			}

		} catch (java.lang.NullPointerException e) {

			Alerts.showAlert("Relatório", "Listagem vazia", "Exportar Relatório", AlertType.ERROR);

		}

	}

	// evento botão exportar saldo

	@FXML
	public void onBtExportarSaldoAction(ActionEvent event) {

		ExportarListaSaldoClienteXLS exportarXLS = new ExportarListaSaldoClienteXLS();

		String caminho = "C:/temp/listaSaldoCliente.xls";

		try {

			try {

				if (listaSaldoCliente.isEmpty() != true) {

					try {

						exportarXLS.exportarListaSaldoClienteXLS(caminho, listaSaldoCliente, cliente);
						new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage006());

					} catch (WriteException e) {

						Alerts.showAlert("Exportar lista", "Erro ao criar o arquivo!", "Exportar ", AlertType.ERROR);

					}

				} else {

					Alerts.showAlert("Exportar lista", "Lista vazia", "Exportar para Excel", AlertType.ERROR);
				}

			} catch (HeadlessException | IOException e2) {

				Alerts.showAlert("Exportar lista", "Feche o arquivo primeiro!", "Exportar para Excel", AlertType.ERROR);

			}

		} catch (java.lang.NullPointerException e) {

			Alerts.showAlert("Exportar lista", "Listagem vazia", "Exportar para Excel", AlertType.ERROR);

		}

	}

	// evento botão pesquisar

	@FXML
	public void onBtPesquisarClienteAction(ActionEvent event) {

		if (textFieldPesquisar.getText().equals("")) {

			Alerts.showAlert("Pesquisar Clientes", "Campo obrigatório", "Digite no campo de pesquisa", AlertType.ERROR);

			clearTableView();

			limpaComboBoxListarClientes();

		} else {

			limpaComboBoxListarClientes();

			List<Cliente> listaCliente = new ArrayList<Cliente>();

			listaCliente = service.pesquisarNomeFantasia(textFieldPesquisar.getText());

			new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
					"Pesquisa: " + textFieldPesquisar.getText().toUpperCase());

			if (listaCliente.isEmpty() == true) {

				Alerts.showAlert("Pesquisar Clientes", "Lista vazia", "A pesquisa não retornou resultado!",
						AlertType.ERROR);

				textFieldPesquisar.setText("");
				textFieldPesquisar.requestFocus();
				textAreaDetalhes.setText("");
				textAreaSaldoCnpj.setText("");
				listaSaldoCliente.clear();

				clearTableView();

				limpaComboBoxListarClientes();

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

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage007());
		Utils.fecharTelaPrincipalFormAction();
		new Forms().loginForm(Strings.getLoginView());

	}

	// evento menu item sair

	@FXML
	public void onMenuItemSairAction(ActionEvent event) {

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage008());
		System.exit(0);

	}

	// evento menu item usuario

	@FXML
	public void onMenuItemUsuarioAction(ActionEvent event) {

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage009());
		new Forms().usuarioForm(usuario, Strings.getUsuarioView());

	}

	// evento menu item cliente

	@FXML
	public void onMenuItemClienteAction(ActionEvent event) {

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage003());
		clienteForm(usuario, null, Strings.getClienteView());

	}

	// evento menu item vendedor

	@FXML
	public void onMenuItemVendedorAction(ActionEvent event) {

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage010());
		new Forms().vendedorForm(usuario, Strings.getVendedorView());

	}

	// evento menu item cliente

	@FXML
	public void onMenuItemUnidadeAction(ActionEvent event) {

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage011());
		new Forms().unidadeForm(usuario, Strings.getUnidadeView());

	}

	@FXML
	public void onMenuItemProdutoAction(ActionEvent event) {

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage012());
		new Forms().produtoForm(usuario, Strings.getProdutoView());

	}

	// evento menu item backup

	@FXML
	public void onMenuItemBackupAction(ActionEvent event) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), Strings.getBackupBancoView());

		if (concedido == true) {

			new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage023());

			new Forms().BackupBancoForm(Strings.getBackupBancoView());

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	// evento menu item log

	@FXML
	public void onMenuItemLogAction(ActionEvent event) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), Strings.getLogSegurancaView());

		if (concedido == true) {

			new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage022());
			new Forms().LogSegurancaForm(Strings.getLogSegurancaView());

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	// evento menu item sobre

	@FXML
	public void onMenuItemAjudaAction(ActionEvent event) {

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage013());
		new Forms().sobreForm(Strings.getSobreView());

	}

	// evento comboBox listar

	@FXML
	public void onComboBoxListarClientesAction(ActionEvent event) {

		textFieldPesquisar.setText("");

		String nome = comboBoxListarClientes.getSelectionModel().getSelectedItem();
		int codVendedor = 0;

		try {

			if (comboBoxListarClientes.getSelectionModel().getSelectedItem() == null
					|| comboBoxListarClientes.getSelectionModel().getSelectedItem().trim().equals("")) {

				limpaComboBoxListarClientes();

			} else if (comboBoxListarClientes.getSelectionModel().getSelectedItem().equalsIgnoreCase("TODOS")) {

				new LogSegurancaService().novoLogSeguranca(usuario.getNome(), "Seleção cliente por vendedor: "
						+ comboBoxListarClientes.getSelectionModel().getSelectedItem().toUpperCase());
				updateTableView();

			} else if (comboBoxListarClientes.getSelectionModel().getSelectedItem().equalsIgnoreCase("LIMPAR")) {

				listaClientes.clear();
				clearTableView();
				textAreaDetalhes.setText("");
				textAreaSaldoCnpj.setText("");
				listaSaldoCliente.clear();

				limpaComboBoxListarClientes();

			} else {

				for (int i = 0; ListaVendedores.size() > i; i++) {

					if (ListaVendedores.get(i).getNomeVendedor().equalsIgnoreCase(nome)) {

						codVendedor = ListaVendedores.get(i).getIdVendedor();

					}

				}

				listaPorVendedor(codVendedor);

				if (!listaClientes.isEmpty() == true) {

					listarTableView();
					new LogSegurancaService().novoLogSeguranca(usuario.getNome(), "Seleção cliente por vendedor: "
							+ comboBoxListarClientes.getSelectionModel().getSelectedItem().toUpperCase());

				} else {

					clearTableView();
					listaClientes.clear();
					textAreaDetalhes.setText("");
					textAreaSaldoCnpj.setText("");

					Alerts.showAlert("Lista vazia", "A lista já está vazia!",
							"Clientes não encontrados para o vendedor", AlertType.ERROR);

					limpaComboBoxListarClientes();

				}

			}

		} catch (java.lang.NullPointerException e) {

			limpaComboBoxListarClientes();
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

	private void limpaComboBoxListarClientes() {
		comboBoxListarClientes.getSelectionModel().clearSelection();
		comboBoxListarClientes.setItems(FXCollections.observableArrayList(listarClientes()));
	}

	// Método com os objetos que devem ser inicializados

	private void initializeNodes() {

		vendedor = "";

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
		tableColumnDetalhes
				.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDetalhesDoCliente()));

	}

	// Lista todos os clientes

	private void listaTodos() {

		listaClientes = FXCollections.observableArrayList(service.findAll());

	}

	private void listaPorVendedor(int codVendedor) {

		listaClientes = FXCollections.observableArrayList(service.buscarPeloVendedor(codVendedor));

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

		ListaVendedores = vendedorService.buscarTodos();

		List<String> lista = new ArrayList<>();
		lista.add("TODOS");
		lista.add("LIMPAR");

		for (Vendedor v : ListaVendedores) {

			lista.add(String.valueOf(v.getNomeVendedor()));

		}

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
				StackPane pane = loader.load();

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

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
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
