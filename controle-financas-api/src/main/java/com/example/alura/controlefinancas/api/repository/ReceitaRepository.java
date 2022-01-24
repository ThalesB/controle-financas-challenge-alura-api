package com.example.alura.controlefinancas.api.repository;

import com.example.alura.controlefinancas.api.model.DespesaFixa;
import com.example.alura.controlefinancas.api.model.Receita;
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
public interface ReceitaRepository extends JpaRepository<Receita, Long>{


    @Query("select a from Receita a where a.descricao =:descricao and a.data between :dataInicio and :dataFim")
    public Optional<Receita> findByDescricaoEData(@Param("descricao")String descricao, @Param("dataInicio")LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

    @Query("select a from Receita a where (:descricao is null or a.descricao =: descricao)")
    public Page<Receita> findByDescricaoPaginada(@Param("descricao")String descricao, Pageable pageable);

    @Query("select a from Receita a where (:descricao is null or a.descricao =: descricao)")
    public List<Receita> findByDescricao(@Param("descricao")String descricao);

    @Query("select a from Receita a where a.data between :dataInicio and :dataFim")
    List<Receita> findByData(@Param("dataInicio")LocalDate dataInicio, @Param("dataFim")LocalDate dataFim);

    @Query("select sum(a.valor) from Receita a where a.data between :dataInicio and :dataFim")
    BigDecimal findValorTotalReceitaPorMes(@Param("dataInicio")LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}
