package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Categorias;

public class FXMLTesteController implements Initializable {

    @FXML
    private Label labelOlaMundo;

    @FXML
    private TextField txtFIELD;

    @FXML
    private void actionButton(ActionEvent event) {
    	System.out.println("clicou");
    	labelOlaMundo.setText(txtFIELD.getText());
    }
    
	public void initialize(URL arg0, ResourceBundle arg1) {
		carregarCategorias();
	}

    
    @FXML
    private ComboBox<Categorias> cbCategorias;
    
    private List<Categorias> categoriasLista = new ArrayList<Categorias>();
    
    private ObservableList<Categorias> obsCategorias;
    
	public void carregarCategorias() {
		categoriasLista.add(new Categorias(1, "Romançe"));
		categoriasLista.add(new Categorias(2, "Economia"));
		categoriasLista.add(new Categorias(3, "Ação"));
		categoriasLista.add(new Categorias(4, "Aventura"));
		
		obsCategorias = FXCollections.observableArrayList(categoriasLista);
		
		System.out.println(obsCategorias);
		
		cbCategorias.setItems(obsCategorias);
	}

}
