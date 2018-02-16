package com.mypos.smartsdk.exceptions;

public class MissingAuthCodeException extends IllegalArgumentException {

    public MissingAuthCodeException(String s) {
        super(s);
    }
}
