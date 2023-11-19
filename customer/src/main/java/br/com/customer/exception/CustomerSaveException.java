package br.com.customer.exception;

public class CustomerSaveException extends RuntimeException {
    public CustomerSaveException(String message) {
        super(message);
    }
}