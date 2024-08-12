package poo.controle;

import poo.item.Item;
import poo.item.Livro;
import poo.item.Periodico;
import poo.item.Dvd;
import poo.usuarios.Aluno;
import poo.usuarios.Professor;
import poo.usuarios.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static javax.swing.UIManager.getInt;


public class Terminal {
    private Biblioteca minhaBiblioteca;
    private int bibliotecaStatus;
    private String admin = "admin";
    private int senhaAdmin = 123;
    public Terminal(){
        this.minhaBiblioteca = new Biblioteca(this);
    }



    public void iniciaBiblioteca(){
        int opcao;
        opcao = getInt("Escolha qual o seu usuário: digite 1 para admin e 2 para usuário comum: ");
        menuEscolha(opcao);
        opcao = getOpcao();
        switch (opcao) {
            case 1 -> {
                // Menu do administrador
                menuAdmin();
            }
            case 2 -> {
                // Menu do usuário comum
                menuUsuario();
            }
            default -> {
                System.out.println("Opção inválida.");
            }
        }
    }

    public void menuEscolha(int opcao){
        if(opcao == 1){
            int senha = getInt("Digite a senha: ");
            String novoUsuario = getString("Digite o usuario: ");
            if(senha == senhaAdmin && novoUsuario.equalsIgnoreCase(admin)){
                setModo(1);
            }
        }else{
            setModo(2);
        }

    }
    public void setModo(int modo){
        if(modo == 1 || modo == 2){
            this.bibliotecaStatus = modo;
        }
    }//1 == atendimento e 2 == administrador

    private int getOpcao() {
        int opcao = 0;

        do {
            if (this.bibliotecaStatus == 1) {
                opcao = menuUsuario();
            } else if (this.bibliotecaStatus == 2) {
                opcao = menuAdmin();
            }
        } while (opcao == 0);

        return opcao;
    }

    private int menuUsuario() {
        int opcao;
        do {
            opcao = getInt("""
                   Menu de Atendimento:
                   1 - Emprestar Item
                   2 - Bloquear Livro/Periodico
                   3 - Devolver Item
                   4 - Buscar Item
                   5 - Verificar Disponibilidade
                   6 - Verificar Cota Máxima
                   0 - Sair""");

            switch (opcao) {
                case 1 -> emprestimo((getInt("Digite a opcao(Livro, Periodico, Dvd)")), (buscaUsuario(getString("digite no nome do usuario:"))), (getString("Digite o titulo do livro:")));
                case 2 -> bloquearItem();
                case 3 -> emprestimoUsuario();
                case 4 -> bloquearLivroUsuario();
                case 5 -> devolveItemUsuario();
                case 6 -> buscarItemUsuario();
                case 7 -> verificarDisponibilidadeUsuario();
                case 8 -> verificarCotaMaximaUsuario();
                case 0 -> System.out.println("Saindo do menu de usuário.");
                default -> System.err.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
        return opcao;
    }
    public int menuAdmin(){
       int opcao=0;
       do{
           opcao = getInt("""
                   Menu de Administrador:
                   1 - Cadastrar Item
                   2 - Remover Item
                   3 - Buscar Item
                   4 - Verificar Disponibilidade
                   5 - Verificar Quantos Livros Emprestados
                   6 - Cadastrar Usuario
                   7 - Remover Usuario
                   8 - Sair""");
           if (opcao != 1 & opcao != 2 & opcao != 3 & opcao != 4 & opcao != 5 & opcao != 6 & opcao != 7 & opcao != 8) {
               opcao = 0;
           }
           if(opcao == 8){
               break;
           }
       }while(opcao ==0);
       return opcao;
    }
    private int getInt(String string){
        Scanner r = new Scanner(System.in);
        System.out.println("Entre com " + string);
        if(r.hasNextInt()){
            return r.nextInt();
        }
        String st = r.next();
        System.err.println("Erro na leitura de Dados");
        return 0;
    }

    private String getString(String string){
        Scanner a = new Scanner(System.in);
        System.out.println("entre com " + string);
        if(a.hasNextInt() || a.hasNextBoolean() || a.hasNextDouble() || a.hasNextFloat()){
            String st = a.next();
            System.out.println("Erro na leitura de Dados");
        }
        return a.next();
    }



    private void cadastrarItem() {
        String opcao = getString("Digite o tipo de item a ser cadastrado (Livro, Periodico, Dvd): ");
        switch(opcao){
            case "Livro"->{
                Livro livro;
                if(this.minhaBiblioteca.cadastraItem(livro = new Livro(getString("Digite o titulo do Livro que deseja cadastrar")))){
                    System.out.println("Livro cadastrado com sucesso");
                }
            }
            case "Periodico"->{
                Periodico periodico;
                if(this.minhaBiblioteca.cadastraItem(periodico = new Periodico(getString("Digite o titulo do Periodico que deseja cadastrar")))){
                    System.out.println("Periodico cadastrado com sucesso");
                }
            }
            case "Dvd"->{
                Dvd dvd;
                if(this.minhaBiblioteca.cadastraItem(dvd = new Dvd(getString("Digite o titulo do Dvd que deseja cadastrar"), getInt("Digite o privilegio: ")))){
                    System.out.println("Dvd cadastrado com sucesso");
                }
            }
            default -> {
                System.err.println("Nao foi possivel. Tente novamente");
            }
        }
    }
    private void removerItem(){
        String titulo = getString("Digite o título do item a ser removido: ");
        if(this.minhaBiblioteca.removeItem(titulo)){
            System.out.println("O item foi removido com sucesso");
        }
    }

    private void buscarItem(){
        int opcao = getInt("Digite o tipo de item a ser buscado (1 - Livro, 2 - Periódico, 3 - DVD): ");
        String titulo = getString("Digite o título do item a ser buscado: ");
        switch(opcao){
            case 1 ->{
                Livro livro;
                livro = this.minhaBiblioteca.getLivro(titulo);
                if (livro != null) {
                    System.out.println("O livro buscado é: " + livro);
                } else {
                    System.out.println("Livro não encontrado.");
                }
            }
            case 2 ->{
                Periodico periodico;
                periodico = this.minhaBiblioteca.getPeriodico(titulo);
                if (periodico != null) {
                    System.out.println("O periódico buscado é: " + periodico);
                } else {
                    System.out.println("Periódico não encontrado.");
                }
            }
            case 3 ->{
                Dvd dvd;
                dvd = this.minhaBiblioteca.getDvd(titulo);
                if (dvd != null) {
                    System.out.println("O DVD buscado é: " + dvd);
                } else {
                    System.out.println("DVD não encontrado.");
                }

            }
        }
    }
    private boolean verificarDisponibilidade(){
        String titulo = getString("Digite o título do item a ser verificado: ");
        if(this.minhaBiblioteca.buscaItem(titulo).isDisponivel()){
            System.out.println("Item está disponivel para emprestimo");
            return true;
        }else{
            System.out.println("Item está indisponivel para emprestimo");
        }
        return false;
    }
//    private void verificarItensCadastrados(int opcao, String titulo){
//    }

    private void bloquearLivro(String titulo, Usuario usuario, int prazo){
        if((minhaBiblioteca.bloqueiaLivro(titulo, usuario, prazo)) && usuario.isProfessor()){
            System.out.println("Livro: "+ titulo + "bloqueado para " + usuario + "pelo tempo de: " + prazo + " dias.\n");
        }else{
            System.out.println("O usuário não é professor. Não foi possível bloquear o livro");
        }
    }

    private void emprestimo(int opcao, Usuario usuario, String titulo){
        switch(opcao){
            case 1->{
                Livro livro = this.minhaBiblioteca.getLivro(titulo);
                if(livro.isBloqueado()){
                    System.err.println("Livro bloqueado, tente novamente depois.");
                }else{
                    if(usuario.isAluno()){
                        Aluno aluno = (Aluno)usuario;
                        if(livro.isDisponivel() && aluno.isAptoARetirar() && !(aluno.temPrazoVencido()) && aluno.isRegular()){
                            livro.empresta(usuario, 7);
                            usuario.retiraItem(livro);
                        }else{
                            if(!aluno.isRegular() && aluno.isAptoARetirar()){
                                livro.empresta(usuario, 4);
                                usuario.retiraItem(livro);
                            }else{
                                System.err.println("Usuario não está apto a retirar. Renove seu tempo de utilização do cartao da biblioteca.");
                            }
                        }
                    }
                    if(livro.isDisponivel() && usuario.isProfessor() && usuario.isAptoARetirar() && (!livro.isBloqueado())){
                        livro.empresta(usuario, 14);
                        usuario.retiraItem(livro);
                    }else{
                        System.err.println("Usuario não está apto a retirar. Quite suas devoluções e tente novamente.");
                    }
                    if(livro.isDisponivel() && usuario.isAptoARetirar()){
                        livro.empresta(usuario, 4);
                        usuario.retiraItem(livro);
                    }else{
                        System.err.println("Usuario não está apto a retirar. Quite suas devoluções e tente novamente.");
                    }
                }

            }
            case 2->{
                Periodico periodico = this.minhaBiblioteca.getPeriodico(titulo);

                if(periodico.isBloqueado()){
                    System.err.println("O periodico está bloqueado, tente novamente em alguns dias.");
                }else{
                    if(periodico.isDisponivel() && usuario.isAptoARetirar() && usuario.isProfessor()){
                        periodico.empresta(usuario, 7);
                        usuario.retiraItem(periodico);
                    }else{
                        if(!periodico.isDisponivel()) {
                            System.err.println("Periodico/Jornal não está disponivel");
                        }
                        if(!usuario.isProfessor()) {
                            System.err.println("O usuario nao tem permissão para retirar o livro.");
                        }
                        if(!usuario.isAptoARetirar()){
                            System.err.println("O usuario tem que devolver livros antes de poder voltar a emprestar.");
                        }
                    }
                }
            }
            case 3->{
                Dvd dvd = this.minhaBiblioteca.getDvd(titulo);
                if(dvd.isDisponivel()){
                    if(usuario.isAluno()){
                        Aluno aluno = (Aluno) usuario;
                        if((!aluno.isRegular()) && usuario.isAluno() && aluno.isAptoARetirar() && dvd.isDisponivel()){
                            dvd.empresta((Usuario) usuario, 2);
                            usuario.retiraItem(dvd);
                        }else if(!aluno.isRegular() && aluno.isAptoARetirar()){
                            dvd.empresta(usuario, 2);
                            usuario.retiraItem(dvd);
                        }else if(!aluno.isAptoARetirar()){
                            System.err.println("Usuario não está apto a retirar. Quite suas devoluções e tente novamente.");
                        }
                    }else{
                        if(dvd.isDisponivel() && usuario.isAptoARetirar()){
                            dvd.empresta(usuario, 2);
                            usuario.retiraItem(dvd);
                        }
                    }
                }else{
                    System.err.println("O dvd nao está disponivel, tente novamente em alguns dias.");
                }
            }
        }
    }

    private void bloquearItem(Usuario usuario, String titulo, int prazo){
        Livro livro = this.minhaBiblioteca.getLivro(titulo);

       if(livro == null){
           System.err.println("Titulo invalido, nao foi possivel completar o pedido.");
       }else{
           if(usuario.isProfessor() & (!livro.isBloqueado())){
                if(prazo>0 && prazo<=20){
                    this.minhaBiblioteca.bloqueiaLivro(titulo, usuario, prazo);
                }else{
                    System.err.println("Prazo invalido. Tente novamente.");
                }
           }else{
               System.err.println("Usuario não tem permissão para bloquear itens.");
           }
       }
    }

    public boolean devolveItem(String nome, String titulo){
        Usuario usuario = this.minhaBiblioteca.buscarUsuario(nome); //busca o usuario na biblioteca e joga na variavel local
        Item item = this.minhaBiblioteca.buscaItem(titulo); //busca o item pelo titulo e joga na variavel local
        if(usuario == null | item == null){
            return false;
        }
        usuario.devolveItem(item);
        return item.retorna(usuario);
    }

    public void cadastroUsuario(){
        String opcao = getString("Digite o tipo de usuário a ser cadastrado (Usuario, Aluno, Professor): ");
        switch (opcao){
            case "Usuario"->{
                this.minhaBiblioteca.cadastrarUsuario(new Usuario(getString("digite o nome do usuario")));
            }
            case "Aluno"->{
                Date data = lerData();
                this.minhaBiblioteca.cadastrarUsuario(new Aluno(getString("Digite o nome do usuario"), data));
            }
            case "Professor"->{
                this.minhaBiblioteca.cadastrarUsuario(new Professor(getString("digite o nome do usuario")));
            }

        }
    }
    public void removeUsuario(){
        String nome = getString("Digite o nome do usuário a ser removido: ");
        if(this.minhaBiblioteca.removerUsuario(nome)){
            System.out.println("Usuario removido com sucesso.");
        }else{
            System.out.println("Nao foi possivel remover o usuario");
        }
    }

    public void buscaUsuario(String nome){
        Usuario usuario = this.minhaBiblioteca.buscarUsuario(nome);
        if(usuario != null){
            System.out.println("Usuario: " + usuario);
        }else{
            System.err.println("Usuario nao cadastrado");
        }
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

}
