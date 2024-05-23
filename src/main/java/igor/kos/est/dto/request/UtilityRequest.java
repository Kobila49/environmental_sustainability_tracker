package igor.kos.est.dto.request;

import igor.kos.est.enums.UtilityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UtilityRequest(
        @PositiveOrZero @NotNull Double emissionFactor,
        UtilityType type,
        @NotBlank String measurementUnit
) {
}
