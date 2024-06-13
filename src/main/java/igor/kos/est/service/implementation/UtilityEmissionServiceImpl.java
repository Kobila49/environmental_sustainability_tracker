package igor.kos.est.service.implementation;

import igor.kos.est.dto.request.UtilityEmissionRequest;
import igor.kos.est.entity.DailyEmission;
import igor.kos.est.entity.Utility;
import igor.kos.est.entity.UtilityEmission;
import igor.kos.est.exceptions.NoEntityFoundException;
import igor.kos.est.repository.UtilityEmissionRepository;
import igor.kos.est.service.UtilityEmissionService;
import igor.kos.est.service.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilityEmissionServiceImpl implements UtilityEmissionService {

    private final UtilityEmissionRepository repository;
    private final UtilityService utilityService;

    @Override
    public UtilityEmission findById(Long id) {
        return getUtilityEmissionById(id).orElseThrow(() -> getNoEntityFoundException(id));
    }

    @Override
    public UtilityEmission save(UtilityEmissionRequest request, DailyEmission dailyEmission) {
        Utility utility = utilityService.getUtility(request.utilityId());
        UtilityEmission utilityEmission = buildUtilityEmission(request, utility);
        utilityEmission.setDailyEmission(dailyEmission);
        return repository.save(utilityEmission);
    }

    @Override
    public UtilityEmission update(UtilityEmissionRequest request, Long id) {
        UtilityEmission entity = getUtilityEmissionById(id).orElseThrow(() -> getNoEntityFoundException(id));
        Utility utility = utilityService.getUtility(request.utilityId());
        final var emission = request.consumption().multiply(utility.getEmissionFactor());
        entity.setUsage(request.consumption());
        entity.setUtilityId(utility.getId());
        entity.setEmissionFactor(utility.getEmissionFactor());
        entity.setEmission(emission);
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
    public UtilityEmissionRequest toRequest(UtilityEmission utilityEmission) {
        return new UtilityEmissionRequest(
                utilityEmission.getUtilityId(),
                utilityEmission.getUsage()
        );
    }

    private static NoEntityFoundException getNoEntityFoundException(Long id) {
        return new NoEntityFoundException(STR."UtilityEmission not found with id \{id}");
    }

    private Optional<UtilityEmission> getUtilityEmissionById(Long id) {
        return repository.findById(id);
    }

    private UtilityEmission buildUtilityEmission(UtilityEmissionRequest request, Utility utility) {
        final var emission = request.consumption().multiply(utility.getEmissionFactor());
        return UtilityEmission.builder()
                .emissionFactor(utility.getEmissionFactor())
                .emission(emission)
                .utilityId(utility.getId())
                .usage(request.consumption())
                .build();
    }

}
