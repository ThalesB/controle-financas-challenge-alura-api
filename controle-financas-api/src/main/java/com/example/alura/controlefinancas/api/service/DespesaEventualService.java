package com.example.alura.controlefinancas.api.service;

import com.example.alura.controlefinancas.api.model.DTO.DespesaEventualListaDto;
import com.example.alura.controlefinancas.api.model.DespesaEventual;
import com.example.alura.controlefinancas.api.model.DespesaFixa;
import com.example.alura.controlefinancas.api.repository.DespesaEventualRepository;
import com.example.alura.controlefinancas.api.service.exception.DespesaEventualMesmaDescricaoNoMesException;
import com.example.alura.controlefinancas.api.service.exception.DespesaEventualNaoExistenteException;
import com.example.alura.controlefinancas.api.service.exception.DespesaFixaMesmaDescricaoNoMesException;
import com.example.alura.controlefinancas.api.service.exception.DespesaFixaNaoExistenteException;
import org.apache.tomcat.jni.Local;
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

        LocalDate dataDespesaEventual = LocalDate.of(despesaEventual.getData().getYear(), despesaEventual.getData().getMonth(), despesaEventual.getData().getDayOfMonth());
        LocalDate dataInicio = dataDespesaEventual.withDayOfMonth(1);
        LocalDate dataFim = dataDespesaEventual.withDayOfMonth(dataDespesaEventual.lengthOfMonth());

        Optional<DespesaEventual> despesaEventualSalva = despesaEventualRepository.findByDescricaoEData(despesaEventual.getDescricao(), dataInicio, dataFim);

        if (despesaEventualSalva.isPresent()){
            throw new DespesaEventualMesmaDescricaoNoMesException();
        }
    }


    public List<DespesaEventualListaDto> pesquisar(String descricao) {

        List<DespesaEventual> listaDespesasEventuais = despesaEventualRepository.findByDescricao(descricao);

        return DespesaEventualListaDto.converterLista(listaDespesasEventuais);
    }

    public Page<DespesaEventualListaDto> pesquisaPaginada(String descricao, Pageable pageable){

        Page<DespesaEventual> listaDespesaEventuais = despesaEventualRepository.findByDescricaoPaginada(descricao, pageable);

        return DespesaEventualListaDto.converterListaPaginada(listaDespesaEventuais);

    }

    public List<DespesaEventual> pesquisarDespesasEventuaisNoMes(Integer ano, Integer mes) {

        LocalDate dataDespesaEventual = LocalDate.of(ano,mes,1);
        LocalDate dataInicio = dataDespesaEventual.withDayOfMonth(1);
        LocalDate dataFim = dataDespesaEventual.withDayOfMonth(dataDespesaEventual.lengthOfMonth());

        return despesaEventualRepository.findByData(dataInicio, dataFim);
    }
}
