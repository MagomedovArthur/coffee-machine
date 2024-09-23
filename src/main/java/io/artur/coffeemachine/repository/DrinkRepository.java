package io.artur.coffeemachine.repository;

import io.artur.coffeemachine.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This repository provides methods for performing CRUD operations on drink data in the database,
 * extending the functionalities of {@link JpaRepository}.
 */
@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {

    /**
     * Retrieves a drink by its name.
     *
     * @param name the name of the drink to search for.
     * @return an {@link Optional} containing the found drink or empty if not found.
     */
    Optional<Drink> findByName(String name);
}
