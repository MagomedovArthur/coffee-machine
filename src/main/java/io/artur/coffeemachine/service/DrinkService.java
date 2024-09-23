package io.artur.coffeemachine.service;

import io.artur.coffeemachine.dto.DrinkDto;
import io.artur.coffeemachine.entity.Drink;
import io.artur.coffeemachine.entity.DrinkIngredient;
import io.artur.coffeemachine.entity.DrinkStatistics;
import io.artur.coffeemachine.exception.DrinksNotFoundException;
import io.artur.coffeemachine.exception.IngredientsNotFoundException;
import io.artur.coffeemachine.exception.NotEnoughQuantityOfIngredientsException;
import io.artur.coffeemachine.mapper.DrinkMapper;
import io.artur.coffeemachine.repository.DrinkIngredientRepository;
import io.artur.coffeemachine.repository.DrinkRepository;
import io.artur.coffeemachine.repository.DrinkStatisticsRepository;
import io.artur.coffeemachine.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository drinkRepository;
    private final DrinkStatisticsRepository drinkStatisticsRepository;
    private final IngredientRepository ingredientRepository;
    private final DrinkIngredientRepository drinkIngredientRepository;
    private final DrinkMapper drinkMapper;

    public List<DrinkDto> getDrinksList() {
        List<Drink> drinkList = drinkRepository.findAll();
        if (drinkList.isEmpty()) {
            throw new DrinksNotFoundException("No drinks were found.");
        }
        return drinkMapper.toDrinkDtoList(drinkList);
    }

    @Transactional
    public DrinkDto prepareDrink(String drinkName) {
        var drink = drinkRepository.findByName(drinkName);
        if (drink.isEmpty()) {
            throw new DrinksNotFoundException("The drink '" + drinkName + "' was not found");
        }
        List<DrinkIngredient> drinkIngredientEntities = drinkIngredientRepository.findAllByDrinkName(drinkName);
        for (DrinkIngredient drinkIngredient : drinkIngredientEntities) {
            var ingredientName = drinkIngredient.getIngredient().getName();
            var ingredientQuantity = drinkIngredient.getQuantity();
            if (checkAvailabilityOfIngredients(ingredientName, ingredientQuantity)) {
                ingredientRepository.reduceQuantity(ingredientName, ingredientQuantity);
            }
        }
        drinkStatisticsRepository.save(new DrinkStatistics(drink.get()));
        return drinkMapper.toDrinkDto(drink.get());
    }

    @Transactional
    public boolean checkAvailabilityOfIngredients(String ingredientName, int ingredientQuantity) {
        var ingredient = ingredientRepository.getIngredientByNameIgnoreCase(ingredientName)
                .orElseThrow(() -> new IngredientsNotFoundException("No ingredient found."));
        var remainingAmountOfIngredient = ingredient.getRemainingQuantity();
        if (remainingAmountOfIngredient - ingredientQuantity >= 0) {
            return true;
        } else {
            throw new NotEnoughQuantityOfIngredientsException("Ingredient '" + ingredientName
                                                              + "' in insufficient quantity.");
        }
    }
}
