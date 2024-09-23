package io.artur.coffeemachine.exception;

public class IngredientAlreadyExistsException extends RuntimeException {

    public IngredientAlreadyExistsException(String message) {
        super(message);
    }
}
