package igor.kos.est.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transportation_emission")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"dailyEmission"})
public class TransportationEmission extends BaseEntity{

    @Column(name = "distance_travelled", nullable = false)
    @NotNull(message = "Distance is mandatory")
    @Min(value = 0, message = "Distance must be positive")
    private BigDecimal distanceTravelled;

    @Column(name = "emission", nullable = false)
    @NotNull(message = "Emission is mandatory")
    private BigDecimal emission;

    @Column(name = "emission_factor", nullable = false)
    @NotNull(message = "Emission factor is mandatory")
    private BigDecimal emissionFactor;

    @Column(name = "transportation_id", nullable = false)
    @NotNull(message = "Transportation id is mandatory")
    private Long transportationId;

    @ManyToOne
    @JoinColumn(name = "daily_emission_id", nullable = false)
    private DailyEmission dailyEmission;
}
