package io.artur.coffeemachine.mapper;

import io.artur.coffeemachine.dto.DrinkDto;
import io.artur.coffeemachine.entity.DrinkStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DrinkStatisticsMapper {

    DrinkStatisticsMapper INSTANCE = Mappers.getMapper(DrinkStatisticsMapper.class);

    List<DrinkDto> toDrinkDtoList(List<DrinkStatistics> drink);
}
