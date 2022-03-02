package com.example.alura.controlefinancas.api.controller;

import com.example.alura.controlefinancas.api.config.security.TokenDto;
import com.example.alura.controlefinancas.api.model.formDTO.UsuarioFormDto;
import com.example.alura.controlefinancas.api.config.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@Valid @RequestBody UsuarioFormDto usuarioFormDto){

        UsernamePasswordAuthenticationToken dadosLogin = usuarioFormDto.converter();

        try {
            Authentication authentication = authenticationManager.authenticate(dadosLogin);

            String token = tokenService.gerarToken(authentication);

            return ResponseEntity.ok(new TokenDto(token, "Bearer"));

        } catch (AuthenticationException e){

            return ResponseEntity.badRequest().build();
        }
    }
}
