package br.com.cdbservice.exception;

public class WalletCDBNotFoundException extends RuntimeException {
    public WalletCDBNotFoundException(String message) {
        super(message);
    }
}