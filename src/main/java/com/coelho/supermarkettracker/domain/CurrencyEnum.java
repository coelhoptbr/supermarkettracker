package com.coelho.supermarkettracker.domain;

import java.util.Arrays;

public enum CurrencyEnum {
    BRL("R$"), EUR ("€"), USD ("US$");

    private String symbol;

    CurrencyEnum(final String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static final CurrencyEnum getByName(String name){
        return Arrays.stream(CurrencyEnum.values()).filter(enumCurr -> enumCurr.name().equals(name)).findFirst().orElse(EUR);
    }


}
