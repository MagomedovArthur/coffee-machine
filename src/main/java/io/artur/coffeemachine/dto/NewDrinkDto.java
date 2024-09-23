package io.artur.coffeemachine.dto;

import java.util.List;

public record NewDrinkDto(
        String drinkName,
        List<IngredientDto> ingredients
) {
}
