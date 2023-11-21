package br.com.cdbservice.exception;

public class WalletCDBSaveException extends RuntimeException {
    public WalletCDBSaveException(String message) {
        super(message);
    }
}