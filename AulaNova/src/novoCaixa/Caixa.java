package novoCaixa;

public class Caixa {
	private Terminal meuTerminal;
	private CadastroContas bdContas;
	private double saldo;
	
		
	public Caixa(Terminal terminal, CadastroContas bd) {
		this.meuTerminal = terminal;
		this.bdContas = bd;

	}
	
	public double consultaSaldo(int numeroDaConta, int senha) {
		Conta conta;
		conta = this.bdContas.buscaConta(numeroDaConta);
		if(conta != null) { //se a conta estiver preenchida verifica
							//o saldo utilizando a senha como input
			return conta.verificaSaldo(senha);
		}else {
			return -1; //codigo de erro
		}
	}
	public boolean efetuaSaque(int numeroDaConta, double valor, int senha) {
		if(valor < 0 || (valor%50)!=0 || valor > 500 || valor > this.saldo) {
			return false;
		}
		Conta conta = bdContas.buscaConta(numeroDaConta); //busca a conta dentro do banco de dados, fazendo o objeto conta receber verdadeiro ou falso
		if(conta == null || !conta.debitaValor(valor, senha, "Saque Automatico")) {
			return false;
		}
		this.liberaCedulas((int)(valor/50));//converte o valor em cedulas de 50 faz tambem a conversao pra int
		this.saldo -= valor;	//saldo do caixa é decrescido
		if(this.saldo < 500) {
			this.meuTerminal.setModo(0); //muda o modo para supervisor s enao tiver dinheiro suficiente pra retirada
		}
		return true;
	}
	
	public boolean efetuaDepositoDinheiro(int numeroDaConta, double valor) {
		Conta conta;
		conta = bdContas.buscaConta(numeroDaConta); //busca a conta dentro do banco de dados
		if(valor<0 || valor > 500 || valor > this.saldo) {
			return false;
		}
		if(conta == null || !conta.creditaValor(valor, "Deposito em Dinheiro")) {
			return false;
		}
		this.saldo -=valor;
		if(this.saldo <500) {
			this.meuTerminal.setModo(0);
		}
		return true;
	}
	
	public boolean efetuaDepositoCheque(int numeroDaConta, double valor) {
		if(valor<0 || valor > 500 || valor > this.saldo) {
			return false;
		}
		Conta conta;
		conta = bdContas.buscaConta(numeroDaConta); //busca a conta dentro do banco de dados
		if(conta == null || !conta.creditaValor(valor, "Deposito em Cheque")) {
			return false;
		}
		this.saldo -=valor;
		if(this.saldo <500) {
			this.meuTerminal.setModo(0);
		}
		return true;
	}
	
	public boolean transferencia(int numeroDaConta1, int numeroDaConta2, double valor, int senha) {
		Conta conta1 = bdContas.buscaConta(numeroDaConta1);
		Conta conta2 = bdContas.buscaConta(numeroDaConta2);
		if(conta1 == null | conta2 == null | !conta1.debitaValor(valor, senha, "transferencia")) {
			return false;
		}else {
			conta2.creditaValor(valor, "transferencia");
			this.saldo -= valor; //saldo do caixa é decrescido
			if(this.saldo < 500) {
				this.meuTerminal.setModo(0);
			}
		}
		return true;
		
	}
	
	public String geraExtratoBancario(int numeroDaConta, int senha) {
		Conta conta;
		conta = bdContas.buscaConta(numeroDaConta);
		HistoricoDeLancamentos historico = conta.getHistoricoDeLancamentos();
		if(conta != null) {
			if(historico != null) {
				return "Extrato da conta:\n" + conta.getHistoricoDeLancamentos().geraHistoricoDeLancamentos() + "\n" + "O saldo atual da conta é: " +conta.verificaSaldo(senha);
			}
		}
		return "não foi possivel imprimir";
	}	
	
	
	public void recarrega() {
		this.saldo = 2000;
		this.meuTerminal.setModo(1);
	}
	
	private void liberaCedulas(int quantidade) {
		while(quantidade-- > 0) {
			System.out.println("===/ R$50,00 /===");
		}
	}
}
