package com.example.alura.controlefinancas.api.model.DTO;

import com.example.alura.controlefinancas.api.model.DespesaEventual;
import com.example.alura.controlefinancas.api.model.enums.TipoDespesaEnum;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DespesaEventualListaDto {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private String observacao;
    private TipoDespesaEnum tipoDespesa;

    public DespesaEventualListaDto(DespesaEventual despesaEventual){
        this.id = despesaEventual.getId();
        this.descricao = despesaEventual.getDescricao();
        this.valor = despesaEventual.getValor();
        this.data = despesaEventual.getData();
        this.observacao = despesaEventual.getObservacao();
        this.tipoDespesa = despesaEventual.getTipoDespesa();
    }


    public static List<DespesaEventualListaDto> converterLista(List<DespesaEventual> listaDespesasEventuais){

        return listaDespesasEventuais.stream().map(DespesaEventualListaDto::new).collect(Collectors.toList());
    }

    public static Page<DespesaEventualListaDto> converterListaPaginada(Page<DespesaEventual> listaDespesasEventuais){

        return listaDespesasEventuais.map(DespesaEventualListaDto::new);
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

    public TipoDespesaEnum getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(TipoDespesaEnum tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }
}
