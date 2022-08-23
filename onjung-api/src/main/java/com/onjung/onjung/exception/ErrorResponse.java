package com.onjung.onjung.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {

    private LocalDateTime timestamp = LocalDateTime.now(); // 오류 발생 시점

    private String message; // 예외 메시지

    private int status; // 상태코드

    // 어떤 필드가 valid 를 통과하지 못했는지
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomFieldError> customFieldErrors;

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

    public ErrorResponse errors(Errors errors) {
        setCustomFieldErrors(errors.getFieldErrors());
        return this;
    }


    public void setCustomFieldErrors(List<FieldError> fieldErrors){

        customFieldErrors = new ArrayList<>();

        fieldErrors.forEach(error -> {
            customFieldErrors.add(new CustomFieldError(
                    error.getCodes()[0],
                    error.getRejectedValue(),
                    error.getDefaultMessage()
            ));
        });

    }

    @Getter
    public static class CustomFieldError {

        private String field;
        private Object value;
        private String reason;

        public CustomFieldError(String field, Object value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}


