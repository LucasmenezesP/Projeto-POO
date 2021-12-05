package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Venda implements Serializable {
	
	private Cliente cliente = new Cliente();
	private Livro livro = new Livro();
	private int quantidade;
	
	public Venda(Cliente cliente, Livro livro, int quantidade) {
		this.setCliente(cliente);
		this.setLivro(livro);
		this.setQuantidade(quantidade);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro produto) {
		this.livro = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public Double getPreco() {
		return livro.getPreco() * quantidade;
	}
	
	public String getAutorLivro() {
		return livro.getAutor();
	}
	
	public String getNomeLivro() {
		return livro.getNome();
	}
	
	public String getNomeCliente() {
		return cliente.getNome();
	}
	
	public String getCpfCliente() {
		return cliente.getCpf();
	}
}
