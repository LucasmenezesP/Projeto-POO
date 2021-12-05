package controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Arquivo;
import models.Cliente;
import models.InterfaceTableView;
import models.Livro;
import models.Venda;

public class FXMLLivrosVendaController extends Arquivo implements Initializable, InterfaceTableView {
	
    @FXML
    private TextField inputIdCliente, inputIdLivro, inputQuantidade;
    
    @FXML
    private TableView<Venda> table;
    
    @FXML
    private TableColumn<Venda, String> tableAutor;

    @FXML
    private TableColumn<Venda, String> tableCliente;

    @FXML
    private TableColumn<Venda, String> tableLivro;
    
    @FXML
    private TableColumn<Venda, String> tableCpf;

    @FXML
    private TableColumn<Venda, Integer> tablePreco;

    @FXML
    private TableColumn<Venda, Integer> tableQuantidade;
    
    @FXML
    void btnClientesAtivos(ActionEvent event) {
    	Main.changeScreen(3);
    }

    @FXML
    void btnClientesInativos(ActionEvent event) {
    	Main.changeScreen(4);
    }

    @FXML
    void btnConfirmarVenda(ActionEvent event) throws ClassNotFoundException {
    	Cliente clienteComprador = new Cliente(0, "Cliente não informado", 0, "", "");
    	if (inputIdCliente.getText() != null) {
        	int idCliente = Integer.parseInt(inputIdCliente.getText());
        	ArrayList<Cliente> listaClientes = readerObjectCliente("src/arquivos/clientes.txt");
        	
           	for (int i = 0; i < listaClientes.size(); i++) {
        		Cliente objAtual = listaClientes.get(i);
        		System.out.println(objAtual);
        		if (objAtual.getId() == idCliente) {
        			clienteComprador = objAtual;
        		}
        	}
    	}
    	System.out.println("Cliente comprador: " + clienteComprador);

    	int idLivro = Integer.parseInt(inputIdLivro.getText());
    	int quantidade = Integer.parseInt(inputQuantidade.getText());
    	Livro livroVendido = new Livro();
    	ArrayList<Livro> lstLivros = readerObjectLivro("src/arquivos/livros.txt");
    	for (int i = 0; i < lstLivros.size(); i++) {
    		Livro livroAtual = lstLivros.get(i);
    		if (livroAtual.getId() == idLivro) {
    			int estoqueAtual = livroAtual.getEstoque();
    			livroAtual.setEstoque(estoqueAtual - quantidade);
    			lstLivros.remove(i);
    			lstLivros.add(i, livroAtual);
    			livroVendido = livroAtual;
    			writerObject("src/arquivos/livros.txt", lstLivros);
    		}
    	}
    	System.out.println("Livro vendido: " + livroVendido);
    	
    	ArrayList<Venda> lstVendas = readerObject("src/arquivos/vendas.txt");
    	Venda novaVenda = new Venda(clienteComprador, livroVendido, quantidade);
    	lstVendas.add(novaVenda);
    	System.out.println(lstVendas);
//    	ArrayList<Venda> lstVendas = new ArrayList<Venda>();
		writerObject("src/arquivos/vendas.txt", lstVendas);
		carregarTable();
    }
    
//    private Livro diminuirEstoque() throws ClassNotFoundException {
//    	ArrayList<Livro> lstLivros = readerObject("src/arquivos/livros.txt");
//    	int produtoId = Integer.parseInt(inputIdLivro.getText()); 
//    	int estoqueQuantidade = Integer.parseInt(inputQuantidade.getText());
//    	Livro livroVendido = new Livro();
//
//    	for (int i = 0; i < lstLivros.size(); i++) {
//    		Livro objAtual = lstLivros.get(i);
//    		System.out.println(objAtual);
//    		if (objAtual.getId() == produtoId) {
//    			int estoqueAtual = objAtual.getEstoque();
//    			objAtual.setEstoque(estoqueAtual - estoqueQuantidade);
//    			lstLivros.remove(i);
//    			lstLivros.add(i, objAtual);
//    			livroVendido = objAtual;
//    			writerObject("src/arquivos/livros.txt", lstLivros);
//    			return livroVendido;
//    		}
//    	}
//		return livroVendido;
//    }

    @FXML
    void btnEstoque(ActionEvent event) {
    	Main.changeScreen(1);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
	    	ArrayList<Venda> listLivros = (ArrayList<Venda>) input.readObject();
			return listLivros;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList readerObjectLivro(String caminho) throws ClassNotFoundException {
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
	
	public ArrayList readerObjectCliente(String caminho) throws ClassNotFoundException {
		try {
			FileInputStream file = new FileInputStream(new File(caminho));
			ObjectInputStream input = new ObjectInputStream(file);
	    	ArrayList<Cliente> listLivros = (ArrayList<Cliente>) input.readObject();
			return listLivros;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void carregarTable() throws ClassNotFoundException {
		table.getItems().clear();
		ArrayList<Venda> listVendas = readerObject("src/arquivos/vendas.txt");
		ObservableList<Venda> listVendasObs = FXCollections.observableArrayList(listVendas);
		
		tableAutor.setCellValueFactory(new PropertyValueFactory<Venda, String>("autorLivro"));
		tableLivro.setCellValueFactory(new PropertyValueFactory<Venda, String>("nomeLivro"));
		tableCliente.setCellValueFactory(new PropertyValueFactory<Venda, String>("nomeCliente"));
		tableCpf.setCellValueFactory(new PropertyValueFactory<Venda, String>("cpfCliente"));
		tableQuantidade.setCellValueFactory(new PropertyValueFactory<Venda, Integer>("quantidade"));
		tablePreco.setCellValueFactory(new PropertyValueFactory<Venda, Integer>("preco"));
		
		table.setItems(listVendasObs);
		
	}

}
