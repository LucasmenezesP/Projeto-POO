package models;

public class Cliente {
	private String nome;
	private int idade;
	private String cpf;
	
	public Cliente() {}
	
	public Cliente(String nome, int idade, String cpf) {
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
	
	
}
