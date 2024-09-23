package io.artur.coffeemachine.service;

import io.artur.coffeemachine.dto.DrinkDto;
import io.artur.coffeemachine.dto.IngredientDto;
import io.artur.coffeemachine.dto.NewDrinkDto;
import io.artur.coffeemachine.entity.Drink;
import io.artur.coffeemachine.entity.DrinkIngredient;
import io.artur.coffeemachine.entity.Ingredient;
import io.artur.coffeemachine.mapper.DrinkMapper;
import io.artur.coffeemachine.repository.DrinkIngredientRepository;
import io.artur.coffeemachine.repository.DrinkRepository;
import io.artur.coffeemachine.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DrinkIngredientService {

    private final DrinkRepository drinkRepository;
    private final DrinkIngredientRepository drinkIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final DrinkMapper drinkMapper;

    @Transactional
    public DrinkDto addNewDrinkRecipe(NewDrinkDto newDrinkDto) {
        Optional<Drink> drink = drinkRepository.findByName(newDrinkDto.drinkName());
        if (drink.isPresent()) {
            throw new RuntimeException(); // TODO
        }
        Drink newDrink = drinkRepository.save(new Drink(newDrinkDto.drinkName()));
        for (IngredientDto ingredient : newDrinkDto.ingredients()) {
            Optional<Ingredient> ingredientOpt = ingredientRepository.getIngredientByNameIgnoreCase(ingredient.name());
            if (ingredientOpt.isEmpty()) {
                throw new RuntimeException("Такого ингредиента нет в БД."); // todo
            }
            drinkIngredientRepository.save(new DrinkIngredient(newDrink, ingredientOpt.get(), ingredient.quantity()));
        }
        return drinkMapper.toDrinkDto(newDrink);
    }
}
