package com.mypos.smartsdk.exceptions;

public class MissingPreauthCodeException extends IllegalArgumentException {

    public MissingPreauthCodeException(String s) {
        super(s);
    }
}
