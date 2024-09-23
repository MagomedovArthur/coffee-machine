package io.artur.coffeemachine.exception;

public class IngredientsNotFoundException extends RuntimeException {

    public IngredientsNotFoundException(String message) {
        super(message);
    }
}
