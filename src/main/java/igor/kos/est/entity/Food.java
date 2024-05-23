package igor.kos.est.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

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
    private Double emissionFactor;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodEmission> foodEmissions;
}
