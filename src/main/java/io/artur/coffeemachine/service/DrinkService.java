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

/**
 * DrinkService provides functionalities for managing drinks in the Coffee Machine system,
 * including retrieving drink lists and preparing drinks based on specified recipes.
 */
@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository drinkRepository;
    private final DrinkStatisticsRepository drinkStatisticsRepository;
    private final IngredientRepository ingredientRepository;
    private final DrinkIngredientRepository drinkIngredientRepository;
    private final DrinkMapper drinkMapper;

    /**
     * Retrieves a list of all drinks in the system.
     *
     * <p>If no drinks are found, a {@link DrinksNotFoundException} is thrown.</p>
     *
     * @return a list of {@link DrinkDto} representing all drinks.
     * @throws DrinksNotFoundException if no drinks are found in the repository.
     */
    public List<DrinkDto> getDrinksList() {
        List<Drink> drinkList = drinkRepository.findAll();
        if (drinkList.isEmpty()) {
            throw new DrinksNotFoundException("No drinks were found.");
        }
        return drinkMapper.toDrinkDtoList(drinkList);
    }

    /**
     * Prepares a drink based on its name.
     * This method checks if the specified drink exists. If it does, it verifies the availability
     * of each ingredient and reduces their quantities accordingly. It also records the preparation
     * in the statistics repository. If the drink is not found, a {@link DrinksNotFoundException}
     * is thrown.
     *
     * @param drinkName the name of the drink to prepare.
     * @return a {@link DrinkDto} representing the prepared drink.
     * @throws DrinksNotFoundException                 if the specified drink is not found.
     * @throws IngredientsNotFoundException            if any ingredient required for the drink is not found.
     * @throws NotEnoughQuantityOfIngredientsException if any ingredient does not have enough quantity.
     */
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

    /**
     * This method retrieves the ingredient by name and verifies if there is sufficient quantity
     * available. If the ingredient is not found, an {@link IngredientsNotFoundException} is thrown.
     * If the quantity is insufficient, a {@link NotEnoughQuantityOfIngredientsException} is thrown.
     *
     * @param ingredientName     the name of the ingredient to check.
     * @param ingredientQuantity the required quantity of the ingredient.
     * @return true if sufficient quantity is available.
     * @throws IngredientsNotFoundException            if the specified ingredient is not found.
     * @throws NotEnoughQuantityOfIngredientsException if the available quantity is insufficient.
     */
    @Transactional
    public boolean checkAvailabilityOfIngredients(String ingredientName, int ingredientQuantity) {
        var ingredient = ingredientRepository.getIngredientByNameIgnoreCase(ingredientName)
                .orElseThrow(() -> new IngredientsNotFoundException("The ingredient '" + ingredientName
                                                                    + "' was not found"));
        var remainingQuantityOfIngredient = ingredient.getRemainingQuantity();
        if (remainingQuantityOfIngredient - ingredientQuantity >= 0) {
            return true;
        } else {
            throw new NotEnoughQuantityOfIngredientsException("Ingredient '" + ingredientName
                                                              + "' in insufficient quantity.");
        }
    }
}
