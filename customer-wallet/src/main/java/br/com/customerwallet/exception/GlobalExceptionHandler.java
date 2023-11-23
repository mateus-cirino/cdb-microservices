package br.com.customerwallet.exception;

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

    @ExceptionHandler(WalletCustomerSaveException.class)
    public ResponseEntity<String> handleWalletCustomerSaveException(WalletCustomerSaveException e) {
        return ResponseEntity.badRequest().body(String.format("Erro ao salvar o wallet customer: %s", e.getMessage()));
    }

    @ExceptionHandler(WalletCustomerNotFoundException.class)
    public ResponseEntity<String> handleWalletCustomerNotFoundException(WalletCustomerNotFoundException e) {
        return ResponseEntity.badRequest().body(String.format("Erro ao buscar o wallet customer: %s", e.getMessage()));
    }
}