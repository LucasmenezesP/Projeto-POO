package models;

import java.io.Serializable;

public class Cliente implements Serializable {
	private int id;
	private String nome;
	private int idade;
	private String cpf;
	
	public Cliente() {}
	
	public Cliente(int id, String nome, int idade, String cpf) {
		this.setId(id);
		this.setNome(nome);
		this.setCpf(cpf);
		this.setIdade(idade);
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
	
	public String getText() {
		return id
	}
}
