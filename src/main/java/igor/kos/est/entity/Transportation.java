package igor.kos.est.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

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
    private BigDecimal emissionFactor;
}
