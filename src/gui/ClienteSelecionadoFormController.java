package gui;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import exportarXLS.ExportarListaSaldoClienteXLS;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jxl.write.WriteException;
import model.dao.DaoFactory;
import model.dao.ServicoImpressaoDao;
import model.entities.Cliente;
import model.entities.Produto;
import model.entities.ServicoImpressao;
import model.entities.Unidade;
import model.entities.Usuario;
import model.entities.Vendedor;
import model.services.ClienteService;
import model.services.LogSegurancaService;
import model.services.Servico�mpressaoService;

public class ClienteSelecionadoFormController implements Initializable, DataChangeListener {

	// Java vari�veis

	// Lista de ouvintes para receber alguma modifica��o

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	private List<ServicoImpressao> listaServicosGeral = new ArrayList<>();

	private static int idClienteSelecionado;

	private Usuario usuario;

	private Cliente cliente;

	private Servico�mpressaoService servico�mpressaoService;

	private ClienteService clienteService;

	private ServicoImpressao servicoImpressao;

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
	private TextField textFieldTipoDeConta;

	@FXML
	private TextField textFieldStatus;

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

	@FXML
	private Button buttonRelatorioDoCliente;

	@FXML
	private Button buttonExcluirServico;

	@FXML
	private Label labelLimite;
	
	@FXML
	private Label labelTotal;


	// @FXML event

	// Evento boto relat�rio do cliente

	@FXML
	public void onButtonRelatorioDoClienteAction(ActionEvent event) {

		Servico�mpressaoService impressaoService = new Servico�mpressaoService();

		List<ServicoImpressao> ListaSi = new ArrayList<>();

		ListaSi = impressaoService.buscarServicosDoCliente(cliente.getIdCliente());

		if (ListaSi.isEmpty() == true) {

			Alerts.showAlert("Exportar lista", "N�o existem servi�os cadastrados para o cliente", "Exportar para Excel",
					AlertType.ERROR);

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",

					"Voc� deseja ver o relat�rio do saldo do cliente " + cliente.getNomeFantasia().toUpperCase()
							+ " ?");

			if (result.get() == ButtonType.OK) {

				String serv = "";
				String saldo = "";

				StringBuffer servicos = new StringBuffer();
				StringBuffer saldoCnpj = new StringBuffer();

				List<String> listaSaldoCliente = new ArrayList<>();

				if (cliente != null) {

					String cnpj = "";

					saldoCnpj.append("Cliente: " + cliente.getNomeFantasia().toUpperCase() + " - Lista por CNPJ.\n");

					saldo = "Cliente: " + cliente.getNomeFantasia().toUpperCase() + " - Lista por CNPJ.\n";
					listaSaldoCliente.add(saldo);

					int index = 1;
					int index2 = 1;

					if (!ListaSi.isEmpty() == true) {

						String tipo = "";

						for (ServicoImpressao si : ListaSi) {

							if (!si.getConta().getCnpj().equals(cnpj)) {

								if (si.getConta().isTipo()) {

									tipo = "SALDO";

								} else {

									tipo = "FATURADO";
								}

								saldoCnpj.append(index + " - Tipo: " + tipo + " - CNPJ: " + si.getConta().getCnpj()
										+ " - Servi�o: " + si.getNomeDoServico().toUpperCase() + " - total: "
										+ String.valueOf(si.getConta().getSaldo()) + " Unidades.");

								saldo = index + " - Tipo de conta: " + tipo + " - CNPJ: " + si.getConta().getCnpj()
										+ " - total: " + String.valueOf(si.getConta().getSaldo()) + " Unidades.\n";

								listaSaldoCliente.add(saldo.toString());

								index++;
							}

							cnpj = si.getConta().getCnpj();

						}

						servicos.append("Cliente: " + cliente.getNomeFantasia().toUpperCase()
								+ " - Lista por Servi�os do cliente - Mesmo CNPJ, compartilham o mesmo total.\n");

						serv = "Cliente: " + cliente.getNomeFantasia().toUpperCase()
								+ " - Lista por Servi�os do cliente - Mesmo CNPJ, compartilham o mesmo total.\n";

						listaSaldoCliente.add("\n");
						listaSaldoCliente.add(serv);

						new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
								"sele��o do cliente " + cliente.getNomeFantasia().toUpperCase());

					} else {

						servicos.append("N�o existem servi�os cadastrados para o cliente.");
						saldoCnpj.append("N�o existem servi�os cadastrados para o cliente.");

						servicos = new StringBuffer();
						saldoCnpj = new StringBuffer();

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
									+ " - Servi�o: " + si.getNomeDoServico().toUpperCase() + " - total: "
									+ String.valueOf(si.getConta().getSaldo()) + " Unidades.");

							serv = index2 + "- Tipo: " + tipo + " - CNPJ: " + si.getConta().getCnpj() + " - Servi�o: "
									+ si.getNomeDoServico().toUpperCase() + " - total: "
									+ String.valueOf(si.getConta().getSaldo()) + " Unidades.";

							listaSaldoCliente.add(serv);

							if (ListaSi.size() != index2) {

								servicos.append("\n");

							}

							cnpj = si.getConta().getCnpj();

							index2++;

						}

					} else {

						servicos.append("N�o existem servi�os cadastrados para o cliente.");
						saldoCnpj.append("N�o existem servi�os cadastrados para o cliente.");

						servicos = new StringBuffer();
						saldoCnpj = new StringBuffer();

					}

				} else {

					servicos = new StringBuffer();
					saldoCnpj = new StringBuffer();

				}

				ExportarListaSaldoClienteXLS exportarXLS = new ExportarListaSaldoClienteXLS();

				String caminho = "C:/temp/listaSaldoCliente.xls";

				try {

					try {

						if (listaSaldoCliente.isEmpty() != true) {

							try {

								exportarXLS.exportarListaSaldoClienteXLS(caminho, listaSaldoCliente, cliente);

							} catch (WriteException e) {

								Alerts.showAlert("Exportar lista", "Erro ao criar o arquivo!", "Exportar ",
										AlertType.ERROR);

							}

						} else {

							Alerts.showAlert("Exportar lista", "Lista vazia", "Exportar para Excel", AlertType.ERROR);
						}

					} catch (HeadlessException | IOException e2) {

						Alerts.showAlert("Exportar lista", "Feche o arquivo primeiro!", "Exportar para Excel",
								AlertType.ERROR);

					}

				} catch (java.lang.NullPointerException e) {

					Alerts.showAlert("Exportar lista", "Listagem nula", "Exportar para Excel", AlertType.ERROR);

				}

			}

		}

	}

	// Evento bot�o novo servi�o

	@FXML
	public void onButtonNovoServicoAction(ActionEvent event) {

		boolean editar = false;
		servicoForm(usuario, cliente, null, Strings.getServicoView(), editar);
		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage013());
		onDataChanged();
		updateDadosServicos();

	}

	// Evento bot�o editar servi�o

	@FXML
	public void onButtonEditarServicoAction(ActionEvent event) {

		boolean editar = true;

		if (listaServicos().isEmpty() == true) {

			Alerts.showAlert("Servi�os do Cliente", "Servi�os de impress�o", "Selecione o servi�o de impress�o",
					AlertType.ERROR);

		} else {

			try {

				servicoForm(usuario, cliente, servicoImpressao, Strings.getServicoView(), editar);
				new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage014());
				onDataChanged();
				updateDadosServicos();

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
				new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage015());

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
				new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage016());

			} catch (java.lang.NullPointerException e) {

				Alerts.showAlert("Servi�os do Cliente", "Servi�os de impress�o", "Selecione um servi�o",
						AlertType.ERROR);
			}

		}

	}

	// Evento bot�o editar cliente servi�o

	@FXML
	public void onButtoEditarClienteServicoAction(ActionEvent event) {

		clienteForm(usuario, getCliente(), Strings.getClienteView());
		new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
				"Editar cliente: " + getCliente().getNomeFantasia().toUpperCase());

	}

	// Evento bot�o editar cliente servi�o

	@FXML
	public void onButtoExcluirClienteServicoAction(ActionEvent event) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), "Excluir Servi�o");

		if (concedido == true) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",

					"Voc� deseja excluir o servi�o  " + servicoImpressao.getNomeDoServico().toUpperCase() + " ?");

			if (result.get() == ButtonType.OK) {

				ServicoImpressaoDao dao = DaoFactory.createServicoImpressaoDao();
				dao.excluir(servicoImpressao.getIdServicoImpressao());
				new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
						"Excluir cliente: " + cliente.getNomeFantasia().toUpperCase());

			}

			onDataChanged();
			limparDadosServicos();

		} else {

			Alerts.showAlert("Acesso negado", "Acesso n�o concedido ao usu�rio logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	// Evento selecionar o servi�o do comboBox

	@FXML
	public void onComboBoxServicoAction(ActionEvent event) {

		// id_cliente_servico = 0;

		try {

			if (comboBoxServico.getSelectionModel().getSelectedItem() != null
					|| !comboBoxServico.getSelectionModel().getSelectedItem().trim().equals("") || !comboBoxServico
							.getSelectionModel().getSelectedItem().equals("ADICIONE UM SERVI�O DE IMPRESS�O")) {

				String nome = comboBoxServico.getSelectionModel().getSelectedItem();

				for (int i = 0; listaServicosGeral.size() > i; i++) {

					if (listaServicosGeral.get(i).getNomeDoServico().equals(nome)) {

						id_cliente_servico = listaServicosGeral.get(i).getIdServicoImpressao();

					}

				}

				new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
						"Servi�o selecionado: " + nome.toUpperCase());

				setServicoImpressao(servico�mpressaoService.buscarPeloIdCliente(id_cliente_servico));

				carregarCamposClienteServico();

				limparComboBoxServico();

			} else {

				Alerts.showAlert("Servi�os do Cliente", "Servi�os de impress�o", "Adicione um servi�o de impress�o",
						AlertType.ERROR);

				limparComboBoxServico();

			}

		} catch (java.lang.NullPointerException e) {

			limparComboBoxServico();

		}

	}

	public void limparComboBoxServico() {

		comboBoxServico.getSelectionModel().clearSelection();
		comboBoxServico.setItems(FXCollections.observableArrayList(listaServicos()));

	}

	// Lista de servi�os

	private List<String> listaServicos() {

		Servico�mpressaoService impressaoService = new Servico�mpressaoService();
		List<String> listaServicoImpressao = new ArrayList<>();

		listaServicosGeral = impressaoService.buscarServicosDoCliente(idClienteSelecionado);

		if (listaServicosGeral.isEmpty() == true) {

			listaServicoImpressao.add("ADICIONE UM SERVI�O DE IMPRESS�O");

		} else {

			for (ServicoImpressao cs : listaServicosGeral) {

				listaServicoImpressao.add(cs.getNomeDoServico());

			}

		}

		return listaServicoImpressao;

	}

	// M�todo para atualizar comboBox servi�os

	public void updateDadosServicos() {

		// principalFormController.updateTableView();

		setServicoImpressao(servico�mpressaoService.buscarPeloIdCliente(id_cliente_servico));

		carregarCamposClienteServico();

	}

	// M�todo para atualizar comboBox servi�os

	public void atualizarDadosServicosNovo() {

		textFieldServico.setText(servicoImpressao.getNomeDoServico().toUpperCase());
		textFieldProdutoDoServico
				.setText(new Produto().apenasNomeProduto(servicoImpressao.getProdutoDoServico()).toUpperCase());
		textFieldSaldo.setText(Constraints.tresDigitos(servicoImpressao.getConta().getSaldo()) + " Unidades");
		textFieldValorUnitario.setText(Constraints.dinheiro(servicoImpressao.getValorUnitario()));
		textFieldLimiteMinimo.setText(Constraints.tresDigitos(servicoImpressao.getLimiteMinimo()) + " Unidades");
		textFieldCnpj.setText(servicoImpressao.getConta().getCnpj());

		String tipo = "";

		if (servicoImpressao.getConta().isTipo()) {

			tipo = "SALDO";

		} else {

			tipo = "FATURADO";
		}

		textFieldTipoDeConta.setText(tipo);
		textAreaDetalhesDoServico.setText(servicoImpressao.getObservacoesServico().toUpperCase());
		comboBoxServico.getSelectionModel().selectLast();

	}

	public void limparDadosServicos() {

		comboBoxServico.setItems(FXCollections.observableArrayList(listaServicos()));
		comboBoxServico.getSelectionModel().selectFirst();

		textFieldServico.setText("");
		textFieldProdutoDoServico.setText("");
		textFieldSaldo.setText("");
		textFieldValorUnitario.setText("");
		textFieldLimiteMinimo.setText("");
		textFieldTipoDeConta.setText("");
		textFieldCnpj.setText("");
		textFieldTipoDeConta.setText("");
		textAreaDetalhesDoServico.setText("");

	}

	public void atualizarSalvoEditado() {

		if (comboBoxServico.getSelectionModel().getSelectedItem() == null) {

			comboBoxServico.setItems(FXCollections.observableArrayList(listaServicos()));
			comboBoxServico.getSelectionModel().selectLast();

		} else {

			comboBoxServico.setItems(FXCollections.observableArrayList(listaServicos()));
		}

		textFieldServico.setText(servicoImpressao.getNomeDoServico().toUpperCase());
		textFieldProdutoDoServico
				.setText(new Produto().apenasNomeProduto(servicoImpressao.getProdutoDoServico()).toUpperCase());
		textFieldSaldo.setText(Constraints.tresDigitos(servicoImpressao.getConta().getSaldo()) + " Unidades");
		textFieldValorUnitario.setText(Constraints.dinheiro(servicoImpressao.getValorUnitario()));
		textFieldLimiteMinimo.setText(Constraints.tresDigitos(servicoImpressao.getLimiteMinimo()) + " Unidades");
		textFieldTipoDeConta.setText(String.valueOf(servicoImpressao.getConta().isTipo()).toUpperCase());
		textFieldCnpj.setText(servicoImpressao.getConta().getCnpj());

		String tipo = "";

		if (servicoImpressao.getConta().isTipo()) {

			tipo = "SALDO";

		} else {

			tipo = "FATURADO";
		}

		textFieldTipoDeConta.setText(tipo);
		textAreaDetalhesDoServico.setText(servicoImpressao.getObservacoesServico().toUpperCase());

	}

	public void atualizarCliente() {

		Cliente clienteAtualizado = new Cliente();
		clienteAtualizado = clienteService.buscarPeloId(idClienteSelecionado);

		labelTituloCliente.setText("Cliente:  " + clienteAtualizado.getNomeFantasia().toUpperCase());
		labelNomeFantasia.setText(clienteAtualizado.getNomeFantasia().toUpperCase());
		labelRazaoSocial.setText(clienteAtualizado.getRazaoSocial().toUpperCase());
		labelCnpj.setText(clienteAtualizado.getCnpjCliente());
		labelInscricaoEstadual.setText(clienteAtualizado.getInscricaoEstadual());
		labelInscricaoMuncipal.setText(clienteAtualizado.getInscricaoMunicipal());
		labelClienteDesde.setText(Constraints.setDateFormat(clienteAtualizado.getDataInicioCliente()));
		labelContato.setText(clienteAtualizado.getContato().getContatoCliente().toUpperCase());
		labelEmail.setText(clienteAtualizado.getContato().getEmailCliente().toLowerCase());
		labelFoneCelular.setText(clienteAtualizado.getContato().getFoneCelular());
		labelFoneFixo.setText(clienteAtualizado.getContato().getFoneFixo());
		labelVendedor.setText(new Vendedor().nomeVendedor((clienteAtualizado.getCod_vendedor())).toUpperCase());
		labelUnidadeDeAtendimento.setText(new Unidade().nomeUnidade(clienteAtualizado.getUnidadeDeAtendimento()));
		labelLogradouro.setText(clienteAtualizado.getEndereco().getLogradouro().toUpperCase());
		labelBairro.setText(clienteAtualizado.getEndereco().getBairro().toUpperCase());
		labelCidade.setText(clienteAtualizado.getEndereco().getCidade().toUpperCase());
		labelCep.setText(clienteAtualizado.getEndereco().getCep());
		labelUf.setText(clienteAtualizado.getEndereco().getUf());
		labelEntrega.setText(
				String.valueOf(Constraints.setBooleanEntrega(clienteAtualizado.isEntregaNoCliente())).toUpperCase());
		textAreaDetalhesDoCliente.setText(clienteAtualizado.getDetalhesDoCliente().toUpperCase());

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
			atualizarCliente();

		} catch (java.lang.NullPointerException e) {

			atualizarCliente();
		}

	}

	// Inicia classe

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();

	}

	// M�todo com os objetos que devem ser inicializados

	private void initializeNodes() {

		setClienteService(new ClienteService());

		servico�mpressaoService = new Servico�mpressaoService();

	}

	// Carregar campos

	public void carregarCampos(Cliente cliente, Usuario usuario) {

		try {

			labelTituloCliente.setText("Cliente - " + cliente.getNomeFantasia().toUpperCase());
			labelNomeFantasia.setText(cliente.getNomeFantasia().toUpperCase());
			labelRazaoSocial.setText(cliente.getRazaoSocial().toUpperCase());
			labelCnpj.setText(cliente.getCnpjCliente());
			labelInscricaoEstadual.setText(cliente.getInscricaoEstadual());
			labelInscricaoMuncipal.setText(cliente.getInscricaoMunicipal());
			labelClienteDesde.setText(Constraints.setDateFormat(cliente.getDataInicioCliente()));
			labelContato.setText(cliente.getContato().getContatoCliente().toUpperCase());
			labelEmail.setText(cliente.getContato().getEmailCliente().toLowerCase());
			labelFoneCelular.setText(cliente.getContato().getFoneCelular());
			labelFoneFixo.setText(cliente.getContato().getFoneFixo());
			labelVendedor.setText(new Vendedor().nomeVendedor(cliente.getCod_vendedor()));
			labelUnidadeDeAtendimento
					.setText(new Unidade().nomeUnidade(cliente.getUnidadeDeAtendimento()).toUpperCase());
			labelLogradouro.setText(cliente.getEndereco().getLogradouro().toUpperCase());
			labelBairro.setText(cliente.getEndereco().getBairro().toUpperCase());
			labelCidade.setText(cliente.getEndereco().getCidade().toUpperCase());
			labelCep.setText(cliente.getEndereco().getCep());
			labelUf.setText(cliente.getEndereco().getUf());
			labelEntrega
					.setText(String.valueOf(Constraints.setBooleanEntrega(cliente.isEntregaNoCliente())).toUpperCase());
			textAreaDetalhesDoCliente.setText(cliente.getDetalhesDoCliente().toUpperCase());

			setCliente(cliente);
			setUsuario(usuario);

			setIdClienteSelecionado(cliente.getIdCliente());

			comboBoxServico.setItems(FXCollections.observableArrayList(listaServicos()));

		} catch (java.lang.NullPointerException e) {

		}

	}

	// Atualizar campos

	public void atualizarCampos() {

		ClienteService clienteService = new ClienteService();
		setCliente(clienteService.buscarPeloId(idClienteSelecionado));

		labelTituloCliente.setText("Cliente:  " + cliente.getNomeFantasia().toUpperCase());
		labelNomeFantasia.setText(cliente.getNomeFantasia().toUpperCase());
		labelRazaoSocial.setText(cliente.getRazaoSocial().toUpperCase());
		labelCnpj.setText(cliente.getCnpjCliente());
		labelInscricaoEstadual.setText(cliente.getInscricaoEstadual());
		labelInscricaoMuncipal.setText(cliente.getInscricaoMunicipal());
		labelClienteDesde.setText(Constraints.setDateFormat(cliente.getDataInicioCliente()));
		labelContato.setText(cliente.getContato().getContatoCliente().toUpperCase());
		labelEmail.setText(cliente.getContato().getEmailCliente().toLowerCase());
		labelFoneCelular.setText(cliente.getContato().getFoneCelular());
		labelFoneFixo.setText(cliente.getContato().getFoneFixo());
		labelVendedor.setText(new Vendedor().nomeVendedor((cliente.getCod_vendedor())));
		labelUnidadeDeAtendimento.setText(new Unidade().nomeUnidade(cliente.getUnidadeDeAtendimento()).toUpperCase());
		labelLogradouro.setText(cliente.getEndereco().getLogradouro().toUpperCase());
		labelBairro.setText(cliente.getEndereco().getBairro().toUpperCase());
		labelCidade.setText(cliente.getEndereco().getCidade().toUpperCase());
		labelCep.setText(cliente.getEndereco().getCep());
		labelUf.setText(cliente.getEndereco().getUf());
		labelEntrega.setText(String.valueOf(Constraints.setBooleanEntrega(cliente.isEntregaNoCliente())));
		textAreaDetalhesDoCliente.setText(cliente.getDetalhesDoCliente().toUpperCase());

	}

	// Converter String para inteiro

	public int servico_para_id_cliente_servico(String servico) {

		int index = servico.indexOf("-");
		String id_cliente_servico = servico.substring(0, index - 1);

		return Integer.valueOf(id_cliente_servico);

	}

	// Carregar os campos ServicoImpressao

	public void carregarCamposClienteServico() {

		if (servicoImpressao.getIdServicoImpressao() == null) {

			limparDadosServicos();

		} else {

			textFieldServico.setText(servicoImpressao.getNomeDoServico().toUpperCase());
			textFieldProdutoDoServico
					.setText(new Produto().apenasNomeProduto(servicoImpressao.getProdutoDoServico()).toUpperCase());
			textFieldSaldo.setText(Constraints.tresDigitos(servicoImpressao.getConta().getSaldo()) + " Unidades");
			textFieldValorUnitario.setText(Constraints.dinheiro(servicoImpressao.getValorUnitario()));
			textFieldLimiteMinimo.setText(Constraints.tresDigitos(servicoImpressao.getLimiteMinimo()) + " Unidades");
			textFieldCnpj.setText(servicoImpressao.getConta().getCnpj());
			textFieldStatus.setText(statusServicoImpressao(servicoImpressao.getConta().isTipo(),
					servicoImpressao.getLimiteMinimo(), servicoImpressao.getConta().getSaldo()));

			String tipo = "";

			if (servicoImpressao.getConta().isTipo()) {

				tipo = "SALDO";

				labelLimite.setText("Limite M�nimo");
				
				labelTotal.setText("Saldo atual");

			} else {

				tipo = "FATURADO";

				labelLimite.setText("Limite M�ximo");
				
				labelTotal.setText("Total � faturar");
				
			}

			textFieldTipoDeConta.setText(tipo);
			textAreaDetalhesDoServico.setText(servicoImpressao.getObservacoesServico().toUpperCase());

		}

	}

	public String statusServicoImpressao(boolean tipoConta, Integer limite, int saldo) {

		String status = "";

		if (tipoConta) {

			if (limite < saldo) {
				
				status = "SALDO NORMAL, ACOMPANHAR O SALDO DO CLIENTE";
				

			} else if (limite == saldo) {

				status = "SALDO BAIXO, AVISAR O SALDO AO CLIENTE";

			} else {

				status = "SALDO NEGATIVO, SOLICITAR NOVA COMPRA AO CLIENTE";
			}

		} else {

			if (limite > saldo) {

				status = "ABAIXO DO LIMITE, MANTENHA A ATEN��O COM O CLIENTE";

			} else if (limite == saldo) {

				status = "ATINGIU DO LIMITE, ATEN��O COM O CLIENTE";

			} else {

				status = "ACIMA DO LIMITE, SOLICITAR O PAGAMENTO DO CLIENTE";

			}

		}

		return status;
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

	// Telea servico

	public void servicoForm(Usuario usuario, Cliente cliente, ServicoImpressao servicoImpressao, String tela,
			boolean editar) {

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
				controller.carregarCampos(cliente, servicoImpressao, usuario, editar);

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
