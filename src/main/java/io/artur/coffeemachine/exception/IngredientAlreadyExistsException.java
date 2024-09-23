package io.artur.coffeemachine.exception;

/**
 * IngredientAlreadyExistsException is a custom exception thrown when an attempt is made to create an ingredient
 * that already exists in the Coffee Machine system.
 * This exception extends {@link RuntimeException}, making it an unchecked exception. It is typically used
 * to indicate that a new ingredient cannot be added because another ingredient with the same name or identifier
 * already exists in the system.
 */
public class IngredientAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new IngredientAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception.
     */
    public IngredientAlreadyExistsException(String message) {
        super(message);
    }
}
