package io.artur.coffeemachine.service;

import io.artur.coffeemachine.dto.DrinkDto;
import io.artur.coffeemachine.dto.IngredientDto;
import io.artur.coffeemachine.dto.NewDrinkDto;
import io.artur.coffeemachine.entity.Drink;
import io.artur.coffeemachine.entity.DrinkIngredient;
import io.artur.coffeemachine.exception.DrinkAlreadyExistsException;
import io.artur.coffeemachine.exception.IngredientsNotFoundException;
import io.artur.coffeemachine.mapper.DrinkMapper;
import io.artur.coffeemachine.repository.DrinkIngredientRepository;
import io.artur.coffeemachine.repository.DrinkRepository;
import io.artur.coffeemachine.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service allows the addition of new drink recipes, ensuring that all specified ingredients
 * are available in the system before saving the drink.
 */
@Service
@RequiredArgsConstructor
public class DrinkIngredientService {

    private final DrinkRepository drinkRepository;
    private final DrinkIngredientRepository drinkIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final DrinkMapper drinkMapper;

    /**
     * Adds a new drink recipe to the system.
     * This method first checks if the drink already exists by name. If it does, a
     * {@link DrinkAlreadyExistsException} is thrown. If not, the drink is created and saved.
     * Each ingredient specified in the {@link NewDrinkDto} is then validated to ensure it exists;
     * if any ingredient is not found, an {@link IngredientsNotFoundException} is thrown.
     * Finally, the drink is mapped to a {@link DrinkDto} and returned.
     *
     * @param newDrinkDto the data transfer object containing the details of the new drink and its ingredients.
     * @return a {@link DrinkDto} representing the newly created drink recipe.
     * @throws DrinkAlreadyExistsException  if a drink with the same name already exists.
     * @throws IngredientsNotFoundException if any specified ingredient is not found in the system.
     */
    @Transactional
    public DrinkDto addNewDrinkRecipe(NewDrinkDto newDrinkDto) {
        var drink = drinkRepository.findByName(newDrinkDto.drinkName());
        if (drink.isPresent()) {
            throw new DrinkAlreadyExistsException("The drink '" + newDrinkDto.drinkName()
                                                  + "' you are trying to add already exists.");
        }
        Drink newDrink = drinkRepository.save(new Drink(newDrinkDto.drinkName()));
        for (IngredientDto ingredient : newDrinkDto.ingredients()) {
            var ingredientOpt = ingredientRepository.getIngredientByNameIgnoreCase(ingredient.name());
            if (ingredientOpt.isEmpty()) {
                throw new IngredientsNotFoundException("The ingredient '" + ingredient.name() + "' was not found. "
                                                       + "Try adding it as a new ingredient, or check the correct spelling "
                                                       + "of the ingredient.");
            }
            drinkIngredientRepository.save(new DrinkIngredient(newDrink, ingredientOpt.get(), ingredient.quantity()));
        }
        return drinkMapper.toDrinkDto(newDrink);
    }
}
