package igor.kos.est.dto.response;

import java.math.BigDecimal;

public record FoodResponse(
        Long id,
        String name,
        BigDecimal emissionFactor,
        String category
) {
}
