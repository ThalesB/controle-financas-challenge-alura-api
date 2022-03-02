package com.example.alura.controlefinancas.api.model.formDTO;

import com.example.alura.controlefinancas.api.model.Perfil;

import javax.validation.constraints.NotNull;

public class PerfilFormDto {

    @NotNull
    private String nome;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Perfil converter() {

        return new Perfil(nome);
    }
}
