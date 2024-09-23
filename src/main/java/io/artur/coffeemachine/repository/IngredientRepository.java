package io.artur.coffeemachine.repository;

import io.artur.coffeemachine.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> getIngredientByNameIgnoreCase(String name);

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
