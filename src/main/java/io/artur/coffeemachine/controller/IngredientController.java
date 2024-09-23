package io.artur.coffeemachine.controller;

import io.artur.coffeemachine.dto.IngredientDto;
import io.artur.coffeemachine.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller provides endpoints for adding new ingredients, increasing the quantity of existing ingredients,
 * and retrieving the list of all available ingredients.
 */
@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    /**
     * This endpoint allows the user to add a new ingredient by providing an {@link IngredientDto} object
     * containing the details of the ingredient.
     *
     * @param ingredientDto the details of the new ingredient to be added.
     * @return a {@link ResponseEntity} containing the details of the newly created ingredient as {@link IngredientDto}.
     */
    @PostMapping
    public ResponseEntity<IngredientDto> addNewIngredient(@RequestBody IngredientDto ingredientDto) {
        IngredientDto ingredient = ingredientService.addNewIngredient(ingredientDto);
        return ResponseEntity.ok(ingredient);
    }

    /**
     * This endpoint allows the user to increase the available quantity of a specific ingredient by providing
     * an {@link IngredientDto} object containing the ingredient details and the new quantity to add.
     *
     * @param ingredientDto the ingredient whose quantity is to be increased.
     * @return a {@link ResponseEntity} containing the updated ingredient as an {@link IngredientDto}.
     */
    @PostMapping("/increase-quantity")
    public ResponseEntity<IngredientDto> increaseQuantityOfIngredient(@RequestBody IngredientDto ingredientDto) {
        IngredientDto ingredient = ingredientService.increaseQuantityOfIngredient(ingredientDto);
        return ResponseEntity.ok(ingredient);
    }

    /**
     * This endpoint fetches and returns all ingredients stored in the system as a list of {@link IngredientDto} objects.
     *
     * @return a {@link ResponseEntity} containing the list of ingredients.
     */
    @GetMapping
    public ResponseEntity<List<IngredientDto>> getIngredientsList() {
        List<IngredientDto> ingredientsList = ingredientService.getIngredientsList();
        return ResponseEntity.ok(ingredientsList);
    }
}
