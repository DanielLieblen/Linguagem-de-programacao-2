package novoCaixa;

public class Conta {
	private int numero;
	private Cliente titular;
	private int senha;
	private double saldo;
	private HistoricoDeLancamentos historico;
	
	
	//construtores
	public Conta(int numero, Cliente titular, int senha) {
		this.numero = numero;
		this.titular = titular;
		this.senha = senha;
		this.historico = new HistoricoDeLancamentos(10);
		
	}
	
	public HistoricoDeLancamentos getHistoricoDeLancamentos() {
		return this.historico;
	}
		
	//fim construtores
	
	public int getNumero() {
		return this.numero;
	}
	
	public Cliente getTitular() {
		return this.titular;
	}
	
	public void setTitular(Cliente titular) {
		this.titular = titular;
	}
	
	
	public double verificaSaldo(int senha) {
		if(senhaEhValida(senha)) {
			return this.saldo;
		}
		return -1;
	}
	
	public boolean creditaValor(double valor, String operacaoBancaria) {
		if (valor < 0) {
			return false;
		}
		this.saldo += valor;
		this.historico.insereLancamento(new Lancamento(operacaoBancaria, valor));
		return true;
		}
	
	public boolean debitaValor(double valor, int senha, String operacaoBancaria) {
		if (!senhaEhValida(senha) | valor > this.saldo | valor < 0) {
			return false;
		}
		this.saldo -= valor;
		this.historico.insereLancamento(new Lancamento(operacaoBancaria, -valor));
		return true;
		}
	
	
	private boolean senhaEhValida(int senha) {
		return this.senha == senha;
	}
	
	
	
}
