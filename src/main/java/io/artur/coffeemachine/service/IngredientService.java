package io.artur.coffeemachine.service;

import io.artur.coffeemachine.dto.IngredientDto;
import io.artur.coffeemachine.entity.Ingredient;
import io.artur.coffeemachine.mapper.IngredientMapper;
import io.artur.coffeemachine.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            throw new RuntimeException(); // todo
        }
        Ingredient newIngredient
                = ingredientRepository.save(new Ingredient(ingredientDto.name(), ingredientDto.quantity()));
        return ingredientMapper.toIngredientDto(newIngredient);
    }

    public List<IngredientDto> getIngredientsList() {
        List<Ingredient> ingredientEntities = ingredientRepository.findAll();
        if (ingredientEntities.isEmpty()) {
            throw new RuntimeException(); // todo
        }
        return ingredientMapper.toIngredientDtoList(ingredientEntities);
    }
}
