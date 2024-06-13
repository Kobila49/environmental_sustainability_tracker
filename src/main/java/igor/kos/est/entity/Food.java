package igor.kos.est.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "food")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Food extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Food name is mandatory")
    private String name;

    @Column(name = "emission_factor", nullable = false)
    @NotNull(message = "Emission factor is mandatory")
    @Min(value = 0, message = "Emission factor must be positive")
    private BigDecimal emissionFactor;

    @Column(name = "category")
    private String category;
}
