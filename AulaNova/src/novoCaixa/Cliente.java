package novoCaixa;

public class Cliente {
	private String titular;
	private String cpf;
	
	
	//construtor
	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.titular = nome;
	}


	public String getTitular() {
		return this.titular;
	}

	public String getCpf() {
		return this.cpf;
	}
	
	public void setTitular(String titular) {
		this.titular = titular;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
	
	
	
	

	
}
