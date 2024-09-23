package io.artur.coffeemachine.repository;

import io.artur.coffeemachine.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This repository provides methods for performing CRUD operations and custom queries on the
 * ingredient data in the database. It extends {@link JpaRepository}, providing standard methods
 * for entity management.
 */
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    /**
     * Retrieves an ingredient by its name, ignoring case.
     *
     * @param name the name of the ingredient to search for.
     * @return an {@link Optional} containing the found ingredient or empty if not found.
     */
    Optional<Ingredient> getIngredientByNameIgnoreCase(String name);

    /**
     * Reduces the quantity of a specified ingredient in the database.
     * This method updates the remaining quantity of the ingredient by subtracting the specified amount.
     * The last updated timestamp is also set to the current time.
     *
     * @param name   the name of the ingredient whose quantity is to be reduced.
     * @param amount the amount to subtract from the remaining quantity.
     */
    @Modifying
    @Query(value = """
            UPDATE ingredient
            SET remaining_quantity = remaining_quantity - :amount,
                updated_at = NOW()
            WHERE name = :name
            """,
            nativeQuery = true)
    void reduceQuantity(@Param("name") String name,
                        @Param("amount") int amount);

    /**
     * Increases the quantity of a specified ingredient in the database.
     * This method updates the remaining quantity of the ingredient by adding the specified amount.
     * The last updated timestamp is also set to the current time.
     *
     * @param name   the name of the ingredient whose quantity is to be increased.
     * @param amount the amount to add to the remaining quantity.
     */
    @Modifying
    @Query(value = """
            UPDATE ingredient
            SET remaining_quantity = remaining_quantity + :amount,
                updated_at = NOW()
            WHERE name = :name
            """,
            nativeQuery = true)
    void increaseQuantity(@Param("name") String name,
                          @Param("amount") int amount);
}
