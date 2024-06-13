package igor.kos.est.service;

import igor.kos.est.dto.request.TransportationEmissionRequest;
import igor.kos.est.entity.DailyEmission;
import igor.kos.est.entity.TransportationEmission;

public interface TransportationEmissionService {

    TransportationEmission findById(Long id);

    TransportationEmission save(TransportationEmissionRequest request);

    TransportationEmission save(TransportationEmissionRequest request, DailyEmission dailyEmission);

    TransportationEmission update(TransportationEmissionRequest request, Long id);

    void delete(Long id);

    TransportationEmissionRequest toRequest(TransportationEmission transportationEmission);

}
