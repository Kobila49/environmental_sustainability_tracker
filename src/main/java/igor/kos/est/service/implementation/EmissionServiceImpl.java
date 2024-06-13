package igor.kos.est.service.implementation;

import igor.kos.est.dto.request.DailyEmissionRequest;
import igor.kos.est.dto.request.FoodEmissionRequest;
import igor.kos.est.dto.request.TransportationEmissionRequest;
import igor.kos.est.dto.request.UtilityEmissionRequest;
import igor.kos.est.dto.response.DailyEmissionResponse;
import igor.kos.est.entity.*;
import igor.kos.est.exceptions.NoEntityFoundException;
import igor.kos.est.repository.DailyEmissionRepository;
import igor.kos.est.repository.UserRepository;
import igor.kos.est.service.EmissionService;
import igor.kos.est.service.FoodEmissionService;
import igor.kos.est.service.TransportationEmissionService;
import igor.kos.est.service.UtilityEmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmissionServiceImpl implements EmissionService {

    private final FoodEmissionService foodEmissionService;
    private final TransportationEmissionService transportationEmissionService;
    private final UtilityEmissionService utilityEmissionService;
    private final DailyEmissionRepository repository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public DailyEmissionResponse createOrUpdateDailyEmission(DailyEmissionRequest request) {
        User user = getCurrentUser();

        Optional<DailyEmission> existingDailyEmission = repository.findByUserIdAndEmissionDate(
                user.getId(), request.date());

        DailyEmission dailyEmission;
        if (existingDailyEmission.isPresent()) {
            dailyEmission = existingDailyEmission.get();
            updateDailyEmission(dailyEmission, request);
        } else {
            dailyEmission = createDailyEmission(request, user);
        }

        return createDailyEmissionResponse(dailyEmission);
    }

    @Override
    public DailyEmissionResponse getDailyEmissionForDate(LocalDate date) {
        User user = getCurrentUser();

        DailyEmission dailyEmission = repository.findByUserIdAndEmissionDate(user.getId(), date)
                .orElseThrow(() -> new NoEntityFoundException(STR."Daily emission not found for date: \{date}"));

        return createDailyEmissionResponse(dailyEmission);
    }

    @Override
    public List<DailyEmissionResponse> getDailyEmissions() {
        User user = getCurrentUser();
        return repository.findAllByUserId(user.getId()).stream()
                .map(this::createDailyEmissionResponse)
                .toList();
    }

    private void updateDailyEmission(DailyEmission dailyEmission, DailyEmissionRequest request) {
        dailyEmission.getFoodEmissions().clear();
        dailyEmission.getTransportationEmissions().clear();
        dailyEmission.getUtilityEmissions().clear();

        dailyEmission = repository.save(dailyEmission);

        processEmissions(dailyEmission, request);

        repository.save(dailyEmission);
    }

    private DailyEmission createDailyEmission(DailyEmissionRequest request, User user) {
        DailyEmission dailyEmission = new DailyEmission();
        dailyEmission.setEmissionDate(request.date());
        dailyEmission.setUser(user);

        dailyEmission = repository.save(dailyEmission);

        processEmissions(dailyEmission, request);

        return repository.save(dailyEmission);
    }

    private void processEmissions(DailyEmission dailyEmission, DailyEmissionRequest request) {
        List<FoodEmission> foodEmissions = request.foodEmissionRecords().stream()
                .map(req -> saveFoodEmission(req, dailyEmission))
                .toList();
        dailyEmission.getFoodEmissions().addAll(foodEmissions);

        List<TransportationEmission> transportationEmissions = request.transportationEmissionRecords().stream()
                .map(req -> saveTransportationEmission(req, dailyEmission))
                .toList();
        dailyEmission.getTransportationEmissions().addAll(transportationEmissions);

        List<UtilityEmission> utilityEmissions = request.utilityEmissionRecords().stream()
                .map(req -> saveUtilityEmission(req, dailyEmission))
                .toList();
        dailyEmission.getUtilityEmissions().addAll(utilityEmissions);

        BigDecimal totalEmission = foodEmissions.stream()
                .map(FoodEmission::getEmission)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(transportationEmissions.stream()
                        .map(TransportationEmission::getEmission)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .add(utilityEmissions.stream()
                        .map(UtilityEmission::getEmission)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .setScale(2, RoundingMode.HALF_UP);
        dailyEmission.setTotalEmission(totalEmission);
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new NoEntityFoundException(STR."User not found with email: \{username}"));
    }

    private FoodEmission saveFoodEmission(FoodEmissionRequest request, DailyEmission dailyEmission) {
        return foodEmissionService.save(request, dailyEmission);
    }

    private TransportationEmission saveTransportationEmission(TransportationEmissionRequest request, DailyEmission dailyEmission) {
        return transportationEmissionService.save(request, dailyEmission);
    }

    private UtilityEmission saveUtilityEmission(UtilityEmissionRequest request, DailyEmission dailyEmission) {
        return utilityEmissionService.save(request, dailyEmission);
    }

    private DailyEmissionResponse createDailyEmissionResponse(DailyEmission dailyEmission) {
        return new DailyEmissionResponse(
                dailyEmission.getId(),
                dailyEmission.getEmissionDate().toString(),
                dailyEmission.getTransportationEmissions().stream()
                        .map(transportationEmissionService::toRequest)
                        .toList(),
                dailyEmission.getTransportationEmissions().stream()
                        .map(TransportationEmission::getEmission)
                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                dailyEmission.getFoodEmissions().stream()
                        .map(foodEmissionService::toRequest)
                        .toList(),
                dailyEmission.getFoodEmissions().stream()
                        .map(FoodEmission::getEmission)
                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                dailyEmission.getUtilityEmissions().stream()
                        .map(utilityEmissionService::toRequest)
                        .toList(),
                dailyEmission.getUtilityEmissions().stream()
                        .map(UtilityEmission::getEmission)
                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                dailyEmission.getTotalEmission()
        );
    }
}
