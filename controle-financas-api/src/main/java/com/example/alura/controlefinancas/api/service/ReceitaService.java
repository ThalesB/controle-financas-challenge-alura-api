package com.example.alura.controlefinancas.api.service;

import com.example.alura.controlefinancas.api.model.DespesaFixa;
import com.example.alura.controlefinancas.api.model.Receita;
import com.example.alura.controlefinancas.api.repository.ReceitaRepository;
import com.example.alura.controlefinancas.api.service.exception.ReceitaMesmaDescricaoNoMesException;
import com.example.alura.controlefinancas.api.service.exception.ReceitaNaoExistenteException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {

    @Autowired
    ReceitaRepository receitaRepository;


    public ResponseEntity<Receita> verificaReceitaExistente(Long id) {

        Optional<Receita> receitaOptional = receitaRepository.findById(id);

        return receitaOptional.isPresent() ? ResponseEntity.ok(receitaOptional.get()) : ResponseEntity.notFound().build();
    }

    public Receita salvar(Receita receita) {

        verificarDescricaoEDataReceita(receita);

        return receitaRepository.save(receita);
    }

    public Receita atualizar(Receita receita, Long id) {

        Receita receitaSalva = buscarReceita(id);

        BeanUtils.copyProperties(receita, receitaSalva, "id");

        verificarDescricaoEDataReceita(receita);

        return receitaRepository.save(receitaSalva);
    }

    private Receita buscarReceita(Long id) {
        Optional<Receita> receitaSalvaOptional = receitaRepository.findById(id);

        if (!receitaSalvaOptional.isPresent()){

            throw new ReceitaNaoExistenteException();
        }

        Receita receitaSalva = receitaSalvaOptional.get();

        return receitaSalva;
    }

    public void verificarDescricaoEDataReceita(Receita receita){

        LocalDate dataReceita = LocalDate.of(receita.getData().getYear(), receita.getData().getMonth(), receita.getData().getDayOfMonth());
        LocalDate dataInicio = dataReceita.withDayOfMonth(1);
        LocalDate dataFim = dataReceita.withDayOfMonth(dataReceita.lengthOfMonth());

        Optional<Receita> receitaSalva = receitaRepository.findByDescricaoEData(receita.getDescricao(), dataInicio, dataFim);

        if (receitaSalva.isPresent()){
            throw new ReceitaMesmaDescricaoNoMesException();
        }
    }


    public Page<Receita> pesquisaPaginada(String descricao, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return receitaRepository.findByDescricaoPaginada(descricao, pageable);
    }

    public List<Receita> pesquisar(String descricao) {

        return receitaRepository.findByDescricao(descricao);
    }

    public List<Receita> pesquisarReceitasNoMes(Integer ano, Integer mes) {

        LocalDate dataDespesaEventual = LocalDate.of(ano,mes,1);
        LocalDate dataInicio = dataDespesaEventual.withDayOfMonth(1);
        LocalDate dataFim = dataDespesaEventual.withDayOfMonth(dataDespesaEventual.lengthOfMonth());

        return receitaRepository.findByData(dataInicio, dataFim);
    }
}
