package io.artur.coffeemachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DrinkAlreadyExistsException.class)
    public ResponseEntity<String> handleDrinkAlreadyExists(DrinkAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(IngredientsNotFoundException.class)
    public ResponseEntity<String> handleIngredientsNotFound(IngredientsNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(DrinksNotFoundException.class)
    public ResponseEntity<String> handleDrinksNotFound(DrinksNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(NotEnoughQuantityOfIngredientsException.class)
    public ResponseEntity<String> handleNotEnoughQuantityOfIngredients(NotEnoughQuantityOfIngredientsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(IngredientAlreadyExistsException.class)
    public ResponseEntity<String> handleIngredientAlreadyExists(IngredientAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}
