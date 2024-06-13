package igor.kos.est.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record TransportationRequest(
        @NotBlank String name,
        @NotNull @PositiveOrZero BigDecimal emissionFactor) {
}
