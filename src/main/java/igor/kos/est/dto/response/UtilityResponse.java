package igor.kos.est.dto.response;

import igor.kos.est.enums.UtilityType;

public record UtilityResponse(
        Long id,
        Double emissionFactor,
        UtilityType type,
        String measurementUnit
) {
}
