package sistemafolha.evento;

import excecoes.FolhaException;

import java.util.Date;

public class EventoHoraExtra extends Evento{
    private double qtdHoras;
    public EventoHoraExtra(Date dt, double qtd) throws FolhaException {
        super(dt, qtd);
        if (qtd > 4)
            throw new FolhaException("Horas Extras com quantidade horas excede o maximo que foi definido para 4.\n Por favor tente novamente.");

    }

    public boolean isTipo(String st) {
        return st.equalsIgnoreCase("Hora Extra");
    }


}
