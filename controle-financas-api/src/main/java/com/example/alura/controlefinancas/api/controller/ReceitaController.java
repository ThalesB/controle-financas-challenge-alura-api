package com.example.alura.controlefinancas.api.controller;

import com.example.alura.controlefinancas.api.event.RecursoCriadoEvent;
import com.example.alura.controlefinancas.api.exceptionhandler.ControleFinancasExceptionHandler;
import com.example.alura.controlefinancas.api.model.DTO.ReceitaDetalheDto;
import com.example.alura.controlefinancas.api.model.DTO.ReceitaDto;
import com.example.alura.controlefinancas.api.model.DTO.ReceitaListaDto;
import com.example.alura.controlefinancas.api.model.DespesaFixa;
import com.example.alura.controlefinancas.api.model.Receita;
import com.example.alura.controlefinancas.api.model.formDTO.ReceitaCadastroFormDto;
import com.example.alura.controlefinancas.api.repository.ReceitaRepository;
import com.example.alura.controlefinancas.api.service.ReceitaService;
import com.example.alura.controlefinancas.api.service.exception.ReceitaMesmaDescricaoNoMesException;
import com.example.alura.controlefinancas.api.service.exception.ReceitaNaoExistenteException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @ResponseStatus(HttpStatus.OK)
    public List<ReceitaListaDto> listarReceita(@RequestParam(value = "descricao", required = false) String descricao){

        return receitaService.listar(descricao);
    }

    @GetMapping("/paginada")
    @ResponseStatus(HttpStatus.OK)
    public Page<ReceitaListaDto> pesquisaPaginada(@RequestParam(value = "descricao", required = false) String descricao, Pageable pageable){

        return receitaService.pesquisaPaginada(descricao, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDetalheDto> findReceitaById(@PathVariable Long id){

        return receitaService.verificaReceitaExistente(id);
    }

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<List<ReceitaListaDto>> listagemReceitaNoMes(@PathVariable Integer ano, @PathVariable Integer mes){

        return ResponseEntity.ok(receitaService.pesquisarReceitasNoMes(ano, mes));
    }

    @PostMapping
    public ResponseEntity<ReceitaDto> cadastroReceita(@Valid @RequestBody ReceitaCadastroFormDto receitaFormDto, HttpServletResponse response){

            Receita receitaSalva = receitaService.salvar(receitaFormDto);

            publisher.publishEvent(new RecursoCriadoEvent(this, response, receitaSalva.getId()));

            return ResponseEntity.status(HttpStatus.CREATED).body(new ReceitaDto(receitaSalva));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removerReceita(@PathVariable Long id){

        receitaRepository.deleteById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReceitaDto> updateReceita(@Valid @RequestBody Receita receita, @PathVariable Long id){

        Receita receitaSalva = receitaService.atualizar(receita, id);

        return ResponseEntity.ok(new ReceitaDto(receitaSalva));
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
