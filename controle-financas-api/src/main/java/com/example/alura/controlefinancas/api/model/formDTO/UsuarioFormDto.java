package com.example.alura.controlefinancas.api.model.formDTO;

import com.example.alura.controlefinancas.api.exceptionhandler.PerfilNaoEncontradoException;
import com.example.alura.controlefinancas.api.model.DTO.PerfilDto;
import com.example.alura.controlefinancas.api.model.Perfil;
import com.example.alura.controlefinancas.api.model.Usuario;
import com.example.alura.controlefinancas.api.repository.PerfilRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioFormDto {

    @NotNull
    private String email;

    @NotNull
    private String senha;

    private List<PerfilDto> perfilDtos = new ArrayList<>();

    public Usuario converterUsuario(PerfilRepository perfilRepository){
        return new Usuario(email, new BCryptPasswordEncoder().encode(senha), mapToPerfilList(perfilRepository));
    }

    private List<Perfil> mapToPerfilList(PerfilRepository perfilRepository) {
        List<Perfil> perfis = new ArrayList<>();

        this.perfilDtos.stream()
                .map(perfilDto -> perfis.add(
                        perfilRepository.findById(perfilDto.getId())
                                .orElseThrow(() -> throwPerfilNaoEncontradoException(perfilDto))
                )).collect(Collectors.toList());

        return perfis;
    }

    private PerfilNaoEncontradoException throwPerfilNaoEncontradoException(PerfilDto perfilDto) {
        return new PerfilNaoEncontradoException(perfilDto.getId());
    }

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(email, senha);
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setPerfilDtos(List<PerfilDto> perfilDtos) {
        this.perfilDtos = perfilDtos;
    }


}
