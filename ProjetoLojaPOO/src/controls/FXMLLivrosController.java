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
    private TextField txtEstoqueID, txtEstoqueQuantidade;


    @FXML
    protected void btnDecrease(ActionEvent event) throws ClassNotFoundException {
    	System.out.println("Botão Increase");
    	int id = Integer.parseInt(txtEstoqueID.getText());
    	int estoqueQuantidade = Integer.parseInt(txtEstoqueQuantidade.getText());
    	ArrayList<Livro> lstLivrosI = readerObject("src/arquivos/livros.txt");
    	for (int i = 0; i < lstLivrosI.size(); i++) {
    		Livro objAtual = lstLivrosI.get(i);
    		System.out.println(objAtual);
    		if (objAtual.getId() == id) {
    			int estoqueAtual = objAtual.getEstoque();
    			objAtual.setEstoque(estoqueAtual - estoqueQuantidade);
    			lstLivrosI.remove(i);
    			lstLivrosI.add(i, objAtual);
    		}
    	}
    	writerObject("src/arquivos/livros.txt", lstLivrosI); 	
    	carregarLivros();
    }

    @FXML
    protected void btnIncrease(ActionEvent event) throws ClassNotFoundException {
    	System.out.println("Botão Increase");
    	int id = Integer.parseInt(txtEstoqueID.getText());
    	int estoqueQuantidade = Integer.parseInt(txtEstoqueQuantidade.getText());
    	ArrayList<Livro> lstLivrosI = readerObject("src/arquivos/livros.txt");
    	for (int i = 0; i < lstLivrosI.size(); i++) {
    		Livro objAtual = lstLivrosI.get(i);
    		System.out.println(objAtual);
    		if (objAtual.getId() == id) {
    			int estoqueAtual = objAtual.getEstoque();
    			objAtual.setEstoque(estoqueAtual + estoqueQuantidade);
    			lstLivrosI.remove(i);
    			lstLivrosI.add(i, objAtual);
    		}
    	}
    	writerObject("src/arquivos/livros.txt", lstLivrosI);
    	carregarLivros();
    }
    
    @FXML
    protected void btnCadastrar(ActionEvent event) {
    	Main.changeScreen(2);
    }
    @FXML
    void btnClientes(ActionEvent event) {
    	Main.changeScreen(4);
    }

	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			carregarLivros();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void carregarLivros() throws ClassNotFoundException {
		lstView.getItems().clear();
		ArrayList<Livro> lstLivros = readerObject("src/arquivos/livros.txt");
		System.out.println(lstLivros);
		for (Livro obj : lstLivros) {
			System.out.println(obj.getText());
			lstView.getItems().add(obj.getText());
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
}