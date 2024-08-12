package poo.controle;
import poo.item.Dvd;
import poo.item.Item;
import poo.item.Livro;
import poo.item.Periodico;
import poo.usuarios.Professor;
import poo.usuarios.Usuario;

import java.util.ArrayList;

public class Biblioteca {
    private Terminal meuTerminal;
    ArrayList<Item> itens;
    ArrayList<Usuario> usuarios;


    public Biblioteca(Terminal terminal){
        this.meuTerminal = terminal;
        this.itens = new ArrayList<>();

    }
//    public boolean cadastrarItem(Item item){
//        return itens.add(item);
//    }

    public boolean removeItem(String titulo){
        for(Item item : this.itens){
            if(item.getTitulo().equalsIgnoreCase(titulo)){
                itens.remove(item);
                return true;
            }
        }
        return false;
    }

    public Item buscaItem(String titulo){
        for(Item item : this.itens){
            if(titulo.equalsIgnoreCase(item.getTitulo())){
                return item;
            }
        }
        return null;
    }

    public boolean cadastraItem(Item item){
        if(this.buscaItem(item.getTitulo()) == null){
            this.itens.add(item);
        }
        return false;
    }

    public void emprestaItem(Usuario usuario, String titulo){
        Item itemEmprestar = this.buscaItem(titulo); //variavel auxiliar pega o titulo digitado pelo usuario e faz uma busca dele

        if(itemEmprestar != null){ //se o titulo realmente existir
            for(Item item : this.itens){ //faz uma busca pelo arraylist de itens
                if(titulo.equalsIgnoreCase(item.getTitulo()) && item.isDisponivel() && usuario.isAptoARetirar()){ //verifica se o item existe e se ele ta disponivel e se o usuario pode retirar ele
                    itemEmprestar = item;
                }else{
                    itemEmprestar = null;
                }
            }
        }
         //verificar se essa porra ta certa
    }

    public void cadastrarUsuario(Usuario usuario){
        if(this.buscarUsuario(usuario.getNome()) == null){
            this.usuarios.add(usuario);
        }
    }

    public Usuario buscarUsuario(String nome){
        for(Usuario usuario : this.usuarios){
            if(nome.equalsIgnoreCase(usuario.getNome())){
                return usuario;
            }
        }
        return null;
    }

    public boolean removerUsuario(String nome){
        for(Usuario usuario : this.usuarios){
            if(usuario.getNome().equalsIgnoreCase(nome)){
                usuarios.remove(usuario);
                return true;
            }
        }
        return false;
    }
    public Livro getLivro(String titulo){
        Livro livro;
        for(Item item : itens){
            if(item instanceof Livro && item.getTitulo().equalsIgnoreCase(titulo)){
                return livro = (Livro) item;
            }
        }
        System.out.println("Livro nao encontrado");
        return null;
    }

    public Periodico getPeriodico(String titulo){
        Periodico periodico;
        for(Item item : itens){
            if(item instanceof Periodico && item.getTitulo().equalsIgnoreCase(titulo)){
                return periodico = (Periodico) item;
            }
        }
        System.out.println("Periodico nao encontrado");
        return null;
    }

    public Dvd getDvd(String titulo){
        Dvd dvd;
        for(Item item : itens){
            if(item instanceof Dvd && item.getTitulo().equalsIgnoreCase(titulo)){
                return dvd = (Dvd) item;
            }
        }
        System.out.println("Dvd nao encontrado");
        return null;
    }

    public boolean bloqueiaLivro(String titulo, Usuario usuario, int prazo){
        for(Item item : itens){
            if(item instanceof Livro && item.getTitulo().equalsIgnoreCase(titulo) && !(((Livro) item).isBloqueado())){
                ((Livro) item).bloqueia(usuario, prazo);
                return true;
            }else if(((item instanceof Livro) && ((Livro)item).isBloqueado())){
                return false;
                }
            }
            return false;
        }

//    public boolean confirmaProfessor(Usuario usuario){
//        for(Usuario usuario1 : this.usuarios){
//            if(usuario1 instanceof Professor && usuario.isProfessor()){
//                return true;
//            }
//        }
//        return false;
//    }



}
