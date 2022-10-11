package com.onjung.onjung.exception;

public class DataNotFoundException extends CustomException{
    private static final long serialVersionUID = -211667454324594101L;

    public DataNotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }

    public DataNotFoundException(String message){
        super(ErrorCode.INVALID_PARAMETER, message);
    }
}
