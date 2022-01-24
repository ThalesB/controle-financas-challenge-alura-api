package com.example.alura.controlefinancas.api.controller;

import com.example.alura.controlefinancas.api.event.RecursoCriadoEvent;
import com.example.alura.controlefinancas.api.exceptionhandler.ControleFinancasExceptionHandler;
import com.example.alura.controlefinancas.api.model.DespesaEventual;
import com.example.alura.controlefinancas.api.model.DespesaFixa;
import com.example.alura.controlefinancas.api.repository.DespesaEventualRepository;
import com.example.alura.controlefinancas.api.repository.DespesaFixaRepository;
import com.example.alura.controlefinancas.api.service.DespesaEventualService;
import com.example.alura.controlefinancas.api.service.DespesaFixaService;
import com.example.alura.controlefinancas.api.service.exception.DespesaEventualMesmaDescricaoNoMesException;
import com.example.alura.controlefinancas.api.service.exception.DespesaEventualNaoExistenteException;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/despesas-eventuais")
public class DespesaEventualController {

    @Autowired
    DespesaEventualRepository despesaEventualRepository;

    @Autowired
    DespesaEventualService despesaEventualService;

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    MessageSource messageSource;

    @GetMapping
    public ResponseEntity<List<DespesaEventual>> DespesasEventualListar(){

        List<DespesaEventual> despesasEventuais = despesaEventualRepository.findAll();

        return ResponseEntity.ok(despesasEventuais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaEventual> findDespesaEventualById(@PathVariable Long id){

        return despesaEventualService.verificarDespesaEventualExistente(id);
    }

    @PostMapping
    public ResponseEntity<DespesaEventual> cadastroDespesaEventual(@Valid @RequestBody DespesaEventual despesaEventual, HttpServletResponse response){

            DespesaEventual despesaEventualSalva = despesaEventualService.salvar(despesaEventual);

            publisher.publishEvent(new RecursoCriadoEvent(this, response, despesaEventualSalva.getId()));

            return ResponseEntity.status(HttpStatus.CREATED).body(despesaEventualSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removerDespesaEventual(@PathVariable Long id){

        despesaEventualRepository.deleteById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DespesaEventual> updateDespesaEventual(@Valid @RequestBody DespesaEventual despesaEventual, @PathVariable Long id){

        DespesaEventual despesaEventualSalva = despesaEventualService.atualizar(despesaEventual, id);

        return ResponseEntity.ok(despesaEventualSalva);
    }

    @ExceptionHandler( {DespesaEventualNaoExistenteException.class} )
    public ResponseEntity<Object> handleDespesaEventualNaoExistenteException(DespesaEventualNaoExistenteException ex){

        String mensagemUsuario = messageSource.getMessage("despesa-eventual.nao-encontrada", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

        List<ControleFinancasExceptionHandler.Erro> erros = Arrays.asList(new ControleFinancasExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler( {DespesaEventualMesmaDescricaoNoMesException.class} )
    public ResponseEntity<Object> handleDespesaEventualMesmaDescricaoNoMesException(DespesaEventualMesmaDescricaoNoMesException ex){

        String mensagemUsuario = messageSource.getMessage("despesa-eventual.mesma-descricao-mes", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

        List<ControleFinancasExceptionHandler.Erro> erros = Arrays.asList(new ControleFinancasExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));

        return ResponseEntity.badRequest().body(erros);
    }

}
