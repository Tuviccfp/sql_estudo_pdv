package com.example.demo.exceptions.handle;

import com.example.demo.exceptions.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsListGlobal {

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Não é permitido valores nulos")
    @ExceptionHandler(NotNull.class)
    public void NotNullValues() {
    }
}
