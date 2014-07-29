package com.mozido.channels.nextweb.model;

/**
 * @Author Alex Manusovich
 */
public class MoneyDTO extends BaseDTO {
    private long value;
    private String currency;

    public MoneyDTO() {
    }

    public MoneyDTO(long value) {
        this.value = value;
    }

    public MoneyDTO(long amount, String currency) {
        this.value = amount;
        this.currency = currency;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
