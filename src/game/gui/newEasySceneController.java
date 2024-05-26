package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.ResourceBundle;

import game.engine.*;
import game.engine.Battle;
import game.engine.base.Wall;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

public class newEasySceneController {
	private Model model;
	@FXML
	private AnchorPane originalPane;
	@FXML
	private ProgressBar lane1Bar;
	@FXML
	private ProgressBar lane2Bar;
	@FXML
	private ProgressBar lane3Bar;
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
	private GridPane laneOneGrid;	
	@FXML
	private GridPane laneTwoGrid;	
	@FXML
	private GridPane laneThreeGrid;	
	 @FXML
	private ChoiceBox<String> laneToBuyWeapon;
	@FXML
	private Pane lane1;
	@FXML
	private Pane lane2;
	@FXML
	private Pane lane3;
	@FXML
	private Pane lane1Path;
	@FXML
	private Pane lane2Path;
	@FXML
	private Pane lane3Path;
	
	@FXML 
	private Pane weaponShop;
	 
	
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
	private Pane vCanonLane1;
	@FXML
	private Pane vCanonLane2;
	@FXML
	private Pane vCanonLane3;
	@FXML
	private Pane sCanonLane1;
	@FXML
	private Pane sCanonLane2;
	@FXML
	private Pane sCanonLane3;

	@FXML
	private Pane trapLane1;
	@FXML
	private Pane trapLane2;
	@FXML
	private Pane trapLane3;

	@FXML
	private Pane weaponOne;
	@FXML
	private Pane weaponTwo;
	@FXML
	private Pane weaponThree;
	@FXML
	private Pane weaponFour;
	@FXML
	private ImageView weaponOneSelectedImage;
	@FXML
	private ImageView weaponTwoSelectedImage;
	@FXML
	private ImageView weaponThreeSelectedImage;
	@FXML
	private ImageView weaponFourSelectedImage;
	@FXML
	private ImageView lane1Wall;
	@FXML
	private ImageView lane2Wall;
	@FXML
	private ImageView lane3Wall;
	
	@FXML
	private ImageView purchaseWeaponHovered;
	
	@FXML
	private ImageView plusSignHoveredImage;
	
	@FXML
	private Pane weaponStatsRibbon;
	@FXML
	private Label currentLaneSCanons;
	@FXML
	private Label currentLaneVCanons;
	@FXML
	private Label currentLanePCanons;
	@FXML
	private Label currentLaneWallTraps;
	@FXML
	private Label currentLaneDangerLevel;
	@FXML
	private ProgressBar currentLaneHealthBar;
	@FXML
	private ImageView laneNumberImageView;
	@FXML
	private ImageView laneNumberHover;
	@FXML
	private ImageView playButtonHover;
	@FXML
	private Pane purchaseButton;
	@FXML
	private Pane playButtonPane;
	@FXML
	private ImageView rescourcesImage;
	
	HashMap<Titan,TranslateTransition> titanHash = new HashMap<Titan,TranslateTransition>();
	
	Image imgPawnRun = new Image(getClass().getResourceAsStream("pawnRedRunningHandsUp.gif"));
	Image imgPawnHammer =  new Image(getClass().getResourceAsStream("pawnRedRunningHammerDown.gif"));
	Image imgPawnIdle = new Image(getClass().getResourceAsStream("pawnRedIdle.gif"));
	Image imgBarrelRedRun = new Image(getClass().getResourceAsStream("barrelRedRunning.gif"));
	Image imgBarellExplode =  new Image(getClass().getResourceAsStream("barrelRedExploding.gif"));
	Image imgBarrelRedIdle = new Image(getClass().getResourceAsStream("barrelRedIdle.gif"));
	Image imgTorchAttack =  new Image(getClass().getResourceAsStream("torchGoblinRedTorchAttackUp.gif"));
	Image imgTorchRun = new Image(getClass().getResourceAsStream("torchGoblinRedTorchAttackRun.gif"));
	Image imgTorchIdle =  new Image(getClass().getResourceAsStream("torchGoblinRedTorchGoblinIdle.gif"));
	Image imgDynamiteAttack =  new Image(getClass().getResourceAsStream("goblinDynamiteRedThrow.gif"));
	Image imgDynamiteRun = new Image(getClass().getResourceAsStream("goblinDynamiteRedRun.gif"));
	Image imgDynamiteIdle =  new Image(getClass().getResourceAsStream("goblinDynamiteRedIdle.gif"));
	
	int selectedWeapon = 2;
	int selectedLane = 1;
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	 




	public newEasySceneController() throws IOException {
		model = new Model(885 ,3, 250);
	}

	
	public void showAlert(String message) {
		BorderPane bp =  new BorderPane();
		bp.setTranslateX(450);
		bp.setTranslateY(920);
		bp.setMinWidth(1080);
		Label l = new Label(message);
		Font font = Font.loadFont(getClass().getResourceAsStream("vinque rg.otf"), 50);
		l.setFont(font);
		bp.setCenter(l);
		originalPane.getChildren().add(bp);
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), l);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(event -> originalPane.getChildren().remove(bp));
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
		laneOneGrid.setVisible(false);
		laneTwoGrid.setVisible(false);
		laneThreeGrid.setVisible(false);
		finalScore.setText(finalScore.getText() + scoreLabel.getText());
//		weaponShop.setVisible(false);
		gameOverPane.setVisible(true);
	}
	
	public void goToMenu(ActionEvent event) throws IOException {
		  root = FXMLLoader.load(getClass().getResource("menu.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}
	
	public void addWeaponToLane(int laneNum) {
		Pane pane;
		Pane[] pCanonLanes = {pCanonLane1, pCanonLane2, pCanonLane3};
	    Pane[] sCanonLanes = {sCanonLane1, sCanonLane2, sCanonLane3};
	    Pane[] vCanonLanes = {vCanonLane1, vCanonLane2, vCanonLane3};
	    Pane[] trapLanes = {trapLane1, trapLane2, trapLane3};

		 switch (this.selectedWeapon) {
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
//		 String textLabel1 = ((Label) pane.getChildren().get(0)).getText();
//		 ((Label) pane.getChildren().get(0)).setText(Integer.parseInt(textLabel1) + 1 + "");
		 pane.setVisible(true);
		 
		
	}
	
	public void purchaseWeapon( Lane lane, int laneNum) throws InsufficientResourcesException, InvalidLaneException, IOException {
		try {
			gameAction(true, this.selectedWeapon, lane);
			addWeaponToLane(laneNum);
			 
		
		}
		catch(InsufficientResourcesException e) {
			showAlert(e.getMessage());
		}
		catch(InvalidLaneException e) {
			showAlert("Lane lost!!");
		}
		
	}
	
	public void handlePurchaseClick(MouseEvent e) throws InsufficientResourcesException, InvalidLaneException, IOException {
		int laneNum = this.selectedLane;
		Lane lane = model.getLaneByNumber(laneNum);

        purchaseWeapon(lane, laneNum);

	}
	
public void updateSceneValues(){
		
//		updateProgressBars();
		updateTitanGrids();
		updateScore();
		updateCurrentPhase();
		updateTurn();
		updateResources();
		updateLanesWallImage();
		updateStatsRibbonValues();
//		updateDangerLevels();

		
	}

	public void updateLanesWallImage() {
		int numberOfLanes = 3;
		for(int i = 1; i <= numberOfLanes; i++) {
			Lane l = model.getLaneByNumber(i);
		
			if(l.isLaneLost()) {
				changeLaneWallImageDefeated(i);
				clearLane(i);
			}
		}
	 
	 
	}

	public void clearLane(int i) {
		Pane[] allLanesGrids = {lane1Path, lane2Path,lane3Path};
	
		allLanesGrids[i-1].getChildren().clear();
		allLanesGrids[i-1].setVisible(false);
		allLanesGrids[i-1].setDisable(true);
	}
	
	public void changeLaneWallImageDefeated(int i) {
		  Image img = new Image(getClass().getResourceAsStream("wallBroken.png"));
		  ImageView[] allWallsImages = {lane1Wall, lane2Wall,lane3Wall};
		  Pane[] pCanonLanes = {pCanonLane1, pCanonLane2, pCanonLane3};
		    Pane[] sCanonLanes = {sCanonLane1, sCanonLane2, sCanonLane3};
		    Pane[] vCanonLanes = {vCanonLane1, vCanonLane2, vCanonLane3};
		    Pane[] trapLanes = {trapLane1, trapLane2, trapLane3};
		  allWallsImages[i-1].setImage(img);
		  if(i != 1) {
			  allWallsImages[i-1].setLayoutY(77);
			  
		  }else {
			  allWallsImages[i-1].setLayoutY(90);	

		  }
		  pCanonLanes[i - 1].setVisible(false);
		  sCanonLanes[i - 1].setVisible(false);
		  vCanonLanes[i - 1].setVisible(false);
		  trapLanes[i - 1].setVisible(false);
		  
	}
	
	public void updateProgressBars() {
		updateLaneProgressBarByNumber(1);
		updateLaneProgressBarByNumber(2);
		updateLaneProgressBarByNumber(3);

	}
	
	public void updateDangerLevels() {
		updateLaneDangerLevelByNumber(1);
		updateLaneDangerLevelByNumber(2);
		updateLaneDangerLevelByNumber(3);

	}
	
	public void updateTitanGrids() {
//		updateLaneTitanGridByNumber(1);
//		updateLaneTitanGridByNumber(2);
//		updateLaneTitanGridByNumber(3);


		updateLaneTitanPaneByNumber(1);
		updateLaneTitanPaneByNumber(2);
		updateLaneTitanPaneByNumber(3);

	}
	public void updateLaneProgressBarByNumber(int num) {
		ProgressBar[] allLanesBars = {lane1Bar, lane2Bar, lane3Bar};
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
		GridPane[] allLanesBars = {laneOneGrid, laneTwoGrid, laneThreeGrid};
		ArrayList<ArrayList<Titan>> titansInLaneForm = model.getTitansInLaneFormByNumber(num);
		PriorityQueue<Titan> titans = model.getLaneByNumber(num).getTitans();
		GridPane selectedGrid = allLanesBars[num - 1];
	    ObservableList<Node> childrens = selectedGrid.getChildren();
	    
		 selectedGrid.getChildren().clear();
		 
		

		
		 for(Titan t :titans) {
			 Pane titansInGridBox = new Pane();
			 Titan currTitan = t;
			 Label tempLabel = new Label();
			 ImageView imgView = new ImageView();
			
				
			 tempLabel.setPrefWidth(200);
				
			 Label hpLabel = new Label("HP: "+currTitan.getCurrentHealth());
             hpLabel.setStyle("-fx-background-color: white; -fx-padding: 5px;");
//             hpLabel.setVisible(false);

             ProgressBar bar = new ProgressBar();
             bar.setProgress((double)(currTitan.getCurrentHealth())/(double)(currTitan.getBaseHealth()));
//             bar.setVisible(false);
             
//             titansInGridBox.setOnMouseEntered(event -> {
//                 hpLabel.setVisible(true);
//                 bar.setVisible(true);
//             });
//
//             titansInGridBox.setOnMouseExited(event -> {
//                 hpLabel.setVisible(false);
//                 bar.setVisible(false);
//                 });



            
			 imgView.setPreserveRatio(true);
				if(currTitan instanceof PureTitan) {
					int layX = -240;
					int layY = -78;
					int fitWidth = 191;
					int fitHeight = 190;
					int transX = 0;
					int transY = 0;
					Image img;
					img = new Image(getClass().getResourceAsStream("pawnRedRunningHandsUp.gif"));
					
					if(currTitan.getDistance() <= 0) {
						img =  new Image(getClass().getResourceAsStream("pawnRedRunningHammerDown.gif"));
					}
					
					imgView.setImage(img);
					imgView.setFitWidth(fitWidth);
					imgView.setFitHeight(fitHeight);
					titansInGridBox.getChildren().add(imgView);
					selectedGrid.add(titansInGridBox, 3, currTitan.getDistance());
					imgView.setLayoutX(layX);
					imgView.setLayoutY(layY);
					imgView.setTranslateX(transX);
					imgView.setTranslateY(transY);
					hpLabel.setLayoutY(imgView.getLayoutY()+20);
		            bar.setLayoutY(imgView.getLayoutY()+40);
		            hpLabel.setLayoutX(imgView.getLayoutX()+40);
		            bar.setLayoutX(imgView.getLayoutX()+40);
		            titansInGridBox.getChildren().addAll(hpLabel,bar);
				}else if(currTitan instanceof ArmoredTitan) {
					int layX = -100;
					int layY = -48;
					int fitWidth = 135;
					int fitHeight = 135;
					int transX = 0;
					int transY = 0;
					Image img;
					img = new Image(getClass().getResourceAsStream("barrelRedRunning.gif"));
					
					if(currTitan.getDistance() <= 0) {
						img =  new Image(getClass().getResourceAsStream("barrelRedExploding.gif"));
						imgView.setFitWidth(fitWidth);
						imgView.setFitHeight(fitHeight);
						
					}
					
					imgView.setImage(img);
					imgView.setFitWidth(fitWidth);
					imgView.setFitHeight(fitHeight);
					titansInGridBox.getChildren().add(imgView);
					selectedGrid.add(titansInGridBox, 3, currTitan.getDistance());
					imgView.setLayoutX(layX);
					imgView.setLayoutY(layY);
					imgView.setTranslateX(transX);
					imgView.setTranslateY(transY);
					hpLabel.setLayoutY(imgView.getLayoutY()-10);
		            bar.setLayoutY(imgView.getLayoutY()+10);
		            hpLabel.setLayoutX(imgView.getLayoutX()+20);
		            bar.setLayoutX(imgView.getLayoutX()+20);
		            titansInGridBox.getChildren().addAll(hpLabel,bar);
				}else if(currTitan instanceof AbnormalTitan) {
					int layX = -150;
					int layY = -67;
					int fitWidth = 143;
					int fitHeight = 150;
					int transX = 0;
					int transY = 0;
					Image img;
					img = new Image(getClass().getResourceAsStream("goblinDynamiteRedRun.gif"));
					
					if(currTitan.getDistance() <= 0) {
						img =  new Image(getClass().getResourceAsStream("goblinDynamiteRedThrow.gif"));
					}
					
					imgView.setImage(img);
					imgView.setFitWidth(fitWidth);
					imgView.setFitHeight(fitHeight);
					titansInGridBox.getChildren().add(imgView);
					selectedGrid.add(titansInGridBox, 3, currTitan.getDistance());
					imgView.setLayoutX(layX);
					imgView.setLayoutY(layY);
					imgView.setTranslateX(transX);
					imgView.setTranslateY(transY);
					hpLabel.setLayoutY(imgView.getLayoutY());
		            bar.setLayoutY(imgView.getLayoutY()+20);
		            hpLabel.setLayoutX(imgView.getLayoutX()+20);
		            bar.setLayoutX(imgView.getLayoutX()+20);
		            titansInGridBox.getChildren().addAll(hpLabel,bar);

				}else {
					int layX = -106;
					int layY = -109;
					int fitWidth = 261;
					int fitHeight = 239;
					int transX = 0;
					int transY = 0;
					Image img;
					img = new Image(getClass().getResourceAsStream("torchGoblinRedTorchAttackRun.gif"));
					
					if(currTitan.getDistance() <= 0) {
						img =  new Image(getClass().getResourceAsStream("torchGoblinRedTorchAttackUp.gif"));
						layX = -76;
						layY = -61;
						transX = -25;
						transY = -34;
						hpLabel.setLayoutY(imgView.getLayoutY()-50);
			            bar.setLayoutY(imgView.getLayoutY()-30);
			            hpLabel.setLayoutX(imgView.getLayoutX() -30);
			            bar.setLayoutX(imgView.getLayoutX() -30);
					}
					
					imgView.setImage(img);
					imgView.setFitWidth(fitWidth);
					imgView.setFitHeight(fitHeight);
					titansInGridBox.getChildren().add(imgView);
					selectedGrid.add(titansInGridBox, 3, currTitan.getDistance());
					imgView.setLayoutX(layX);
					imgView.setLayoutY(layY);
					imgView.setTranslateX(transX);
					imgView.setTranslateY(transY);
					if(currTitan.getDistance() > 0) {
						hpLabel.setLayoutY(imgView.getLayoutY() + 30);
						bar.setLayoutY(imgView.getLayoutY() + 50);
						hpLabel.setLayoutX(imgView.getLayoutX() + 80);
						bar.setLayoutX(imgView.getLayoutX() + 80);
					}
		            titansInGridBox.getChildren().addAll(hpLabel,bar);
				}
			}
		 
//		for(int i = 0; i < titansInLaneForm.size(); i++) {
//			Pane titansInGridBox = new Pane();
//			ArrayList<Titan> innerArrayList = titansInLaneForm.get(i);
//			for(int j = 0; j < innerArrayList.size() ; j++) {
//				Titan currTitan = innerArrayList.get(j);
//				Label tempLabel = new Label();
//				ImageView imgView = new ImageView();
//				
//				
//				tempLabel.setPrefWidth(200);
//				
//				Label hpLabel = new Label("HP: "+currTitan.getCurrentHealth());
//                hpLabel.setStyle("-fx-background-color: white; -fx-padding: 5px;");
//                hpLabel.setVisible(false);
//
//                ProgressBar bar = new ProgressBar();
//                bar.setProgress((double)(currTitan.getCurrentHealth())/(double)(currTitan.getBaseHealth()));
//                bar.setVisible(false);
//                
//                imgView.setOnMouseEntered(event -> {
//                    hpLabel.setVisible(true);
//                    bar.setVisible(true);
//                });
//
//                imgView.setOnMouseExited(event -> {
//                    hpLabel.setVisible(false);
//                    bar.setVisible(false);
//                    });
//
//
//
//                hpLabel.setLayoutY(imgView.getLayoutY()-50);
//                bar.setLayoutY(imgView.getLayoutY()-30);
//                titansInGridBox.getChildren().addAll(hpLabel,bar);
//				if(currTitan instanceof PureTitan) {
//					Image img = new Image(getClass().getResourceAsStream("pawnRedRunningHandsUp.gif"));
//					imgView.setImage(img);
//					imgView.setFitWidth(200);
//					imgView.setFitHeight(150);
//					titansInGridBox.getChildren().add(imgView);
//					selectedGrid.add(titansInGridBox, 0, i);
//					imgView.setTranslateX(-54);
//					imgView.setTranslateY(0);
//				}else if(currTitan instanceof ArmoredTitan) {
//					Image img = new Image(getClass().getResourceAsStream("barrelRedRunning.gif"));
//					imgView.setImage(img);
//					imgView.setFitWidth(129);
//					imgView.setFitHeight(131);
//					
//					titansInGridBox.getChildren().add(imgView);
//					selectedGrid.add(titansInGridBox, 2, i);
//					imgView.setTranslateX(-41);
//					imgView.setTranslateY(-11);
//				}else if(currTitan instanceof AbnormalTitan) {
//					Image img = new Image(getClass().getResourceAsStream("goblinDynamiteRedRun.gif"));
//					imgView.setImage(img);
//					imgView.setFitWidth(182);
//					imgView.setFitHeight(171);
//					
//					titansInGridBox.getChildren().add(imgView);
//					selectedGrid.add(titansInGridBox, 1, i);
//					imgView.setTranslateX(-61);
//					imgView.setTranslateY(-6);
//				}else {
//					Image img = new Image(getClass().getResourceAsStream("torchGoblinRedTorchAttackRun.gif"));
//					imgView.setImage(img);
//					imgView.setFitWidth(261);
//					imgView.setFitHeight(239);
//					titansInGridBox.getChildren().add(imgView);
//					imgView.setTranslateX(-100);
//					imgView.setTranslateY(-20);
//				}
//			}
//			selectedGrid.add(titansInGridBox, 3, i);
//			
//			 
//		}
	}
	
	
	
	
	
	
	public void updateLaneTitanPaneByNumber(int num) {
		Pane[] allLanesBars = {lane1Path, lane2Path, lane3Path};
		ArrayList<ArrayList<Titan>> titansInLaneForm = model.getTitansInLaneFormByNumber(num);
		PriorityQueue<Titan> titans = model.getLaneByNumber(num).getTitans();
		Pane selectedPane = allLanesBars[num - 1];
		Random random = new Random();
		
		

		
		 for(Titan t :titans) {
			 Pane titansInGridBox = new Pane();
			 Titan currTitan = t;
			 Label tempLabel = new Label();
			 ImageView imgView = new ImageView();
			
				
			 tempLabel.setPrefWidth(200);
				
			 Label hpLabel = new Label("HP: "+currTitan.getCurrentHealth());
             hpLabel.setStyle("-fx-background-color: white; -fx-padding: 5px;");
//             hpLabel.setVisible(false);

             ProgressBar bar = new ProgressBar();
             bar.setProgress((double)(currTitan.getCurrentHealth())/(double)(currTitan.getBaseHealth()));
//             bar.setVisible(false);
             
//             titansInGridBox.setOnMouseEntered(event -> {
//                 hpLabel.setVisible(true);
//                 bar.setVisible(true);
//             });
//
//             titansInGridBox.setOnMouseExited(event -> {
//                 hpLabel.setVisible(false);
//                 bar.setVisible(false);
//                 });



            
			 imgView.setPreserveRatio(true);
			 if(this.titanHash.get(currTitan) == null) {
				if(currTitan instanceof PureTitan) {
					int fitWidth = 191;
					int fitHeight = 190;
					Image img;
					img = this.imgPawnIdle;
					
					imgView.setImage(img);
					imgView.setFitWidth(fitWidth);
					imgView.setFitHeight(fitHeight);
					titansInGridBox.getChildren().add(imgView);
					selectedPane.getChildren().add(titansInGridBox);
					titansInGridBox.setLayoutY(760);
					hpLabel.setLayoutY(imgView.getLayoutY()+20);
		            bar.setLayoutY(imgView.getLayoutY()+40);
		            hpLabel.setLayoutX(imgView.getLayoutX()+40);
		            bar.setLayoutX(imgView.getLayoutX()+40);
		            
		            titansInGridBox.getChildren().addAll(hpLabel,bar);
				}else if(currTitan instanceof ArmoredTitan) {
					int fitWidth = 135;
					int fitHeight = 135;

					Image img;
					img = this.imgBarrelRedIdle;
					
					
					imgView.setImage(img);
					imgView.setFitWidth(fitWidth);
					imgView.setFitHeight(fitHeight);
					titansInGridBox.getChildren().add(imgView);
					selectedPane.getChildren().add(titansInGridBox);
					titansInGridBox.setLayoutY(760);
					hpLabel.setLayoutY(imgView.getLayoutY()-10);
		            bar.setLayoutY(imgView.getLayoutY()+10);
		            hpLabel.setLayoutX(imgView.getLayoutX()+20);
		            bar.setLayoutX(imgView.getLayoutX()+20);
		            titansInGridBox.getChildren().addAll(hpLabel,bar);
				}else if(currTitan instanceof AbnormalTitan) {
					int fitWidth = 143;
					int fitHeight = 150;
					Image img;
					img = this.imgDynamiteIdle;
					
					
					imgView.setImage(img);
					imgView.setFitWidth(fitWidth);
					imgView.setFitHeight(fitHeight);
					titansInGridBox.getChildren().add(imgView);
					selectedPane.getChildren().add(titansInGridBox);
					titansInGridBox.setLayoutY(760);
					hpLabel.setLayoutY(imgView.getLayoutY());
		            bar.setLayoutY(imgView.getLayoutY()+20);
		            hpLabel.setLayoutX(imgView.getLayoutX()+20);
		            bar.setLayoutX(imgView.getLayoutX()+20);
		            titansInGridBox.getChildren().addAll(hpLabel,bar);

				}else {
					int fitWidth = 261;
					int fitHeight = 239;
					Image img;
					img = this.imgTorchIdle;
					
					imgView.setImage(img);
					imgView.setFitWidth(fitWidth);
					imgView.setFitHeight(fitHeight);
					titansInGridBox.getChildren().add(imgView);
					selectedPane.getChildren().add(titansInGridBox);
					titansInGridBox.setLayoutY(760);
					hpLabel.setLayoutY(imgView.getLayoutY() + 30);
					bar.setLayoutY(imgView.getLayoutY() + 50);
					hpLabel.setLayoutX(imgView.getLayoutX() + 80);
					bar.setLayoutX(imgView.getLayoutX() + 80);
					
					
		            titansInGridBox.getChildren().addAll(hpLabel,bar);
				}
				
				int randomNumber = random.nextInt((76 - (-71)) + 1) + (-71);
				titansInGridBox.setLayoutX(randomNumber);
				 TranslateTransition translateTransition = new TranslateTransition();
			       translateTransition.setDuration(Duration.seconds(1.8)); // Set duration of 2 seconds
			       translateTransition.setNode(titansInGridBox); // Set the node to be animated
			       translateTransition.setByY(-currTitan.getSpeed());
			        
			        this.titanHash.put(currTitan, translateTransition);
			 }else {
				TranslateTransition trans = this.titanHash.get(currTitan);
				
				 if(!currTitan.hasReachedTarget() && !currTitan.isDefeated()) {
					 
				
				 	if(currTitan instanceof ColossalTitan) {
					 	trans.setByY(-currTitan.getSpeed());
				 	}
				 	
				 	
					this.purchaseButton.setDisable(true);
					this.playButtonPane.setDisable(true);
					
					Pane titanPaneRun = (Pane) trans.getNode();
					ImageView titanImageIdle = (ImageView) titanPaneRun.getChildren().get(0);
					if(currTitan instanceof PureTitan) {
						titanImageIdle.setImage(this.imgPawnRun);
					}else if(currTitan instanceof AbnormalTitan) {
						titanImageIdle.setImage(this.imgDynamiteRun);
					}else if(currTitan instanceof ArmoredTitan) {
						titanImageIdle.setImage(this.imgBarrelRedRun);
					}else {
						titanImageIdle.setImage(this.imgTorchRun);
					}

				 	trans.play();
					updateHealth(trans, currTitan);
				 	trans.setOnFinished((event) ->{
				 		this.playButtonPane.setDisable(false);
				 		this.purchaseButton.setDisable(false);
				 		

				 		Pane titanPane = (Pane) trans.getNode();
						 ImageView titanImage = (ImageView) titanPane.getChildren().get(0);
						 if(currTitan instanceof PureTitan) {
							 titanImage.setImage(this.imgPawnIdle);
						 }else if(currTitan instanceof AbnormalTitan) {
							 titanImage.setImage(this.imgDynamiteIdle);
						 }else if(currTitan instanceof ArmoredTitan) {
							 titanImage.setImage(this.imgBarrelRedIdle);
						 }else {
							 titanImage.setImage(this.imgTorchIdle);
						 }

				 	});
				 		


				 
				 } 
				 if(currTitan.hasReachedTarget() && !currTitan.isDefeated()){
					 Pane titanPane = (Pane) trans.getNode();
					 ImageView titanImage = (ImageView) titanPane.getChildren().get(0);
					 if(currTitan instanceof PureTitan) {
						 titanImage.setImage(imgPawnHammer);
					 }else if(currTitan instanceof AbnormalTitan) {
						 titanImage.setImage(imgDynamiteAttack);
					 }else if(currTitan instanceof ArmoredTitan) {
						 titanImage.setImage(this.imgBarellExplode);
					 }else {
						 titanImage.setImage(this.imgTorchAttack);
					 }
					 updateHealth(trans, currTitan);
					 
				 }
			 }
			 
			
		}
		 for(Titan t : this.titanHash.keySet()) {
			 
			 if(t.isDefeated()) {
				
				 TranslateTransition trans2 = this.titanHash.get(t);
				 Pane titanPane = (Pane) trans2.getNode();
				 if(titanPane.getChildren().size()> 0) {
					 ImageView titanImage = (ImageView) titanPane.getChildren().get(0);
				 
					updateHealth(trans2, t);
				 	trans2.setByY(0);
				 	trans2.setDuration(Duration.seconds(1.8));
				 	trans2.play();
				 	Image imgDead;
				 	if(t instanceof ArmoredTitan) {
					 	imgDead =  new Image(getClass().getResourceAsStream("Explosion.gif"));
					 	titanImage.setImage(imgDead);
					 	titanImage.setFitWidth(600);
					 	titanImage.setScaleY(2);
					 	titanImage.setScaleX(2);

				 	}else {
				 		imgDead =  new Image(getClass().getResourceAsStream("Dead.gif"));
				 		titanImage.setImage(imgDead);
					 	titanImage.setFitWidth(125);
				 	}
				 	
				 	titanImage.setPreserveRatio(true);
				 	Image rescources = new Image(getClass().getResourceAsStream("Resources.gif"));
					rescourcesImage.setImage(rescources);
				 	trans2.setOnFinished(event ->{
				 		((Pane)this.titanHash.get(t).getNode()).getChildren().clear();
				 		Image rescources2 = new Image(getClass().getResourceAsStream("G_Idle_(NoShadow).png"));
						rescourcesImage.setImage(rescources2);
				 	});
				 }
			 }
		 }
	 	
	}
	
	
	public void updateHealth(TranslateTransition trans, Titan currTitan) {
		Label hpLabelVonTitan = (Label) ((Pane) trans.getNode()).getChildren().get(1);
		ProgressBar hpBarVonTitan = (ProgressBar) ((Pane) trans.getNode()).getChildren().get(2);

		DoubleProperty progress = new SimpleDoubleProperty(hpBarVonTitan.getProgress());

		hpBarVonTitan.progressProperty().bind(progress);

		double finalValue = (double)currTitan.getCurrentHealth()/(double)currTitan.getBaseHealth();

		Duration duration = Duration.seconds(1.8);

		KeyValue keyValue = new KeyValue(progress, finalValue);
		KeyFrame keyFrame = new KeyFrame(duration, keyValue);

		Timeline timeline = new Timeline(keyFrame);


		String prefix = hpLabelVonTitan.getText().split(":")[0] + ": ";
		System.out.println(prefix);
		int value = Integer.parseInt(hpLabelVonTitan.getText().split(":")[1].trim());
		IntegerProperty health1 = new SimpleIntegerProperty(value);

		hpLabelVonTitan.textProperty().bind(health1.asString(prefix + "%d"));

		double finalValueHealth = currTitan.getCurrentHealth();

		Duration duration2 = Duration.seconds(1.8);

		KeyValue keyValue2 = new KeyValue(health1, finalValueHealth);
		KeyFrame keyFrame2 = new KeyFrame(duration2, keyValue2);

		Timeline timeline2 = new Timeline(keyFrame2);
		timeline2.setCycleCount(1); // Play the animation once

// 		hpLabelVonTitan.setText("HP: " + currTitan.getCurrentHealth());
// 		hpBarVonTitan.setProgress((double)currTitan.getCurrentHealth()/(double)currTitan.getBaseHealth());
		timeline.play();
		timeline2.play();
	}
	
	
	
	
	public void updateLaneDangerLevelByNumber(int num) {
		Label[] allLanesDLBars = {laneOneDangerLevelLabel, laneTwoDangerLevelLabel, laneThreeDangerLevelLabel};
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
			Font font = Font.loadFont(getClass().getResourceAsStream("vinque rg.otf"), 34);
			
			phaseLabel.setTextFill(Color.GREEN);
			phaseLabel.setFont(font);
			
		}
		else if(phaseLabel.getText().equals("INTENSE")) {
			Font font = Font.loadFont(getClass().getResourceAsStream("vinque rg.otf"), 34);
			phaseLabel.setTextFill(Color.ORANGE);
			phaseLabel.setFont(font);
		}
		else {
			Font font = Font.loadFont(getClass().getResourceAsStream("vinque rg.otf"), 26);
			phaseLabel.setTextFill(Color.RED);
			phaseLabel.setFont(font);
		}
	}
	public void updateTurn(){
		turnLabel.setText(this.model.getBattle().getNumberOfTurns()+"");
	}
	public void updateResources(){
		int value = Integer.parseInt(this.resourcesLabel.getText());
		IntegerProperty rescources1 = new SimpleIntegerProperty(value);

		resourcesLabel.textProperty().bind(rescources1.asString());

		double finalValueHealth = this.model.getBattle().getResourcesGathered();

		Duration duration2 = Duration.seconds(1.8);

		KeyValue keyValue2 = new KeyValue(rescources1, finalValueHealth);
		KeyFrame keyFrame2 = new KeyFrame(duration2, keyValue2);

		Timeline timeline2 = new Timeline(keyFrame2);
		timeline2.setCycleCount(1); // Play the animation once
		timeline2.setOnFinished(actionEvent -> {
			if(Integer.parseInt(resourcesLabel.getText()) < 150) {
				resourcesLabel.setTextFill(Color.RED);
			}
			else {
				resourcesLabel.setTextFill(Color.BLUE);
			}
		});
		timeline2.play();
	}
	public void updateWeaponSelected(MouseEvent event) {
		Pane[] initialweaponsPanesArray = {weaponOne,weaponTwo,weaponThree,weaponFour};
		ArrayList<Pane> weaponsPanes = new ArrayList<>(Arrays.asList(initialweaponsPanesArray));
		ImageView[] initialweaponsImagesArray = {weaponOneSelectedImage,weaponTwoSelectedImage,weaponThreeSelectedImage,weaponFourSelectedImage};
		ArrayList<ImageView> weaponsImages = new ArrayList<>(Arrays.asList(initialweaponsImagesArray));
		Pane weaponSelectedPane = (Pane)event.getSource();
		int newSelectedWeapon = weaponsPanes.indexOf(weaponSelectedPane);
		this.selectedWeapon = newSelectedWeapon + 1;
		updateSelectedVisibility(initialweaponsImagesArray[newSelectedWeapon]);
		
	}
	
	public void updateSelectedVisibility(ImageView IV) {
		ImageView[] initialweaponsImagesArray = {weaponOneSelectedImage,weaponTwoSelectedImage,weaponThreeSelectedImage,weaponFourSelectedImage};
		for(int i = 0; i < initialweaponsImagesArray.length; i++) {
			initialweaponsImagesArray[i].setVisible(false);
		}
		
		IV.setVisible(true);
		
	}
	
	
	public void updatePurchaseWeaponHoveredOn() {		
		purchaseWeaponHovered.setVisible(true);
	}
	
	public void updatePurchaseWeaponHoveredOff() {
		purchaseWeaponHovered.setVisible(false);
	}
	
	public void handleWeaponStatsButtonClose() {
		weaponStatsRibbon.setVisible(false);
	}
	
	public void handleWeaponStatsButtonOpen() {
		updateStatsRibbonValues();
		weaponStatsRibbon.setVisible(true);
		
	}
	
	public void updatePlusRibbonHoveredOn() {		
		plusSignHoveredImage.setVisible(true);
	}
	
	public void updatePlusRibbonHoveredOff() {
		plusSignHoveredImage.setVisible(false);
	}
	
	public void updateLaneNumberButtonHoverOn() {
		laneNumberHover.setVisible(true);
	}
	public void updateLaneNumberButtonHoverOff() {
		laneNumberHover.setVisible(false);
	}
	
	public void handleLaneNumberButtonPress() {
		selectedLane++;
		if(selectedLane>3) {
			selectedLane = 1;
		}
		
		if(selectedLane == 1) {
			Image img = new Image(getClass().getResourceAsStream("numberOneImage.png"));
			laneNumberImageView.setImage(img);
			laneNumberImageView.setFitWidth(54);
			laneNumberImageView.setFitWidth(54);
			laneNumberImageView.setLayoutX(6);
			laneNumberImageView.setLayoutY(5);
		}if(selectedLane == 2) {
			Image img = new Image(getClass().getResourceAsStream("numberTwoImage.png"));
			laneNumberImageView.setImage(img);
			laneNumberImageView.setFitWidth(54);
			laneNumberImageView.setFitWidth(54);
			laneNumberImageView.setLayoutX(6);
			laneNumberImageView.setLayoutY(5);
		}if(selectedLane == 3) {
			Image img = new Image(getClass().getResourceAsStream("numberThreeImage.png"));
			laneNumberImageView.setImage(img);
			laneNumberImageView.setFitWidth(54);
			laneNumberImageView.setFitWidth(54);
			laneNumberImageView.setLayoutX(6);
			laneNumberImageView.setLayoutY(5);
		}
		this.updateStatsRibbonValues();
	}
	
	public void updateStatsRibbonValues() {
		int numCurrentLaneSCanons = 0;
		int numCurrentLaneVCanons = 0;
		int numCurrentLanePCanons = 0;
		int numCurrentLaneWallTraps = 0;
		Lane currentLane = model.getLaneByNumber(selectedLane);
		for(Weapon weapon : currentLane.getWeapons()) {
			if(weapon instanceof SniperCannon) {
				numCurrentLaneSCanons++;
			}else if(weapon instanceof PiercingCannon) {
				numCurrentLanePCanons++;
			}else if(weapon instanceof VolleySpreadCannon) {
				numCurrentLaneVCanons++;
			}else {
				numCurrentLaneWallTraps++;
			}
		}
		double wallHealth = (double) currentLane.getLaneWall().getCurrentHealth()/(double)currentLane.getLaneWall().getBaseHealth();
		currentLaneHealthBar.setProgress(wallHealth);
		if(wallHealth < 0.3) {
			currentLaneHealthBar.setStyle("-fx-accent: #FF0000");
		}else {
			currentLaneHealthBar.setStyle("-fx-accent: #088cbc");
		}
		currentLaneDangerLevel.setText(currentLane.getDangerLevel()+"");
		currentLaneWallTraps.setText("x" +numCurrentLaneWallTraps + "");
		currentLanePCanons.setText("x" + numCurrentLanePCanons+"");
		currentLaneVCanons.setText("x" +numCurrentLaneVCanons+"");
		currentLaneSCanons.setText("x" +numCurrentLaneSCanons+"");
		
	}
	
	
	public void updatePlayButtonHoverOn() {
		playButtonHover.setVisible(true);
	}
	public void updatePlayButtonHoverOff() {
		playButtonHover.setVisible(false);
	}
	public void passTurnButtonClicked() throws IOException {
		model.getBattle().passTurn();
		this.updateSceneValues();
		boolean isGameOver = model.getBattle().isGameOver();
		if(isGameOver) {
			showGameOver();
		}
	}
	
	public void initialize() {
		Image image = new Image(getClass().getResourceAsStream("01.png"));
		ImageCursor cursor = new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2);
		
		cursor.getBestSize(32, 32);
		
		originalPane.setCursor(cursor);
		updateSceneValues();
		
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