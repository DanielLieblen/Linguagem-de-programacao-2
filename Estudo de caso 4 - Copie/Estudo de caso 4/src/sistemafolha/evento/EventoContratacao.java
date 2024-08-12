package sistemafolha.evento;

import excecoes.FolhaException;

import java.util.Date;

public class EventoContratacao extends Evento{

    private String nomeFuncionario;
    private String cargo;
    private double salarioInicial;

    public EventoContratacao(Date dt, String nomeFuncionario, String cargo, double salarioInicial) throws FolhaException {
        super(dt,0);
        this.nomeFuncionario = nomeFuncionario;
        this.cargo = cargo;
        this.salarioInicial = salarioInicial;
    }

    public String getTipoEvento(){
        return "Contratação";
    }

    public String toString() {
        return super.getTipoEvento() + " em " + super.getDtEvento() +
                "\nNome: " + nomeFuncionario +
                "\nCargo: " + cargo +
                "\nSalário Inicial: " + salarioInicial;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public String getCargo() {
        return cargo;
    }

    public double getSalarioInicial() {
        return salarioInicial;
    }



}
