package br.com.customerwallet.exception;

public class WalletCustomerSaveException extends RuntimeException {
    public WalletCustomerSaveException(String message) {
        super(message);
    }
}