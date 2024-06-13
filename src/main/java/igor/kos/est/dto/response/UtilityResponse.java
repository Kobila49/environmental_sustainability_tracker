package igor.kos.est.dto.response;

import igor.kos.est.enums.UtilityType;

import java.math.BigDecimal;

public record UtilityResponse(
        Long id,
        BigDecimal emissionFactor,
        UtilityType type,
        String measurementUnit
) {
}
