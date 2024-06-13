package igor.kos.est.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "daily_emission", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "emission_date"})
})
@Data
@Builder
@AllArgsConstructor
@ToString(exclude = {"foodEmissions", "transportationEmissions", "utilityEmissions"})
public class DailyEmission extends BaseEntity {

    @NotNull(message = "Emission date is mandatory")
    @Column(name = "emission_date", nullable = false)
    private LocalDate emissionDate;

    @NotNull(message = "Total emission is mandatory")
    @Column(name = "total_emission", nullable = false)
    private BigDecimal totalEmission;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "dailyEmission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodEmission> foodEmissions = new ArrayList<>();

    @OneToMany(mappedBy = "dailyEmission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransportationEmission> transportationEmissions = new ArrayList<>();

    @OneToMany(mappedBy = "dailyEmission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UtilityEmission> utilityEmissions = new ArrayList<>();

    public DailyEmission() {
        this.totalEmission = BigDecimal.ZERO;
    }
}
