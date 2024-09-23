package io.artur.coffeemachine.controller;

import io.artur.coffeemachine.dto.DrinkDto;
import io.artur.coffeemachine.service.DrinkStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class DrinkStatisticsController {

    private final DrinkStatisticsService drinkStatisticsService;

    @GetMapping("/popular")
    public ResponseEntity<DrinkDto> getPopularDrink() {
        DrinkDto popularDrink = drinkStatisticsService.getMostPopularDrink();
        return ResponseEntity.ok(popularDrink);
    }
}
