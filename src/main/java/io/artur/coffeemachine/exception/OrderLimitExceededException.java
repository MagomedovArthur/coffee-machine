package io.artur.coffeemachine.exception;

/**
 * This custom exception is used to indicate that the number of drink orders
 * placed within a specified time period (e.g., in the last hour) has exceeded the allowed limit.
 * It extends the {@link RuntimeException} and is typically thrown when the coffee machine
 * has processed too many orders in a given time frame.
 */
public class OrderLimitExceededException extends RuntimeException {

    /**
     * Constructs a new {@code OrderLimitExceededException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception is thrown
     */
    public OrderLimitExceededException(String message) {
        super(message);
    }
}
