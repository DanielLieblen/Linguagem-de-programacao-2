package poo.usuarios;
import poo.item.Item;
import poo.item.Livro;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import poo.usuarios.Aluno;
public class Usuario { //classe mãe ou classe super do polimorfismo
    private String nome;
//    private ArrayList<Livro> livrosRetirados;
    private ArrayList<Item> itensRetirados;


    public Usuario(String nome){
        this.nome = nome;
        this.itensRetirados = new ArrayList<>();
    }

    public boolean retiraItem(Item item){
        if(this.isAptoARetirar()){
            if(item.empresta(this, getPrazoMaximo())){
                this.itensRetirados.add(item);
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean devolveItem(Item item){
        if(item.retorna(this)){
            this.itensRetirados.remove(item);
            return true;
        }
        return false;
    }

    public int getCotaMaxima(){
        return 2;
    }
    public int getPrazoMaximo(){
        return 4;
    }

    public boolean isADevolver(){
        if(this.itensRetirados.size() >= this.getCotaMaxima() || this.temPrazoVencido()){
            return true;
        }
        return false; //reescrita de uma funcao
    }

    public boolean isAptoARetirar(){
        return(!this.isADevolver());
    }

    public boolean temPrazoVencido(){
        for(Item item : this.itensRetirados){ //foi substituido o iterator por um foreach
            if(item.isEmAtraso()){ //tambem foi substituido de livro pra Item para usar polimorfia assim pode-se conferir quaisquer itens que o usuario pegue emprestado
                return true;
            }
        }
        return false;
    }
//    public boolean isUsuario(){return true;}
    public boolean isAluno(){ return false;}
    public boolean isProfessor(){
        return false;
    } //metodo auxiliar
    //tarefa 03 metodos auxiliares sao importantes pois auxiliam metodos principais a ter apenas uma responsabilidade,
    //desta forma nao fica confuso o codigom, e classes nao ficam com muitas atribuições
    //ficando com apenas uma atribuição o codigo fica mais simples de ler e entender
    public String getNome(){
        return this.nome;
    } //metodo auxiliar


    //tarefa 4 a mudança de estado de um objeto representa os estados que o objeto pode assumir
    //como se ele pudesse realizar operações e se comunicar com outros objetos mais livremente
    //a construção de diagramas de estados facilita a codificação pois deixa mais claro
    //o entendimento entre os objetos e como eles funcionam, quais estados podem assumir
    //e os resultados desses estados, deixando mais facil a implementação

    //tarefa 05 - a elaboração de diagramas de atividades é importante porque mostra nao apenas
    //a interação entre objetos, mas quais as decisões cada objeto pode tomar e como essa decisão
    //se comunica com outro objeto, para então criar erro ou uma nova desição;


    public String toString(){
        return "Usuario " + nome;
    } //metodo auxiliar

    public void listaCarga(){
        System.out.println(this.toString() + "Limite: " + this.getCotaMaxima() + " Cota atual: " + this.itensRetirados.size());
        for(Item item : this.itensRetirados){ //foi substituido o iterator por um foreach
            System.out.println(item);
        }
    }



    //metodos da classe aluno








}
