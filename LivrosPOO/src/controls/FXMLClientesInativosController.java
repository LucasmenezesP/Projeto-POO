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

public class FXMLClientesInativosController extends Arquivo implements Initializable, InterfaceTableView {
	
    @FXML
    private TextField inputId;

    @FXML
    private TableView<Cliente> table;

    @FXML
    private TableColumn<Cliente, String> tableCPF, tableNome, tableNumero;

    @FXML
    private TableColumn<Cliente, Integer> tableId, tableIdade;

    @FXML
    void btnConfirmar(ActionEvent event) throws ClassNotFoundException {
    	int id = Integer.parseInt(inputId.getText());
    	ArrayList<Cliente> clientesAtivos = readerObject("src/arquivos/clientes.txt");
    	ArrayList<Cliente> clientesInativos = readerObject("src/arquivos/clientesExcluidos.txt");
    	for (int i = 0; i < clientesAtivos.size(); i++) {
    		Cliente clienteAtual = clientesAtivos.get(i);
    		if (clienteAtual.getId() == id) {
    			clientesInativos.add(clienteAtual);
    			clientesAtivos.remove(i);
    			
    		}
    	}
    	writerObject("src/arquivos/clientes.txt", clientesAtivos); 
    	writerObject("src/arquivos/clientesExcluidos.txt", clientesInativos);
    	carregarTable();
    }

    @FXML
    void btnCadastrarVenda(ActionEvent event) {
    	Main.changeScreen(2);
    }

    @FXML
    void btnClientesAtivos(ActionEvent event) {
    	Main.changeScreen(3);
    }

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
	public void carregarTable() throws ClassNotFoundException {
		table.getItems().clear();
		ArrayList<Cliente> listClientesInativos = readerObject("src/arquivos/clientesExcluidos.txt");
		ObservableList<Cliente> listClientesInativosObs = FXCollections.observableArrayList(listClientesInativos);
		
		tableCPF.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));
		tableId.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("id"));
		tableIdade.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("idade"));
		tableNome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
		tableNumero.setCellValueFactory(new PropertyValueFactory<Cliente, String>("numero"));
		
		table.setItems(listClientesInativosObs);
	}

	@Override
	public ArrayList readerObject(String caminho) throws ClassNotFoundException {
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
    
}
