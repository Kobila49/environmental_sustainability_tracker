package igor.kos.est.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UtilityEmissionRequest(
        @NotNull @Positive Long utilityId,
        @NotNull @PositiveOrZero BigDecimal consumption) {
}
