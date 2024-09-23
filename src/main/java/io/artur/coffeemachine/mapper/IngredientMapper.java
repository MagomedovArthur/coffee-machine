package io.artur.coffeemachine.mapper;

import io.artur.coffeemachine.dto.IngredientDto;
import io.artur.coffeemachine.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    @Mapping(source = "remainingQuantity", target = "quantity")
    IngredientDto toIngredientDto(Ingredient ingredient);

    List<IngredientDto> toIngredientDtoList(List<Ingredient> ingredientEntities);
}
