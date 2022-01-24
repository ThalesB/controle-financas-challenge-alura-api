package com.example.alura.controlefinancas.api.repository;

import com.example.alura.controlefinancas.api.model.DespesaEventual;
import com.example.alura.controlefinancas.api.model.DespesaFixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DespesaEventualRepository extends JpaRepository<DespesaEventual, Long> {


    @Query("select a from DespesaEventual a where a.descricao =:descricao and a.data between :dataInicio and :dataFim")
    public Optional<DespesaEventual> findByDescricaoEData(@Param("descricao")String descricao, @Param("dataInicio")LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

}