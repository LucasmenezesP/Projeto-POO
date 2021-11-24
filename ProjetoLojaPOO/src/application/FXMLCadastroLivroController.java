package application;

import java.io.IOException;
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
import javafx.scene.control.TextField;
import models.Arquivo;
import models.Categorias;
import models.Livro;

public class FXMLCadastroLivroController extends Arquivo implements Initializable{

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtEstoque;
 
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    public void actionButton(ActionEvent event) throws IOException, ClassNotFoundException {
    	String nome = txtNome.getText();
    	String autor = txtAutor.getText();
    	String estoque = txtEstoque.getText();
    	String categoria = cbCategoria.getValue().toString();
    	int estoqueInt = Integer.parseInt(estoque);
    	int id = getId();
    	
    	Livro novoLivro = new Livro(id, estoqueInt, nome, autor, categoria);
    	//ArrayList<Livro> listLivros = new ArrayList<Livro>();
    	
    	ArrayList<Livro> listLivros = readerObject("src/arquivos/livros.txt");
    	listLivros.add(novoLivro);
    	writerObject("src/arquivos/livros.txt", listLivros); 	
    	
    	System.out.println("BOTÃO ");	
    }
    
    public int getId() throws IOException {
    	int oldId = Integer.parseInt(reader("src/arquivos/id.txt"));
    	int newId = oldId + 1;
    	writer("src/arquivos/id.txt", Integer.toString(newId), false);
		return newId;
    }

	public void initialize(URL arg0, ResourceBundle arg1) {
		carregarCategorias();
		
	}
	
	@FXML
	private ComboBox<Categorias> cbCategoria;
    
    private List<Categorias> categoriasLista = new ArrayList<Categorias>();
    
    private ObservableList<Categorias> obsCategorias;
    
	public void carregarCategorias() {
		categoriasLista.add(new Categorias(1, "Romançe"));
		categoriasLista.add(new Categorias(2, "Economia"));
		categoriasLista.add(new Categorias(3, "Ação"));
		categoriasLista.add(new Categorias(4, "Aventura"));
		categoriasLista.add(new Categorias(5, "Programação"));
		categoriasLista.add(new Categorias(6, "Ficção"));
		categoriasLista.add(new Categorias(7, "Autoajuda"));
		categoriasLista.add(new Categorias(8, "Científico"));
		categoriasLista.add(new Categorias(9, "Religião"));
		categoriasLista.add(new Categorias(10, "Contabilidade"));
		categoriasLista.add(new Categorias(11, "Psicologia"));
		categoriasLista.add(new Categorias(12, "Música"));
		
		obsCategorias = FXCollections.observableArrayList(categoriasLista);
		
		System.out.println(obsCategorias.sorted());
		
		cbCategoria.setItems(obsCategorias.sorted());
	}

}