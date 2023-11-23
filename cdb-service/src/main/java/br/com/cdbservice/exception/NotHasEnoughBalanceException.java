package br.com.cdbservice.exception;

public class NotHasEnoughBalanceException extends RuntimeException {
    public NotHasEnoughBalanceException(String message) {
        super(message);
    }
}