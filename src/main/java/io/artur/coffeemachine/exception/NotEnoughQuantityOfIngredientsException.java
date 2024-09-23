package io.artur.coffeemachine.exception;

public class NotEnoughQuantityOfIngredientsException extends RuntimeException {

    public NotEnoughQuantityOfIngredientsException(String message) {
        super(message);
    }
}
