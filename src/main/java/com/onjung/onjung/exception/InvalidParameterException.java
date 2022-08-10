package com.onjung.onjung.exception;

import org.springframework.validation.Errors;

public class InvalidParameterException extends CustomException {

    private static final long serialVersionUID = -21166714305194101L;

    private final Errors errors;

    public InvalidParameterException(Errors errors) {
        super(ErrorCode.INVALID_PARAMETER);
        this.errors = errors;
    }

    public Errors getErrors() {
        return this.errors;
    }
}
