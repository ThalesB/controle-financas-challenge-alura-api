package com.example.alura.controlefinancas.api.repository;

import com.example.alura.controlefinancas.api.model.DespesaEventual;
import com.example.alura.controlefinancas.api.model.DespesaFixa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaEventualRepository extends JpaRepository<DespesaEventual, Long> {


    @Query("select a from DespesaEventual a where a.descricao =:descricao and a.data between :dataInicio and :dataFim")
    public Optional<DespesaEventual> findByDescricaoEData(@Param("descricao")String descricao, @Param("dataInicio")LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

    @Query("select a from DespesaEventual a where (:descricao is null or a.descricao = :descricao)")
    List<DespesaEventual> findByDescricao(@Param("descricao") String descricao);

    @Query("select a from DespesaEventual a where (:descricao is null or a.descricao = :descricao)")
    Page<DespesaEventual> findByDescricaoPaginada(@Param("descricao")String descricao, Pageable pageable);

    @Query("select a from DespesaEventual a where a.data between :dataInicio and :dataFim")
    List<DespesaEventual> findByData(@Param("dataInicio")LocalDate dataInicio, @Param("dataFim")LocalDate dataFim);
}
