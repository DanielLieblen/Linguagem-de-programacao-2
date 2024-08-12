package sistemafolha.evento;

import excecoes.FolhaException;

import java.util.Date;

public class EventoComissao extends Evento{
    public EventoComissao(Date dt, double val) throws FolhaException {
        super(dt, val);
        if (val <= 0)
            throw new FolhaException("Comissao com valor <= 0.");

    }
    public boolean isTipo(String st) {
        return st.equalsIgnoreCase("Comissao");
    }


}
