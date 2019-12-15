package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Strings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SplashFormController implements Initializable  {
	
	// @FXML variáveis
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private Label titleSplash;
	
	@FXML
	private ImageView imageSplash;
	
	// Inicia classe

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		initializeNodes();
		
	}

	private void initializeNodes() {
		
		titleSplash.setText(Strings.getTitleSplash());
		imageSplash.setImage(new Image(Strings.getImageSplash()));
		
	}
	
	
}
