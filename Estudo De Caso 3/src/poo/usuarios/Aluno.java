package poo.usuarios;
import java.util.Date;

public class Aluno extends Usuario{ //outra sub-classe do polimorfismo, sendo subclasse de usuario
    protected Date dataLimite;

    public Aluno(String nome, Date data){
        super(nome);
        this.dataLimite = data;
    }

    public void renovaCartao(Date data){
        this.dataLimite = data;
    }

    public boolean isRegular(){
        Date hoje = new Date();
        return dataLimite.after(hoje);
    }

    public boolean isARenovar(){
        return !isRegular();
    }

    public int getCotaMaxima(){
        if(isRegular()){
            return 3;
        }else{
            return super.getCotaMaxima();
        }
    }

    public int getPrazoMaximo(){
        if(isRegular()){
            return 7;
        }else{
            return super.getPrazoMaximo();
        }
    }

    public String toString(){
        return("Aluno: " + getNome());
    }
    public boolean isAluno(){
        return true;
    }
}
