package sistemafolha.evento;

import excecoes.FolhaException;

import java.util.Date;

public class EventoReajuste extends Evento{
    public EventoReajuste(Date dt, double val) throws FolhaException {
        super(dt, val);
        if (val < 300)
            throw new FolhaException("Reajuste com novo salario < 300.");
    }
    public boolean isTipo(String st) {
        return st.equalsIgnoreCase("Reajuste");
    }

}
