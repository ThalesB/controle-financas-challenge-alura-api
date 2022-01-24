package com.example.alura.controlefinancas.api.model.enums;

public enum TipoDespesaEnum {

    MORADIA(1, "Moradia"),
    TRANSPORTE(2, "Transporte"),
    COMUNICACO(3, "Comunicação"),
    LAZER(4, "Lazer"),
    CARTAO(5, "Cartão");


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
