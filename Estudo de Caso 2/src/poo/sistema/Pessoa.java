package poo.sistema;

public class Pessoa {
    String licitante;

    public Pessoa(String nome){
        this.licitante = nome;
    }//construtor da classe


    public String getNome() {
        return this.licitante;
    }


    public void setLicitante(String licitante) {
        this.licitante = licitante;
    }

}
