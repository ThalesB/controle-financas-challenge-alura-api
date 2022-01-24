package com.example.alura.controlefinancas.api.service;

import com.example.alura.controlefinancas.api.model.Receita;
import com.example.alura.controlefinancas.api.model.ResumoMes;
import com.example.alura.controlefinancas.api.model.enums.TipoDespesaEnum;
import com.example.alura.controlefinancas.api.repository.DespesaEventualRepository;
import com.example.alura.controlefinancas.api.repository.DespesaFixaRepository;
import com.example.alura.controlefinancas.api.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ResumoMesService {

    @Autowired
    ReceitaRepository receitaRepository;

    @Autowired
    DespesaFixaRepository despesaFixaRepository;

    @Autowired
    DespesaEventualRepository despesaEventualRepository;

    public ResumoMes criarResumoMes(Integer ano, Integer mes) {

        BigDecimal valorReceitaMes = buscarReceitaTotalPorMes(ano, mes);
        BigDecimal valorDespesasMes = buscarValorDespesasMes(ano, mes);
        BigDecimal saldoFinalDoMes = calculoSaldoFinalDoMes(valorReceitaMes, valorDespesasMes);
        BigDecimal valorTotalGastoCategoriaAlimentacao = calculoSaldoPorTipo(ano, mes, TipoDespesaEnum.ALIMENTACAO);
        BigDecimal valortTotalGastoCategoriaSaude = calculoSaldoPorTipo(ano, mes, TipoDespesaEnum.SAUDE);
        BigDecimal valortTotalGastoCategoriaMoradia = calculoSaldoPorTipo(ano, mes, TipoDespesaEnum.MORADIA);
        BigDecimal valortTotalGastoCategoriaTransporte = calculoSaldoPorTipo(ano, mes, TipoDespesaEnum.TRANSPORTE);
        BigDecimal valortTotalGastoCategoriaEducacao = calculoSaldoPorTipo(ano, mes, TipoDespesaEnum.EDUCACAO);
        BigDecimal valortTotalGastoCategoriaLazer = calculoSaldoPorTipo(ano, mes, TipoDespesaEnum.LAZER);
        BigDecimal valortTotalGastoCategoriaImprevistos = calculoSaldoPorTipo(ano, mes, TipoDespesaEnum.IMPREVISTOS);
        BigDecimal valortTotalGastoCategoriaOutras = calculoSaldoPorTipo(ano, mes, TipoDespesaEnum.OUTRAS);

        return new ResumoMes(valorReceitaMes, valorDespesasMes, saldoFinalDoMes, valorTotalGastoCategoriaAlimentacao,
                valortTotalGastoCategoriaSaude, valortTotalGastoCategoriaMoradia, valortTotalGastoCategoriaTransporte,
                valortTotalGastoCategoriaEducacao, valortTotalGastoCategoriaLazer, valortTotalGastoCategoriaImprevistos, valortTotalGastoCategoriaOutras);
    }

    private BigDecimal calculoSaldoPorTipo(Integer ano, Integer mes, TipoDespesaEnum tipo) {

        LocalDate dataDespesaEventual = LocalDate.of(ano,mes,1);
        LocalDate dataInicio = dataDespesaEventual.withDayOfMonth(1);
        LocalDate dataFim = dataDespesaEventual.withDayOfMonth(dataDespesaEventual.lengthOfMonth());

        Double saldoCategoriaFixa = despesaFixaRepository.findByTipoNoMes(dataInicio, dataFim, tipo);
        Double saldoCategoriaEventual = despesaEventualRepository.findByTipoNoMes(dataInicio, dataFim, tipo);

        if (saldoCategoriaFixa == null) {
            saldoCategoriaFixa = new Double(0);
        }
        if (saldoCategoriaEventual == null) {
            saldoCategoriaEventual = new Double(0);
        }

        Double saldoAlimentacaoTotal = saldoCategoriaFixa + saldoCategoriaEventual;

        return new BigDecimal(saldoAlimentacaoTotal);
    }

    private BigDecimal buscarReceitaTotalPorMes(Integer ano, Integer mes) {

        LocalDate dataDespesaEventual = LocalDate.of(ano,mes,1);
        LocalDate dataInicio = dataDespesaEventual.withDayOfMonth(1);
        LocalDate dataFim = dataDespesaEventual.withDayOfMonth(dataDespesaEventual.lengthOfMonth());

        BigDecimal valorTotalReceitaPorMes = receitaRepository.findValorTotalReceitaPorMes(dataInicio, dataFim);

        if (valorTotalReceitaPorMes == null){
            return new BigDecimal(0);
        }

        return valorTotalReceitaPorMes;
    }

    private BigDecimal buscarValorDespesasMes(Integer ano, Integer mes) {

        LocalDate dataDespesaEventual = LocalDate.of(ano,mes,1);
        LocalDate dataInicio = dataDespesaEventual.withDayOfMonth(1);
        LocalDate dataFim = dataDespesaEventual.withDayOfMonth(dataDespesaEventual.lengthOfMonth());

        Double valorTotalDespesasFixas = despesaFixaRepository.findValorTotalDespesasFixasPorMes(dataInicio, dataFim);
        Double valorTotalDespesasEventuais = despesaEventualRepository.findValorTotalDespesasEventuaisPorMes(dataInicio, dataFim);

        if (valorTotalDespesasEventuais == null)  {
            valorTotalDespesasEventuais = new Double(0);
        }
        if (valorTotalDespesasFixas == null){
            valorTotalDespesasFixas = new Double(0);
        }

        Double valorTotalDespesas = valorTotalDespesasEventuais + valorTotalDespesasFixas;

        return new BigDecimal(valorTotalDespesas);
    }

    private BigDecimal calculoSaldoFinalDoMes(BigDecimal valorReceitaMes, BigDecimal valorDespesasMes) {

        Double valorTotalReceita = Double.parseDouble(String.valueOf(valorReceitaMes));
        Double valorTotalDespesa = Double.parseDouble(String.valueOf(valorDespesasMes));
        Double saldoFinalMes = valorTotalReceita - valorTotalDespesa;

        return new BigDecimal(saldoFinalMes);
    }
}
