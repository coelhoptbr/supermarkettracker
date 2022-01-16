package com.coelho.supermarkettracker.domain;

public enum CurrencyEnum {
    BRL("R$"), EUR ("€"), USD ("US$");

    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    private CurrencyEnum(String symbol) {
        this.symbol = symbol;
    }
}
