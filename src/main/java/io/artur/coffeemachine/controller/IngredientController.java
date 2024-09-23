package io.artur.coffeemachine.controller;

import io.artur.coffeemachine.dto.IngredientDto;
import io.artur.coffeemachine.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping("/add")
    public ResponseEntity<IngredientDto> addNewIngredient(@RequestBody IngredientDto ingredientDto) {
        IngredientDto ingredient = ingredientService.addNewIngredient(ingredientDto);
        return ResponseEntity.ok(ingredient);
    }

    @PostMapping("/increase-quantity")
    public ResponseEntity<IngredientDto> increaseAmountOfIngredient(@RequestBody IngredientDto ingredientDto) {
        IngredientDto ingredient = ingredientService.increaseAmountOfIngredient(ingredientDto);
        return ResponseEntity.ok(ingredient);
    }

    @GetMapping("/list")
    public ResponseEntity<List<IngredientDto>> getIngredientList() {
        List<IngredientDto> ingredientsList = ingredientService.getIngredientsList();
        return ResponseEntity.ok(ingredientsList);
    }
}
