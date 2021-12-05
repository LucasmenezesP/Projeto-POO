module LivrosPOO {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	exports controls;
	exports application;
	exports models;
	
	opens models to javafx.fxml;
	opens controls to javafx.fxml;
	opens application to javafx.graphics, javafx.fxml;
}
