package igor.kos.est.service;

import igor.kos.est.TestUtil;
import igor.kos.est.dto.response.FoodResponse;
import igor.kos.est.entity.Food;
import igor.kos.est.repository.FoodRepository;
import igor.kos.est.service.implementation.FoodServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class FoodServiceTest {

    @Mock
    private FoodRepository repository;

    @InjectMocks
    private FoodServiceImpl foodService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        final var foodList = List.of(TestUtil.getMockFood());
        final var expected = foodList.stream().map(f -> foodService.mapToFoodResponse(f)).toList();
        when(repository.findAll()).thenReturn(foodList);

        List<FoodResponse> result = foodService.findAll();

        assertThat(result).containsExactlyElementsOf(expected);
        assertThat(result.getFirst()).isEqualTo(expected.getFirst());
    }

    @Test
    void testGetFood() {
        Long id = 1L;
        Food mockFood = new Food();
        when(repository.findById(id)).thenReturn(Optional.of(mockFood));

        Food result = foodService.getFood(id);

        assertEquals(mockFood, result);
    }
}
