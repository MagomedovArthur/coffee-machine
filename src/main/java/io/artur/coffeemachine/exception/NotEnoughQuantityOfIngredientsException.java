package io.artur.coffeemachine.exception;

/**
 * NotEnoughQuantityOfIngredientsException is a custom exception thrown when there is insufficient
 * quantity of ingredients available in the Coffee Machine system to fulfill a request.
 * This exception extends {@link RuntimeException}, making it an unchecked exception.
 * It is typically used to indicate that the operation cannot proceed due to a lack of the necessary
 * ingredients in the required quantity.
 */
public class NotEnoughQuantityOfIngredientsException extends RuntimeException {

    /**
     * Constructs a new NotEnoughQuantityOfIngredientsException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception.
     */
    public NotEnoughQuantityOfIngredientsException(String message) {
        super(message);
    }
}
