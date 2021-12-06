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
    private TableColumn<Cliente, String> tableCPF, tableNome, tableNumero;

    @FXML
    private TableColumn<Cliente, Integer> tableId, tableIdade;

    @FXML
    private Label txtErroCPF;

    @FXML
    protected void btnCadastrarCliente(ActionEvent event) throws IOException, ClassNotFoundException {
    	String nome = inputNomeCliente.getText();
    	String cpf = inputCPFCliente.getText();
    	String numero = inputNumeroCliente.getText();
    	int idade = Integer.parseInt(inputIdadeCliente.getText());
    	
    	if (isCpf(cpf)) {
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
    
    @FXML
    protected void buttonRefresh(ActionEvent event) throws ClassNotFoundException {
    	carregarTable();
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
	
//	MIT License
//
//	Copyright (c) 2020 Pedro Daniel 
//
//	Permission is hereby granted, free of charge, to any person obtaining a copy
//	of this software and associated documentation files (the "Software"), to deal
//	in the Software without restriction, including without limitation the rights
//	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//	copies of the Software, and to permit persons to whom the Software is
//	furnished to do so, subject to the following conditions:
//
//	The above copyright notice and this permission notice shall be included in all
//	copies or substantial portions of the Software.
//
//	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//	SOFTWARE.
//
//  https://github.com/pedrodanieljardim/cpf-cnpj-validator
	
    private boolean isCpf(String cpf) {
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");
        try {
            Long.parseLong(cpf);
        } catch (NumberFormatException e) {
            return false;
        }
        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;
        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;
        for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();
            // multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4
            // e assim por diante.
            d1 = d1 + (11 - nCount) * digitoCPF;
            // para o segundo digito repita o procedimento incluindo o primeiro
            // digito calculado no passo anterior.
            d2 = d2 + (12 - nCount) * digitoCPF;
        }
        ;
        // Primeiro resto da divisão por 11.
        resto = (d1 % 11);
        // Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
        // menos o resultado anterior.
        if (resto < 2)
            digito1 = 0;
        else
            digito1 = 11 - resto;
        d2 += 2 * digito1;
        // Segundo resto da divisão por 11.
        resto = (d2 % 11);
        // Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
        // menos o resultado anterior.
        if (resto < 2)
            digito2 = 0;
        else
            digito2 = 11 - resto;
        // Digito verificador do CPF que está sendo validado.
        String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());
        // Concatenando o primeiro resto com o segundo.
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
        // comparar o digito verificador do cpf com o primeiro resto + o segundo
        // resto.
        return nDigVerific.equals(nDigResult);
    }
}
