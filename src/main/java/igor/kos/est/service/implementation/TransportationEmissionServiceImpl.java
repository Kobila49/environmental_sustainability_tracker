package igor.kos.est.service.implementation;

import igor.kos.est.dto.request.TransportationEmissionRequest;
import igor.kos.est.entity.TransportationEmission;
import igor.kos.est.service.TransportationEmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransportationEmissionServiceImpl implements TransportationEmissionService {
    @Override
    public TransportationEmission findById(Long id) {
        return null;
    }

    @Override
    public TransportationEmission save(TransportationEmissionRequest request) {
        return null;
    }

    @Override
    public TransportationEmission update(TransportationEmissionRequest foodEmission, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
