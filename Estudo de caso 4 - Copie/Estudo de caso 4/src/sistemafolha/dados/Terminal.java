package sistemafolha.dados;


import excecoes.FolhaException;
import sistemafolha.evento.Evento;

import sistemafolha.usuario.Funcionario;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Terminal {

    private static RepositorioDados repositorio;
    private static final Scanner scanner = new Scanner(System.in);

    public Terminal(){
        this.repositorio = new RepositorioDados(this);
    }

    public static void menuAdm() throws Exception {
        int opcao;
        do {
            opcao = getInt("""
               Menu de Administrador:
               1 - Contratar Funcionário
               2 - Adicionar Evento
               3 - Listar Funcionários
               4 - Listar Eventos
               5 - Demitir Funcionário
               6 - Remover Evento
               7 - Menu do Funcionário
               8 - Menu do Convênio
               9 - Sair""");

            switch (opcao) {
                case 1 -> contratarFuncionario();
                case 2 -> adicionarEvento();
                case 3 -> listarFuncionarios();
                case 4 -> listarEventos();
                case 5 -> demitirFuncionario();
                case 6 -> removerEvento();
                case 7 -> menuFuncionario(lerFuncionarioParaEvento());
                case 8 -> menuConvenio(lerFuncionarioParaEvento());
                case 9 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 9);
    }

    public static void menuFuncionario(Funcionario func) throws Exception {
        int opcao;
        do {
            opcao = getInt("""
           Menu do Funcionário:
           1 - Visualizar Demonstrativo
           2 - Listar Eventos
           3 - Menu do Convênio
           4 - Voltar""");

            switch (opcao) {
                case 1 -> visualizarDemonstrativo(func);
                case 2 -> listarEventosFuncionario(func);
                case 3 -> menuConvenio(func);
                case 4 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 4);
    }

    public static void menuConvenio(Funcionario func) throws Exception {
        int opcao;
        do {
            opcao = getInt("""
           Menu do Convênio:
           1 - Adicionar Convênio
           2 - Remover Convênio
           3 - Menu do Funcionário
           4 - Voltar""");

            switch (opcao) {
                case 1 -> adicionarConvenio(func);
                case 2 -> removerConvenio(func);
                case 3 -> menuFuncionario(func);
                case 4 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 4);
    }

    private static void adicionarConvenio(Funcionario func) {
        double desconto = getDouble("Digite o valor do desconto: ");
        try {
            repositorio.criaConvenio(func, func.getConvenio(), desconto);
            System.out.println("Convênio adicionado com sucesso!");
        } catch (FolhaException e) {
            System.out.println("Erro ao adicionar convênio: " + e.getMessage());
        }
    }

    private static void removerConvenio(Funcionario func){
        try {
            repositorio.removeConvenio(func, func.getConvenio());
            System.out.println("Convênio removido com sucesso!");
        } catch (FolhaException e) {
            System.out.println("Erro ao remover convênio: " + e.getMessage());
        }
    }

    private static Funcionario lerFuncionarioParaEvento() {
        Funcionario funcionario = null;
        do {
            String nomeFuncionario = getString("Digite o nome do funcionário: ");
            if (repositorio.existeFuncionario(nomeFuncionario)) {
                funcionario = repositorio.buscaFuncionario(nomeFuncionario);
            } else {
                System.out.println("Funcionário não encontrado. Tente novamente.");
            }
        } while (funcionario == null);
        return funcionario;
    }

    private static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    private static int getInt(String prompt) {
        int result = 0;
        boolean validInput = false;
        do {
            try {
                System.out.print(prompt);
                result = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        } while (!validInput);
        return result;
    }

    private static double getDouble(String prompt) {
        double result = 0.0;
        boolean validInput = false;
        do {
            try {
                System.out.print(prompt);
                result = Double.parseDouble(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        } while (!validInput);
        return result;
    }
    public static Date lerData() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.print("Digite uma data (formato dd/MM/yyyy): ");
        String dataString = scanner.next();

        try {
            return dateFormat.parse(dataString);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Certifique-se de usar o formato dd/MM/yyyy.");
            return null; // Retorna null em caso de erro.
        }
    }
    private static boolean getBoolean(String prompt) {
        boolean result = false;
        boolean validInput = false;
        do {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().toLowerCase();
                if (input.equals("true") || input.equals("false")) {
                    result = Boolean.parseBoolean(input);
                    validInput = true;
                } else {
                    System.out.println("Por favor, digite 'true' para sim ou 'false' para não.");
                }
            } catch (Exception e) {
                System.out.println("Por favor, digite um valor booleano válido.");
            }
        } while (!validInput);
        return result;
    }
    private static void contratarFuncionario() {
        try {
            String nome = getString("Digite o nome do funcionário: ");
            Date data = lerData();
            String cargo = getString("Digite o cargo do funcionário: ");
            double salario = getDouble("Digite o salário do funcionário: ");

            repositorio.contrataFuncionario(nome, data, cargo, salario);
            System.out.println("Funcionário contratado com sucesso!");
        } catch (FolhaException e) {
            System.out.println("Erro ao adicionar funcionário: " + e.getMessage());
        }
    }

    private static void listarFuncionarios() {
        System.out.println("Lista de Funcionários:");
        for (Funcionario func : repositorio.getFuncionarios()) {
            System.out.println(func.getNome());
        }
    }
    private static void removerEvento() {
        Funcionario funcionario = lerFuncionarioParaEvento();
        if (funcionario != null) {
            try {
                listarEventosFuncionario(funcionario); // Lista os eventos do funcionário para escolha
                String idEvento = getString("Digite o ID do evento que deseja remover: ");

                repositorio.removeEvento(idEvento, funcionario);
                System.out.println("Evento removido com sucesso!");
            } catch (FolhaException e) {
                System.out.println("Erro ao remover evento: " + e.getMessage());
            }
        }
    }

    private static void listarEventosFuncionario(Funcionario funcionario) {
        System.out.println("Lista de Eventos do Funcionário " + funcionario.getNome() + ":");

        try {
            ArrayList<Evento> eventos = repositorio.getEventos(funcionario);

            if (eventos.isEmpty()) {
                System.out.println("Nenhum evento encontrado para este funcionário.");
            } else {
                for (int i = 0; i < eventos.size(); i++) {
                    Evento evento = eventos.get(i);
                    System.out.println("ID: " + i + " - Tipo: " + evento.getTipoEvento());
                }
            }
        } catch (FolhaException e) {
            System.out.println("Erro ao listar eventos: " + e.getMessage());
        }
    }

    private static void adicionarEvento() {
        Funcionario funcionario = lerFuncionarioParaEvento();
        if (funcionario != null) {
            String tipoEvento = getString("Digite o tipo do evento (Atraso, Comissão, Falta, Hora Extra, Reajuste, Rescisão): ");
            try {
                Evento novoEvento;

                // Escolha do tipo de evento com base na entrada do usuário
                switch (tipoEvento.toLowerCase()) {
                    case "contratação":
                        contratarFuncionario();
                        break;
                    case "atraso":
                        int minutosAtraso = getInt("Digite a quantidade de minutos de atraso: ");
                        repositorio.cadastraAtraso(funcionario, lerData(), minutosAtraso);
                        break;
                    case "comissão":
                        double valorComissao = getDouble("Digite o valor da comissão: ");
                        repositorio.cadastraComissao(funcionario, lerData(), valorComissao);
                        break;
                    case "falta":
//                        int diasFalta = getInt("Digite a quantidade de dias de falta: ");
                        repositorio.cadastraFalta(funcionario, lerData());
                        break;
                    case "hora extra":
                        int horasExtras = getInt("Digite a quantidade de horas extras: ");
                        repositorio.cadastraHoraExtra(funcionario, horasExtras, lerData());
                        break;
                    case "reajuste":
                        double percentualReajuste = getDouble("Digite o percentual de reajuste: ");
                        repositorio.cadastraReajuste(funcionario, percentualReajuste, lerData());
                        break;
                    case "rescisão":
                        demitirFuncionario();
                        break;
                    default:
                        System.out.println("Tipo de evento inválido.");
                        return;
                }
                System.out.println("Evento adicionado com sucesso!");
            } catch (FolhaException e) {
                System.out.println("Erro ao adicionar evento: " + e.getMessage());
            }
        }
    }

    private static void criarDemonstrativos() {
        Date dtInicial = lerData();
        Date dtFinal = lerData();

        try {
            repositorio.criaDemonstrativosParaFuncionarios(dtInicial, dtFinal);
            System.out.println("Demonstrativos criados com sucesso!");
        } catch (FolhaException e) {
            System.out.println("Erro ao criar demonstrativos: " + e.getMessage());
        }
    }

    private static void visualizarDemonstrativo(Funcionario func) throws Exception {
        System.out.println("Demonstrativos para o Funcionário " + func.getNome() + ":");

        ArrayList<Demonstrativo> demonstrativos = func.getDemonstrativos();
        if (demonstrativos.isEmpty()) {
            System.out.println("Nenhum demonstrativo disponível.");
        } else {
            for (int i = 0; i < demonstrativos.size(); i++) {
                System.out.println("Demonstrativo " + (i + 1) + ":");
                demonstrativos.get(i).imprime();
                System.out.println(); // Adiciona uma linha em branco entre os demonstrativos
            }
        }
    }


    private static void listarEventos() {
        System.out.println("Lista de Eventos:");
        for (Evento evento : repositorio.getEventos()) {
            System.out.println(evento.getTipoEvento());
        }
    }

    private static void demitirFuncionario() {
        String nome = getString("Digite o nome do funcionário a ser demitido: ");
        Date dataDemissao = lerData();  // Adicione a leitura da data de demissão
        int tipoDemissao = getInt("""
        Digite o tipo de demissão:
        1 - Se demitiu
        2 - Foi demitido
        3 - Aposentado""");

        boolean avisoPrevio = getBoolean("Houve aviso prévio? (Digite 'true' para sim, 'false' para não): ");

        try {
            repositorio.demiteFuncionario(nome, dataDemissao, tipoDemissao, avisoPrevio);
            System.out.println("Funcionário demitido com sucesso!");
        } catch (FolhaException e) {
            System.out.println("Erro ao demitir funcionário: " + e.getMessage());
        }
    }

    public void acessarMenuAdministrador() throws Exception {
        String id = getString("Digite o ID do Administrador: ");
        String senha = getString("Digite a senha do Administrador: ");

        // Lógica de autenticação do administrador (exemplo: ID "admin" e senha "admin123")
        if ("admin".equals(id) && "admin123".equals(senha)) {
            System.out.println("Autenticação bem-sucedida!");
            menuAdm();
        } else {
            System.out.println("Autenticação falhou. Tente novamente.");
        }
    }

    public void acessarMenuFuncionario() throws Exception {
        String nomeFuncionario = getString("Digite o nome do Funcionário: ");

        // Lógica de autenticação do funcionário (verificar se o funcionário está cadastrado)
        if (repositorio.existeFuncionario(nomeFuncionario)) {
            Funcionario func = repositorio.buscaFuncionario(nomeFuncionario);
            System.out.println("Autenticação bem-sucedida!");
            menuFuncionario(func);
        } else {
            System.out.println("Funcionário não encontrado. Tente novamente.");
        }
    }

    public void acessarMenuConvenio() throws Exception {
        String nomeFuncionario = getString("Digite o nome do Funcionário para acessar o Convênio: ");

        // Lógica de autenticação do convênio (verificar se o funcionário está cadastrado e possui convênio)
        if (repositorio.existeFuncionario(nomeFuncionario) && repositorio.temConvenio(repositorio.buscaFuncionario(nomeFuncionario))) {
            Funcionario func = repositorio.buscaFuncionario(nomeFuncionario);
            System.out.println("Autenticação bem-sucedida!");
            menuConvenio(func);
        } else {
            System.out.println("Funcionário não encontrado ou sem convênio. Tente novamente.");
        }
    }


}
