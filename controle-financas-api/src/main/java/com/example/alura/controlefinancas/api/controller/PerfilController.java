package com.example.alura.controlefinancas.api.controller;

import com.example.alura.controlefinancas.api.event.RecursoCriadoEvent;
import com.example.alura.controlefinancas.api.model.DTO.PerfilDto;
import com.example.alura.controlefinancas.api.model.Perfil;
import com.example.alura.controlefinancas.api.model.formDTO.PerfilFormDto;
import com.example.alura.controlefinancas.api.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    ApplicationEventPublisher publisher;

    @PostMapping
    @Transactional
    public ResponseEntity<PerfilDto> cadastrar (@Valid @RequestBody PerfilFormDto perfilFormDto, HttpServletResponse response){

        Perfil perfil = perfilFormDto.converter();

        Perfil perfilSalvo = perfilRepository.save(perfil);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, perfilSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new PerfilDto(perfilSalvo));
    }
}
