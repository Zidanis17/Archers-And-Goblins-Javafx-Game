package game.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController {
	@FXML
	private Label easyLabel;
	@FXML
	private Label hardLabel;
	@FXML
	private Label startLabel;
	@FXML
	private TextArea instructionText;
	@FXML
	private Button instructionButton;
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String mode = "easy";
	
	
	public void onLabelClick(MouseEvent e) throws IOException {
		System.out.println((((Text) e.getTarget()).getText()));
		String x = (((Text) e.getTarget()).getText());
	    if (x.equals("EASY MODE")) {
	        easyLabel.setTextFill(Color.YELLOW);
	        hardLabel.setTextFill(Color.WHITE);
	        mode = "easy";
	    } else if (x.equals("HARD MODE")) {
	        hardLabel.setTextFill(Color.YELLOW);
	        easyLabel.setTextFill(Color.WHITE);
	        mode = "hard";
	    }
	    else if(x.equals("START GAME")) {
	    	start(e);
	    }
	}
	
	public void showInstructions() {
		instructionText.setEditable(false);
		if(instructionText.isVisible()) {
			instructionText.setVisible(false);
			instructionButton.setText("Game Instructions");
		}
		else {
			instructionText.setVisible(true);
			instructionButton.setText("Close Game Instructions");
		}
	}

	
	public void start(MouseEvent e) throws IOException {
		if(mode == "easy") {
			goEasy(e);
		}
		else {
			goHard(e);
		}
	}
	
	public void goEasy(MouseEvent event) throws IOException {
		System.out.println("EZ");
		  root = FXMLLoader.load(getClass().getResource("newEasyScene.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
		  stage.setFullScreen(true);
	}
	
	public void goHard(MouseEvent event) throws IOException {
		System.out.println("HARD");
		  root = FXMLLoader.load(getClass().getResource("newHardScene.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
		  stage.setFullScreen(true);
	}
	public void goToMenu(MouseEvent event) throws IOException {
		  System.out.println("backtomenu");
		  root = FXMLLoader.load(getClass().getResource("menu.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}

//	public void setEasy(ActionEvent event) {
//        easyLabel.setTextFill(Color.YELLOW);
//        hardLabel.setTextFill(Color.RED);
//        mode = "easy";
//        System.out.println(mode);
//	}
//	
//	public void setHard(ActionEvent event) {
//		hardLabel.setTextFill(Color.YELLOW);
//		easyLabel.setTextFill(Color.RED);
//		mode = "hard";
//		System.out.println(mode);
//	}
//	
//	public static void playSound(){
//		  String audioFilePath = "click-21156.mp3";
//	      Media media = new Media(audioFilePath);
//	      MediaPlayer mediaPlayer = new MediaPlayer(media);
//	      mediaPlayer.play();
//	}
	
//	public static void main(String[] args) {
//		playSound();
//	}
	
}
