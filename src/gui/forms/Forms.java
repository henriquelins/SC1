package gui.forms;

import java.io.IOException;

import application.SC1Main;
import gui.ClienteSelecionadoFormController;
import gui.ConfigurarPerpetiesDBFormController;
import gui.LancamentoListFormController;
import gui.PrincipalFormController;
import gui.ProdutoFormController;
import gui.ServicoFormController;
import gui.UnidadeFormController;
import gui.VendedorFormController;
import gui.util.Acesso;
import gui.util.Alerts;
import gui.util.Strings;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.entities.Cliente;
import model.entities.ServicoImpressao;
import model.entities.Usuario;
import model.services.ProdutoService;
import model.services.UnidadeService;
import model.services.VendedorService;

public class Forms {

	// forms tela splash

	public void splashForm(String tela) throws IOException {

		try {

			StackPane pane = FXMLLoader.load(getClass().getResource(tela));

			Scene scene = new Scene(pane);
			scene.setFill(Color.TRANSPARENT);

			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.TRANSPARENT);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.show();

			// Carrega a tela splash com fade in effect
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(2);
			fadeIn.setCycleCount(1);

			// Termina a tela splash com fade out effect
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
			fadeOut.setFromValue(2);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);

			fadeIn.play();

			// Depois de fade in, inicia o fade out
			fadeIn.setOnFinished((e) -> {

				fadeOut.play();

			});

			// Depois do fade out, carrega a tela inicial - login
			fadeOut.setOnFinished((e) -> {

				primaryStage.close();
				loginForm(Strings.getLoginView());

			});

		} catch (IOException ex) {

			Alerts.showAlert("Controle de Estoque", "Erro ao abrir o splash", ex.getLocalizedMessage(),
					AlertType.ERROR);

		}

	}

	// forms tela login

	public void loginForm(String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);

			SC1Main.setMainScene(scene);

			Stage principalStage = new Stage();
			principalStage.setTitle(Strings.getTitle());
			principalStage.setScene(SC1Main.getMainScene());
			principalStage.setResizable(false);

			principalStage.initStyle(StageStyle.TRANSPARENT);
			// principalStage.setOpacity(0.7);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			principalStage.getIcons().add(applicationIcon);

			principalStage.show();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getCause().toString(), AlertType.ERROR);

		}

	}

	// form tela principal

	public void principalForm(Usuario usuario, String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			ScrollPane pane = loader.load();

			SC1Main.setMainScene(new Scene(pane));

			PrincipalFormController controller = loader.getController();
			controller.setLabelLogado(usuario.toUsuarioLogado());
			controller.setUsuario(usuario);

			pane.setFitToHeight(true);
			pane.setFitToWidth(true);

			Stage primaryStage = new Stage();
			primaryStage.setTitle(Strings.getTitle());
			primaryStage.setScene(SC1Main.getMainScene());

			primaryStage.setResizable(true);
			primaryStage.setMaximized(true);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.show();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", Strings.erroCarregarTela(), e.getCause().toString(), AlertType.ERROR);

		}

	}

	// forms tela cliente selecionado

	public void clienteSelecionadoForm(Usuario usuario, Cliente cliente, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				VBox pane = loader.load();

				ClienteSelecionadoFormController controller = loader.getController();
				controller.carregarCampos(cliente, usuario);

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(new Scene(pane));
				primaryStage.setResizable(true);
				primaryStage.initModality(Modality.APPLICATION_MODAL);

				primaryStage.setResizable(true);
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

	// forms tela usuário

	public void usuarioForm(Usuario usuario, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {
 
				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				ScrollPane pane = loader.load();

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(new Scene(pane));
				primaryStage.setResizable(false);
				primaryStage.initModality(Modality.APPLICATION_MODAL);
				primaryStage.initOwner(null);

				pane.setFitToHeight(true);
				pane.setFitToWidth(true);

				// primaryStage.setResizable(true);
				// primaryStage.setMaximized(true);

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

	// forms tela sobre

	public void sobreForm(String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			VBox pane = loader.load();

			Stage primaryStage = new Stage();
			primaryStage.setTitle(Strings.getTitle());
			primaryStage.setScene(new Scene(pane));
			primaryStage.setResizable(false);
			primaryStage.initModality(Modality.APPLICATION_MODAL);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", Strings.erroCarregarTela(), e.getMessage(), AlertType.ERROR);

		}

	}

	// forms tela lista lançamento

	public void lancamentoListForm(Usuario usuario, Cliente cliente, ServicoImpressao clienteServico, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				ScrollPane pane = loader.load();

				LancamentoListFormController controller = loader.getController();
				controller.carregarCampos(cliente, clienteServico, usuario);

				pane.setFitToHeight(true);
				pane.setFitToWidth(true);

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(new Scene(pane));
				primaryStage.initModality(Modality.APPLICATION_MODAL);

				primaryStage.setResizable(true);
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

	// forms tela serviço

	public void servicoForm(Usuario usuario, Cliente cliente, ServicoImpressao clienteServico, String tela,
			boolean editar) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				ScrollPane pane = loader.load();

				ServicoFormController controller = loader.getController();
				controller.carregarCampos(cliente, clienteServico, usuario, editar);

				pane.setFitToHeight(true);
				pane.setFitToWidth(true);

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

	public void unidadeForm(Usuario usuario, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				VBox pane = loader.load();

				UnidadeFormController controller = loader.getController();
				controller.setUnidadeService(new UnidadeService());
				controller.carregarCampos(usuario);

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(new Scene(pane));
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

	public void vendedorForm(Usuario usuario, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				VBox pane = loader.load();

				VendedorFormController controller = loader.getController();
				controller.setVendedorService(new VendedorService());
				controller.carregarCampos(usuario);

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(new Scene(pane));
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

	public void produtoForm(Usuario usuario, String tela) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), tela);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
				VBox pane = loader.load();

				ProdutoFormController controller = loader.getController();
				controller.setProdutoService(new ProdutoService());
				controller.carregarCampos(usuario);

				Stage primaryStage = new Stage();
				primaryStage.setTitle(Strings.getTitle());
				primaryStage.setScene(new Scene(pane));
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

	public void ConfigurarPerpetiesDBForm(String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			VBox pane = loader.load();

			ConfigurarPerpetiesDBFormController controller = loader.getController();
			controller.carregarCampos();

			Stage primaryStage = new Stage();
			primaryStage.setTitle(Strings.getTitle());
			primaryStage.setScene(new Scene(pane));
			primaryStage.setResizable(false);
			primaryStage.initModality(Modality.APPLICATION_MODAL);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getCause().toString(), AlertType.ERROR);

		}

	}
	
	public void LogSegurancaForm(String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			VBox pane = loader.load();

			Stage primaryStage = new Stage();
			primaryStage.setTitle(Strings.getTitle());
			primaryStage.setScene(new Scene(pane));
			primaryStage.setResizable(false);
			primaryStage.initModality(Modality.APPLICATION_MODAL);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getCause().toString(), AlertType.ERROR);

		}

	}
	
	public void BackupBancoForm(String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			VBox pane = loader.load();

			Stage primaryStage = new Stage();
			primaryStage.setTitle(Strings.getTitle());
			primaryStage.setScene(new Scene(pane));
			primaryStage.setResizable(false);
			primaryStage.initModality(Modality.APPLICATION_MODAL);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getCause().toString(), AlertType.ERROR);

		}

	}

}
