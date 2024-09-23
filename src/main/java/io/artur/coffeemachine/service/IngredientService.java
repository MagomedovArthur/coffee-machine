package io.artur.coffeemachine.service;

import io.artur.coffeemachine.dto.IngredientDto;
import io.artur.coffeemachine.entity.Ingredient;
import io.artur.coffeemachine.exception.IngredientAlreadyExistsException;
import io.artur.coffeemachine.exception.IngredientsNotFoundException;
import io.artur.coffeemachine.mapper.IngredientMapper;
import io.artur.coffeemachine.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public IngredientDto addNewIngredient(IngredientDto ingredientDto) {
        Optional<Ingredient> ingredient = ingredientRepository.getIngredientByNameIgnoreCase(ingredientDto.name());
        if (ingredient.isPresent()) {
            throw new IngredientAlreadyExistsException("The ingredient '" + ingredientDto.name()
                                                       + "' you are trying to add already exists.");
        }
        Ingredient newIngredient
                = ingredientRepository.save(new Ingredient(ingredientDto.name(), ingredientDto.quantity()));
        return ingredientMapper.toIngredientDto(newIngredient);
    }

    public List<IngredientDto> getIngredientsList() {
        List<Ingredient> ingredientEntities = ingredientRepository.findAll();
        if (ingredientEntities.isEmpty()) {
            throw new IngredientsNotFoundException("No ingredients found.");
        }
        return ingredientMapper.toIngredientDtoList(ingredientEntities);
    }

    @Transactional
    public IngredientDto increaseQuantityOfIngredient(IngredientDto ingredientDto) {
        var ingredient = ingredientRepository.getIngredientByNameIgnoreCase(ingredientDto.name());
        if (ingredient.isEmpty()) {
            throw new IngredientsNotFoundException("The ingredient '" + ingredientDto.name() + "' was not found.");
        }
        ingredientRepository.increaseQuantity(ingredientDto.name(), ingredientDto.quantity());
        var updatedIngredient = ingredientRepository.getIngredientByNameIgnoreCase(ingredientDto.name())
                .orElseThrow(() -> new IngredientsNotFoundException("The ingredient '" + ingredientDto.name()
                                                                    + "' was not found"));
        return ingredientMapper.toIngredientDto(updatedIngredient);
    }
}
