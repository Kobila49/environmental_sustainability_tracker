package igor.kos.est.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "utility_emission")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"dailyEmission"})
public class UtilityEmission extends BaseEntity {

    @Column(name = "usage", nullable = false)
    @NotNull(message = "Usage is mandatory")
    @Min(value = 0, message = "Usage must be positive")
    private BigDecimal usage;

    @Column(name = "emission", nullable = false)
    @NotNull(message = "Emission is mandatory")
    private BigDecimal emission;

    @Column(name = "emission_factor", nullable = false)
    @NotNull(message = "Emission factor is mandatory")
    private BigDecimal emissionFactor;

    @Column(name = "utility_id", nullable = false)
    @NotNull(message = "Utility id is mandatory")
    private Long utilityId;

    @ManyToOne
    @JoinColumn(name = "daily_emission_id", nullable = false)
    private DailyEmission dailyEmission;
}
