package com.example.alura.controlefinancas.api.model.DTO;


import com.example.alura.controlefinancas.api.model.Perfil;

public class PerfilDto {

    private Long id;
    private String nome;

    public PerfilDto(){

    }

    public PerfilDto(Perfil perfil){
        this.id = perfil.getId();
        this.nome = perfil.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
