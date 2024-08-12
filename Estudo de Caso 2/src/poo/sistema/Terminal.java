package poo.sistema;

import java.util.ArrayList;
import java.util.Scanner;

public class Terminal {
        private Leilao meuLeilao;
        private int leilaoStatus;

        public Terminal(){
            this.meuLeilao = new Leilao(this);
        }

        public void iniciaLeilao(){
            int opcao;
            setModo(1);
            opcao = this.getOpcao();
            while(opcao!= 0){
                switch (opcao){
                   case 1 ->{
                       adicionarLote();
                   }
                   case 2 ->{
                       removerLote();
                    }
                    case 3 ->{
                        inicia();
                    }
                    case 4 ->{
                       lance();
                    }
                    case 5 ->{
                       encerrar();
                    }
                    case 6 ->{
                       mostrarLote();
                    }
                    case 7 ->{
                        pesquisar();
                    }
                    case 8 ->{
                        System.out.println(naoVendidos());
                    }
                    case 9 ->{
                        System.exit(0);
                    }

                    default -> throw new IllegalStateException("Unexpected value: " + opcao);
                }
                opcao = getOpcao();
            }

        }
        public void setModo(int modo){
            if(modo == 1 || modo == 2 || modo == 3){
                this.leilaoStatus = modo;
            }
        } //1 = ligado e 0= desligado

        public void inicia(){
            setModo(2);
        }
        private int getOpcao(){ //isso aqui ta errado
            int opcao=0;
            do{
                if(this.leilaoStatus == 1){ //tem que gerenciar a opcao aqui dentro
                    opcao = getInt("Opcao: 1- Inserir lote, 2- Remover Lote, 3- Iniciar leilao");
                    if(opcao != 1 & opcao != 2 & opcao != 3){
                        opcao = 0;
                    }
                }
                if(this.leilaoStatus == 2){
                    opcao = getInt("4 - Dar Lance, 5 - Encerrar, 6 - Mostrar Lotes, 7 - Pesquisar Lote");
                    if(opcao !=4 & opcao !=5 & opcao !=6 & opcao != 7){
                        opcao = 0;
                    }
                }
                if(this.leilaoStatus == 3){
                    opcao = getInt("Opcao: 6 - Mostrar Lotes, 8 - Mostrar Nao Vendidos, 9 - Fechar");
                    if(opcao != 6 & opcao != 8 & opcao != 9){
                        opcao = 0;
                    }
                }
            }while(opcao == 0);
            return opcao;
        }


        private boolean adicionarLote(){
            boolean adicionaLote = this.meuLeilao.adicionarLote(getDescricaoLote());
            if(adicionaLote){
                System.out.println("Lote adicionado com sucesso");
                return true;
            }
                System.err.println("Erro ao adicionar, tente outra vez");
                return false;

        }

        private boolean removerLote(){
            boolean removeLote = this.meuLeilao.removerLote(getInt("Digite o numero do Lote"));
            if (removeLote) {
                System.out.println("Lote removido com sucesso");
                return true;
            }else{
                System.err.println("Erro a remover, tente outro numero");
                return false;
            }
        }

        private boolean lance(){
            boolean Lance = this.meuLeilao.darLance(new Pessoa(getString("Digite o nome do licitante")), getInt("Digite o numero do Lote"), getDouble("Digite o valor do lance"));
            if(Lance){
                System.out.println("Lance dado com sucesso");
                return true;
            }else{
                System.err.println("Nao foi possivel dar lance, verifique o numero do leilão e tente novamente.");
                return false;
            }
        }

        private void encerrar(){
            this.meuLeilao.encerrarLeilao();
            setModo(3);
        }

        private void mostrarLote(){
            this.meuLeilao.mostraLotes();
        }

        private void pesquisar(){
            this.meuLeilao.getLote(getInt("digite o numero do lote"));
        }

        private String naoVendidos(){
            ArrayList<Lote> novaLista = this.meuLeilao.getNaoVendidos();
            if(novaLista != null){
                for(Lote lote : novaLista){
                    if(lote != null){
                        return "Numero: " + lote.getNumero() + ", Descricao: " + lote.getDescricao() + ", Maior Lance:" + lote.getMaiorLance();
                    }
                }

            }
                return "não há entrada";
        }

//        public boolean Encerrado(){
//        return this.isEncerrado = true;
//    }

        private int getInt(String string){
            Scanner r = new Scanner(System.in);
            System.out.println("Entre com " + string);
            if(r.hasNextInt()){
                return r.nextInt();
            }
            String st = r.next();
            System.err.println("Erro na leitura de Dados");
            return 0;
        }
        private String getString(String string){
            Scanner a = new Scanner(System.in);
            System.out.println("entre com " + string);
            if(a.hasNextInt() || a.hasNextBoolean() || a.hasNextDouble() || a.hasNextFloat()){
                String st = a.next();
                System.out.println("Erro na leitura de Dados");
            }
            return a.next();
        }

    private String getDescricaoLote() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entre com a descrição do lote para leilão: ");
        return scanner.nextLine();
    }


    private double getDouble(String string){
            Scanner r = new Scanner(System.in);
            System.out.println("Entre com " + string);
            if(r.hasNextDouble()){
                return r.nextDouble();
            }
            String st = r.next();
            System.out.println("Erro na leitura de Dados");
            return 0;
        }


}



