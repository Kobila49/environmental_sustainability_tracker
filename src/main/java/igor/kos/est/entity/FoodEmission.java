package igor.kos.est.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "food_emission")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"dailyEmission"})
public class FoodEmission extends BaseEntity {

    @Column(name = "consumption_in_kg", nullable = false)
    @NotNull(message = "Consumption is mandatory")
    @Min(value = 0, message = "Consumption must be positive")
    private BigDecimal consumptionInKg;

    @Column(name = "emission", nullable = false)
    @NotNull(message = "Emission is mandatory")
    private BigDecimal emission;

    @Column(name = "emission_factor", nullable = false)
    @NotNull(message = "Emission factor is mandatory")
    private BigDecimal emissionFactor;

    @Column(name = "food_id", nullable = false)
    @NotNull(message = "Food id is mandatory")
    private Long foodId;

    @ManyToOne
    @JoinColumn(name = "daily_emission_id", nullable = false)
    private DailyEmission dailyEmission;
}
