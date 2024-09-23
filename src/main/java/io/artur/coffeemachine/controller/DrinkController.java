package io.artur.coffeemachine.controller;

import io.artur.coffeemachine.dto.DrinkDto;
import io.artur.coffeemachine.dto.NewDrinkDto;
import io.artur.coffeemachine.service.DrinkIngredientService;
import io.artur.coffeemachine.service.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller exposes REST API endpoints for adding new drink recipes, fetching the list of available drinks,
 * and ordering a drink to be prepared.
 * It uses {@link DrinkService} for managing drink operations and {@link DrinkIngredientService} for handling
 * the creation of new drink recipes.
 */
@RestController
@RequestMapping("/api/drinks")
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;
    private final DrinkIngredientService drinkIngredientService;

    /**
     * This endpoint allows the user to create a new drink recipe by providing a {@link NewDrinkDto} object
     * that contains the drink details.
     *
     * @param newDrinkDto the details of the new drink to be added.
     * @return a {@link ResponseEntity} containing the details of the newly created drink as a {@link DrinkDto}.
     */
    @PostMapping
    public ResponseEntity<DrinkDto> addNewDrinkRecipe(@RequestBody NewDrinkDto newDrinkDto) {
        DrinkDto drink = drinkIngredientService.addNewDrinkRecipe(newDrinkDto);
        return ResponseEntity.ok(drink);
    }

    /**
     * This endpoint fetches all the available drink recipes currently stored in the system.
     *
     * @return a {@link ResponseEntity} containing a list of drinks as {@link DrinkDto}.
     */
    @GetMapping
    public ResponseEntity<List<DrinkDto>> getDrinksList() {
        List<DrinkDto> drinkList = drinkService.getDrinksList();
        return ResponseEntity.ok(drinkList);
    }

    /**
     * This endpoint processes a drink order by its name, and returns a confirmation message
     * when the drink is ready to be served.
     *
     * @param drinkName the name of the drink to be prepared.
     * @return a {@link ResponseEntity} containing a success message indicating the drink is ready.
     */
    @PostMapping("/order")
    public ResponseEntity<String> prepareDrink(@RequestParam(name = "drinkName") String drinkName) {
        DrinkDto drink = drinkService.prepareDrink(drinkName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Your '" + drink.name() + "' is ready!");
    }
}
