package sistemafolha.evento;

import excecoes.FolhaException;

import java.util.Date;

public class EventoAtraso extends Evento{
    public EventoAtraso(Date dt, double qtd) throws FolhaException {
        super(dt, qtd);
        if (qtd > 2)
            throw new FolhaException("Atraso com qtde. horas > 2");

    }
    public boolean isTipo(String st) {
        return st.equalsIgnoreCase("Atraso");
    }

}
