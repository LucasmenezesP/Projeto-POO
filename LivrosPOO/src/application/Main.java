package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private static Stage stage;
	private static Scene telaLivros;
	private static Scene telaVendas;
	private static Scene telaClientesAtivos;
	private static Scene telaClientesInativos;
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		
		try {
			AnchorPane fxmlTelaLivros = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/FXMLLivros.fxml"));
			telaLivros = new Scene(fxmlTelaLivros, 1550, 800);
			
			AnchorPane fxmlTelaVendas = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/FXMLLivrosVenda.fxml"));
			telaVendas = new Scene(fxmlTelaVendas, 1550, 800);
			
			AnchorPane fxmlTelaClientesAtivos = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/FXMLClientesAtivos.fxml"));
			telaClientesAtivos = new Scene(fxmlTelaClientesAtivos, 1550, 800);
			
			AnchorPane fxmlTelaClientesInativos = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/FXMLClientesInativos.fxml"));
			telaClientesInativos = new Scene(fxmlTelaClientesInativos, 1550, 800);
			
			primaryStage.setScene(telaLivros);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void changeScreen(int newScreen) {
		switch (newScreen) {
			case 1:
				stage.setScene(telaLivros);
				break;
			case 2:
				stage.setScene(telaVendas);
				break;
			case 3:
				stage.setScene(telaClientesAtivos);
				break;
			case 4:
				stage.setScene(telaClientesInativos);
				break;
			default:
				break;
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
