package com.example.alura.controlefinancas.api.service;

import com.example.alura.controlefinancas.api.model.DespesaEventual;
import com.example.alura.controlefinancas.api.model.DespesaFixa;
import com.example.alura.controlefinancas.api.repository.DespesaEventualRepository;
import com.example.alura.controlefinancas.api.service.exception.DespesaEventualMesmaDescricaoNoMesException;
import com.example.alura.controlefinancas.api.service.exception.DespesaEventualNaoExistenteException;
import com.example.alura.controlefinancas.api.service.exception.DespesaFixaMesmaDescricaoNoMesException;
import com.example.alura.controlefinancas.api.service.exception.DespesaFixaNaoExistenteException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DespesaEventualService {

    @Autowired
    DespesaEventualRepository despesaEventualRepository;


    public ResponseEntity<DespesaEventual> verificarDespesaEventualExistente(Long id) {

        Optional<DespesaEventual> despesaEventualOptional = despesaEventualRepository.findById(id);

        return despesaEventualOptional.isPresent() ? ResponseEntity.ok(despesaEventualOptional.get()) : ResponseEntity.notFound().build();
    }

    public DespesaEventual salvar(DespesaEventual despesaEventual) {

        verificarDescricaoEDataDespesaEventual(despesaEventual);

        return despesaEventualRepository.save(despesaEventual);
    }

    public DespesaEventual atualizar(DespesaEventual despesaEventual, Long id) {

        DespesaEventual despesaEventualSalva = buscarDespesaEventual(id);

        BeanUtils.copyProperties(despesaEventual, despesaEventualSalva, "id");

        verificarDescricaoEDataDespesaEventual(despesaEventual);

        return despesaEventualRepository.save(despesaEventualSalva);
    }

    private DespesaEventual buscarDespesaEventual(Long id) {
        Optional<DespesaEventual> despesaEventualSalvaOptional = despesaEventualRepository.findById(id);

        if (!despesaEventualSalvaOptional.isPresent()){

            throw new DespesaEventualNaoExistenteException();
        }

        DespesaEventual despesaEventualSalva = despesaEventualSalvaOptional.get();

        return despesaEventualSalva;
    }

    public void verificarDescricaoEDataDespesaEventual(DespesaEventual despesaEventual){

        LocalDate dataReceita = LocalDate.of(despesaEventual.getData().getYear(), despesaEventual.getData().getMonth(), despesaEventual.getData().getDayOfMonth());
        LocalDate dataInicio = dataReceita.withDayOfMonth(1);
        LocalDate dataFim = dataReceita.withDayOfMonth(dataReceita.lengthOfMonth());

        Optional<DespesaEventual> despesaEventualSalva = despesaEventualRepository.findByDescricaoEData(despesaEventual.getDescricao(), dataInicio, dataFim);

        if (despesaEventualSalva.isPresent()){
            throw new DespesaEventualMesmaDescricaoNoMesException();
        }
    }


}
