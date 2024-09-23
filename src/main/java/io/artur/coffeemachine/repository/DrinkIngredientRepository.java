package io.artur.coffeemachine.repository;

import io.artur.coffeemachine.entity.DrinkIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkIngredientRepository extends JpaRepository<DrinkIngredient, Long> {

    List<DrinkIngredient> findAllByDrinkName(String name);
}
