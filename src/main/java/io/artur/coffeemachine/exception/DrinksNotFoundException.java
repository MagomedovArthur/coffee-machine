package io.artur.coffeemachine.exception;

/**
 * DrinksNotFoundException is a custom exception thrown when no drinks are found in the Coffee Machine system.
 * This exception extends {@link RuntimeException}, making it an unchecked exception. It is typically used
 * to indicate that a requested list of drinks or a specific drink could not be found in the system.
 */
public class DrinksNotFoundException extends RuntimeException {

    /**
     * Constructs a new DrinksNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception.
     */
    public DrinksNotFoundException(String message) {
        super(message);
    }
}
