package com.annakhuseinova.springwebflux.exceptionhandler;

import com.annakhuseinova.springwebflux.dto.InputFailedValidationResponse;
import com.annakhuseinova.springwebflux.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class InputValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException e){
        InputFailedValidationResponse response = new InputFailedValidationResponse();
        response.setErrorCode(e.getErrorCode());
        response.setInput(e.getInput());
        response.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
