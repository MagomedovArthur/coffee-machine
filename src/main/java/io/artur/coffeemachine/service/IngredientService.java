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

/**
 * This service allows for adding new ingredients, retrieving a list of existing ingredients,
 * and increasing the quantity of a specific ingredient.
 */
@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    /**
     * Adds a new ingredient to the system.
     * This method first checks if an ingredient with the same name already exists. If it does,
     * an {@link IngredientAlreadyExistsException} is thrown. If not, the ingredient is created and saved,
     * and the corresponding {@link IngredientDto} is returned.
     *
     * @param ingredientDto the data transfer object containing the details of the ingredient to add.
     * @return a {@link IngredientDto} representing the newly added ingredient.
     * @throws IngredientAlreadyExistsException if an ingredient with the same name already exists.
     */
    @Transactional
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

    /**
     * Retrieves a list of all ingredients in the system.
     *
     * <p>If no ingredients are found, an {@link IngredientsNotFoundException} is thrown.</p>
     *
     * @return a list of {@link IngredientDto} representing all ingredients.
     * @throws IngredientsNotFoundException if no ingredients are found in the repository.
     */
    public List<IngredientDto> getIngredientsList() {
        List<Ingredient> ingredientEntities = ingredientRepository.findAll();
        if (ingredientEntities.isEmpty()) {
            throw new IngredientsNotFoundException("No ingredients found.");
        }
        return ingredientMapper.toIngredientDtoList(ingredientEntities);
    }

    /**
     * Increases the quantity of a specified ingredient.
     * This method retrieves the ingredient by name and checks its existence. If the ingredient
     * does not exist, an {@link IngredientsNotFoundException} is thrown. If it exists, the quantity
     * is increased as specified, and the updated ingredient is returned as a {@link IngredientDto}.
     *
     * @param ingredientDto the data transfer object containing the ingredient name and quantity to increase.
     * @return a {@link IngredientDto} representing the updated ingredient.
     * @throws IngredientsNotFoundException if the specified ingredient is not found.
     */
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
