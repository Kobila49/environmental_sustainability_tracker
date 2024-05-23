package igor.kos.est.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "daily_emission")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyEmission extends BaseEntity {

    @NotNull(message = "Emission date is mandatory")
    @Column(name = "emission_date", nullable = false)
    private LocalDate emissionDate;

    @NotNull(message = "Total emission is mandatory")
    @Column(name = "total_emission", nullable = false)
    private Double totalEmission;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "food_emission_id")
    private FoodEmission foodEmission;

    @ManyToOne
    @JoinColumn(name = "transportation_emission_id")
    private TransportationEmission transportationEmission;

    @ManyToMany
    @JoinTable(
            name = "daily_emission_utility_emission",
            joinColumns = @JoinColumn(name = "daily_emission_id"),
            inverseJoinColumns = @JoinColumn(name = "utility_emission_id")
    )
    private List<UtilityEmission> utilityEmissions;
}
