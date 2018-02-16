package com.mypos.smartsdk.exceptions;

public class InvalidAmountException extends IllegalArgumentException {

    public InvalidAmountException(String s) {
        super(s);
    }
}
