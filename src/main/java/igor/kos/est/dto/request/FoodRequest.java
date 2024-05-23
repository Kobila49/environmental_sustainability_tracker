package igor.kos.est.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record FoodRequest(
        @NotBlank String name,
        @PositiveOrZero @NotNull Double emissionFactor,
        @NotBlank String category) {
}
