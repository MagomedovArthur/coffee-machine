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

@Service
@RequiredArgsConstructor
public class DrinkIngredientService {

    private final DrinkRepository drinkRepository;
    private final DrinkIngredientRepository drinkIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final DrinkMapper drinkMapper;

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
