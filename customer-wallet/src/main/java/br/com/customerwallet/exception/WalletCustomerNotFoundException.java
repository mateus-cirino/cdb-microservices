package br.com.customerwallet.exception;

public class WalletCustomerNotFoundException extends RuntimeException {
    public WalletCustomerNotFoundException(String message) {
        super(message);
    }
}