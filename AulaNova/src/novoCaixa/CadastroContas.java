package novoCaixa;

public class CadastroContas { //aqui é o banco de dados
	private Conta[] contas;
	private int numeroDeContas;
	
	public CadastroContas(int numeroDeContas) {
		this.contas = new Conta[numeroDeContas];
	}
	
	public boolean adicionaConta(Conta conta) {
		if(this.numeroDeContas == this.contas.length | this.buscaConta(conta.getNumero()) != null){
			return false;
		}
		this.contas[this.numeroDeContas++] = conta;
		return true;
		//adiciona uma nova conta caso as condicoes sejam verdadeiras
		//ou seja nao entra no if
		//na proxima casa do vetor de contas
		//numeroDeContas = posição atual do vetor
	}
	
	public Conta buscaConta(int numero) {
		for(int i = 0; i < this.numeroDeContas; i++) {
			if(numero == this.contas[i].getNumero()) {
				return this.contas[i];
			}
		}
		return null;
	}//busca as contas pra ver se ela ja existe no vetor de conta
		
}
