package igor.kos.est.entity;

import igor.kos.est.enums.UtilityType;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "utility")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Utility extends BaseEntity {

    @Column(name = "emission_factor", nullable = false)
    private Double emissionFactor;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, unique = true)
    private UtilityType type;

    @Column(name = "measurement_unit", nullable = false)
    private String measurementUnit;
}
