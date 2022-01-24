package com.example.alura.controlefinancas.api.repository;

import com.example.alura.controlefinancas.api.model.DespesaEventual;
import com.example.alura.controlefinancas.api.model.DespesaFixa;
import com.example.alura.controlefinancas.api.model.Receita;
import com.example.alura.controlefinancas.api.model.enums.TipoDespesaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaFixaRepository extends JpaRepository<DespesaFixa, Long> {


    @Query("select a from DespesaFixa a where a.descricao =:descricao and a.data between :dataInicio and :dataFim")
    public Optional<DespesaFixa> findByDescricaoEData(@Param("descricao")String descricao, @Param("dataInicio")LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

    @Query("select a from DespesaFixa a where (:descricao is null or a.descricao = :descricao)")
    public List<DespesaFixa> findByDescricao(@Param("descricao")String descricao);

    @Query("select a from DespesaFixa a where (:descricao is null or a.descricao = :descricao)")
    public Page<DespesaFixa> findByDescricaoPaginada(@Param("descricao")String descricao, Pageable pageable);

    @Query("select a from DespesaFixa a where a.data between :dataInicio and :dataFim")
    public List<DespesaFixa> findByData(@Param("dataInicio")LocalDate dataInicio, @Param("dataFim")LocalDate dataFim);

    @Query("select sum(a.valor) from DespesaFixa a where a.data between :dataInicio and :dataFim")
    public Double findValorTotalDespesasFixasPorMes(@Param("dataInicio")LocalDate dataInicio, @Param("dataFim")LocalDate dataFim);

    @Query("select sum(a.valor) from DespesaFixa a where a.tipoDespesa = :tipo and a.data between :dataInicio and :dataFim")
    public Double findByTipoNoMes(@Param("dataInicio")LocalDate dataInicio, @Param("dataFim")LocalDate dataFim, @Param("tipo") TipoDespesaEnum tipo);
}
