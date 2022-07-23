package com.onjung.onjung.exception;

public class InvalidParameterException extends CustomException {

    private static final long serialVersionUID = -21166714305194101L;

    public InvalidParameterException() {
        super(ErrorCode.INVALID_PARAMETER);
    }
}
