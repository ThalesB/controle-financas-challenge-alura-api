package com.example.alura.controlefinancas.api.controller;

import com.example.alura.controlefinancas.api.event.RecursoCriadoEvent;
import com.example.alura.controlefinancas.api.exceptionhandler.ControleFinancasExceptionHandler;
import com.example.alura.controlefinancas.api.model.DespesaFixa;
import com.example.alura.controlefinancas.api.repository.DespesaFixaRepository;
import com.example.alura.controlefinancas.api.service.DespesaFixaService;
import com.example.alura.controlefinancas.api.service.exception.DespesaFixaMesmaDescricaoNoMesException;
import com.example.alura.controlefinancas.api.service.exception.DespesaFixaNaoExistenteException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.crypto.Des;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/despesas-fixas")
public class DespesaFixaController {

    @Autowired
    DespesaFixaRepository despesaFixaRepository;

    @Autowired
    DespesaFixaService despesaFixaService;

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    MessageSource messageSource;

    @GetMapping
    public ResponseEntity<List<DespesaFixa>> DespesasFixasListar(){

        List<DespesaFixa> despesasFixas = despesaFixaRepository.findAll();

        return ResponseEntity.ok(despesasFixas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaFixa> findDespesaFixaById(@PathVariable Long id){

        return despesaFixaService.verificarDespesaExistente(id);
    }

    @PostMapping
    public ResponseEntity<DespesaFixa> cadastroDespesaFixa(@Valid @RequestBody DespesaFixa despesaFixa, HttpServletResponse response){

            DespesaFixa despesaFixaSalva = despesaFixaService.salvar(despesaFixa);

            publisher.publishEvent(new RecursoCriadoEvent(this, response, despesaFixaSalva.getId()));

            return ResponseEntity.status(HttpStatus.CREATED).body(despesaFixaSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removerDespesaFix(@PathVariable Long id){

        despesaFixaRepository.deleteById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DespesaFixa> updateDespesaFixa(@Valid @RequestBody DespesaFixa despesaFixa, @PathVariable Long id){

        DespesaFixa despesaFixaSalva = despesaFixaService.atualizar(despesaFixa, id);

        return ResponseEntity.ok(despesaFixaSalva);
    }

    @ExceptionHandler( {DespesaFixaNaoExistenteException.class} )
    public ResponseEntity<Object> handleDespesaFixaNaoExistenteException(DespesaFixaNaoExistenteException ex){

        String mensagemUsuario = messageSource.getMessage("despesa-fixa.nao-encontrada", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

        List<ControleFinancasExceptionHandler.Erro> erros = Arrays.asList(new ControleFinancasExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler( {DespesaFixaMesmaDescricaoNoMesException.class} )
    public ResponseEntity<Object> handleDespesaFixaMesmaDescricaoNoMesException(DespesaFixaMesmaDescricaoNoMesException ex){

        String mensagemUsuario = messageSource.getMessage("despesa-fixa.mesma-descricao-mes", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

        List<ControleFinancasExceptionHandler.Erro> erros = Arrays.asList(new ControleFinancasExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));

        return ResponseEntity.badRequest().body(erros);
    }

}
