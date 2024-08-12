package sistemafolha.evento;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class Evento {
    private Date dtEvento;
    private double valorEvento;
    private int dia;
    private int mes;
    private int ano;

    public Evento(Date dt, double val) {
        this.dtEvento = dt;
        Calendar cal = new GregorianCalendar();
        cal.setTime(dtEvento);
        this.dia = cal.get(Calendar.DATE);
        this.mes = cal.get(Calendar.MONTH) + 1;
        this.ano = cal.get(Calendar.YEAR) + 1900;
        this.valorEvento = val;
    }
    public Date getDtEvento() {
        return this.dtEvento;
    }
    public double getValorEvento() {
        return this.valorEvento;
    }
    public String getTipoEvento() {
        return (this.getClass()).getName();
        //retorna a propria classe = nome
    }


    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Evento evento = (Evento) obj;
        return dia == evento.dia && mes == evento.mes && ano == evento.ano && Double.compare(evento.valorEvento, valorEvento) == 0 && dtEvento.equals(evento.dtEvento);

    }
    public String toString() {
        return getTipoEvento() + " em " + this.dia + "/" +
                this.mes + "/" + this.ano +
                " valor=" + valorEvento;
    }

}
