package poo.item;

import poo.usuarios.Usuario;

public class Dvd extends Item{ //outra subclasse do polimorfismo

    private int privilegio;

    public Dvd(String titulo, int privilegio){
        super(titulo);
        this.privilegio = privilegio;
    }


//    public boolean isLivro(){
//        return false;
//    }
//    public boolean isPeriodico(){
//        return false;
//    }
//
//    public boolean isDvd(){
//        return true;
//    }


    public boolean empresta(Usuario usuario,  int prazo){
        switch (this.privilegio){
            case 1->{
                return super.empresta(usuario, 2);
            }
            case 2 ->{
                if(usuario.isProfessor() || usuario.isAluno()){
                    return super.empresta(usuario, 2);
                }
                return false;
            }
            case 3 ->{
                if(usuario.isProfessor()){
                    return super.empresta(usuario, 2);
                }
                return false;
            }
            default -> {
                return false;
            }
        }
    }


}
