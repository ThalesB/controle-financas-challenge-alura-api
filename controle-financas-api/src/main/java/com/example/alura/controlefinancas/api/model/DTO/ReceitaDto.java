package com.example.alura.controlefinancas.api.model.DTO;

import com.example.alura.controlefinancas.api.model.Receita;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReceitaDto {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private String observacao;

    public ReceitaDto(Receita receita){
        this.id = receita.getId();
        this.descricao = receita.getDescricao();
        this.valor = receita.getValor();
        this.data = receita.getData();
        this.observacao = receita.getObservacao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
