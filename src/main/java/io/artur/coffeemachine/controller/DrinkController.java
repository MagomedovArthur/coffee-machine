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

@RestController
@RequestMapping("/api/drinks")
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;
    private final DrinkIngredientService drinkIngredientService;

    @PostMapping
    public ResponseEntity<DrinkDto> addNewDrinkRecipe(@RequestBody NewDrinkDto newDrinkDto) {
        DrinkDto drink = drinkIngredientService.addNewDrinkRecipe(newDrinkDto);
        return ResponseEntity.ok(drink);
    }

    @GetMapping
    public ResponseEntity<List<DrinkDto>> getDrinksList() {
        List<DrinkDto> drinkList = drinkService.getDrinksList();
        return ResponseEntity.ok(drinkList);
    }

    @PostMapping("/order")
    public ResponseEntity<String> prepareDrink(@RequestParam(name = "drinkName") String drinkName) {
        DrinkDto drink = drinkService.prepareDrink(drinkName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Your '" + drink.name() + "' is ready!");
    }
}
