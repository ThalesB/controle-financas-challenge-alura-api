package com.example.alura.controlefinancas.api.model.enums;

public enum TipoDespesaEnum {

    ALIMENTACAO(1, "Alimentação"),
    SAUDE(2, "Saúde"),
    MORADIA(3, "Moradia"),
    TRANSPORTE(4, "Transporte"),
    EDUCACAO(5, "Educação"),
    LAZER(6, "Lazer"),
    IMPREVISTOS(7, "Imprevistos"),
    OUTRAS(8, "Outras");


    private int id;
    private String value;

    TipoDespesaEnum(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
