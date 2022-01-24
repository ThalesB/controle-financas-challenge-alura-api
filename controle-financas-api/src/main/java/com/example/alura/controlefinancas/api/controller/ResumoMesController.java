package com.example.alura.controlefinancas.api.controller;


import com.example.alura.controlefinancas.api.model.ResumoMes;
import com.example.alura.controlefinancas.api.service.ResumoMesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/resumo")
public class ResumoMesController {

    @Autowired
    ResumoMesService resumoMesService;

    @GetMapping("/{ano}/{mes}")
     public ResponseEntity<ResumoMes> buscarResumoMes(@PathVariable Integer ano, @PathVariable Integer mes){

        ResumoMes resumoMes = resumoMesService.criarResumoMes(ano, mes);

        return ResponseEntity.ok(resumoMes);
    }

}
