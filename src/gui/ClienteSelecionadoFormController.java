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
import model.entities.Conta;
import model.entities.Produto;
import model.entities.ServicoImpressao;
import model.entities.Unidade;
import model.entities.Usuario;
import model.entities.Vendedor;
import model.services.ClienteService;
import model.services.ContaService;
import model.services.LogSegurancaService;
import model.services.ServicoÌmpressaoService;

public class ClienteSelecionadoFormController implements Initializable, DataChangeListener {

	// Java variáveis

	// Lista de ouvintes para receber alguma modificação

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	private List<ServicoImpressao> listaServicosGeral = new ArrayList<>();

	private static int idClienteSelecionado;

	private Usuario usuario;

	private Cliente cliente;
	
	private Conta conta;

	private ServicoÌmpressaoService servicoÌmpressaoService;
	
	private ClienteService clienteService;

	private ServicoImpressao servicoImpressao;

	private static int id_cliente_servico;

	private static String nomeServico;

	// @FXML variáveis

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

	// Evento boto relatório do cliente

	@FXML
	public void onButtonRelatorioDoClienteAction(ActionEvent event) {

		ServicoÌmpressaoService impressaoService = new ServicoÌmpressaoService();

		List<ServicoImpressao> ListaSi = new ArrayList<>();

		ListaSi = impressaoService.buscarServicosDoCliente(cliente.getIdCliente());

		if (ListaSi.isEmpty() == true) {

			Alerts.showAlert("Exportar lista", "Não existem serviços cadastrados para o cliente", "Exportar para Excel",
					AlertType.ERROR);

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",

					"Você deseja ver o relatório do saldo do cliente " + cliente.getNomeFantasia().toUpperCase()
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
							
							si.getIdConta();
							
							Conta conta = new ContaService().buscarContaId(si.getIdCliente());

							if (!conta.getCnpj().equals(cnpj)) {

								if (conta.isTipo()) {

									tipo = "SALDO";

								} else {

									tipo = "FATURADO";
								}

								saldoCnpj.append(index + " - Tipo: " + tipo + " - CNPJ: " + conta.getCnpj()
										+ " - Serviço: " + si.getNomeDoServico().toUpperCase() + " - total: "
										+ String.valueOf(conta.getSaldo()) + " Unidades.");

								saldo = index + " - Tipo de conta: " + tipo + " - CNPJ: " + conta.getCnpj()
										+ " - total: " + String.valueOf(conta.getSaldo()) + " Unidades.\n";

								listaSaldoCliente.add(saldo.toString());

								index++;
							}

							cnpj = conta.getCnpj();

						}

						servicos.append("Cliente: " + cliente.getNomeFantasia().toUpperCase()
								+ " - Lista por Serviços do cliente - Mesmo CNPJ, compartilham o mesmo total.\n");

						serv = "Cliente: " + cliente.getNomeFantasia().toUpperCase()
								+ " - Lista por Serviços do cliente - Mesmo CNPJ, compartilham o mesmo total.\n";

						listaSaldoCliente.add("\n");
						listaSaldoCliente.add(serv);

						new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
								"seleção do cliente " + cliente.getNomeFantasia().toUpperCase());

					} else {

						servicos.append("Não existem serviços cadastrados para o cliente.");
						saldoCnpj.append("Não existem serviços cadastrados para o cliente.");

						servicos = new StringBuffer();
						saldoCnpj = new StringBuffer();

					}

					if (!ListaSi.isEmpty() == true) {

						String tipo = "";

						for (ServicoImpressao si : ListaSi) {
							
							Conta conta = new ContaService().buscarContaId(si.getIdCliente());

							if (conta.isTipo()) {

								tipo = "SALDO";

							} else {

								tipo = "FATURADO";
							}

							servicos.append(index2 + "- Tipo: " + tipo + " - CNPJ: " + conta.getCnpj()
									+ " - Serviço: " + si.getNomeDoServico().toUpperCase() + " - total: "
									+ String.valueOf(conta.getSaldo()) + " Unidades.");

							serv = index2 + "- Tipo: " + tipo + " - CNPJ: " + conta.getCnpj() + " - Serviço: "
									+ si.getNomeDoServico().toUpperCase() + " - total: "
									+ String.valueOf(conta.getSaldo()) + " Unidades.";

							listaSaldoCliente.add(serv);

							if (ListaSi.size() != index2) {

								servicos.append("\n");

							}

							cnpj = conta.getCnpj();

							index2++;

						}

					} else {

						servicos.append("Não existem serviços cadastrados para o cliente.");
						saldoCnpj.append("Não existem serviços cadastrados para o cliente.");

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

	// Evento botão novo serviço

	@FXML
	public void onButtonNovoServicoAction(ActionEvent event) {

		boolean editar = false;
		servicoForm(usuario, cliente, null, Strings.getServicoView(), editar);
		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage013());
		onDataChanged();
		updateDadosServicos();

	}

	// Evento botão editar serviço

	@FXML
	public void onButtonEditarServicoAction(ActionEvent event) {

		boolean editar = true;

		if (listaServicos().isEmpty() == true) {

			Alerts.showAlert("Serviços do Cliente", "Serviços de impressão", "Selecione o serviço de impressão",
					AlertType.ERROR);

		} else {

			try {

				servicoForm(usuario, cliente, servicoImpressao, Strings.getServicoView(), editar);
				new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage014());
				onDataChanged();
				updateDadosServicos();

			} catch (java.lang.NullPointerException e) {

				Alerts.showAlert("Serviços do Cliente", "Serviços de impressão", "Selecione um serviço",
						AlertType.ERROR);
			}

		}

	}

	// Evento botão fazer lançamento

	@FXML
	public void onButtonFazerLancamentoAction(ActionEvent event) {

		if (listaServicos().isEmpty() == true) {

			Alerts.showAlert("Serviços do Cliente", "Serviços de impressão", "Selecione o serviço de impressão",
					AlertType.ERROR);

		} else {

			try {

				lancamentoForm(usuario, cliente, servicoImpressao, Strings.getLancamentoView());
				new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage015());

			} catch (java.lang.NullPointerException e) {

				Alerts.showAlert("Serviços do Cliente", "Serviços de impressão", "Selecione um serviço",
						AlertType.ERROR);
			}

		}

	}

	// Evento botão ver lançamento

	@FXML
	public void onButtonVerLancamentoAction(ActionEvent event) {

		if (listaServicos().isEmpty() == true) {

			Alerts.showAlert("Serviços do Cliente", "Serviços de impressão", "Selecione o serviço de impressão",
					AlertType.ERROR);

		} else {

			try {

				new Forms().lancamentoListForm(usuario, cliente, servicoImpressao, Strings.getLancamentoListView());
				new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage016());

			} catch (java.lang.NullPointerException e) {

				Alerts.showAlert("Serviços do Cliente", "Serviços de impressão", "Selecione um serviço",
						AlertType.ERROR);
			}

		}

	}

	// Evento botão editar cliente serviço

	@FXML
	public void onButtoEditarClienteServicoAction(ActionEvent event) {

		clienteForm(usuario, getCliente(), Strings.getClienteView());
		new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
				"Editar cliente: " + getCliente().getNomeFantasia().toUpperCase());

	}

	// Evento botão editar cliente serviço

	@FXML
	public void onButtoExcluirClienteServicoAction(ActionEvent event) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), "Excluir Serviço");

		if (concedido == true) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",

					"Você deseja excluir o serviço  " + servicoImpressao.getNomeDoServico().toUpperCase() + " ?");

			if (result.get() == ButtonType.OK) {

				ServicoImpressaoDao dao = DaoFactory.createServicoImpressaoDao();
				dao.excluir(servicoImpressao.getIdServicoImpressao());
				new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
						"Excluir cliente: " + cliente.getNomeFantasia().toUpperCase());

			}

			onDataChanged();
			limparDadosServicos();

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	// Evento selecionar o serviço do comboBox

	@FXML
	public void onComboBoxServicoAction(ActionEvent event) {

		id_cliente_servico = 0;

		try {

			if (comboBoxServico.getSelectionModel().getSelectedItem() != null
					|| !comboBoxServico.getSelectionModel().getSelectedItem().trim().equals("") || !comboBoxServico
							.getSelectionModel().getSelectedItem().equals("ADICIONE UM SERVIÇO DE IMPRESSÃO")) {

				nomeServico = comboBoxServico.getSelectionModel().getSelectedItem();
				
				for (int i = 0; listaServicosGeral.size() > i; i++) {

					if (listaServicosGeral.get(i).getNomeDoServico().equals(nomeServico)) {

						id_cliente_servico = listaServicosGeral.get(i).getIdServicoImpressao();
						
					}

				}

				new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
						"Serviço selecionado: " + nomeServico.toUpperCase());

				setServicoImpressao(servicoÌmpressaoService.buscarPeloIdCliente(id_cliente_servico));
				
				setConta(new ContaService().buscarContaId(getServicoImpressao().getIdConta()));
		
				carregarCamposClienteServico();

				limparComboBoxServico();

			} else {

				Alerts.showAlert("Serviços do Cliente", "Serviços de impressão", "Adicione um serviço de impressão",
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

	// Lista de serviços

	private List<String> listaServicos() {

		ServicoÌmpressaoService impressaoService = new ServicoÌmpressaoService();
		List<String> listaServicoImpressao = new ArrayList<>();

		listaServicosGeral = impressaoService.buscarServicosDoCliente(idClienteSelecionado);
		
		if (listaServicosGeral.isEmpty()) {

			listaServicoImpressao.add("ADICIONE UM SERVIÇO DE IMPRESSÃO");

		} else {

			for (ServicoImpressao cs : listaServicosGeral) {

				listaServicoImpressao.add(cs.getNomeDoServico());

			}

		}

		return listaServicoImpressao;

	}

	// Método para atualizar comboBox serviços

	public void updateDadosServicos() {

		setServicoImpressao(servicoÌmpressaoService.buscarPeloIdCliente(id_cliente_servico));

		carregarCamposClienteServico();

	}

	// Método para atualizar comboBox serviços

	public void atualizarDadosServicosNovo() {

		textFieldServico.setText(getServicoImpressao().getNomeDoServico().toUpperCase());
		textFieldProdutoDoServico
				.setText(new Produto().apenasNomeProduto(getServicoImpressao().getIdProdutoDoServico()).toUpperCase());
		textFieldSaldo.setText(getConta().getSaldo() + " Unidades");
		textFieldValorUnitario.setText(Constraints.dinheiro(getServicoImpressao().getValorUnitario()));
		textFieldLimiteMinimo.setText(Constraints.tresDigitos(getServicoImpressao().getLimiteMinimo()) + " Unidades");
		textFieldCnpj.setText(getConta().getCnpj());

		String tipo = "";

		if (getConta().isTipo()) {

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
		comboBoxServico.getSelectionModel().selectLast();

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
			
			if (!nomeServico.equals("")) {
				
				comboBoxServico.setItems(FXCollections.observableArrayList(listaServicos()));
				comboBoxServico.getSelectionModel().select(nomeServico);
			
			} else {
				
				comboBoxServico.setItems(FXCollections.observableArrayList(listaServicos()));
				
			}
			
		

		} else {

			comboBoxServico.setItems(FXCollections.observableArrayList(listaServicos()));
			
		}

		textFieldServico.setText(getServicoImpressao().getNomeDoServico().toUpperCase());
		textFieldProdutoDoServico
				.setText(new Produto().apenasNomeProduto(servicoImpressao.getIdProdutoDoServico()).toUpperCase());
		textFieldSaldo.setText(Constraints.tresDigitos(getConta().getSaldo()) + " Unidades");
		textFieldValorUnitario.setText(Constraints.dinheiro(servicoImpressao.getValorUnitario()));
		textFieldLimiteMinimo.setText(Constraints.tresDigitos(servicoImpressao.getLimiteMinimo()) + " Unidades");
		textFieldTipoDeConta.setText(String.valueOf(getConta().isTipo()).toUpperCase());
		textFieldCnpj.setText(getConta().getCnpj());

		String tipo = "";

		if (getConta().isTipo()) {

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

	// Adiciona a lista um ouvinte, quando há uma modificação

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

	// Método com os objetos que devem ser inicializados

	private void initializeNodes() {

		nomeServico = "";

		setClienteService(new ClienteService());

		servicoÌmpressaoService = new ServicoÌmpressaoService();
		

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
		
		if (getServicoImpressao().getIdServicoImpressao() == null) {

			limparDadosServicos();

		} else {
			
			textFieldServico.setText(getServicoImpressao().getNomeDoServico().toUpperCase());
			textFieldProdutoDoServico
					.setText(new Produto().apenasNomeProduto(getServicoImpressao().getIdProdutoDoServico()).toUpperCase());
			textFieldSaldo.setText(Constraints.tresDigitos(getConta().getSaldo()) + " Unidades");
			textFieldValorUnitario.setText(Constraints.dinheiro(getServicoImpressao().getValorUnitario()));
			textFieldLimiteMinimo.setText(Constraints.tresDigitos(getServicoImpressao().getLimiteMinimo()) + " Unidades");
			textFieldCnpj.setText(getConta().getCnpj());
			textFieldStatus.setText(statusServicoImpressao(getConta().isTipo(),	getServicoImpressao().getLimiteMinimo(), getConta().getSaldo()));

			String tipo = "";

			if (getConta().isTipo()) {

				tipo = "SALDO";

				labelLimite.setText("Limite Mínimo");
				labelTotal.setText("Saldo atual");

			} else {

				tipo = "FATURADO";

				labelLimite.setText("Limite Máximo");
				labelTotal.setText("Total à faturar");

			}

			textFieldTipoDeConta.setText(tipo);
			textAreaDetalhesDoServico.setText(getServicoImpressao().getObservacoesServico().toUpperCase());

		}

	}

	public String statusServicoImpressao(boolean tipoConta, Integer limite, int saldo) {

		String status = "";

		if (tipoConta) {

			if (saldo >= limite *3 ) {

				status = "SALDO NORMAL, ACOMPANHAR O SALDO DO CLIENTE";

			} else if ((saldo >= limite) && (saldo <= limite *3 )) {

				status = "SALDO BAIXO, AVISAR O SALDO AO CLIENTE";

			} else {

				status = "SALDO NEGATIVO, SOLICITAR NOVA COMPRA AO CLIENTE";
			}

		} else {

			if ((saldo < limite) && (saldo < limite/2)) {

				status = "ABAIXO DO LIMITE, MONITORE O SALDO DO CLIENTE";

			} else if ((saldo >= limite/2) && saldo < limite) {

				status = "CHEGANDO PRÓXIMO AO LIMITE, ATENÇÃO COM O CLIENTE";

			}  else if (saldo == limite) {

				status = "ATINGIU DO LIMITE, CONSULTAR O CLIENTE";

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

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
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

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	// forms tela lançamento

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

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
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

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public void subscribeDataChangeListener(PrincipalFormController principalFormController2) {

	}

}
