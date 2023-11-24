package br.com.cdbservice.exception;

public class SellMorePaperThanHaveException extends RuntimeException {
    public SellMorePaperThanHaveException(String message) {
        super(message);
    }
}