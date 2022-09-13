package com.onjung.onjung.exception;

import org.springframework.validation.Errors;

public class UnauthorizedException extends CustomException{

    private static final long serialVersionUID = -21166714305194101L;


    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }


}
