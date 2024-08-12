package convenio;

import sistemafolha.dados.InterfaceFolha;
import sistemafolha.usuario.Funcionario;

public interface InterfaceConvenio {
    void processaContrato(String id, InterfaceFolha f) throws Exception;

    boolean contemFuncionario(String idContrato);

    void adicionarFuncionario(String idFuncionario, double desconto);

    void removerFuncionario(String idFuncionario);


}
