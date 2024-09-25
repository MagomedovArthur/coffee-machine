package io.artur.coffeemachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler is a centralized exception handling component that intercepts exceptions
 * thrown by the controllers in the Coffee Machine application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the DrinkAlreadyExistsException and returns a 409 Conflict response.
     *
     * @param e the exception that was thrown.
     * @return a {@link ResponseEntity} containing the error message and HTTP status code.
     */
    @ExceptionHandler(DrinkAlreadyExistsException.class)
    public ResponseEntity<String> handleDrinkAlreadyExists(DrinkAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    /**
     * Handles the IngredientsNotFoundException and returns a 404 Not Found response.
     *
     * @param e the exception that was thrown.
     * @return a {@link ResponseEntity} containing the error message and HTTP status code.
     */
    @ExceptionHandler(IngredientsNotFoundException.class)
    public ResponseEntity<String> handleIngredientsNotFound(IngredientsNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    /**
     * Handles the DrinksNotFoundException and returns a 404 Not Found response.
     *
     * @param e the exception that was thrown.
     * @return a {@link ResponseEntity} containing the error message and HTTP status code.
     */
    @ExceptionHandler(DrinksNotFoundException.class)
    public ResponseEntity<String> handleDrinksNotFound(DrinksNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    /**
     * Handles the NotEnoughQuantityOfIngredientsException and returns a 409 Conflict response.
     *
     * @param e the exception that was thrown.
     * @return a {@link ResponseEntity} containing the error message and HTTP status code.
     */
    @ExceptionHandler(NotEnoughQuantityOfIngredientsException.class)
    public ResponseEntity<String> handleNotEnoughQuantityOfIngredients(NotEnoughQuantityOfIngredientsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    /**
     * Handles the IngredientAlreadyExistsException and returns a 409 Conflict response.
     *
     * @param e the exception that was thrown.
     * @return a {@link ResponseEntity} containing the error message and HTTP status code.
     */
    @ExceptionHandler(IngredientAlreadyExistsException.class)
    public ResponseEntity<String> handleIngredientAlreadyExists(IngredientAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    /**
     * This method is invoked when an {@code OrderLimitExceededException} is thrown in the application.
     * It creates a response entity with an HTTP status of {@code CONFLICT} (409) and includes
     * the exception message in the response body. This informs the client that the request
     * could not be processed due to the order limit being exceeded.
     *
     * @param e the {@code OrderLimitExceededException} that was thrown
     * @return a {@link ResponseEntity} containing the error message and a conflict status
     */
    @ExceptionHandler(OrderLimitExceededException.class)
    public ResponseEntity<String> handleIngredientAlreadyExists(OrderLimitExceededException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}
