package igor.kos.est.dto.response;

import java.math.BigDecimal;

public record TransportationResponse(Long id, String name, BigDecimal emissionFactor) {
}
