package io.artur.coffeemachine.service;

import io.artur.coffeemachine.dto.DrinkDto;
import io.artur.coffeemachine.entity.Drink;
import io.artur.coffeemachine.entity.DrinkIngredient;
import io.artur.coffeemachine.entity.DrinkStatistics;
import io.artur.coffeemachine.mapper.DrinkMapper;
import io.artur.coffeemachine.repository.DrinkIngredientRepository;
import io.artur.coffeemachine.repository.DrinkRepository;
import io.artur.coffeemachine.repository.DrinkStatisticsRepository;
import io.artur.coffeemachine.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
            throw new RuntimeException("Напитки не найдены"); // todo
        }
        return drinkMapper.toDrinkDtoList(drinkList);
    }

    @Transactional
    public DrinkDto prepareDrink(String drinkName) {
        Optional<Drink> drink = drinkRepository.findByName(drinkName);
        if (drink.isEmpty()) {
            throw new RuntimeException("Напиток не найден"); // todo
        }
        List<DrinkIngredient> drinkIngredientEntities = drinkIngredientRepository.findAllByDrinkName(drinkName);
        for (DrinkIngredient drinkIngredient : drinkIngredientEntities) {
            String ingredientName = drinkIngredient.getIngredient().getName();
            int ingredientQuantity = drinkIngredient.getQuantity();
            int amountOfIngredient = ingredientRepository.getIngredientByNameIgnoreCase(ingredientName).get().getRemainingQuantity();
            if (amountOfIngredient - ingredientQuantity >= 0) {
                ingredientRepository.reduceQuantity(ingredientName, ingredientQuantity);
            } else {
                throw new RuntimeException("Недостаточно ингредиентов!"); // todo
            }
        }
        drinkStatisticsRepository.save(new DrinkStatistics(drink.get()));
        return drinkMapper.toDrinkDto(drink.get());
    }
}
