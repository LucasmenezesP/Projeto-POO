package controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Arquivo;
import models.Categorias;
import models.InterfaceTableView;
import models.Livro;
import models.UtilitarioId;
import models.Venda;

public class FXMLLivrosController extends Arquivo implements Initializable, UtilitarioId, InterfaceTableView {
    
    @FXML
    private ChoiceBox<Categorias> choiceCategoria;

    @FXML
    private TextField inputAutor, inputEstoque, inputNome, inputPreco, inputIdLivro, inputQuantidadeLivro;
    
    @FXML
    private Label labelErroLivro, labelErroEstoque;
    
    @FXML
    private TableView<Livro> table;

    @FXML
    private TableColumn<Livro, String> tableAutor, tableNome, tableCategoria;

    @FXML
    private TableColumn<Livro, Integer> tableEstoque, tableId, tablePreco;

    @FXML
    protected void btnCadastrarVenda(ActionEvent event) {
    	Main.changeScreen(2);
    }

    @FXML
    protected void btnClientesAtivos(ActionEvent event) {
    	Main.changeScreen(3);
    }

    @FXML
    protected void btnClientesInativos(ActionEvent event) {
    	Main.changeScreen(4);
    }
    
    @FXML
    protected void buttonRefresh(ActionEvent event) throws ClassNotFoundException {
    	carregarTable();
    }
    
    @FXML
    protected void btnAddEstoque(ActionEvent event) {
    	try {
        	int idLivro = Integer.parseInt(inputIdLivro.getText());
        	int quantidadeLivro = Integer.parseInt(inputQuantidadeLivro.getText());
        	
        	ArrayList<Livro> lstLivrosI = readerObject("src/arquivos/livros.txt");
        	for (int i = 0; i < lstLivrosI.size(); i++) {
        		Livro objAtual = lstLivrosI.get(i);
        		if (objAtual.getId() == idLivro) {
        			int estoqueAtual = objAtual.getEstoque();
        			objAtual.setEstoque(estoqueAtual + quantidadeLivro);
        			lstLivrosI.remove(i);
        			lstLivrosI.add(i, objAtual);
        		}
        	}
        	writerObject("src/arquivos/livros.txt", lstLivrosI);
        	carregarTable();
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			labelErroEstoque.setText("Livro não encontrado!");
		}
    }

    @FXML
    protected void btnConfirmarLivro(ActionEvent event) throws ClassNotFoundException {
    	try {
        	String autor = inputAutor.getText();
        	String nome = inputNome.getText();
        	String categoria = choiceCategoria.getValue().toString();
        	Double preco = Double.parseDouble(inputPreco.getText());
        	int estoque = Integer.parseInt(inputEstoque.getText());
        	int id = 0;
    		try {
    			id = getId();
    		} catch (IOException e) {
    			labelErroLivro.setText("Erro ao gerar ID");
    			e.printStackTrace();
    		}
        	
        	Livro novoLivro = new Livro(id, estoque, preco, nome, autor, categoria);
        	
        	ArrayList<Livro> listLivros = readerObject("src/arquivos/livros.txt");
        	listLivros.add(novoLivro);
        	writerObject("src/arquivos/livros.txt", listLivros);
        	carregarTable();
		} catch (Exception e) {
			System.out.println(e);
			labelErroLivro.setText("Campo vazio ou incorreto");
		}

    }
    
 
	public void carregarCategorias() {
	    List<Categorias> categoriasLista = new ArrayList<Categorias>();
	    ObservableList<Categorias> obsCategorias;
	    
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
		
		choiceCategoria.getItems().addAll(obsCategorias.sorted());
	}
	
	@Override
	public void carregarTable() throws ClassNotFoundException {
		table.getItems().clear();
		ArrayList<Livro> listLivros = readerObject("src/arquivos/livros.txt");
		ObservableList<Livro> listLivrosObs = FXCollections.observableArrayList(listLivros);
		
		tableAutor.setCellValueFactory(new PropertyValueFactory<Livro, String>("autor"));
		tableEstoque.setCellValueFactory(new PropertyValueFactory<Livro, Integer>("estoque"));
		tableId.setCellValueFactory(new PropertyValueFactory<Livro, Integer>("id"));
		tableNome.setCellValueFactory(new PropertyValueFactory<Livro, String>("nome"));
		tablePreco.setCellValueFactory(new PropertyValueFactory<Livro, Integer>("preco"));
		tableCategoria.setCellValueFactory(new PropertyValueFactory<Livro, String>("categoria"));
		
		table.setItems(listLivrosObs);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarCategorias();
		try {
			carregarTable();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList readerObject(String caminho) throws ClassNotFoundException {
		try {
			FileInputStream file = new FileInputStream(new File(caminho));
			ObjectInputStream input = new ObjectInputStream(file);
	    	ArrayList<Livro> listLivros = (ArrayList<Livro>) input.readObject();
			return listLivros;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
    public int getId() throws IOException {
    	int oldId = Integer.parseInt(reader("src/arquivos/id.txt"));
    	int newId = oldId + 1;
    	writer("src/arquivos/id.txt", Integer.toString(newId), false);
		return newId;
    }
}
