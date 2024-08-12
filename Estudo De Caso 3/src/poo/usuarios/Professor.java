package poo.usuarios;
import poo.item.Livro; //o erro era que nao tava importando o pacote de livro
import poo.item.Item;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Professor extends Usuario{ //polimofismo da classe usuario, pois professor é um usuario com privilegios
    public Professor(String nome){
        super(nome);
    }


    public boolean bloqueiaLivro(Livro livro, int prazo){
        return livro.bloqueia(this, prazo);
    }

    public boolean desbloqueiaLivro(Livro livro){
        return livro.desbloqueia(this);
    }

    public int getCotaMaxima(){
        return 5;
    }
    public int getPrazoMaximo(){
        return 14;
    }

    public boolean isProfessor(){
        return true;
    } //metodo auxiliar pra dizer que a classe eh professor
    public String toString(){
        return "Prof.: " + super.getNome();
    }


}
