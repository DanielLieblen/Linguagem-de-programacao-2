package poo.item;
import poo.usuarios.Usuario;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Objects;

public class Livro extends Item{ //subclasse do polimorfismo item
    protected String titulo;
    private Usuario retiradoPor;
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private Usuario bloqueadoPor;
    private Date dataBloqueio;
    private Date dataDesbloqueio;

    public Livro(String titulo){
        super(titulo);
    }

    public boolean isDisponivel(){
        Date hoje = new Date();
        return this.retiradoPor==null && (this.bloqueadoPor==null || this.dataDesbloqueio.before(hoje));
    }
    public boolean isEmprestado(){
        return !(this.retiradoPor==null);
    }

    public boolean isBloqueado(){
        Date hoje = new Date();
        return this.retiradoPor ==null && !(this.bloqueadoPor==null) && !(this.dataDesbloqueio.before(hoje));
    }

    public boolean isEmAtraso(){
        Date hoje = new Date();
        return (retiradoPor != null) && (this.dataDevolucao.before(hoje));
    }
    public boolean bloqueia(Usuario usuario, int prazo){
        GregorianCalendar calendario = new GregorianCalendar();
        if(this.isDisponivel() && usuario.isProfessor()){
            this.bloqueadoPor= usuario;
            this.dataBloqueio = calendario.getTime();
            calendario.add(Calendar.DATE, (prazo>20?20:prazo));
            this.dataDesbloqueio = calendario.getTime();
            return true;
        }
        return false;
    }

    public boolean desbloqueia(Usuario usuario){
        if(usuario == this.bloqueadoPor){ //Reescrito tirando operador ternario
            this.bloqueadoPor = null;
            return true;
        }
        return false;
    }

    public boolean isLivro(){
        return true;
    }
    public boolean isPeriodico(){
        return false;
    }

    public String toString(){
        String frase;
        if(isDisponivel()){
            return this.titulo+ " disponivel";
        }
        if(isEmprestado()){
            frase = " retirado por " + retiradoPor + " em " + dma(dataEmprestimo) + " ate " + dma(dataDevolucao);
        }else{
            frase = " bloqueaddo por " + bloqueadoPor + " em " + dma(dataBloqueio) + " ate " + dma(dataDesbloqueio);
        }
        return titulo + frase;
    }


}
