package igor.kos.est.service;

import igor.kos.est.dto.request.TransportationEmissionRequest;
import igor.kos.est.entity.TransportationEmission;

public interface UtilityEmissionService {

    TransportationEmission findById(Long id);

    TransportationEmission save(TransportationEmissionRequest request);

    TransportationEmission update(TransportationEmissionRequest foodEmission, Long id);

    void delete(Long id);
}
