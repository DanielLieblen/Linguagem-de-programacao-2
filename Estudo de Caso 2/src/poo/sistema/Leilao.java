package poo.sistema;
import java.util.ArrayList;


public class Leilao {
    private final Terminal meuTerminal;
    protected ArrayList<Lote> naoVendidos;
    private ArrayList<Lote> lotes;
    private int numeroProxLote;
//    private boolean isEncerrado;



    public Leilao(Terminal terminal) {
            this.meuTerminal =  terminal;
            this.lotes = new ArrayList<>();
            this.numeroProxLote = 1;
    }

    public boolean adicionarLote(String descricao) {
        if(this.lotes.add(new Lote(this.numeroProxLote, descricao))){
            this.numeroProxLote++;
            return true;
        }
        return false;
    }

    public boolean removerLote(int numero){
        if((numero >=1 ) && (numero < this.numeroProxLote)) {
            for(Lote lote : this.lotes){
                if(lote.getNumero() == numero){
                    this.lotes.remove(numero-1);
                    return true;
                }
            }
            System.err.println("lote invalido");
        }else{
            System.err.println("Numero do lote fora do intervalo válido");
        }
        return false;
    } //metodo pra deletar o lote


    public void mostraLotes() {
            for (Lote lote : this.lotes) {
                System.out.println(lote.getNumero() + ": " + lote.getDescricao());
                Lance melhorLance = lote.getMaiorLance();
                if (melhorLance != null) {
                    System.out.println(" Lance:" + melhorLance.getValor());
                } else {
                    System.out.println(" (Nenhum Lance)");
                }
            }
        }
        public Lote getLote(int numero) {
            if ((numero >= 1) && (numero < this.numeroProxLote)) {
                Lote loteSelecionado = this.lotes.get(numero - 1);
                if (loteSelecionado.getNumero() != numero) {
                    System.out.println("Erro!!");
                    return null;
                }
                return loteSelecionado;
            } else {
                System.out.println("Lote numero " + numero + "não existe");
                return null;
            }
        }

    public boolean darLance(Pessoa licitante, int numero, double valor){
        if((numero >=1 ) && (numero < this.numeroProxLote)) {
            Lote loteSelecionado = null;

            // Encontrar o lote correspondente ao número
            for(Lote lote : this.lotes){
                if(lote.getNumero() == numero){
                    loteSelecionado = lote;
                   break;
                }
            }

            if(loteSelecionado != null){
                loteSelecionado.lancePara(licitante, valor);
                return true;
            }else{
                System.err.println("Lote nao encontrado");
            }
        }else{
            System.err.println("Numero de lote fora do intervalo válido");
        }
        return false;
    }


//    public boolean encerrarLeilao(){
//        System.out.println("======= Detalhes dos Lotes =======");
//        for (Lote lote : this.lotes) {
//            System.out.println("Lote " + lote.getNumero() + ": " + lote.getDescricao());
//            Lance lanceVencedor = lote.getMaiorLance();
//            if(lanceVencedor != null){
//                System.out.println("Arrematador: " + lanceVencedor.getNome().getLicitante());
//                System.out.println("Valor do Lance: " + lanceVencedor.getValor());
//                meuTerminal.setModo(3);
//                return true;
//            }else{
//                System.out.println("Este lote não foi vendido.");
//            }
//        }
//        meuTerminal.setModo(1);
//        return false;
//    }//encerra leilao
//
//
//

//    public void salvarDescricaoLote(int numero, String novaDescricao) {
//        if ((numero >= 1) && (numero <= this.numeroProxLote)) {
//            Lote loteSelecionado = null;
//
//            // Encontre o lote correspondente ao número
//            for (Lote lote : this.lotes) {
//                if (lote.getNumero() == numero) {
//                    loteSelecionado = lote;
//                    break;  // Encontrou o lote, não é necessário continuar o loop
//                }
//            }
//
//            if (loteSelecionado != null) {
//                // Obtenha a descrição existente do lote
//                String descricaoExistente = loteSelecionado.getDescricao();
//
//                // Atualize a descrição do lote
//                loteSelecionado.setDescricao(novaDescricao);
//                System.out.println("Descrição do lote " + numero + " atualizada com sucesso.");
//                System.out.println("Descrição anterior: " + descricaoExistente);
//                System.out.println("Nova descrição: " + novaDescricao);
//            } else {
//                System.err.println("Lote não encontrado.");
//            }
//        } else {
//            System.err.println("Número de lote fora do intervalo válido.");
//        }
//    }


    public void encerrarLeilao() {
        if (meuTerminal != null) {
            meuTerminal.setModo(0);
        }
        boolean loteVendido = false;

        if (lotes != null) {
            System.out.println("======= Detalhes dos Lotes =======");
            for (Lote lote : this.lotes) {
                if (lote != null) {
                    Lance lanceVencedor = lote.getMaiorLance();

                    if (lanceVencedor != null) {
                        loteVendido = true;
                        System.out.println("Lote: " + lote.getDescricao() + "\n");
                        System.out.println("Vencedor: " + lanceVencedor.getLicitante().getNome() + "\n");
                        System.out.println("Valor do Lance: " + lanceVencedor.getValor() + "\n");
                        System.out.println();

                    }
                }
            }
        }
        if (!loteVendido) {
            System.err.println("Nenhum lance foi feito em nenhum lote.");
        }
    }




    public ArrayList<Lote> getNaoVendidos(){
        naoVendidos = new ArrayList<>();
        for (Lote lote : this.lotes) {
            if (lote.getMaiorLance() == null) {
                naoVendidos.add(lote);
            }
        }
        return naoVendidos;
    }



}
