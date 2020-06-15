package application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import db.DB;
import gui.forms.Forms;
import gui.util.Alerts;
import gui.util.Strings;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.entities.Usuario;
import model.services.UsuarioService;
import properties.PropertiesFile;

public class SCS1Main extends Application {

	// Tela principal
	
	public static String erro;
	
	public static Scene mainScene;

	private static Socket socket;

	private static ServerSocket serverSocket;

	private static int portSocket;

	// public static String style = "/application/darktheme.css";

	@Override
	public void start(Stage primaryStage) throws SQLException {

		Connection conn = null;
		conn = DB.getConnectionTeste();
				
		if (conn != null) {

			try {

				// impede que seja criada uma nova instância do programa
				portSocket = Integer
						.parseInt(PropertiesFile.loadPropertiesSocket().getProperty(Strings.getPropertiessocketPort()));
				setServerSocket(new ServerSocket(portSocket));
				setSocket(new Socket(InetAddress.getLocalHost().getHostAddress(), portSocket));

				try {	
					iniciar();
					
					// Define o Style
					// setUserAgentStylesheet(STYLESHEET_CASPIAN);
					setUserAgentStylesheet(STYLESHEET_MODENA);

					// Application.setUserAgentStylesheet(getClass().getResource(style).toExternalForm());

					new Forms().splashForm(Strings.getSplashView());

				} catch (Exception e) {

					Alerts.showAlert("Controle de Saldo", "Erro ao abrir a tela", e.getLocalizedMessage(),
							AlertType.ERROR);

				}

			} catch (IOException e) {

				Alerts.showAlert("Controle de Saldo", "Erro ao abrir o programa",
						"Já existe uma instância do programa aberta!", AlertType.ERROR);

			}

		} else {
			
			Optional<ButtonType> result = Alerts.showConfirmation("Erro ao abrir o banco de dados",
					"Erro: " + erro +" .Você deseja configurar as propriedades do banco de dados ?");

			if (result.get() == ButtonType.OK) {
				
				new Forms().ConfigurarPerpetiesDBForm(Strings.getConfigurarPerpetiesDBView());
				
			}
			
		}
	}

	// Getters and Setters

	public static Scene getMainScene() {

		return mainScene;

	}

	public static void setMainScene(Scene mainScene) {

		SCS1Main.mainScene = mainScene;

	}

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {

		SCS1Main.socket = socket;

	}

	public static ServerSocket getServerSocket() {
		return serverSocket;
	}

	public static void setServerSocket(ServerSocket serverSocket) {

		SCS1Main.serverSocket = serverSocket;

	}

	public static int getPortSocket() {
		return portSocket;
	}

	public static void setPortSocket(int portSocket) {
		SCS1Main.portSocket = portSocket;
	}

	// inicia o aplicativo

	public static void main(String[] args) {

		launch(args);

	}

	private static void iniciar() {

		Usuario usuario = new UsuarioService().find(1);

		if (usuario == null) {

			usuario = new Usuario(null, "ADMINISTRADOR", "adm", "11", 1);
			new UsuarioService().usuarioNovoOuEditar(usuario);

		}

	}

}
