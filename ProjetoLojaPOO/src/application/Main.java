package application;
	




import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;



public class Main extends Application {
	private static Stage stage;
	private static Scene telaEstoque;
	private static Scene telaCadastroLivros;

	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		
		Parent fxmlCadastroLivros = (Parent) FXMLLoader.load(getClass().getResource("../views/FXMLCadastroLivro.fxml"));
		telaCadastroLivros = new Scene(fxmlCadastroLivros, 1500, 800);
		Parent fxmlEstoque = (Parent) FXMLLoader.load(getClass().getResource("../views/FXMLLivros.fxml"));
		telaEstoque = new Scene(fxmlEstoque, 1500, 800);
		
		primaryStage.setScene(telaEstoque);
		primaryStage.show();
	}
	
	public static void changeScreen(int newScreen) {
		switch (newScreen) {
			case 1:
				stage.setScene(telaEstoque);
				break;
			case 2:
				stage.setScene(telaCadastroLivros);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
