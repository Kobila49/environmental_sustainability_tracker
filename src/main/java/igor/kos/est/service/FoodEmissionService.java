package igor.kos.est.service;

import igor.kos.est.dto.request.FoodEmissionRequest;
import igor.kos.est.entity.DailyEmission;
import igor.kos.est.entity.FoodEmission;

public interface FoodEmissionService {

    FoodEmission findById(Long id);

    FoodEmission save(FoodEmissionRequest request);

    FoodEmission save(FoodEmissionRequest request, DailyEmission dailyEmission);

    FoodEmission update(FoodEmissionRequest request, Long id);

    void delete(Long id);

    FoodEmissionRequest toRequest(FoodEmission foodEmission);
}
