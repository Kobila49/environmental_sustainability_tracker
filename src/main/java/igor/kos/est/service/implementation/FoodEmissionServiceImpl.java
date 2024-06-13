package igor.kos.est.service.implementation;

import igor.kos.est.dto.request.FoodEmissionRequest;
import igor.kos.est.entity.DailyEmission;
import igor.kos.est.entity.Food;
import igor.kos.est.entity.FoodEmission;
import igor.kos.est.exceptions.NoEntityFoundException;
import igor.kos.est.repository.FoodEmissionRepository;
import igor.kos.est.service.FoodEmissionService;
import igor.kos.est.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodEmissionServiceImpl implements FoodEmissionService {

    private final FoodEmissionRepository repository;
    private final FoodService foodService;

    @Override
    public FoodEmission findById(Long id) {
        return getFoodEmissionById(id)
                .orElseThrow(() -> getNoEntityFoundException(id));
    }

    @Override
    public FoodEmission save(FoodEmissionRequest request, DailyEmission dailyEmission) {
        Food food = foodService.getFood(request.foodId());
        FoodEmission foodEmission = buildFoodEmission(request, food);
        foodEmission.setDailyEmission(dailyEmission);
        return repository.save(foodEmission);
    }

    @Override
    public FoodEmission update(FoodEmissionRequest request, Long id) {
        FoodEmission entity = getFoodEmissionById(id)
                .orElseThrow(() -> getNoEntityFoundException(id));

        Food food = foodService.getFood(request.foodId());
        final var emission = request.consumption().multiply(food.getEmissionFactor());
        entity.setEmission(emission);
        entity.setFoodId(food.getId());
        entity.setEmissionFactor(food.getEmissionFactor());
        entity.setConsumptionInKg(request.consumption());
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw getNoEntityFoundException(id);
        }
    }

    @Override
    public FoodEmissionRequest toRequest(FoodEmission foodEmission) {
        return new FoodEmissionRequest(
                foodEmission.getFoodId(),
                foodEmission.getConsumptionInKg()
        );
    }

    private static NoEntityFoundException getNoEntityFoundException(Long id) {
        return new NoEntityFoundException(STR."FoodEmission not found with id \{id}");
    }

    private Optional<FoodEmission> getFoodEmissionById(Long id) {
        return repository.findById(id);
    }

    private FoodEmission buildFoodEmission(FoodEmissionRequest request, Food food) {
        final var emission = request.consumption().multiply(food.getEmissionFactor());
        return FoodEmission.builder()
                .emissionFactor(food.getEmissionFactor())
                .emission(emission)
                .foodId(food.getId())
                .consumptionInKg(request.consumption())
                .build();
    }
}
