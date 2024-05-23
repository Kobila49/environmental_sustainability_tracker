package igor.kos.est.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "utility_emission")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilityEmission extends BaseEntity {

    @Column(name = "usage", nullable = false)
    @NotNull(message = "Usage is mandatory")
    @Min(value = 0, message = "Usage must be positive")
    private Double usage;

    @Column(name = "emission", nullable = false)
    @NotNull(message = "Emission is mandatory")
    private Double emission;

    @ManyToOne
    @JoinColumn(name = "utility_id", nullable = false)
    private Utility utility;
}
