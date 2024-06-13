package igor.kos.est.dto.request;

import igor.kos.est.annotations.AtLeastOneElement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.List;

public record DailyEmissionRequest(@NotNull @PastOrPresent LocalDate date,
                                   @AtLeastOneElement List<TransportationEmissionRequest> transportationEmissionRecords,
                                   @AtLeastOneElement List<FoodEmissionRequest> foodEmissionRecords,
                                   @AtLeastOneElement List<UtilityEmissionRequest> utilityEmissionRecords) {
}
