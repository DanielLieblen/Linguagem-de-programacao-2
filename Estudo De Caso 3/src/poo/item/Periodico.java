package poo.item;

import poo.usuarios.Usuario;

public class Periodico extends Livro{
    private Usuario usuario;
    public Periodico(String titulo){
        super(titulo);
    }

    public boolean empresta(Usuario usuario, int prazo){
        if(usuario.isProfessor()){
            return super.empresta(usuario, prazo);
        }else{
            return false;
        }
    }

}
