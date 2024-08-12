package novoCaixa;

import java.util.Scanner;

public class Terminal {	//a interface do bagulho
	private Caixa meuCaixa;
	private int modoAtual;
	
	public Terminal(CadastroContas bd) {	//construtor de terminal, com a data base
		this.meuCaixa = new Caixa(this, bd); //ele referencia ele mesmo fazendo a chamada de caixa
	}
	public void iniciaOperacao() { 
		int opcao;
		opcao = this.getOpcao();
		while(opcao != 8) {
			switch(opcao) {
			case 1: double saldo = this.meuCaixa.consultaSaldo(getInt("Numero da Conta"), getInt("Senha")); //chama o objeto de scanner
					if(saldo != -1) { //se nao der erro entra aqui
						System.out.println("Saldo Atual: " + saldo);
					}else {	//se der erro entra nessa segunda condicao
						System.out.println("Conta/senha inválida");
					}
					break; //consulta saldo
			
			case 2: boolean b = this.meuCaixa.efetuaSaque(getInt("Numero da Conta"), (double)getInt("Valor"), getInt("Senha"));
					if(b) {
						System.out.println("Retire o dinheiro");
					}else {
						System.out.println("Pedido de saque recusado");
					}
					break; //saque
			case 3: this.meuCaixa.recarrega();
					break;
			
			case 4: boolean depositoDinheiro = this.meuCaixa.efetuaDepositoDinheiro(getInt("Numero da Conta"), getInt("Valor a ser depositado"));
					if(depositoDinheiro) {
						System.out.println("Deposito Efeuado com sucesso");
					}else {
						System.out.println("Não foi possivel efetuar deposito");
					}
					
					break;
			case 5: boolean depositoCheque = this.meuCaixa.efetuaDepositoCheque(getInt("Numero da Conta"), getInt("Valor a ser depositado"));
					if(depositoCheque) {
						System.out.println("Deposito Efeuado com Sucesso");
					}else {
						System.out.println("Não foi possivel efetuar deposito");
					}
					
					break;
			case 6: boolean transferindo = this.meuCaixa.transferencia(getInt("Numero da Conta de Retirada"), getInt("numero da conta que receberá o deposito"), getInt("valor da transferencia"), getInt("Senha"));
					if(transferindo) {
						System.out.println("Transferencia Feita com Sucesso");
					}else {
						System.out.println("Não foi possivel efetuar transferencia");
					}
			
					break;
			case 7: String extrato = this.meuCaixa.geraExtratoBancario(getInt("Numero da Conta"), getInt("Senha"));
					if(extrato != null) {
						System.out.println(extrato);
					}else {
						System.out.println("Não foi Possivel Emitir Extrato!");
					}
					break;
			}
			opcao = getOpcao();
		}
	}
	
	
	
	
	public void setModo(int modo) {
		if(modo ==0 || modo == 1) {
			this.modoAtual = modo;
			//vai mudando o modo para cliente ou supervisor
		}
	}
	
	private int getOpcao() {
		int opcao;
		do {
			if(this.modoAtual == 1) {
				opcao = getInt("Opcao:\n 1 - Consulta Saldo, \n 2- Saque, \n 4 - Deposito em Dinheiro, \n 5 - Deposito em cheque, \n 6 - Transferencia, \n 7 - Extrato, \n 8 - Sair");
				if(opcao != 1 & opcao != 2 & opcao !=4 & opcao !=5 & opcao !=6 & opcao !=7 & opcao !=8) {
					opcao = 0;
				}
			}else {
				opcao = getInt("Opcao:\n 3 - Recarrega,\n 8 - Sair");
				if(opcao != 3 & opcao != 7) {
					opcao = 0;
				}
			}
		}while(opcao == 0);
		return opcao;
	}
	
	private int getInt(String string) { //objeto pra scannear recebe uma string como input
		Scanner r = new Scanner(System.in);
		System.out.println("Entre com " + string);
		if(r.hasNextInt()) { //verifica a input do usuario pra ver se é Int mesmo, se nao for joga pro buffer do teclado e pede pra digitar de novo
			return r.nextInt();	//se for verdadeiro retorna ele no objeto r
		}
		String st = r.next();
		System.out.println("Erro na Leitura de Dados");
		return 0;
	}
}
