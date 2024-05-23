package igor.kos.est.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "food_emission")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodEmission extends BaseEntity {

    @Column(name = "consumption_in_kg", nullable = false)
    @NotNull(message = "Consumption is mandatory")
    @Min(value = 0, message = "Consumption must be positive")
    private Double consumptionInKg;

    @Column(name = "emission", nullable = false)
    @NotNull(message = "Emission is mandatory")
    private Double emission;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;
}
