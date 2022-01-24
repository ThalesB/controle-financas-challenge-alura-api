package com.example.alura.controlefinancas.api.model;

import java.math.BigDecimal;
import java.util.Objects;

public class ResumoMes {

    private BigDecimal valorTotalReceitasMes;
    private BigDecimal valorTotalDespesasMes;
    private BigDecimal saldoFinalMes;
    private BigDecimal valorTotalGastoCategoriaAlimentacao;
    private BigDecimal valortTotalGastoCategoriaSaude;
    private BigDecimal valortTotalGastoCategoriaMoradia;
    private BigDecimal valortTotalGastoCategoriaTransporte;
    private BigDecimal valortTotalGastoCategoriaEducacao;
    private BigDecimal valortTotalGastoCategoriaLazer;
    private BigDecimal valortTotalGastoCategoriaImprevistos;
    private BigDecimal valortTotalGastoCategoriaOutras;

    public ResumoMes(BigDecimal valorTotalReceitasMes, BigDecimal valorTotalDespesasMes, BigDecimal saldoFinalMes, BigDecimal valorTotalGastoCategoriaAlimentacao, BigDecimal valortTotalGastoCategoriaSaude, BigDecimal valortTotalGastoCategoriaMoradia, BigDecimal valortTotalGastoCategoriaTransporte, BigDecimal valortTotalGastoCategoriaEducacao, BigDecimal valortTotalGastoCategoriaLazer, BigDecimal valortTotalGastoCategoriaImprevistos, BigDecimal valortTotalGastoCategoriaOutras) {
        this.valorTotalReceitasMes = valorTotalReceitasMes;
        this.valorTotalDespesasMes = valorTotalDespesasMes;
        this.saldoFinalMes = saldoFinalMes;
        this.valorTotalGastoCategoriaAlimentacao = valorTotalGastoCategoriaAlimentacao;
        this.valortTotalGastoCategoriaSaude = valortTotalGastoCategoriaSaude;
        this.valortTotalGastoCategoriaMoradia = valortTotalGastoCategoriaMoradia;
        this.valortTotalGastoCategoriaTransporte = valortTotalGastoCategoriaTransporte;
        this.valortTotalGastoCategoriaEducacao = valortTotalGastoCategoriaEducacao;
        this.valortTotalGastoCategoriaLazer = valortTotalGastoCategoriaLazer;
        this.valortTotalGastoCategoriaImprevistos = valortTotalGastoCategoriaImprevistos;
        this.valortTotalGastoCategoriaOutras = valortTotalGastoCategoriaOutras;
    }

    public BigDecimal getValorTotalReceitasMes() {
        return valorTotalReceitasMes;
    }

    public void setValorTotalReceitasMes(BigDecimal valorTotalReceitasMes) {
        this.valorTotalReceitasMes = valorTotalReceitasMes;
    }

    public BigDecimal getValorTotalDespesasMes() {
        return valorTotalDespesasMes;
    }

    public void setValorTotalDespesasMes(BigDecimal valorTotalDespesasMes) {
        this.valorTotalDespesasMes = valorTotalDespesasMes;
    }

    public BigDecimal getSaldoFinalMes() {
        return saldoFinalMes;
    }

    public void setSaldoFinalMes(BigDecimal saldoFinalMes) {
        this.saldoFinalMes = saldoFinalMes;
    }

    public BigDecimal getValorTotalGastoCategoriaAlimentacao() {
        return valorTotalGastoCategoriaAlimentacao;
    }

    public void setValorTotalGastoCategoriaAlimentacao(BigDecimal valorTotalGastoCategoriaAlimentacao) {
        this.valorTotalGastoCategoriaAlimentacao = valorTotalGastoCategoriaAlimentacao;
    }

    public BigDecimal getValortTotalGastoCategoriaSaude() {
        return valortTotalGastoCategoriaSaude;
    }

    public void setValortTotalGastoCategoriaSaude(BigDecimal valortTotalGastoCategoriaSaude) {
        this.valortTotalGastoCategoriaSaude = valortTotalGastoCategoriaSaude;
    }

    public BigDecimal getValortTotalGastoCategoriaMoradia() {
        return valortTotalGastoCategoriaMoradia;
    }

    public void setValortTotalGastoCategoriaMoradia(BigDecimal valortTotalGastoCategoriaMoradia) {
        this.valortTotalGastoCategoriaMoradia = valortTotalGastoCategoriaMoradia;
    }

    public BigDecimal getValortTotalGastoCategoriaTransporte() {
        return valortTotalGastoCategoriaTransporte;
    }

    public void setValortTotalGastoCategoriaTransporte(BigDecimal valortTotalGastoCategoriaTransporte) {
        this.valortTotalGastoCategoriaTransporte = valortTotalGastoCategoriaTransporte;
    }

    public BigDecimal getValortTotalGastoCategoriaEducacao() {
        return valortTotalGastoCategoriaEducacao;
    }

    public void setValortTotalGastoCategoriaEducacao(BigDecimal valortTotalGastoCategoriaEducacao) {
        this.valortTotalGastoCategoriaEducacao = valortTotalGastoCategoriaEducacao;
    }

    public BigDecimal getValortTotalGastoCategoriaLazer() {
        return valortTotalGastoCategoriaLazer;
    }

    public void setValortTotalGastoCategoriaLazer(BigDecimal valortTotalGastoCategoriaLazer) {
        this.valortTotalGastoCategoriaLazer = valortTotalGastoCategoriaLazer;
    }

    public BigDecimal getValortTotalGastoCategoriaImprevistos() {
        return valortTotalGastoCategoriaImprevistos;
    }

    public void setValortTotalGastoCategoriaImprevistos(BigDecimal valortTotalGastoCategoriaImprevistos) {
        this.valortTotalGastoCategoriaImprevistos = valortTotalGastoCategoriaImprevistos;
    }

    public BigDecimal getValortTotalGastoCategoriaOutras() {
        return valortTotalGastoCategoriaOutras;
    }

    public void setValortTotalGastoCategoriaOutras(BigDecimal valortTotalGastoCategoriaOutras) {
        this.valortTotalGastoCategoriaOutras = valortTotalGastoCategoriaOutras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResumoMes resumoMes = (ResumoMes) o;
        return Objects.equals(valorTotalReceitasMes, resumoMes.valorTotalReceitasMes) && Objects.equals(valorTotalDespesasMes, resumoMes.valorTotalDespesasMes) && Objects.equals(saldoFinalMes, resumoMes.saldoFinalMes) && Objects.equals(valorTotalGastoCategoriaAlimentacao, resumoMes.valorTotalGastoCategoriaAlimentacao) && Objects.equals(valortTotalGastoCategoriaSaude, resumoMes.valortTotalGastoCategoriaSaude) && Objects.equals(valortTotalGastoCategoriaMoradia, resumoMes.valortTotalGastoCategoriaMoradia) && Objects.equals(valortTotalGastoCategoriaTransporte, resumoMes.valortTotalGastoCategoriaTransporte) && Objects.equals(valortTotalGastoCategoriaEducacao, resumoMes.valortTotalGastoCategoriaEducacao) && Objects.equals(valortTotalGastoCategoriaLazer, resumoMes.valortTotalGastoCategoriaLazer) && Objects.equals(valortTotalGastoCategoriaImprevistos, resumoMes.valortTotalGastoCategoriaImprevistos) && Objects.equals(valortTotalGastoCategoriaOutras, resumoMes.valortTotalGastoCategoriaOutras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valorTotalReceitasMes, valorTotalDespesasMes, saldoFinalMes, valorTotalGastoCategoriaAlimentacao, valortTotalGastoCategoriaSaude, valortTotalGastoCategoriaMoradia, valortTotalGastoCategoriaTransporte, valortTotalGastoCategoriaEducacao, valortTotalGastoCategoriaLazer, valortTotalGastoCategoriaImprevistos, valortTotalGastoCategoriaOutras);
    }
}
