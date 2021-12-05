package models;

import java.io.Serializable;

public class Cliente implements Serializable {
	private int id;
	private String nome;
	private int idade;
	private String cpf;
	private String numero;
	
	public Cliente() {}
	
	public Cliente(int id, String nome, int idade, String cpf, String numero) {
		this.setId(id);
		this.setNome(nome);
		this.setCpf(cpf);
		this.setIdade(idade);
		this.setNumero(numero);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
}
