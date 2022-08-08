package com.onjung.onjung.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime timestamp = LocalDateTime.now(); // 오류 발생 시점

    private String message; // 예외 메시지

    private int status; // 상태코드

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("errors")
//    private List<CustomFieldError> customFieldErrors;

    public ErrorResponse(){
    }

    static public ErrorResponse create(){
        return new ErrorResponse();
    }

    public ErrorResponse message(String message){
        this.message = message;
        return this;
    }

    public ErrorResponse status(int status){
        this.status = status;
        return this;
    }

}


