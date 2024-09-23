package io.artur.coffeemachine.exception;

public class DrinksNotFoundException extends RuntimeException {

    public DrinksNotFoundException(String message) {
        super(message);
    }
}
