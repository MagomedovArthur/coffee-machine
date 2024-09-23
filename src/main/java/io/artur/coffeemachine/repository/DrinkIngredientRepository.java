package io.artur.coffeemachine.repository;

import io.artur.coffeemachine.entity.DrinkIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This repository provides methods for performing CRUD operations on drink ingredient data
 * in the database, extending the functionalities of {@link JpaRepository}.
 */
@Repository
public interface DrinkIngredientRepository extends JpaRepository<DrinkIngredient, Long> {

    /**
     * Retrieves a list of drink ingredients associated with a specific drink by its name.
     *
     * @param name the name of the drink for which ingredients are to be retrieved.
     * @return a list of {@link DrinkIngredient} entities associated with the specified drink name.
     */
    List<DrinkIngredient> findAllByDrinkName(String name);
}
