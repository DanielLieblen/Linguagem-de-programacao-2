package poo.teste;
import poo.sistema.*;

public class testeleilao {

    public static void main(String[] args) {

        Terminal terminal = new Terminal();
        Leilao db1 = new Leilao(terminal);

        terminal.iniciaLeilao();


    }


}
