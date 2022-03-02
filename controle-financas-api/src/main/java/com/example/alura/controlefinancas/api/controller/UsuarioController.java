package com.example.alura.controlefinancas.api.controller;

import com.example.alura.controlefinancas.api.event.RecursoCriadoEvent;
import com.example.alura.controlefinancas.api.model.Usuario;
import com.example.alura.controlefinancas.api.model.formDTO.UsuarioFormDto;
import com.example.alura.controlefinancas.api.repository.PerfilRepository;
import com.example.alura.controlefinancas.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PerfilRepository perfilRepository;

    @PostMapping
    public ResponseEntity cadastrar(@Valid @RequestBody UsuarioFormDto usuarioFormDto, HttpServletResponse response){

        Usuario usuario = usuarioFormDto.converterUsuario(perfilRepository);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
