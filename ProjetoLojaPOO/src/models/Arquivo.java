package models;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Arquivo {
	//Escreve strings no txt
	//Caso o boolean sob for true, uma string que for escrita não vai sobescrever outra que já
	//esteja lá; e se for false será substituido.
	public static void writer(String caminho, String texto, boolean sob) {
		String separador = System.getProperty("line.separator");
		FileWriter fw;
		try {
			fw = new FileWriter(caminho, sob);
			fw.write(texto + separador);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			System.out.println("Erro" + e.getMessage());
		}
	}
	
	//Lê o que estiver escrito no txt
	public static String reader(String caminho) throws IOException {
		try {
			FileReader fr = new FileReader(caminho);
			BufferedReader bf = new BufferedReader(fr);
			String linha = bf.readLine();
			System.out.println(linha);
			return linha;
		} catch (FileNotFoundException e) {
			System.out.println("Erro" + e.getMessage());
		}
		return null;
	}
	
	//Lê um objeto que está gravado no txt. No caso de algumas utilizações que foram feitas no projeto,
	//ele lê um ArrayList que contem objetos dentro.
	public static ArrayList<Livro> readerObject(String caminho) throws ClassNotFoundException {
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
	
	//Grava um ArrayList de objetos dentro de um txt
	public static void writerObject(String caminho, ArrayList array) {
		try {
			FileOutputStream file = new FileOutputStream(caminho, true);
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(array);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

