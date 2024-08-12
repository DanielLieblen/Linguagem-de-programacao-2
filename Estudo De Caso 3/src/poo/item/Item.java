package poo.item;

import poo.usuarios.Usuario;
import poo.controle.Biblioteca;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Item { //classe mae do polimorfismo entre os itens
    private final String titulo;
    protected Usuario retiradoPor;
    protected Date dataEmprestimo;
    protected Date dataDevolucao;

    public Item(String titulo){
        this.titulo = titulo;
    }
    public boolean isDisponivel(){
        Date hoje = new Date();
        return this.retiradoPor==null;
    }
    public boolean isEmprestado(){
        return !(this.retiradoPor==null);
    }

    public boolean isEmAtraso(){
        Date hoje = new Date();
        return (retiradoPor != null) && (this.dataDevolucao.before(hoje));
    }


    public boolean empresta(Usuario usuario, int prazo) {
        GregorianCalendar calendario = new GregorianCalendar();
        if (this.isDisponivel()) {
            this.retiradoPor = usuario;
            this.dataEmprestimo = calendario.getTime();
            calendario.add(Calendar.DATE, prazo);
            this.dataDevolucao = calendario.getTime();
            return true;
        }
        return false;
    }


    public boolean retorna(Usuario usuario){
        if(usuario == this.retiradoPor){    //tambem tirou operador ternario daqui
            this.retiradoPor = null;
            return true;
        }
        return false;
    }


    public String toString(){
        String frase = "";
        if(isDisponivel()){
            return this.titulo + " disponivel";
        }
        else if(isEmprestado()){
            frase = " retirado por " + retiradoPor + " em " + dma(dataEmprestimo) + " ate " + dma(dataDevolucao);
        }
        return titulo + frase;
    }
    protected String dma(Date data){
        GregorianCalendar calendario = new GregorianCalendar();
        calendario.setTime(data);
        return calendario.get(Calendar.DATE) + "/"+
                (calendario.get(Calendar.MONTH) + 1) +"/" +
                calendario.get(Calendar.YEAR);
    }

    public String getTitulo() {
        return this.titulo;
    }
}
