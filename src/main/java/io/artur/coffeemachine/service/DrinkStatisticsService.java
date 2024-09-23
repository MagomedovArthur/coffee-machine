package io.artur.coffeemachine.service;

import io.artur.coffeemachine.dto.DrinkDto;
import io.artur.coffeemachine.exception.DrinksNotFoundException;
import io.artur.coffeemachine.mapper.DrinkMapper;
import io.artur.coffeemachine.repository.DrinkRepository;
import io.artur.coffeemachine.repository.DrinkStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service allows for the retrieval of the most popular drink based on statistics collected
 * from the system, ensuring that the necessary validations are performed before returning the result.
 */
@Service
@RequiredArgsConstructor
public class DrinkStatisticsService {

    private final DrinkStatisticsRepository drinkStatisticsRepository;
    private final DrinkRepository drinkRepository;
    private final DrinkMapper drinkMapper;

    /**
     * <p>This method queries the statistics repository to find the most popular drink. If no statistics
     * are found, a {@link DrinksNotFoundException} is thrown. If the drink is found in the statistics
     * but cannot be retrieved from the drink repository, another {@link DrinksNotFoundException}
     * is thrown. If successful, the popular drink is returned as a {@link DrinkDto}.</p>
     *
     * @return a {@link DrinkDto} representing the most popular drink.
     * @throws DrinksNotFoundException if no drinks are found or the popular drink cannot be retrieved.
     */
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
