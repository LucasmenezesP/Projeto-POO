package controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Arquivo;
import models.Cliente;
import models.Livro;
import models.Venda;

public class FXMLLivrosVendaController extends Arquivo {
    @FXML
    private TextField idCliente;

    @FXML
    private TextField idProduto;

    @FXML
    private ListView<String> lstViewVendas;

    @FXML
    private TextField txtQuantidade;

    @FXML
    protected void btnCadastrar(ActionEvent event) {
    	Main.changeScreen(2);
    }

    @FXML
    protected void btnCadastrarVenda(ActionEvent event) throws ClassNotFoundException {
    	Cliente clienteComprador = getCliente();
    	Livro livroComprado = getLivro();
    	int quantidade = Integer.parseInt(txtQuantidade.getText());
    	if (clienteComprador == null || livroComprado == null) {
    		//
    	}else {
    		Venda venda = new Venda(clienteComprador, livroComprado, quantidade);
    		ArrayList<Venda> lstVendas = readerObject("src/arquivos/vendas.txt");
    		lstVendas.add(venda);
    		writerObject("src/arquivos/vendas.txt", lstVendas);
    		ArrayList<Livro> lstLivros = readerObject("src/arquivos/livros.txt");
    		ArrayList<Livro> lstLivrosDecrease = diminuirEstoque(lstLivros);
        	writerObject("src/arquivos/livros.txt", lstLivrosDecrease);
    	}
    }
    
    private ArrayList<Livro> diminuirEstoque(ArrayList<Livro> lstLivros) throws ClassNotFoundException {
    	int produtoId = Integer.parseInt(idProduto.getText()); 
    	int estoqueQuantidade = Integer.parseInt(txtQuantidade.getText());

    	for (int i = 0; i < lstLivros.size(); i++) {
    		Livro objAtual = lstLivros.get(i);
    		System.out.println(objAtual);
    		if (objAtual.getId() == produtoId) {
    			int estoqueAtual = objAtual.getEstoque();
    			objAtual.setEstoque(estoqueAtual - estoqueQuantidade);
    			lstLivros.remove(i);
    			lstLivros.add(i, objAtual);
    			return lstLivros;
    		}
    	}
		return lstLivros;
    }
    
    private Cliente getCliente() throws ClassNotFoundException {
    	Cliente clienteComprador;
    	int clienteId = Integer.parseInt(idCliente.getText());
    	ArrayList<Cliente> lstClientes = readerObject("src/arquivos/clientes.txt");
    	for (int i = 0; i < lstClientes.size(); i++) {
    		Cliente objAtual = lstClientes.get(i);
    		if (objAtual.getId() == clienteId) {
    			return objAtual;
    		}
    	}
		return null;
    }
    
    private Livro getLivro() throws ClassNotFoundException {
    	Livro livroComprado;
    	int produtoId = Integer.parseInt(idProduto.getText()); 
    	ArrayList<Livro> lstLivros = readerObject("src/arquivos/livros.txt");
    	for (int i = 0; i < lstLivros.size(); i++) {
    		Livro objAtual = lstLivros.get(i);
    		if (objAtual.getId() == produtoId) {
    			return objAtual;
    		}
    	}
		return null;
    }

    @FXML
    protected void btnClientes(ActionEvent event) {
    	Main.changeScreen(4);
    }
    
    @FXML
    protected void btnEstoque(ActionEvent event) {
    	Main.changeScreen(1);
    }

	@Override
	public ArrayList readerObject(String caminho) throws ClassNotFoundException {
		try {
			FileInputStream file = new FileInputStream(new File(caminho));
			ObjectInputStream input = new ObjectInputStream(file);
	    	ArrayList<?> listLivros = (ArrayList<?>) input.readObject();
			return listLivros;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
