package poo.sistema;

public class Lote {
    private final int numero;
    private String descricao;
    private Lance maiorLance;


    public Lote(int numero, String descricao){
        this.numero = numero;
        this.descricao = descricao;
    }


    public int getNumero(){
        return this.numero;
    }

    public String getDescricao(){
        return this.descricao;
    }

    public Lance getMaiorLance(){
        return this.maiorLance;
    }

    public Pessoa getArrematador(){
            return maiorLance.getLicitante();
    }

    public boolean lancePara(Pessoa licitante, double valor){
        if(this.maiorLance == null || (this.maiorLance.getValor() < valor)){
            this.setMaiorLance(new Lance(licitante, valor)); //agregação pois sem lote nao tem porque lance existir
            return true;
        }else{
            return false;
        }
    }
//
//    public void setDescricao(String descricao) {
//        this.descricao = descricao;
//    }


//    else{
//        System.out.println("Lote nº: " + this.getNumero() + "(" + this.getDescricao() + ")" + "Atualmente tem um lance de: " + this.maiorLance.getValor());
//    }

    private void setMaiorLance(Lance maiorLance){
        this.maiorLance = maiorLance;
    }




}
