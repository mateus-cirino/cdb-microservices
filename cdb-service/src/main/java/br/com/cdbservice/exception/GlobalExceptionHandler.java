package br.com.cdbservice.exception;

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

    @ExceptionHandler(PaperSaveException.class)
    public ResponseEntity<String> handlePaperSaveException(PaperSaveException e) {
        return ResponseEntity.badRequest().body(String.format("Erro ao salvar o paper: %s", e.getMessage()));
    }

    @ExceptionHandler(WalletCDBSaveException.class)
    public ResponseEntity<String> handleWalletCDBSaveException(PaperSaveException e) {
        return ResponseEntity.badRequest().body(String.format("Erro ao salvar o wallet cdb: %s", e.getMessage()));
    }
}