package com.demoProject.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionDTO {
    private HttpStatus statusCode;
    private String message;

    public ExceptionDTO(HttpStatus status, String message){
        this.statusCode = status;
        this.message = message;
    }
}
