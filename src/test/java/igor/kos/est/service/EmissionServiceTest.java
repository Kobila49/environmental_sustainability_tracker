package igor.kos.est.service;

import igor.kos.est.dto.request.DailyEmissionRequest;
import igor.kos.est.dto.request.FoodEmissionRequest;
import igor.kos.est.dto.request.TransportationEmissionRequest;
import igor.kos.est.dto.request.UtilityEmissionRequest;
import igor.kos.est.dto.response.DailyEmissionResponse;
import igor.kos.est.entity.DailyEmission;
import igor.kos.est.entity.User;
import igor.kos.est.repository.DailyEmissionRepository;
import igor.kos.est.repository.UserRepository;
import igor.kos.est.service.implementation.EmissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static igor.kos.est.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class EmissionServiceTest {

    @Mock
    private FoodEmissionService foodEmissionService;

    @Mock
    private TransportationEmissionService transportationEmissionService;

    @Mock
    private UtilityEmissionService utilityEmissionService;

    @Mock
    private DailyEmissionRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private EmissionServiceImpl emissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testCreateOrUpdateDailyEmission() {
        User mockUser = getMockUser();
        mockUser.setId(1L);

        DailyEmissionRequest request = getMockDailyEmissionRequest();
        DailyEmission mockDailyEmission = getMockDailyEmission();

        mockSecurityContext(mockUser);
        mockRepositoryBehavior(mockDailyEmission);
        mockEmissionServiceBehavior();

        DailyEmissionResponse response = emissionService.createOrUpdateDailyEmission(request);

        assertDailyEmissionResponse(response);
        verify(repository, times(1)).findByUserIdAndEmissionDate(anyLong(), any(LocalDate.class));
        verify(repository, times(2)).save(any(DailyEmission.class));
    }

    @Test
    void getDailyEmissionForDate() {
        User mockUser = getMockUser();
        mockUser.setId(1L);

        DailyEmission mockDailyEmission = getMockDailyEmissionFull();

        mockSecurityContext(mockUser);
        when(repository.findByUserIdAndEmissionDate(anyLong(), any(LocalDate.class))).thenReturn(Optional.of(mockDailyEmission));

        DailyEmissionResponse response = emissionService.getDailyEmissionForDate(LocalDate.now());

        assertDailyEmissionResponse(response);
    }

    @Test
    void getDailyEmissions() {
        User mockUser = getMockUser();
        mockUser.setId(1L);

        DailyEmission mockDailyEmission = getMockDailyEmissionFull();

        mockSecurityContext(mockUser);
        when(repository.findAllByUserId(anyLong())).thenReturn(List.of(mockDailyEmission));

        List<DailyEmissionResponse> response = emissionService.getDailyEmissions();

        assertThat(response).hasSize(1);
        assertDailyEmissionResponse(response.getFirst());
    }

    private void mockSecurityContext(User mockUser) {
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
    }

    private void mockRepositoryBehavior(DailyEmission mockDailyEmission) {
        when(repository.findByUserIdAndEmissionDate(anyLong(), any(LocalDate.class))).thenReturn(Optional.of(mockDailyEmission));
        when(repository.save(any(DailyEmission.class))).thenReturn(mockDailyEmission);
    }

    private void mockEmissionServiceBehavior() {
        when(foodEmissionService.save(any(FoodEmissionRequest.class), any(DailyEmission.class))).thenReturn(getMockFoodEmission());
        when(transportationEmissionService.save(any(TransportationEmissionRequest.class), any(DailyEmission.class))).thenReturn(getMockTransportationEmission());
        when(utilityEmissionService.save(any(UtilityEmissionRequest.class), any(DailyEmission.class))).thenReturn(getMockUtilityEmission());
    }

    private void assertDailyEmissionResponse(DailyEmissionResponse response) {
        assertNotNull(response);
        assertThat(response.totalEmission()).isEqualTo(new BigDecimal("69.00"));
        assertThat(response.foodEmissionRecords()).hasSize(1);
        assertThat(response.transportationEmissionRecords()).hasSize(1);
        assertThat(response.utilityEmissionRecords()).hasSize(1);
        assertThat(response.transportationEmissionDailyTotal()).isEqualTo(new BigDecimal("44.00"));
        assertThat(response.foodEmissionDailyTotal()).isEqualTo(new BigDecimal("3.00"));
        assertThat(response.utilityEmissionDailyTotal()).isEqualTo(new BigDecimal("22.00"));
    }
}
