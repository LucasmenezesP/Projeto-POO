package models;

import java.util.ArrayList;

public class Venda {
	private Cliente cliente;
	private Livro livro;
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
	
	public String getText() {
		return cliente.getNome() + "comprou" + quantidade + "livro(s)" + livro.getNome();
	}
}
