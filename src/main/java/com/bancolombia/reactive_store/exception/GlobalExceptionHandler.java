package com.bancolombia.reactive_store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ProblemDetail handleNotFound(ProductNotFoundException ex){
       return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    ProblemDetail handleValidation(WebExchangeBindException ex){
        return ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, 
            "la solicitud contiene campos incorrectos"
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail handleBusines(IllegalArgumentException ex){
       return ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, 
            ex.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleUnexpected(Exception ex){
        return ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, 
            "Ocurrió un error inesperado"
        );
    }

}
