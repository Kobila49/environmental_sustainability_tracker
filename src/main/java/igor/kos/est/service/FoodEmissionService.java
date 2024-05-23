package igor.kos.est.service;

import igor.kos.est.dto.request.FoodEmissionRequest;
import igor.kos.est.entity.FoodEmission;

public interface FoodEmissionService {

    FoodEmission findById(Long id);

    FoodEmission save(FoodEmissionRequest request);

    FoodEmission update(FoodEmissionRequest foodEmission, Long id);

    void delete(Long id);

}
