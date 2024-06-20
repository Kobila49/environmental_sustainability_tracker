package igor.kos.est.service.implementation;

import igor.kos.est.dto.request.TransportationEmissionRequest;
import igor.kos.est.entity.DailyEmission;
import igor.kos.est.entity.Transportation;
import igor.kos.est.entity.TransportationEmission;
import igor.kos.est.exceptions.NoEntityFoundException;
import igor.kos.est.repository.TransportationEmissionRepository;
import igor.kos.est.service.TransportationEmissionService;
import igor.kos.est.service.TransportationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class TransportationEmissionServiceImpl implements TransportationEmissionService {

    private final TransportationEmissionRepository repository;
    private final TransportationService transportationService;

    @Override
    public TransportationEmission findById(Long id) {
        return getTransportationEmissionById(id).orElseThrow(() -> getNoEntityFoundException(id));
    }

    @Override
    public TransportationEmission save(TransportationEmissionRequest request, DailyEmission dailyEmission) {
        Transportation transportation = transportationService.getTransportation(request.transportationId());
        TransportationEmission transportationEmission = buildTransportationEmission(request, transportation);
        transportationEmission.setDailyEmission(dailyEmission);
        return repository.save(transportationEmission);
    }

    @Override
    public TransportationEmission update(TransportationEmissionRequest request, Long id) {
        TransportationEmission entity = getTransportationEmissionById(id)
                .orElseThrow(() -> getNoEntityFoundException(id));

        Transportation transportation = transportationService.getTransportation(request.transportationId());
        final var emission = request.distance().multiply(transportation.getEmissionFactor());
        entity.setEmission(emission);
        entity.setTransportationId(transportation.getId());
        entity.setEmissionFactor(transportation.getEmissionFactor());
        entity.setDistanceTravelled(request.distance());
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
    public TransportationEmissionRequest toRequest(TransportationEmission transportationEmission) {
        return new TransportationEmissionRequest(
                transportationEmission.getTransportationId(),
                transportationEmission.getDistanceTravelled()
        );
    }

    private TransportationEmission buildTransportationEmission(TransportationEmissionRequest request, Transportation transportation) {
        final var emission = request.distance().multiply(transportation.getEmissionFactor());
        return TransportationEmission.builder()
                .emissionFactor(transportation.getEmissionFactor())
                .emission(emission)
                .transportationId(transportation.getId())
                .distanceTravelled(request.distance())
                .build();
    }

    private Optional<TransportationEmission> getTransportationEmissionById(Long id) {
        return repository.findById(id);
    }

    private static NoEntityFoundException getNoEntityFoundException(Long id) {
        return new NoEntityFoundException(format("UtilityEmission not found with id {0}", id));
    }
}
