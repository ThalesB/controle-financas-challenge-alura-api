package com.example.alura.controlefinancas.api.controller;

import com.example.alura.controlefinancas.api.event.RecursoCriadoEvent;
import com.example.alura.controlefinancas.api.exceptionhandler.ControleFinancasExceptionHandler;
import com.example.alura.controlefinancas.api.model.Receita;
import com.example.alura.controlefinancas.api.repository.ReceitaRepository;
import com.example.alura.controlefinancas.api.service.ReceitaService;
import com.example.alura.controlefinancas.api.service.exception.ReceitaMesmaDescricaoNoMesException;
import com.example.alura.controlefinancas.api.service.exception.ReceitaNaoExistenteException;
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
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    ReceitaRepository receitaRepository;

    @Autowired
    ReceitaService receitaService;

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    MessageSource messageSource;

    @GetMapping
    public ResponseEntity<List<Receita>> receitasListar(){

        List<Receita> receitas = receitaRepository.findAll();

        return ResponseEntity.ok(receitas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receita> findReceitaById(@PathVariable Long id){

        return receitaService.verificaReceitaExistente(id);
    }

    @PostMapping
    public ResponseEntity<Receita> cadastroReceita(@Valid @RequestBody Receita receita, HttpServletResponse response){

            Receita receitaSalva = receitaService.salvar(receita);

            publisher.publishEvent(new RecursoCriadoEvent(this, response, receitaSalva.getId()));

            return ResponseEntity.status(HttpStatus.CREATED).body(receitaSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removerReceita(@PathVariable Long id){

        receitaRepository.deleteById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Receita> updateReceita(@Valid @RequestBody Receita receita, @PathVariable Long id){

        Receita receitaSalva = receitaService.atualizar(receita, id);

        return ResponseEntity.ok(receitaSalva);
    }

    @ExceptionHandler( {ReceitaNaoExistenteException.class} )
    public ResponseEntity<Object> handleReceitaNaoExistenteException(ReceitaNaoExistenteException ex){

        String mensagemUsuario = messageSource.getMessage("receita.nao-encontrada", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

        List<ControleFinancasExceptionHandler.Erro> erros = Arrays.asList(new ControleFinancasExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler( {ReceitaMesmaDescricaoNoMesException.class} )
    public ResponseEntity<Object> handleReceitaMesmaDescricaoNoMes(ReceitaMesmaDescricaoNoMesException ex){

        String mensagemUsuario = messageSource.getMessage("receita.mesma-descricao-mes", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

        List<ControleFinancasExceptionHandler.Erro> erros = Arrays.asList(new ControleFinancasExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));

        return ResponseEntity.badRequest().body(erros);
    }

}
