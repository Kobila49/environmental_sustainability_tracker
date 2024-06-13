package igor.kos.est.dto.request;

import igor.kos.est.enums.UtilityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UtilityRequest(
        @PositiveOrZero @NotNull BigDecimal emissionFactor,
        @NotBlank UtilityType type,
        @NotBlank String measurementUnit
) {
}
