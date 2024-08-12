package sistemafolha.usuario;

import convenio.InterfaceConvenio;
import excecoes.FolhaException;
import sistemafolha.dados.Demonstrativo;
import sistemafolha.dados.InterfaceFolha;
import sistemafolha.evento.Evento;

import java.util.ArrayList;
import java.util.Date;

public abstract class Funcionario {

    protected String nomeFunc;
    protected double salarioBase;
    protected Date dtAdmissao;
    protected Date dtSalario;
    protected Date dtFechamento;
    protected Date dtRescisao; //null se o funcionario permanece ativo
    protected ArrayList<Evento> eventos;
    protected ArrayList<Demonstrativo> demonstrativos;
    protected InterfaceConvenio convenio; //pode ser nulo caso o funcionario nao tenha convenios
    protected String idContrato; //relativo ao convenio, tambem pode ser null
    protected static int IdFunc=0;
    protected int contador=0;

    public Funcionario(String nome, Date dt, double sal) {
        this.nomeFunc = nome;
        this.dtAdmissao = dt;
        this.dtFechamento = dt;
        this.dtSalario = dt;
        this.salarioBase = sal;
        this.eventos = new ArrayList<>();
        this.demonstrativos = new ArrayList<>();
        IdFunc = contador++;
    }
    public void registraEvento(Evento e) throws FolhaException {
        Date hoje = new Date();
        if (dtRescisao != null){
            throw new FolhaException("Evento para funcionario ja desligado.", this, e);
        }
        else if (!(e.getDtEvento()).after(dtFechamento)){
            throw new FolhaException("Evento com data anterior a do fechamento.", this, e);
        }
        else if ((e.getDtEvento()).after(hoje)){
            throw new FolhaException("Evento com data futura", this, e);
        }
        else if (eventoDuplicado(e)){
            throw new FolhaException("Evento duplicado para o funcionario.", this, e);
        }
        this.eventos.add(e);
    }
    private boolean eventoDuplicado(Evento e) {
        for (Evento evento : this.eventos) { //troquei por for each
            if (e.equals(evento))
                return true;
        }
        return false;
    }
    public void registraConvenio(InterfaceConvenio c, String id) {
        convenio = c;
        idContrato = id;
    }

    public void retiraConvenio(){
        convenio = null;
        idContrato = null;
    }

    public void geraDemonstrativo() throws FolhaException {
        Evento e;
        Demonstrativo d;
        Date hoje = new Date();
        for (Evento evento : eventos) {
            e = evento;
            if ((e.getDtEvento()).after(dtFechamento)) {
                if (!eventoComum(e))
                    try {
                        processaEvento(e);
                    } catch (FolhaException exc) {
                        System.out.println(exc);
                    }
            }
        }
        d = new Demonstrativo(this, dtFechamento, hoje);
        try {
            d.incluiCredito("Salario base", this.salarioBase);
        } catch (Exception ex) {
            throw new FolhaException("Erro ao lancar salario base", this, ex);
        }
        geraLancamentos(d);
        if (idContrato != null){
            try {
                convenio.processaContrato(idContrato, d);
            } catch (Exception ex) {
                System.out.println("Erro ao lancar convenio - contrato" + idContrato);
            }
        }
        this.demonstrativos.add(d);
        this.dtFechamento = hoje;
        d.imprime();
    }

    private boolean eventoComum(Evento e) {
        if (e.getTipoEvento().equals("EventoReajuste")) {
            dtSalario = e.getDtEvento();
            salarioBase = e.getValorEvento();
            return true;
        } else if (e.getTipoEvento().equals("EventoRescisao")) {
            dtRescisao = e.getDtEvento();
            return true;
        } else
            return false;
    }
    public String toString() {
        return ("Funcionario: " + this.nomeFunc);
    }
    abstract void processaEvento(Evento evento) throws FolhaException;
    abstract void geraLancamentos(Demonstrativo d) throws FolhaException;

    public String getNome(){
        return this.nomeFunc;
    }

    public ArrayList<Evento> getEventos(){
        return eventos;
    }

    public abstract void processarFolha(InterfaceFolha interfaceFolha, InterfaceConvenio interfaceConvenio, Funcionario f) throws FolhaException, Exception;

    public boolean isUsuarioConvenio() {
        // Lógica para determinar se o funcionário é usuário do convênio
        return false;  // Exemplo: sempre false para fins de demonstração
    }
    protected double getSalarioBase(){
        return salarioBase;
    }

    public String getIdContrato() {
        return idContrato;
    }
    public InterfaceConvenio getConvenio(){
        return convenio;
    }

    public int getIdFunc() {
        return this.IdFunc; // Supondo que 'id' é o atributo que representa o identificador do funcionário
    }

    public boolean isComissionado() {
        return false;
    }
    public boolean isMensalista(){
        return false;
    }

    public ArrayList<Demonstrativo> getDemonstrativos() throws Exception {
      try{
          ArrayList<Demonstrativo> demonstrativos = new ArrayList<>();

          for (Evento evento : eventos) {
              if (evento instanceof InterfaceFolha) {
                  InterfaceFolha interfaceFolha = (InterfaceFolha) evento;
                  Demonstrativo demonstrativo = new Demonstrativo(this, interfaceFolha.getDtInicial(), interfaceFolha.getDtFinal());
                  demonstrativos.add(demonstrativo);
              }
          }
          return demonstrativos;
      }catch (Exception e){
          throw new Exception("Nao foi possivel gerar demonstrativos");
      }
    }


}
