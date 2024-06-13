package igor.kos.est.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record FoodEmissionRequest(
        @NotNull @Positive Long foodId,
        @NotNull @PositiveOrZero BigDecimal consumption) {
}
