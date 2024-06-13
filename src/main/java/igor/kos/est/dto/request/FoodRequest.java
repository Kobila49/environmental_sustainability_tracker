package igor.kos.est.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record FoodRequest(
        @NotBlank String name,
        @PositiveOrZero @NotNull BigDecimal emissionFactor,
        @NotBlank String category) {
}
