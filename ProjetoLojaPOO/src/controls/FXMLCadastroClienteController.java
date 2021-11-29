package controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Arquivo;
import models.Cliente;
import models.Livro;

public class FXMLCadastroClienteController extends Arquivo implements Initializable {

    @FXML
    private TextField txtCpfCliente;

    @FXML
    private TextField txtIdadeCliente;

    @FXML
    private TextField txtNomeCliente;
    
    @FXML
    private Label labelValidacao;
    
    @FXML
    private TextField txtIdExcluir;

    @FXML
    protected void actionButton(ActionEvent event) throws ClassNotFoundException, NumberFormatException, IOException {
    	String cpf = txtCpfCliente.getText();
    	String nome = txtNomeCliente.getText();
    	int idade = Integer.parseInt(txtIdadeCliente.getText());
    	
    	if (validarCPF(cpf)) {
        	int id = getId();
        	Cliente novoLivro = new Cliente(id, nome, idade, cpf);
        	ArrayList<Cliente> listClientes = readerObject("src/arquivos/clientes.txt");
        	listClientes.add(novoLivro);
        	writerObject("src/arquivos/clientes.txt", listClientes);
        	labelValidacao.setText("Cadastrado com sucesso!");
    	}else {
        	labelValidacao.setText("CPF incorreto");
    	}
//    	
//    	Cliente novoLivro = new Cliente(id, nome, idade, cpf);
//    	ArrayList<Cliente> listClientes = new ArrayList<Cliente>();
//    	
//    	ArrayList<Cliente> listClientes = readerObject("src/arquivos/clintes.txt");
//    	listClientes.add(novoLivro);
//    	writerObject("src/arquivos/clientes.txt", listClientes); 
    }

    private int getId() throws NumberFormatException, IOException {
    	int oldId = Integer.parseInt(reader("src/arquivos/idCliente.txt"));
    	int newId = oldId + 1;
    	writer("src/arquivos/idCliente.txt", Integer.toString(newId), false);
		return newId;
	}

	@FXML
    protected void btnCadastrados(ActionEvent event) {
    	Main.changeScreen(4);
    }

    @FXML
    protected void btnLivros(ActionEvent event) {
    	Main.changeScreen(1);
    }
    
    @FXML
    protected void buttonExcluir(ActionEvent event) throws ClassNotFoundException {
    	int id = Integer.parseInt(txtIdExcluir.getText());
    	ArrayList<Cliente> lstClientes = readerObject("src/arquivos/clientes.txt");
    	ArrayList<Cliente> lstClientesExcluidos = readerObject("src/arquivos/clientesExcluidos.txt");
    	for (int i = 0; i < lstClientes.size(); i++) {
    		Cliente objAtual = lstClientes.get(i);
    		System.out.println(objAtual);
    		if (objAtual.getId() == id) {
    			lstClientesExcluidos.add(objAtual);
    			lstClientes.remove(i);
    		}
    	}
//    	ArrayList<Cliente> lstClientesExcluidos = new ArrayList<Cliente>();
    	writerObject("src/arquivos/clientes.txt", lstClientes); 
    	writerObject("src/arquivos/clientesExcluidos.txt", lstClientesExcluidos);
    	FXMLClientesController clt = new FXMLClientesController();
    	clt.carregarClientes();
    }
    
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

	@Override
	public ArrayList<Cliente> readerObject(String caminho) throws ClassNotFoundException {
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
