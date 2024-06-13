package igor.kos.est.service;

import igor.kos.est.dto.request.FoodRequest;
import igor.kos.est.dto.response.FoodResponse;
import igor.kos.est.entity.Food;

import java.util.List;

public interface FoodService {

    List<FoodResponse> findAll();

    FoodResponse findById(Long id);

    FoodResponse save(FoodRequest request);

    FoodResponse update(FoodRequest request, Long id);

    void delete(Long id);

    Food getFood(Long id);
}
