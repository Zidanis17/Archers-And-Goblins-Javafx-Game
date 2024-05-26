module MileStone3frfrongong {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.media;
	requires javafx.base;
	
	opens game.gui to javafx.graphics, javafx.fxml;
}
