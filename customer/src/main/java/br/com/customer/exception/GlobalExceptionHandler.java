package br.com.customer.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body("Erro de validação: " + e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(CustomerSaveException.class)
    public ResponseEntity<String> handleCustomerSaveException(CustomerSaveException e) {
        return ResponseEntity.badRequest().body(String.format("Erro ao salvar o customer: %s", e.getMessage()));
    }
}