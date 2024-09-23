package io.artur.coffeemachine.controller;

import io.artur.coffeemachine.dto.DrinkDto;
import io.artur.coffeemachine.service.DrinkStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is responsible for returning drink statistics, such as the most popular drink
 * based on usage data or customer preference.
 */
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class DrinkStatisticsController {

    private final DrinkStatisticsService drinkStatisticsService;

    /**
     * This endpoint returns the drink that has been ordered or prepared the most,
     * as determined by the {@link DrinkStatisticsService}.
     *
     * @return a {@link ResponseEntity} containing the most popular drink as a {@link DrinkDto}.
     */
    @GetMapping("/popular")
    public ResponseEntity<DrinkDto> getPopularDrink() {
        DrinkDto popularDrink = drinkStatisticsService.getMostPopularDrink();
        return ResponseEntity.ok(popularDrink);
    }
}
