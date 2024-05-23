package igor.kos.est.service.implementation;

import igor.kos.est.dto.request.FoodEmissionRequest;
import igor.kos.est.entity.Food;
import igor.kos.est.entity.FoodEmission;
import igor.kos.est.exceptions.NoEntityFoundException;
import igor.kos.est.repository.FoodEmissionRepository;
import igor.kos.est.service.FoodEmissionService;
import igor.kos.est.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static java.lang.StringTemplate.STR;

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
    public FoodEmission save(FoodEmissionRequest request) {
        Food food = foodService.getFood(request.foodId());
        FoodEmission foodEmission = buildFoodEmission(request, food);
        return repository.save(foodEmission);
    }

    @Override
    public FoodEmission update(FoodEmissionRequest request, Long id) {
        FoodEmission entity = getFoodEmissionById(id)
                .orElseThrow(() -> getNoEntityFoundException(id));

        if (!Objects.equals(entity.getFood().getId(), request.foodId())) {
            Food food = foodService.getFood(request.foodId());
            entity.setFood(food);
        }

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

    private static NoEntityFoundException getNoEntityFoundException(Long id) {
        return new NoEntityFoundException(STR."FoodEmission not found with id \{id}");
    }

    private Optional<FoodEmission> getFoodEmissionById(Long id) {
        return repository.findById(id);
    }

    private FoodEmission buildFoodEmission(FoodEmissionRequest request, Food food) {
        return FoodEmission.builder()
                .food(food)
                .consumptionInKg(request.consumption())
                .build();
    }
}
