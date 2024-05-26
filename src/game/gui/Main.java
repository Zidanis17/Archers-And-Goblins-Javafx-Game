package game.gui;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));


			Label label = new Label("Hover Me!");

		        // Add CSS class to the label
		    label.getStyleClass().add("flashing-text");
			Scene scene = new Scene(root);
//	        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			Image icon = new Image("/logo.png");
			primaryStage.setTitle("Attack on titans");
			primaryStage.setMaximized(true);
			primaryStage.setFullScreen(true);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
