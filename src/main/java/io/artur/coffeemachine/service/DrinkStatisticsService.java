package io.artur.coffeemachine.service;

import io.artur.coffeemachine.dto.DrinkDto;
import io.artur.coffeemachine.exception.DrinksNotFoundException;
import io.artur.coffeemachine.mapper.DrinkMapper;
import io.artur.coffeemachine.repository.DrinkRepository;
import io.artur.coffeemachine.repository.DrinkStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DrinkStatisticsService {

    private final DrinkStatisticsRepository drinkStatisticsRepository;
    private final DrinkRepository drinkRepository;
    private final DrinkMapper drinkMapper;

    @Transactional
    public DrinkDto getMostPopularDrink() {
        var drinkStatistics = drinkStatisticsRepository.getMostPopularDrink();
        if (drinkStatistics.isEmpty()) {
            throw new DrinksNotFoundException("No drinks found.");
        }
        var popularDrink = drinkRepository.findById(drinkStatistics.get().getDrink().getId());
        if (popularDrink.isEmpty()) {
            throw new DrinksNotFoundException("Popular drink not found.");
        }
        return drinkMapper.toDrinkDto(popularDrink.get());
    }
}
