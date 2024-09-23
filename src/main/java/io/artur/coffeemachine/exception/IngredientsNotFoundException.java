package io.artur.coffeemachine.exception;

/**
 * IngredientsNotFoundException is a custom exception thrown when no ingredients are found
 * in the Coffee Machine system. This exception extends {@link RuntimeException}, making it an unchecked exception.
 * It is typically used to indicate that a requested ingredient or list of ingredients
 * could not be found in the system.
 */
public class IngredientsNotFoundException extends RuntimeException {

    /**
     * Constructs a new IngredientsNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception.
     */
    public IngredientsNotFoundException(String message) {
        super(message);
    }
}
