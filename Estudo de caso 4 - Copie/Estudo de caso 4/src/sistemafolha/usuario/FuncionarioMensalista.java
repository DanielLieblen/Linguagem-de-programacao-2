package sistemafolha.usuario;
import java.util.Date;

import convenio.InterfaceConvenio;
import excecoes.FolhaException;
import sistemafolha.dados.Demonstrativo;
import sistemafolha.dados.InterfaceFolha;
import sistemafolha.evento.Evento;

public class FuncionarioMensalista extends Funcionario{

    private double horasExtras;
    private double faltas;
    private double horasAtrasos;
    public FuncionarioMensalista(String nome, Date dt, double sal) {
        super(nome, dt, sal);
    }
    public void processaEvento(Evento e) throws FolhaException {
        if (e.getTipoEvento().equals("EventoHoraExtra")){
            this.horasExtras += e.getValorEvento();
        }
        else if (e.getTipoEvento().equals("EventoFalta")){
            this.faltas++;
        }
        else if (e.getTipoEvento().equals("EventoAtraso")){
            this.horasAtrasos += e.getValorEvento();
        }
        else{
            throw new FolhaException("Evento invalido para mensalista.", this, e);
        }
    }
    public void geraLancamentos(Demonstrativo d) throws FolhaException {
        double valor;
        double salarioHora;
        try {
            salarioHora = super.salarioBase / 176;
            if (this.horasExtras > 0) {
                valor = this.horasExtras * 2 * salarioHora;
                d.incluiCredito("Horas Extras (" + this.horasExtras +
                        " hs)", valor);
            }
            if (this.faltas > 0) {
                valor = this.faltas * 8 * salarioHora;
                d.incluiDebito("Faltas (" + this.faltas + " dia)",

                        valor);

            }
            if (this.horasAtrasos > 0) {
                valor = this.horasAtrasos * salarioHora;
                d.incluiDebito("Atrasos (" + this.horasAtrasos +
                        " hs)", valor);
            }

        } catch (Exception ex) {
            throw new FolhaException("Erro ao gerar lancamentos do ponto.", this, ex);
        }
    }

    @Override
    public void processarFolha(InterfaceFolha interfaceFolha, InterfaceConvenio interfaceConvenio, Funcionario f) throws FolhaException, Exception {
        // Lógica específica para processamento de folha de funcionário comissionado
        double salarioFinal = getSalarioBase();

        interfaceFolha.calcularFolha("01/11/2023", "30/11/2023");
        interfaceFolha.incluiCredito("salario base",getSalarioBase());
        interfaceFolha.incluiCredito("salario Final", salarioFinal);

        // Se o funcionário for usuário do convênio, processar o convênio
        if (isUsuarioConvenio()) {
            interfaceConvenio.processaContrato(f.nomeFunc, interfaceFolha);
        }
    }

    public boolean isMensalista(){
        return true;
    }


}

