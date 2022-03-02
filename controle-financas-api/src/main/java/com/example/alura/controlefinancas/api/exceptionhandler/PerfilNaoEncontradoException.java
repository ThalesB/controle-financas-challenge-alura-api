package com.example.alura.controlefinancas.api.exceptionhandler;


public class PerfilNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PerfilNaoEncontradoException(Long perfilId){
        super("Perfil de Id: " + perfilId + ", não encontrado.");
    }
}
