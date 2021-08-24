package com.github.imczfy.czutils.exceptions;

public class LanguageNotFoundException extends Exception {
    private int errorCode;
    public LanguageNotFoundException() {
        super("Cannot find the language you want in the config.yml! Error Code: 10001");
        this.errorCode = 10001;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
