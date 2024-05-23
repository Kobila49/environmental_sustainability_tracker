package igor.kos.est.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transportation_emission")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransportationEmission extends BaseEntity{

    @Column(name = "distance_travelled", nullable = false)
    @NotNull(message = "Distance is mandatory")
    @Min(value = 0, message = "Distance must be positive")
    private Double distanceTravelled;

    @Column(name = "emission", nullable = false)
    @NotNull(message = "Emission is mandatory")
    private Double emission;

    @ManyToOne
    @JoinColumn(name = "transportation_id", nullable = false)
    private Transportation transportation;
}
