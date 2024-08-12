package convenio;


import excecoes.FolhaException;
import sistemafolha.dados.Demonstrativo;
import sistemafolha.dados.InterfaceFolha;
import sistemafolha.dados.RepositorioDados;
import sistemafolha.usuario.Funcionario;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MedGrupo implements InterfaceConvenio{

//    private ArrayList<Funcionario> funcionarios;
    private Map<String, Double> funcionarioDesconto;
    public MedGrupo(){
//      this.funcionarios = new ArrayList<>();
        this.funcionarioDesconto = new HashMap<>();
    }
    public void adicionarFuncionario(String idFuncionario, double desconto) {
        funcionarioDesconto.put(idFuncionario, desconto);
    }
    public void removerFuncionario(String idFuncionario) {
        funcionarioDesconto.remove(idFuncionario);
    }

    public boolean contemFuncionario(String idFuncionario) {
        return funcionarioDesconto.containsKey(idFuncionario);
    }
    public double obterDesconto(Funcionario funcionario) {
        return funcionarioDesconto.getOrDefault(funcionario, 0.0);
    }

    public void processaContrato(String idFuncionario, InterfaceFolha f) throws Exception {
        if (funcionarioDesconto.containsKey(idFuncionario)) {
            double temDesconto = funcionarioDesconto.get(idFuncionario);
            if (temDesconto > 0.0) {
                f.incluiDebito("Convenio MedGrupo", 30 - temDesconto);
                System.out.println("O funcionário com ID " + idFuncionario + " tem desconto de: " + temDesconto + ".\n");
            } else {
                System.out.println("O funcionário com ID " + idFuncionario + " não tem desconto.");
            }
        } else {
            throw new Exception("O funcionário com ID " + idFuncionario + " não está cadastrado no convênio.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        MedGrupo outroConvenio = (MedGrupo) obj;

        return this.funcionarioDesconto.equals(outroConvenio.funcionarioDesconto);
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//
//        MedGrupo outroConvenio = (MedGrupo) obj;
//
//        return this.funcionarioDesconto.equals(outroConvenio.funcionarioDesconto);
//    }


//
//    public void adicionarFuncionario(Funcionario funcionario){
//        this.funcionarios.add(funcionario);
//    }
//    public void removerFuncionario(Funcionario funcionario){
//        this.funcionarios.remove(funcionario);
//    }
//    public boolean contemFuncionario(Funcionario funcionario){
//        return funcionarios.contains(funcionario);
//    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//
//        MedGrupo convenio = (MedGrupo) obj;
//
//        if(this.funcionarios != null){
//            return funcionarios.equals(convenio.funcionarios); //retorna a comparacao boleana pra ver se o convenio tem aquele funcionario procurado
//        }else{
//            return convenio.funcionarios == null; //significa que esse arraylist está vazio
//        }
//    }

//    public void processaContrato(String id, InterfaceFolha f) throws Exception {
//        if (id.equals("1")) {
//            f.incluiDebito("Convenio MedGrupo", 30);
//        }else {
//            throw new Exception("Convenio não existe, por favor tente novamente.");
//        }
//    }



}
