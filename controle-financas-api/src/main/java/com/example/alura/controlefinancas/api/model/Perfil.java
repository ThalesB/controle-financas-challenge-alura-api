package com.example.alura.controlefinancas.api.model;

import com.example.alura.controlefinancas.api.model.DTO.PerfilDto;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "perfis")
public class Perfil implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "perfis")
    private List<Usuario> usuarios;

    public Perfil(String nome) {
        this.nome = nome;
    }

    public Perfil(PerfilDto perfilDto){
        this.id = perfilDto.getId();
        this.nome = perfilDto.getNome();
    }

    public Perfil() {

    }

    @Override
    public String getAuthority() {
        return this.nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perfil perfil1 = (Perfil) o;
        return Objects.equals(id, perfil1.id) && Objects.equals(nome, perfil1.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
