package io.artur.coffeemachine.repository;

import io.artur.coffeemachine.entity.DrinkStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * This repository provides methods for performing CRUD operations on drink statistics data in the database,
 * extending the functionalities of {@link JpaRepository}.
 */
@Repository
public interface DrinkStatisticsRepository extends JpaRepository<DrinkStatistics, Long> {

    /**
     * Retrieves the most popular drink based on the statistics.
     * This query finds the drink that has been ordered the most by counting how many times each drink
     * appears in the statistics, and returning the one with the highest count.
     *
     * @return an {@link Optional} containing the {@link DrinkStatistics} of the most popular drink, or empty if no data is found.
     */
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

    void deleteAllByCreatedAtBefore(Timestamp createdAt);

    /**
     * Checks if the number of drink orders in the last hour exceeds a specified limit.
     *
     * <p>This query counts the number of orders placed in the drink statistics table
     * since a specified time (usually one hour ago) and compares it to the provided order limit.</p>
     *
     * @param orderLimit the threshold limit of orders to check (e.g., 30 for 30 orders per hour)
     * @param oneHourAgo the timestamp representing one hour ago from the current time
     * @return {@code true} if the number of orders in the last hour is greater than or equal to {@code orderLimit},
     * {@code false} otherwise
     */
    @Query(value = """
            SELECT COUNT(*) >= :orderLimit
            FROM drink_statistics
            WHERE created_at >= :oneHourAgo
             """, nativeQuery = true)
    boolean wereThere30OrdersInTheLastHour(@Param("orderLimit") Integer orderLimit,
                                           @Param("oneHourAgo") LocalDateTime oneHourAgo);

}
