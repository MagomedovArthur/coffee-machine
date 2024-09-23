package io.artur.coffeemachine.exception;

/**
 * DrinkAlreadyExistsException is a custom exception thrown when an attempt is made to create a drink
 * that already exists in the Coffee Machine system.
 * This exception extends {@link RuntimeException}, making it an unchecked exception. It is typically used
 * to signal that a new drink cannot be added because another drink with the same name or identifier already exists.
 */
public class DrinkAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new DrinkAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception.
     */
    public DrinkAlreadyExistsException(String message) {
        super(message);
    }
}
