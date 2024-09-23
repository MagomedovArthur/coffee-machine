package io.artur.coffeemachine.exception;

public class DrinkAlreadyExistsException extends RuntimeException {

    public DrinkAlreadyExistsException(String message) {
        super(message);
    }
}
