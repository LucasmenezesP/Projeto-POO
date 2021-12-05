package controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Arquivo;
import models.Cliente;
import models.InterfaceTableView;
import models.Livro;
import models.UtilitarioId;

public class FXMLClientesAtivosController extends Arquivo implements Initializable, UtilitarioId, InterfaceTableView {
	
    @FXML
    private TextField inputCPFCliente, inputIdadeCliente, inputNomeCliente, inputNumeroCliente;

    @FXML
    private TableView<Cliente> table;

    @FXML
    private TableColumn<Cliente, String> tableCPF;

    @FXML
    private TableColumn<Cliente, Integer> tableId;

    @FXML
    private TableColumn<Cliente, Integer> tableIdade;

    @FXML
    private TableColumn<Cliente, String> tableNome;

    @FXML
    private TableColumn<Cliente, String> tableNumero;

    @FXML
    private Label txtErroCPF;

    @FXML
    protected void btnCadastrarCliente(ActionEvent event) throws IOException, ClassNotFoundException {
    	String nome = inputNomeCliente.getText();
    	String cpf = inputCPFCliente.getText();
    	String numero = inputNumeroCliente.getText();
    	int idade = Integer.parseInt(inputIdadeCliente.getText());
    	int idCliente = getId();
    	
    	if (validarCPF(cpf)) {
        	int id = getId();
        	Cliente novoLivro = new Cliente(id, nome, idade, cpf, numero);
        	ArrayList<Cliente> listClientes = readerObject("src/arquivos/clientes.txt");
        	listClientes.add(novoLivro);
        	writerObject("src/arquivos/clientes.txt", listClientes);
        	txtErroCPF.setText("Cadastrado com sucesso!");
        	carregarTable();
    	}else {
    		txtErroCPF.setText("CPF incorreto");
    	}
    	
//    	Cliente novoLivro = new Cliente(id, nome, idade, cpf);
//    	ArrayList<Cliente> listClientes = new ArrayList<Cliente>();
//    	
//    	ArrayList<Cliente> listClientes = readerObject("src/arquivos/clintes.txt");
//    	listClientes.add(novoLivro);
//    	writerObject("src/arquivos/clientes.txt", listClientes); 
    }

    @FXML
    protected void btnCadastrarVenda(ActionEvent event) {
    	Main.changeScreen(2);
    }

    @FXML
    protected void btnClientesInativos(ActionEvent event) {
    	Main.changeScreen(4);
    }

    @FXML
    protected void btnEstoque(ActionEvent event) {
    	Main.changeScreen(1);
    }

	@Override
	public int getId() throws IOException {
    	int oldId = Integer.parseInt(reader("src/arquivos/idCliente.txt"));
    	int newId = oldId + 1;
    	writer("src/arquivos/idCliente.txt", Integer.toString(newId), false);
		return newId;
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
	
	@Override
	public void carregarTable() throws ClassNotFoundException {
		table.getItems().clear();
		ArrayList<Cliente> listClientes = readerObject("src/arquivos/clientes.txt");
		ObservableList<Cliente> listClientesObs = FXCollections.observableArrayList(listClientes);
		
		tableCPF.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));
		tableId.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("id"));
		tableIdade.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("idade"));
		tableNome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
		tableNumero.setCellValueFactory(new PropertyValueFactory<Cliente, String>("numero"));
		
		table.setItems(listClientesObs);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			carregarTable();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//Autor alanielnascimento
	//Link GitHub: https://github.com/alanielnascimento/Validar-CPF
	public static boolean validarCPF(String cpf) {
		return isCPF(cpf);
	}
	
	public static boolean isCPF(String cpf) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999") || (cpf.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

}
