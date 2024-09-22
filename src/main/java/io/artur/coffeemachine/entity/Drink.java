package io.artur.coffeemachine.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "drink")
public class Drink {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DrinkIngredient> drinkIngredientEntities;

    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DrinkStatistics> drinkStatisticEntities;

    public Drink(String name) {
        this.name = name;
    }
}
