package sistemafolha.usuario;
import convenio.InterfaceConvenio;
import excecoes.FolhaException;
import sistemafolha.dados.Demonstrativo;
import sistemafolha.dados.InterfaceFolha;
import sistemafolha.evento.Evento;

import java.util.Date;

public class FuncionarioComissionado extends Funcionario{
    private double comissoes;
    public FuncionarioComissionado(String nome, Date dt, double sal) {
        super(nome, dt, sal);
    }

    public void processaEvento(Evento e) throws FolhaException {
        if (e.getTipoEvento().equals("EventoComissao"))
            comissoes += e.getValorEvento();
        else
            throw new FolhaException("Evento invalido para comissionado.", this, e);
    }
    public void geraLancamentos(Demonstrativo d) throws FolhaException {
        try {
            if (comissoes > 0)
                d.incluiCredito("Comissoes", comissoes);

        } catch (Exception ex) {
            throw new FolhaException("Erro ao gerar lancamento de comissoes.", this, ex);
        }
    }

    public void processarFolha(InterfaceFolha interfaceFolha, InterfaceConvenio interfaceConvenio, Funcionario f) throws FolhaException, Exception {
        // Lógica específica para processamento de folha de funcionário comissionado
        double salarioFinal = getSalarioBase() + comissoes;

        interfaceFolha.calcularFolha("01/11/2023", "30/11/2023");
        interfaceFolha.incluiCredito("salario base",getSalarioBase());
        interfaceFolha.incluiCredito("comissoes", comissoes);
        interfaceFolha.incluiCredito("salario Final", salarioFinal);

        // Se o funcionário for usuário do convênio, processar o convênio
        if (isUsuarioConvenio()) {
            interfaceConvenio.processaContrato(f.nomeFunc, interfaceFolha);
        }
    }

    public boolean isComissionado(){
        return true;
    }

}
