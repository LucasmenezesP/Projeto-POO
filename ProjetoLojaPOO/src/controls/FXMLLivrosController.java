package controls;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Arquivo;
import models.Categorias;
import models.Livro;

public class FXMLLivrosController extends Arquivo implements Initializable{
    @FXML
    private ListView<String> lstView;
    
    @FXML
    protected void btnCadastrar(ActionEvent event) {
    	Main.changeScreen(2);
    }

	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			carregarLivros();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void carregarLivros() throws ClassNotFoundException {
		lstView.getItems().add("Teste");
		ArrayList<Livro> lstLivros = readerObject("src/arquivos/livros.txt");
		System.out.println(lstLivros);
		for (Livro obj : lstLivros) {
			System.out.println(obj.getText());
			lstView.getItems().add(obj.getText());
		}
	}
}