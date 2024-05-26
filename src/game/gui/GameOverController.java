package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameOverController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	private Label finalScore;
	public int score;
	
	@FXML
	private Button backToMenu;
	
	public static void displayScore(String score) {
		
	}
	
	public void goToMenu(MouseEvent event) throws IOException {
		  System.out.println("backtomenu");
		  root = FXMLLoader.load(getClass().getResource("menu.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		finalScore.setText("Score: " + score);
	}
	
}
