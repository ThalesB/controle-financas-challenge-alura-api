package com.example.alura.controlefinancas.api.service;

import com.example.alura.controlefinancas.api.model.DespesaFixa;
import com.example.alura.controlefinancas.api.model.Receita;
import com.example.alura.controlefinancas.api.repository.DespesaFixaRepository;
import com.example.alura.controlefinancas.api.repository.ReceitaRepository;
import com.example.alura.controlefinancas.api.service.exception.DespesaFixaMesmaDescricaoNoMesException;
import com.example.alura.controlefinancas.api.service.exception.DespesaFixaNaoExistenteException;
import com.example.alura.controlefinancas.api.service.exception.ReceitaMesmaDescricaoNoMesException;
import com.example.alura.controlefinancas.api.service.exception.ReceitaNaoExistenteException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.crypto.Des;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DespesaFixaService {

    @Autowired
    DespesaFixaRepository despesaFixaRepository;


    public ResponseEntity<DespesaFixa> verificarDespesaExistente(Long id) {

        Optional<DespesaFixa> despesaFixaOptional = despesaFixaRepository.findById(id);

        return despesaFixaOptional.isPresent() ? ResponseEntity.ok(despesaFixaOptional.get()) : ResponseEntity.notFound().build();
    }

    public DespesaFixa salvar(DespesaFixa despesaFixa) {

        verificarDescricaoEDataDespesaFixa(despesaFixa);

        return despesaFixaRepository.save(despesaFixa);
    }

    public DespesaFixa atualizar(DespesaFixa despesaFixa, Long id) {

        DespesaFixa despesaFixaSalva = buscarDespesaFixa(id);

        BeanUtils.copyProperties(despesaFixa, despesaFixaSalva, "id");

        verificarDescricaoEDataDespesaFixa(despesaFixa);

        return despesaFixaRepository.save(despesaFixaSalva);
    }

    private DespesaFixa buscarDespesaFixa(Long id) {
        Optional<DespesaFixa> despesaFixaSalvaOptional = despesaFixaRepository.findById(id);

        if (!despesaFixaSalvaOptional.isPresent()){

            throw new DespesaFixaNaoExistenteException();
        }

        DespesaFixa despesaFixaSalva = despesaFixaSalvaOptional.get();

        return despesaFixaSalva;
    }

    public void verificarDescricaoEDataDespesaFixa(DespesaFixa despesaFixa){

        LocalDate dataReceita = LocalDate.of(despesaFixa.getData().getYear(), despesaFixa.getData().getMonth(), despesaFixa.getData().getDayOfMonth());
        LocalDate dataInicio = dataReceita.withDayOfMonth(1);
        LocalDate dataFim = dataReceita.withDayOfMonth(dataReceita.lengthOfMonth());

        Optional<DespesaFixa> despesaFixaSalva = despesaFixaRepository.findByDescricaoEData(despesaFixa.getDescricao(), dataInicio, dataFim);

        if (despesaFixaSalva.isPresent()){
            throw new DespesaFixaMesmaDescricaoNoMesException();
        }
    }


}
