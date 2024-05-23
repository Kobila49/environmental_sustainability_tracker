package igor.kos.est.service.implementation;

import igor.kos.est.dto.request.FoodRequest;
import igor.kos.est.dto.response.FoodResponse;
import igor.kos.est.entity.Food;
import igor.kos.est.exceptions.NoEntityFoundException;
import igor.kos.est.repository.FoodRepository;
import igor.kos.est.service.FoodService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class FoodServiceImpl implements FoodService {

    private final FoodRepository repository;

    @Override
    public List<FoodResponse> findAll() {
        log.info("Fetching all foods");
        return repository.findAll()
                .stream()
                .map(this::mapToFoodResponse)
                .toList();
    }

    @Override
    public FoodResponse findById(Long id) {
        log.info("Fetching food with id: {}", id);
        final var food = getFood(id);
        return mapToFoodResponse(food);
    }

    @Override
    @Transactional
    public FoodResponse save(FoodRequest foodRequest) {
        log.info("Saving new food: {}", foodRequest);
        var food = buildFoodFromRequest(foodRequest);
        food = repository.save(food);
        return mapToFoodResponse(food);
    }

    @Override
    @Transactional
    public FoodResponse update(FoodRequest foodRequest, Long id) {
        log.info("Updating food with id: {}", id);
        var food = getFood(id);
        updateFoodFromRequest(food, foodRequest);
        food = repository.save(food);
        return mapToFoodResponse(food);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting food with id: {}", id);
        if (!repository.existsById(id)) {
            throw new NoEntityFoundException(STR."Food not found with id: \{id}");
        }
        repository.deleteById(id);
    }

    @Override
    public Food getFood(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException(STR."Food not found with id: \{id}"));
    }

    private Food buildFoodFromRequest(FoodRequest foodRequest) {
        return Food.builder()
                .name(foodRequest.name())
                .emissionFactor(foodRequest.emissionFactor())
                .category(foodRequest.category())
                .build();
    }

    private void updateFoodFromRequest(Food food, FoodRequest foodRequest) {
        food.setName(foodRequest.name());
        food.setEmissionFactor(foodRequest.emissionFactor());
        food.setCategory(foodRequest.category());
    }

    private FoodResponse mapToFoodResponse(Food food) {
        return new FoodResponse(food.getId(), food.getName(), food.getEmissionFactor(), food.getCategory());
    }
}
