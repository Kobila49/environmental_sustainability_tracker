package igor.kos.est.service.implementation;

import igor.kos.est.dto.request.TransportationRequest;
import igor.kos.est.dto.response.TransportationResponse;
import igor.kos.est.entity.Transportation;
import igor.kos.est.exceptions.NoEntityFoundException;
import igor.kos.est.repository.TransportationRepository;
import igor.kos.est.service.TransportationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransportationServiceImpl implements TransportationService {

    private final TransportationRepository repository;

    @Override
    public List<TransportationResponse> findAll() {
        log.info("Fetching all transportation records");
        return repository.findAll()
                .stream()
                .map(this::mapToTransportationResponse)
                .toList();
    }

    @Override
    public TransportationResponse findById(Long id) {
        log.info("Fetching transportation record with id: {}", id);
        var transportation = getTransportation(id);
        return mapToTransportationResponse(transportation);
    }

    @Override
    @Transactional
    public TransportationResponse saveTransportation(TransportationRequest request) {
        log.info("Saving new transportation record: {}", request);
        var transportation = buildTransportationFromRequest(request);
        transportation = repository.save(transportation);
        return mapToTransportationResponse(transportation);
    }

    @Override
    @Transactional
    public TransportationResponse updateTransportation(TransportationRequest request, Long id) {
        log.info("Updating transportation record with id: {}", id);
        var transportation = getTransportation(id);
        updateTransportationFromRequest(transportation, request);
        transportation = repository.save(transportation);
        return mapToTransportationResponse(transportation);
    }

    @Override
    @Transactional
    public void deleteTransportation(Long id) {
        log.info("Deleting transportation record with id: {}", id);
        if (!repository.existsById(id)) {
            throw new NoEntityFoundException(STR."Transportation not found with id: \{id}");
        }
        repository.deleteById(id);
    }

    private Transportation getTransportation(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException(STR."Transportation not found with id: \{id}"));
    }

    private Transportation buildTransportationFromRequest(TransportationRequest request) {
        return Transportation.builder()
                .name(request.name())
                .emissionFactor(request.emissionFactor())
                .build();
    }

    private void updateTransportationFromRequest(Transportation transportation, TransportationRequest request) {
        transportation.setName(request.name());
        transportation.setEmissionFactor(request.emissionFactor());
    }

    private TransportationResponse mapToTransportationResponse(Transportation transportation) {
        return new TransportationResponse(transportation.getId(), transportation.getName(), transportation.getEmissionFactor());
    }
}
