package igor.kos.est.dto.response;

import igor.kos.est.dto.request.FoodEmissionRequest;
import igor.kos.est.dto.request.TransportationEmissionRequest;
import igor.kos.est.dto.request.UtilityEmissionRequest;

import java.math.BigDecimal;
import java.util.List;

public record DailyEmissionResponse(
        Long id,
        String date,
        List<TransportationEmissionRequest> transportationEmissionRecords,
        BigDecimal transportationEmissionDailyTotal,
        List<FoodEmissionRequest> foodEmissionRecords,
        BigDecimal foodEmissionDailyTotal,
        List<UtilityEmissionRequest> utilityEmissionRecords,
        BigDecimal utilityEmissionDailyTotal,
        BigDecimal totalEmission) {
}
