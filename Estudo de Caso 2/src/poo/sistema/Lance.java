package poo.sistema;

public class Lance {
    private Pessoa licitante;

    private double valor;

    public Lance (Pessoa licitante, double valor){
        this.licitante = licitante;
        this.valor = valor;
    }

    public Pessoa getLicitante() {
        return this.licitante;
    }

//    @Override
//    public String toString(){
//        return("licitante: " + nome + " R$ " + this.valor);
//    }


    public double getValor() {
        return this.valor;
    }

}
