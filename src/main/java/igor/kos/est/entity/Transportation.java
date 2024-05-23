package igor.kos.est.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transportation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transportation extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Transportation name is mandatory")
    private String name;

    @Column(name = "emission_factor", nullable = false)
    private Double emissionFactor;

    @OneToMany(mappedBy = "transportation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransportationEmission> transportationEmissions;
}
