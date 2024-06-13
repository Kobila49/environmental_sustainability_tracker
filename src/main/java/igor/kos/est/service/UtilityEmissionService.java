package igor.kos.est.service;

import igor.kos.est.dto.request.UtilityEmissionRequest;
import igor.kos.est.entity.DailyEmission;
import igor.kos.est.entity.UtilityEmission;

public interface UtilityEmissionService {

    UtilityEmission findById(Long id);

    UtilityEmission save(UtilityEmissionRequest request, DailyEmission dailyEmission);

    UtilityEmission update(UtilityEmissionRequest request, Long id);

    void delete(Long id);

    UtilityEmissionRequest toRequest(UtilityEmission utilityEmission);
}
