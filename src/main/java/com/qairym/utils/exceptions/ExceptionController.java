package com.qairym.utils.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<?> exception(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(
            "Invalid identifier"
        );
    }
}
