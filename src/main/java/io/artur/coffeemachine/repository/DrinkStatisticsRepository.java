package io.artur.coffeemachine.repository;

import io.artur.coffeemachine.entity.DrinkStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrinkStatisticsRepository extends JpaRepository<DrinkStatistics, Long> {

    @Query(value = """
            SELECT *
            FROM drink_statistics
            WHERE drink_id = (SELECT drink_id
                              FROM drink_statistics
                              GROUP BY drink_id
                              ORDER BY COUNT(*) DESC
                              LIMIT 1)
            LIMIT 1
             """, nativeQuery = true)
    Optional<DrinkStatistics> getMostPopularDrink();
}
