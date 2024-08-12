package novoCaixa;

public class Lancamento {
	
	private String descricao;
	private double valor;
	
	
	public Lancamento(String descricao, double valor) {
		this.descricao = descricao;
		this.valor = valor;
	}
	//construtor


	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
	

}
