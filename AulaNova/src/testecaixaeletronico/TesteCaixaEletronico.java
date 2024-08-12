package testecaixaeletronico;
import novoCaixa.*;

public class TesteCaixaEletronico {
	public static void main(String[] args) {
		Conta c1 = new Conta(222, new Cliente("Daniel", "1231341"), 123);
		Conta c2 = new Conta(211, new Cliente("Victor", "1231341"), 123);
		Conta c3 = new Conta(224, new Cliente("Victoria", "1231341"), 123);
		Conta c4 = new Conta(225, new Cliente("Marcos", "1231341"), 123);
		Conta c5 = new Conta(12212, new Cliente("Mercedes", "1231341"), 123); 
		//criação de contas
		
		
		CadastroContas cd1 = new CadastroContas(5); // criacao do banco de dados
		
		cd1.adicionaConta(c1);	
		cd1.adicionaConta(c2);
		cd1.adicionaConta(c3);
		cd1.adicionaConta(c4); 
		cd1.adicionaConta(c5); //adicionando as contas ao banco de dados
		
		Terminal tt = new Terminal(cd1); //criando o terminal
		
		tt.iniciaOperacao(); //faz o terminal iniciar
		
		
		
		
	
	
	}
}
