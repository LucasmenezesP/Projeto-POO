package controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import models.Arquivo;
import models.Cliente;
import models.Livro;

public class FXMLClientesController extends Arquivo implements Initializable {

    @FXML
    private ListView<String> lstClientesAtivos;

    @FXML
    private ListView<String> lstClientesInativos;

    @FXML
    void btnCadastrar(ActionEvent event) {
    	Main.changeScreen(3);
    }
    
    @FXML
    void btnLivros(ActionEvent event) {
    	Main.changeScreen(1);
    }
    
	public void carregarClientes() throws ClassNotFoundException {
		lstClientesAtivos.getItems().clear();
		ArrayList<Cliente> lstClientes = readerObject("src/arquivos/clientes.txt");
		ArrayList<Cliente> lstClientesExcluidos = readerObject("src/arquivos/clientesExcluidos.txt");
		try {
			for (Cliente obj : lstClientes) {
				lstClientesAtivos.getItems().add(obj.getText());
			}
		} catch (Exception e) {
			System.out.println("Sem clientes ativos.");
		}
		
		try {
			for (Cliente obj : lstClientesExcluidos) {
				lstClientesInativos.getItems().add(obj.getText());
			}
		} catch (Exception e) {
			System.out.println("Sem clientes excluidos.");
		}
	}

	@Override
	public ArrayList readerObject(String caminho) throws ClassNotFoundException {
		try {
			FileInputStream file = new FileInputStream(new File(caminho));
			ObjectInputStream input = new ObjectInputStream(file);
	    	ArrayList<Cliente> listCliente = (ArrayList<Cliente>) input.readObject();
			return listCliente;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			carregarClientes();
			ArrayList<Cliente> lstClientes = readerObject("src/arquivos/clientes.txt");
			System.out.println(lstClientes);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
