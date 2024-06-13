package igor.kos.est.service;

import igor.kos.est.dto.request.DailyEmissionRequest;
import igor.kos.est.dto.response.DailyEmissionResponse;

import java.time.LocalDate;
import java.util.List;

public interface EmissionService {

    DailyEmissionResponse createOrUpdateDailyEmission(DailyEmissionRequest request);

    DailyEmissionResponse getDailyEmissionForDate(LocalDate date);

    List<DailyEmissionResponse> getDailyEmissions();
}
