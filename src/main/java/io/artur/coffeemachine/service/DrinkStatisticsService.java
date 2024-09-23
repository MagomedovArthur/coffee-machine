package io.artur.coffeemachine.service;

import io.artur.coffeemachine.dto.DrinkDto;
import io.artur.coffeemachine.entity.DrinkStatistics;
import io.artur.coffeemachine.exception.DrinksNotFoundException;
import io.artur.coffeemachine.mapper.DrinkStatisticsMapper;
import io.artur.coffeemachine.repository.DrinkStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrinkStatisticsService {

    private final DrinkStatisticsRepository drinkStatisticsRepository;
    private final DrinkStatisticsMapper drinkStatisticsMapper;

    public List<DrinkDto> getMostPopularDrink() {
        List<DrinkStatistics> drinkStatistics = drinkStatisticsRepository.getMostPopularDrinks();
        if (drinkStatistics.isEmpty()) {
            throw new DrinksNotFoundException("No drinks found.");
        }
        return drinkStatisticsMapper.toDrinkDtoList(drinkStatistics);
    }
}
