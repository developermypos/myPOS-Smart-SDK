package com.mypos.smartsdk.exceptions;

public class MissingCurrencyException extends IllegalArgumentException {

    public MissingCurrencyException(String s) {
        super(s);
    }
}
