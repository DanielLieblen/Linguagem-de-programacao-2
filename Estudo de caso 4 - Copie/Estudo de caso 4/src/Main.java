import sistemafolha.dados.Terminal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Terminal terminal = new Terminal();
        int opcao;
        do {
            opcao = getInt("""
                Escolha uma opção:
                1 - Acessar Menu Administrador
                2 - Acessar Menu Convenio
                3 - Acessar Menu Funcionário
                4 - Sair\n""");

            switch (opcao) {
                case 1:
                    try {
                        terminal.acessarMenuAdministrador();
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        terminal.acessarMenuConvenio();
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        terminal.acessarMenuFuncionario();
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 4);
    }

    private static int getInt(String prompt) {
        Scanner scanner = new Scanner(System.in);

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

}