package sistemafolha.dados;
import convenio.InterfaceConvenio;
import convenio.MedGrupo;
import excecoes.FolhaException;
import sistemafolha.evento.*;
import sistemafolha.usuario.Funcionario;
import sistemafolha.usuario.FuncionarioComissionado;
import sistemafolha.usuario.FuncionarioMensalista;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RepositorioDados {
    private ArrayList<Funcionario> funcionarios;
    private ArrayList<Evento> eventos;
    private Terminal meuTerminal;
    private ArrayList<InterfaceConvenio> convenios; //coleção de diferentes convenios
//    private ArrayList<MedGrupo> conveniados; //coleção de funcionarios que tem convenios
    //posso fazer com que seja possivel consultar apenas os funcionarios conveniados aqui

    private ArrayList<Demonstrativo> demonstrativos;


    private static int idContrato = 0; //variavel estatica
    private int numeroContratoInstancia;

    public RepositorioDados(Terminal terminal) {
        this.meuTerminal = terminal;
        this.eventos = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.convenios = new ArrayList<>();
        this.numeroContratoInstancia = idContrato++;
    }


    public Evento buscaEvento(String titulo) {
        for (Evento evento : this.eventos) {
            if (titulo.equalsIgnoreCase(evento.getTipoEvento())) {
                return evento;
            }
        }
        return null;
    }

    public ArrayList<Evento> getEventos(Funcionario funcionario) throws FolhaException {
       ArrayList<Evento> eventosDoFuncionario = new ArrayList<>();

        for (Evento evento : this.eventos) {
            if (funcionario.getEventos()!=null) {
                eventosDoFuncionario.add(evento);
            }
        }
        return eventosDoFuncionario;
    }

    public boolean cadastraEvento(Evento evento, Funcionario func) throws FolhaException {
        if (this.buscaEvento(evento.getTipoEvento()) == null) {
            this.eventos.add(evento);
            this.adicionaEventoParaFuncionario(evento, func);
            return true;
        } else {
            throw new FolhaException("Não foi possivel cadastrar Evento, Evento já foi registrado");
        }
    }

    public boolean removeEvento(String titulo, Funcionario func) throws FolhaException {
        if (this.buscaEvento(titulo) == null) { //acho que evento tem que ser adicionado como string e nao objeto
            throw new FolhaException("Evento não existe, por favor tente novamente com outro titulo.");
        } else {
            for (Funcionario funcionario : funcionarios) {
                if (funcionario.equals(func)) {
                    Evento evento = this.buscaEvento(titulo);
                    if (funcionario.getEventos().equals(evento)) {
                        funcionario.getEventos().remove(evento);
                    } //tem que procurar o evento dentro do funcionario
                }
            }
            Evento novo = this.buscaEvento(titulo);
            eventos.remove(novo);
            return true;
        }
    }

    public Funcionario buscaFuncionario(String nome) {
        for (Funcionario func : this.funcionarios) {
            if (nome.equalsIgnoreCase(func.getNome())) {
                return func;
            }
        }
        return null;
    }

    public boolean existeFuncionario(String nome) {
        for (Funcionario func : this.funcionarios) {
            if (nome.equalsIgnoreCase(func.getNome())) {
                return true;
            }
        }
        return false;
    }

    private boolean adicionaFuncionario(Funcionario funcionario) throws FolhaException {
        if (this.buscaFuncionario(funcionario.getNome()) == null) {
            this.funcionarios.add(funcionario);
            return true;
        } else {
            throw new FolhaException("Funcionario já existe");
        }
    }

    public boolean contrataFuncionario(String nome, Date data, String cargo, double sal) throws FolhaException {
        if (this.buscaFuncionario(nome) == null) {
            EventoContratacao eventoContratacao = new EventoContratacao(data, nome, cargo, sal);

            Funcionario func;
            if (cargo.equalsIgnoreCase("Mensalista")) {
                func = new FuncionarioMensalista(nome, data, sal);
            } else if (cargo.equalsIgnoreCase("Comissionado")) {
                func = new FuncionarioComissionado(nome, data, sal);
            } else {
                throw new FolhaException("Tipo de funcionário não reconhecido: " + cargo);
            }

            adicionaFuncionario(func);
            adicionaEventoParaFuncionario(eventoContratacao, func);

            return true;
        } else {
            throw new FolhaException("Funcionário já existe no nosso banco de dados. Por favor, tente novamente.");
        }
    }

    public boolean demiteFuncionario(String nome, Date data, int tipo, boolean aviso) throws FolhaException {
        if (this.buscaFuncionario(nome) != null) {
            EventoRescisao eventoRescisao = new EventoRescisao(data, tipo, aviso);
            //busca usando metodo auxiliar que faz a busca do funcionario
            Funcionario func = this.buscaFuncionario(nome);
            //usa metodo auxiliar para adicionar o metodo no funcionario
            adicionaEventoParaFuncionario(eventoRescisao, func);
            if (aviso) {//verifica se o aviso previo existe
                //se existir retorna true;
                eventoRescisao.getAvisoPrevio();
                eventoRescisao.toString();
            } else {
                throw new FolhaException("Funcionario não teve aviso previo.");
            }
            //remove o funcionario de dentro do repositorio depois de checar se ele existe
            if (removeFuncionario(nome)) {
                System.out.println("Funcionario removido com exito");
            }
            return true;
        } else {
            throw new FolhaException("Funcionario não existe no nosso banco de dados, por favor tente novamente.");
        }
    }

//    public Funcionario consultarDadosFuncionario(String nomeFunc) throws FolhaException{
//        Funcionario func = this.buscaFuncionario(nomeFunc);
//        if(func != null){
//            return func;
//        }else{
//            throw new FolhaException("Funcionario nao existe");
//        }
//    }

    private void adicionaEventoParaFuncionario(Evento evento, Funcionario funcionario) throws FolhaException {
        if (evento == null || funcionario == null) {
            throw new FolhaException("Tentativa de adicionar Evento fracassou. O Evento ou Funcionario não existe");
        }
        if (this.buscaFuncionario(funcionario.getNome()) == null) {
            throw new FolhaException("Funcionario não existe em nosso banco de dados.");
        }
        funcionario.registraEvento(evento);
//        cadastraEvento(evento, funcionario);
    }

    private boolean removeFuncionario(String nome) throws FolhaException {
        Funcionario funcionario = buscaFuncionario(nome);
        if (funcionario != null) {
            // Remove o funcionário do repositório
            funcionarios.remove(funcionario);
            return true;
        } else {
            throw new FolhaException("Funcionário não existe no banco de dados. Por favor, tente novamente.");
        }
    }

    public boolean criaConvenio(Funcionario funcionario, InterfaceConvenio convenio, double desconto) throws FolhaException {
        try {
            //verifica primeiro se o funcionario ja tem convenio
            if (temConvenio(funcionario)) {
                throw new FolhaException("O funcionario ja está conveniado");
            }
//            MedGrupo novoConvenio = new MedGrupo();
//            novoConvenio.
            convenio.adicionarFuncionario(funcionario.getIdContrato(), desconto);// adiciona na interface convenio (medgrupo) o conveniado e o desconto se houver
            this.convenios.add(convenio); //adiciona na arraylist de convenio o convenio
            funcionario.registraConvenio(convenio, this.getNumeroContratoInstancia()); //ja registra o convenio e o numero do Contrato dentro do funcionario.
            return true;
        } catch (Exception e) {
            throw new FolhaException("Não foi possivel adicionar convenio ao funcionario, por favor tente novamente.");
        }
    }

    protected boolean temConvenio(Funcionario funcionario) {
        //verifica se o funcionario já possui convenio
        for (InterfaceConvenio convenio : this.convenios) {
            if (convenio.contemFuncionario(funcionario.getIdContrato())) {
                return true;
            }
        }
        return false;
    }

    public boolean removeConvenio(Funcionario funcionario, InterfaceConvenio convenio) throws FolhaException {
        try {
            if (convenio.contemFuncionario(funcionario.getIdContrato()) && temConvenio(funcionario)) {
                convenio.removerFuncionario(funcionario.getIdContrato()); //tira do convenio (da arvore no caso)
                funcionario.retiraConvenio(); //tira do funcionario
                return true;
            } else {
                throw new FolhaException("O funcionario não está associado a nenhum convenio.");
            }
        } catch (Exception e) {
            throw new FolhaException("Nao foi possivel remover convenio do funcionario." + e.getMessage());
        }
    }

    public String getNumeroContratoInstancia() {
        return Integer.toString(numeroContratoInstancia);
    }

    private void criaDemonstrativo(Funcionario funcionario, Date dtInicial, Date dtFinal) throws FolhaException {
        try {
            if (buscaFuncionario(funcionario.getNome()) != null) {
                Demonstrativo demonstrativo = new Demonstrativo(funcionario, dtInicial, dtFinal);//cria uma nova instancia do Demonstrativo
                funcionario.geraDemonstrativo();//gera demonstrativo para o funcionario
                this.demonstrativos.add(demonstrativo); //adiciona na Arraylist de demonstrativos este demonstrativo recem criado
            }
        } catch (FolhaException e) {
            throw new FolhaException("Erro ao criar demonstrativo, tente novamente.");
        }
    }

//    public Demonstrativo obterDemonstrativo(Funcionario func, Date inicio, Date fim) throws FolhaException {
//        return new Demonstrativo(func, inicio, fim);
//    }
//
//    public void visualizarDemonstrativo(Funcionario func, String dtI, String dtF) {
//        Demonstrativo demonstrativo = buscarDemonstrativo(func, dtI, dtF);
//
//        if (demonstrativo != null) {
//            demonstrativo.imprime();
//        } else {
//            System.out.println("Demonstrativo não encontrado para o período especificado.");
//        }
//    }

    protected Demonstrativo buscarDemonstrativo(Funcionario func, String dtI, String dtF) throws Exception {
      try{
          Date dtInicial = parseDate(dtI);
          Date dtFinal = parseDate(dtF);

          // Busca o demonstrativo correspondente ao período e funcionário
          for (Demonstrativo demo : func.getDemonstrativos()) {
              if (demo.getDtInicial().equals(dtInicial) && demo.getDtFinal().equals(dtFinal)) {
                  return demo;
              }
          }


      }catch (Exception e){
          throw new Exception("Demonstrativo nao existe em nosso banco de dados, tente novamente.");

      }
        return null;
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

    public void criaDemonstrativosParaFuncionarios(Date dtInicial, Date dtFinal) throws FolhaException { //cria demonstrativo para todos os viados
        for(Funcionario funcionario : this.funcionarios){
            try{
                criaDemonstrativo(funcionario, dtInicial, dtFinal);
            }catch(FolhaException e){
                throw new FolhaException("Erro ao criar demonstrativos, por favor tente novamente.");
            }
        }
    }

    public ArrayList<Evento> getEventos() {
        return new ArrayList<>(this.eventos);
    }
    public ArrayList<Funcionario> getFuncionarios() {
        return new ArrayList<>(funcionarios);
    }

    public void cadastraAtraso(Funcionario func, Date dia, int min) throws FolhaException {
        EventoAtraso evento = new EventoAtraso(dia, min);
        cadastraEvento(evento, func);
    }

    public void cadastraComissao(Funcionario func, Date dia, double quant) throws FolhaException {
        EventoComissao evento = new EventoComissao(dia, quant);
        cadastraEvento(evento, func);
    }

    public void cadastraFalta(Funcionario func, Date diaFalta) throws FolhaException {
        EventoFalta evento = new EventoFalta(diaFalta);
        cadastraEvento(evento, func);
    }

    public void cadastraHoraExtra(Funcionario func, int horasExtras, Date dt) throws FolhaException {
        EventoHoraExtra evento = new EventoHoraExtra(dt, horasExtras);
        cadastraEvento(evento, func);
    }

    public void cadastraReajuste(Funcionario func, double percentualReajuste, Date dt) throws FolhaException {
        EventoReajuste evento = new EventoReajuste(dt, percentualReajuste);
        cadastraEvento(evento, func);
    }

//    public void adicionarConvenio(Funcionario func, InterfaceConvenio convenio) throws FolhaException {
//        if (!existeFuncionario(func.getNome())) {
//            throw new FolhaException("Funcionário não encontrado.");
//        }
//        func.registraConvenio(convenio, func.getIdContrato());
//    }
//
//    public void removerConvenio(Funcionario func, String nomeConvenio) throws FolhaException {
//        if (!existeFuncionario(func.getNome())) {
//            throw new FolhaException("Funcionário não encontrado.");
//        }
//        func.retiraConvenio();
//    }


//


}
