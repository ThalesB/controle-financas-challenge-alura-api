package com.example.alura.controlefinancas.api.model;

import com.example.alura.controlefinancas.api.model.enums.TipoDespesaEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="despesas_fixas")
public class DespesaFixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name="descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name="valor", nullable = false)
    private BigDecimal valor;

    @NotNull
    @Column(name="data", nullable = false)
    private LocalDate data;

    @NotNull
    @Column(name="observacao", nullable = false)
    private String observacao;

    @Column(name="tipo_despesa")
    @Enumerated(EnumType.STRING)
    private TipoDespesaEnum tipoDespesa;

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

    public TipoDespesaEnum getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(TipoDespesaEnum tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DespesaFixa that = (DespesaFixa) o;
        return Objects.equals(id, that.id) && Objects.equals(descricao, that.descricao) && Objects.equals(valor, that.valor) && Objects.equals(data, that.data) && Objects.equals(observacao, that.observacao) && tipoDespesa == that.tipoDespesa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, valor, data, observacao, tipoDespesa);
    }
}
