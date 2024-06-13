package igor.kos.est.service;

import igor.kos.est.dto.request.FoodEmissionRequest;
import igor.kos.est.entity.DailyEmission;
import igor.kos.est.entity.FoodEmission;
import igor.kos.est.repository.FoodEmissionRepository;
import igor.kos.est.service.implementation.FoodEmissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static igor.kos.est.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class FoodEmissionServiceTest {

    @Mock
    private FoodEmissionRepository repository;

    @Mock
    private FoodService foodService;

    @InjectMocks
    private FoodEmissionServiceImpl foodEmissionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {

        FoodEmission mockFoodEmission = getMockFoodEmission();

        when(repository.findById(1L)).thenReturn(Optional.of(mockFoodEmission));

        FoodEmission result = foodEmissionService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSave() {
        FoodEmissionRequest request = getMockFoodEmissionRequest();
        DailyEmission dailyEmission = getMockDailyEmissionFull();
        FoodEmission mockFoodEmission = getMockFoodEmission();

        when(repository.save(any(FoodEmission.class))).thenReturn(mockFoodEmission);
        when(foodService.getFood(anyLong())).thenReturn(getMockFood());

        FoodEmission result = foodEmissionService.save(request, dailyEmission);

        assertNotNull(result);
        assertEquals(mockFoodEmission, result);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        FoodEmissionRequest request = getMockFoodEmissionRequest();
        FoodEmission mockFoodEmission = getMockFoodEmission();

        when(repository.findById(1L)).thenReturn(Optional.of(mockFoodEmission));
        when(repository.save(any(FoodEmission.class))).thenReturn(mockFoodEmission);
        when(foodService.getFood(anyLong())).thenReturn(getMockFood());

        FoodEmission result = foodEmissionService.update(request, id);

        assertNotNull(result);
        assertEquals(mockFoodEmission, result);
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(anyLong());

        foodEmissionService.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testToRequest() {
        FoodEmission foodEmission = getMockFoodEmission();
        FoodEmissionRequest mockRequest = getMockFoodEmissionRequest();

        FoodEmissionRequest result = foodEmissionService.toRequest(foodEmission);

        assertNotNull(result);
        assertEquals(mockRequest, result);
    }
}
