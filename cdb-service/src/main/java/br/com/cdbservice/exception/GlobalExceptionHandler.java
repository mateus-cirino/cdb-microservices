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

    @ExceptionHandler(PaperNotFoundException.class)
    public ResponseEntity<String> handlePaperNotFoundException(PaperNotFoundException e) {
        return ResponseEntity.badRequest().body(String.format("Erro ao buscar o paper: %s", e.getMessage()));
    }

    @ExceptionHandler(WalletCDBNotFoundException.class)
    public ResponseEntity<String> handleWalletCDBNotFoundException(WalletCDBNotFoundException e) {
        return ResponseEntity.badRequest().body(String.format("Erro ao buscar o walletCDB: %s", e.getMessage()));
    }

    @ExceptionHandler(NotHasEnoughBalanceException.class)
    public ResponseEntity<String> handleNotHasEnoughBalanceException(NotHasEnoughBalanceException e) {
        return ResponseEntity.badRequest().body(String.format("Customer não possuí saldo suficiente: %s", e.getMessage()));
    }

    @ExceptionHandler(SellMorePaperThanHaveException.class)
    public ResponseEntity<String> handleSellMorePaperThanHaveException(SellMorePaperThanHaveException e) {
        return ResponseEntity.badRequest().body(String.format("Tentando vender mais papeis do que de fato ele tem: %s", e.getMessage()));
    }
}