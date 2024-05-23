package igor.kos.est.service.implementation;

import igor.kos.est.dto.request.UtilityRequest;
import igor.kos.est.dto.response.UtilityResponse;
import igor.kos.est.entity.Utility;
import igor.kos.est.exceptions.NoEntityFoundException;
import igor.kos.est.repository.UtilityRepository;
import igor.kos.est.service.UtilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UtilityServiceImpl implements UtilityService {

    private final UtilityRepository repository;

    @Override
    public List<UtilityResponse> findAll() {
        log.info("Fetching all utilities");
        return repository.findAll()
                .stream()
                .map(this::mapToUtilityResponse)
                .toList();
    }

    @Override
    public UtilityResponse findById(Long id) {
        log.info("Fetching utility with id: {}", id);
        var utility = getUtility(id);
        return mapToUtilityResponse(utility);
    }

    @Override
    public UtilityResponse save(UtilityRequest request) {
        log.info("Saving new utility: {}", request);
        var utility = buildUtilityFromRequest(request);
        utility = repository.save(utility);
        return mapToUtilityResponse(utility);
    }

    @Override
    public UtilityResponse update(UtilityRequest request, Long id) {
        log.info("Updating utility with id: {}", id);
        var utility = getUtility(id);
        updateUtilityFromRequest(utility, request);
        utility = repository.save(utility);
        return mapToUtilityResponse(utility);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting utility with id: {}", id);
        if (!repository.existsById(id)) {
            throw new NoEntityFoundException(STR."Utility not found with id: \{id}");
        }
        repository.deleteById(id);
    }

    private Utility getUtility(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException(STR."Utility not found with id: \{id}"));
    }

    private Utility buildUtilityFromRequest(UtilityRequest request) {
        return Utility.builder()
                .emissionFactor(request.emissionFactor())
                .type(request.type())
                .measurementUnit(request.measurementUnit())
                .build();
    }

    private void updateUtilityFromRequest(Utility utility, UtilityRequest request) {
        utility.setEmissionFactor(request.emissionFactor());
        utility.setType(request.type());
        utility.setMeasurementUnit(request.measurementUnit());
    }

    private UtilityResponse mapToUtilityResponse(Utility utility) {
        return new UtilityResponse(
                utility.getId(),
                utility.getEmissionFactor(),
                utility.getType(),
                utility.getMeasurementUnit()
        );
    }
}
