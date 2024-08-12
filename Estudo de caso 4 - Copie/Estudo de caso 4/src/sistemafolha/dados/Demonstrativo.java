package sistemafolha.dados;

import sistemafolha.usuario.Funcionario;
import sistemafolha.evento.Evento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Demonstrativo implements InterfaceFolha{
    private Funcionario funcionario;
    private Date dtInicial;
    private Date dtFinal;
    private ArrayList<Lancamento> lancamentos;
    public Demonstrativo(Funcionario f, Date inicio, Date fim) {
        this.funcionario = f;
        this.dtInicial = inicio;
        this.dtFinal = fim;
        this.lancamentos = new ArrayList<>();
    }
    public Date getDtInicial() {
        return this.dtInicial;
    }
    public Date getDtFinal() {
        return this.dtFinal;
    }
    public void incluiDebito(String hist, double val) throws Exception {
        this.lancamentos.add(new Lancamento(hist, -val));
    }
    public void incluiCredito(String hist, double val) throws Exception {
        this.lancamentos.add(new Lancamento(hist, val));
    }
    public String toString() {
        return ("Demonstrativo de Pagamento:" + "\n Periodo de " +
                this.dtInicial + " a " + this.dtFinal + "\n " +
                this.funcionario);
    }
    public void imprime() {
        Lancamento l;
        double total = 0;
        System.out.println(this);
        for (Lancamento lancamento : this.lancamentos) { //troquei iterator por for each
            l = lancamento;
            System.out.println(l);
            total += l.getValor();
        }
        System.out.println("Total a pagar: " + total);
    }

    @Override
    public void calcularFolha(String dtI, String dtF) {
        // Assume-se que s e s1 representam o período a ser considerado na folha de pagamento.
        Date dtInicial = parseDate(dtI);
        Date dtFinal = parseDate(dtF);

        // Limpa os lançamentos anteriores
        this.lancamentos.clear();

        // Percorre os eventos do funcionário no período especificado
        for (Evento evento : this.funcionario.getEventos()) {
            if (evento.getDtEvento().after(dtInicial) && evento.getDtEvento().before(dtFinal)) {
                if (evento.getTipoEvento().equalsIgnoreCase("Debito")) { //pegar o tipo de evento com uma string no terminal
                    try {
                        this.incluiDebito(evento.getTipoEvento(), evento.getValorEvento());
                    } catch (Exception e) {
                        e.printStackTrace(); // Trate a exceção de acordo com sua lógica
                    }
                } else if (evento.getTipoEvento().equalsIgnoreCase("Credito")) {
                    try {
                        this.incluiCredito(evento.getTipoEvento(), evento.getValorEvento());
                    } catch (Exception e) {
                        e.printStackTrace(); // Trate a exceção de acordo com sua lógica
                    }
                }
            }
        }
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace(); // Trate a exceção de acordo com sua lógica
            return null; // Ou lance uma exceção adequada
        }
    }

}
