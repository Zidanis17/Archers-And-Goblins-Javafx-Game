package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import game.engine.*;
import game.engine.Battle;
import game.engine.base.Wall;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.weapons.WeaponRegistry;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HardSceneController implements Initializable {
	private Model model;
	@FXML
	private ProgressBar lane1Bar;
	@FXML
	private ProgressBar lane2Bar;
	@FXML
	private ProgressBar lane3Bar;
	@FXML
	private ProgressBar lane4Bar;
	@FXML
	private ProgressBar lane5Bar;
	@FXML
	private Label phaseLabel;
	@FXML
	private Label scoreLabel;
	@FXML
	private Label turnLabel;
	@FXML
	private Label resourcesLabel;
	@FXML
	private Label laneOneDangerLevelLabel;
	@FXML
	private Label laneTwoDangerLevelLabel;
	@FXML
	private Label laneThreeDangerLevelLabel;
	@FXML
	private Label laneFourDangerLevelLabel;
	@FXML
	private Label laneFiveDangerLevelLabel;	
	@FXML
	private GridPane laneOneGrid;	
	@FXML
	private GridPane laneTwoGrid;	
	@FXML
	private GridPane laneThreeGrid;	
	@FXML
	private GridPane laneFourGrid;	
	@FXML
	private GridPane laneFiveGrid;	
	 @FXML
	private ChoiceBox<String> laneToBuyWeapon;
	@FXML
	private Pane lane1;
	@FXML
	private Pane lane2;
	@FXML
	private Pane lane3;
	@FXML
	private Pane lane4;
	@FXML
	private Pane lane5;
	@FXML 
	private Pane weaponShop;
	 
	@FXML
	private AnchorPane originalPane;
	@FXML
	private Pane gameOverPane;
	@FXML
	private Label finalScore;
	@FXML
	private Pane pCanonLane1;
	@FXML
	private Pane pCanonLane2;
	@FXML
	private Pane pCanonLane3;
	@FXML
	private Pane pCanonLane4;
	@FXML
	private Pane pCanonLane5;
	@FXML
	private Pane vCanonLane1;
	@FXML
	private Pane vCanonLane2;
	@FXML
	private Pane vCanonLane3;
	@FXML
	private Pane vCanonLane4;
	@FXML
	private Pane vCanonLane5;
	@FXML
	private Pane sCanonLane1;
	@FXML
	private Pane sCanonLane2;
	@FXML
	private Pane sCanonLane3;
	@FXML
	private Pane sCanonLane4;
	@FXML
	private Pane sCanonLane5;
	@FXML
	private Pane trapLane1;
	@FXML
	private Pane trapLane2;
	@FXML
	private Pane trapLane3;
	@FXML
	private Pane trapLane4;
	@FXML
	private Pane trapLane5;
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	 




	public HardSceneController() throws IOException {
		model = new Model(9 ,5, 125);
		
	}

	
	public void showAlert(String message) {
		BorderPane bp =  new BorderPane();
		bp.setTranslateX(450);
		bp.setTranslateY(920);
		bp.setMinWidth(1080);
		Label l = new Label(message);
		Font font = Font.loadFont(getClass().getResourceAsStream("lintxt.ttf"), 64);
		l.setFont(font);
		bp.setCenter(l);
		originalPane.getChildren().add(bp);
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), l);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(event -> originalPane.getChildren().remove(l));
		fadeOut.play();
	   }
	
	public void passTurn(ActionEvent e) throws InsufficientResourcesException, InvalidLaneException, IOException {
		gameAction(false, 0, null);
	}
	
	public void gameAction(boolean willBuy, int code, Lane lane ) throws InsufficientResourcesException, InvalidLaneException, IOException {
		if(willBuy) {
			model.getBattle().purchaseWeapon(code, lane);
		}
		else {
			model.getBattle().passTurn();
		}
		updateSceneValues();
		boolean isGameOver = model.getBattle().isGameOver();
		if(isGameOver) {
			showGameOver();
		}
		
	}
	
	public void showGameOver() throws IOException {
		lane1.setVisible(false);
		lane2.setVisible(false);
		lane3.setVisible(false);
		lane4.setVisible(false);
		lane5.setVisible(false);
		finalScore.setText(finalScore.getText() + scoreLabel.getText());
		weaponShop.setVisible(false);
		gameOverPane.setVisible(true);
	}
	
	public void goToMenu(MouseEvent event) throws IOException {
		  root = FXMLLoader.load(getClass().getResource("menu.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}
	
	public void addWeaponToLane(int laneNum, int code) {
		Pane pane;
		Pane[] pCanonLanes = {pCanonLane1, pCanonLane2, pCanonLane3, pCanonLane4, pCanonLane5};
	    Pane[] sCanonLanes = {sCanonLane1, sCanonLane2, sCanonLane3, sCanonLane4, sCanonLane5};
	    Pane[] vCanonLanes = {vCanonLane1, vCanonLane2, vCanonLane3, vCanonLane4, vCanonLane5};
	    Pane[] trapLanes = {trapLane1, trapLane2, trapLane3, trapLane4, trapLane5};

		 switch (code) {
         case 1:
             pane = pCanonLanes[laneNum - 1];
             break;
         case 2:
             pane = sCanonLanes[laneNum - 1];
             break;
         case 3:
             pane = vCanonLanes[laneNum - 1];
             break;
         default:
             pane = trapLanes[laneNum - 1];
             break;
     }
		 String textLabel1 = ((Label) pane.getChildren().get(0)).getText();
		 ((Label) pane.getChildren().get(0)).setText(Integer.parseInt(textLabel1) + 1 + "");
		 pane.setVisible(true);
		 
		
	}
	
	public void purchaseWeapon(int code, Lane lane, int laneNum) throws InsufficientResourcesException, InvalidLaneException, IOException {
		try {
			gameAction(true, code, lane);
			addWeaponToLane(laneNum, code);
			 
		
		}
		catch(InsufficientResourcesException e) {
			showAlert(e.getMessage());
		}
		catch(InvalidLaneException e) {
			showAlert("Lane lost!!");
		}
		
	}
	
	public void handlePurchaseClick(MouseEvent e) throws InsufficientResourcesException, InvalidLaneException, IOException {
		int weaponCode;
		int laneNum = Integer.parseInt(laneToBuyWeapon.getValue().substring(5));
		Lane lane = model.getLaneByNumber(laneNum);
		int availableResources = Integer.parseInt(resourcesLabel.getText());
		Label clickedLabel = (Label) e.getSource();
        String labelId = clickedLabel.getId();
        if(labelId.equals("weaponOnePurchaseLabel")) {
        	weaponCode = 1;
        }
        else if(labelId.equals("weaponTwoPurchaseLabel")) {
        	weaponCode = 2;
        }
        else if(labelId.equals("weaponThreePurchaseLabel")) {
        	weaponCode = 3;
        }
        else {
        	weaponCode = 4;
        }
        purchaseWeapon(weaponCode, lane, laneNum);

	}
	
	public void updateSceneValues(){
		
		updateProgressBars();
		updateScore();
		updateCurrentPhase();
		updateTurn();
		updateResources();
		updateDangerLevels();
		updateTitanGrids();
		
	}
	public void updateProgressBars() {
		updateLaneProgressBarByNumber(1);
		updateLaneProgressBarByNumber(2);
		updateLaneProgressBarByNumber(3);
		updateLaneProgressBarByNumber(4);
		updateLaneProgressBarByNumber(5);
	}
	
	public void updateDangerLevels() {
		updateLaneDangerLevelByNumber(1);
		updateLaneDangerLevelByNumber(2);
		updateLaneDangerLevelByNumber(3);
		updateLaneDangerLevelByNumber(4);
		updateLaneDangerLevelByNumber(5);
	}
	
	public void updateTitanGrids() {
		updateLaneTitanGridByNumber(1);
		updateLaneTitanGridByNumber(2);
		updateLaneTitanGridByNumber(3);
		updateLaneTitanGridByNumber(4);
		updateLaneTitanGridByNumber(5);

	}
	public void updateLaneProgressBarByNumber(int num) {
		ProgressBar[] allLanesBars = {lane1Bar, lane2Bar, lane3Bar, lane4Bar, lane5Bar};
		Wall wall = model.getLaneByNumber(num).getLaneWall();
		ProgressBar currProgBar = allLanesBars[num - 1];
		double newProgress = (double) wall.getCurrentHealth() / (double) wall.getBaseHealth();
		
		if(currProgBar.getProgress() != newProgress) {
			PauseTransition pause = new PauseTransition(Duration.millis(500));
			pause.setOnFinished(event ->{
				currProgBar.setStyle(null);
			});
			pause.play();
			currProgBar.setStyle("-fx-accent: #FF0000");
		}
		currProgBar.setProgress(newProgress);
	}
	
	public void updateLaneTitanGridByNumber(int num) {
		GridPane[] allLanesBars = {laneOneGrid, laneTwoGrid, laneThreeGrid, laneFourGrid, laneFiveGrid};
		ArrayList<ArrayList<Titan>> titansInLaneForm = model.getTitansInLaneFormByNumber(num);
		GridPane selectedGrid = allLanesBars[num - 1];
	    ObservableList<Node> childrens = selectedGrid.getChildren();
	    
		 selectedGrid.getChildren().clear();
		 
		

		
		for(int i = 0; i < titansInLaneForm.size(); i++) {
			Pane titansInGridBox = new Pane();
			ArrayList<Titan> innerArrayList = titansInLaneForm.get(i);
			for(int j = 0; j < innerArrayList.size() ; j++) {
				Titan currTitan = innerArrayList.get(j);
				Label tempLabel = new Label();
				ImageView imgView = new ImageView();
				
				
				tempLabel.setPrefWidth(200);
				
				Label hpLabel = new Label("HP: "+currTitan.getCurrentHealth());
                hpLabel.setStyle("-fx-background-color: white; -fx-padding: 5px;");
                hpLabel.setVisible(false);

                ProgressBar bar = new ProgressBar();
                bar.setProgress((double)(currTitan.getCurrentHealth())/(double)(currTitan.getBaseHealth()));
                bar.setVisible(false);
                
                imgView.setOnMouseEntered(event -> {
                    hpLabel.setVisible(true);
                    bar.setVisible(true);
                });

                imgView.setOnMouseExited(event -> {
                    hpLabel.setVisible(false);
                    bar.setVisible(false);
                    });



                hpLabel.setLayoutY(imgView.getLayoutY()-50);
                bar.setLayoutY(imgView.getLayoutY()-30);
                titansInGridBox.getChildren().addAll(hpLabel,bar);
				if(currTitan instanceof PureTitan) {
					
					
					
					Image img = new Image(getClass().getResourceAsStream("blueSlime.gif"));
					imgView.setImage(img);
					imgView.setFitWidth(114);
					imgView.setFitHeight(79);
					titansInGridBox.getChildren().add(imgView);
				}else if(currTitan instanceof ArmoredTitan) {
					Image img = new Image(getClass().getResourceAsStream("jumpySlime.gif"));
					imgView.setImage(img);
					imgView.setFitWidth(165);
					imgView.setFitHeight(174);
					
					titansInGridBox.getChildren().add(imgView);
				}else if(currTitan instanceof AbnormalTitan) {
					Image img = new Image(getClass().getResourceAsStream("DumbDUmb.gif"));
					imgView.setImage(img);
					imgView.setFitWidth(165);
					imgView.setFitHeight(174);
					
					titansInGridBox.getChildren().add(imgView);
				}else {
					Image img = new Image(getClass().getResourceAsStream("mm.gif"));
					imgView.setImage(img);
					imgView.setFitWidth(114);
					imgView.setFitHeight(79);
					titansInGridBox.getChildren().add(imgView);
				}
			}
			selectedGrid.add(titansInGridBox, i, 0);
			 
		}
	}
	
	public void updateLaneDangerLevelByNumber(int num) {
		Label[] allLanesDLBars = {laneOneDangerLevelLabel, laneTwoDangerLevelLabel, laneThreeDangerLevelLabel, laneFourDangerLevelLabel, laneFiveDangerLevelLabel};
		int dangerLevel = model.getLaneByNumber(num).getDangerLevel();
		allLanesDLBars[num - 1].setText(dangerLevel +"");;
	}
	
//	public void updateTitansOnGridByNumber(int num) {
//		laneOneGrid.add(lane1Bar, 1, num);
//	}

	
	public void updateScore(){
		scoreLabel.setText(this.model.getBattle().getScore() + "");
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void updateCurrentPhase(){
		phaseLabel.setText(this.model.getBattle().getBattlePhase().toString());
		if(phaseLabel.getText().equals("EARLY")) {
			phaseLabel.setTextFill(Color.GREEN);
		}
		else if(phaseLabel.getText().equals("INTENSE")) {
			phaseLabel.setTextFill(Color.ORANGE);
		}
		else {
			phaseLabel.setTextFill(Color.RED);
		}
	}
	public void updateTurn(){
		turnLabel.setText(this.model.getBattle().getNumberOfTurns()+"");
	}
	public void updateResources(){
		resourcesLabel.setText(this.model.getBattle().getResourcesGathered() +"");
		if(Integer.parseInt(resourcesLabel.getText()) < 150) {
			resourcesLabel.setTextFill(Color.RED);
		}
		else {
			resourcesLabel.setTextFill(Color.BLUE);
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image image = new Image(getClass().getResourceAsStream("01.png"));
		ImageCursor cursor = new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2);
		
		cursor.getBestSize(32, 32);
		
		originalPane.setCursor(cursor);
		updateSceneValues();
		laneToBuyWeapon.getItems().addAll("Lane 1", "Lane 2", "Lane 3", "Lane 4", "Lane 5");
		laneToBuyWeapon.setValue("Lane 1");
		laneToBuyWeapon.setOnAction(event -> {
			String selectedOption = laneToBuyWeapon.getValue();
	        laneToBuyWeapon.setValue(selectedOption);
	    });
		
		
	}
	
//	public static void delay(long millis, Runnable continuation) {
//	      Task<Void> sleeper = new Task<Void>() {
//	          @Override
//	          protected Void call() throws Exception {
//	              try { Thread.sleep(millis); }
//	              catch (InterruptedException e) { }
//	              return null;
//	          }
//	      };
//	      sleeper.setOnSucceeded(event -> continuation.run());
//	      new Thread(sleeper).start();
//	    }
}
