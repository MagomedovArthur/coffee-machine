package io.artur.coffeemachine.mapper;

import io.artur.coffeemachine.dto.DrinkDto;
import io.artur.coffeemachine.entity.Drink;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DrinkMapper {

    DrinkMapper INSTANCE = Mappers.getMapper(DrinkMapper.class);

    List<DrinkDto> toDrinkDtoList(List<Drink> drink);

    DrinkDto toDrinkDto(Drink drink);
}
