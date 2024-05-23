package igor.kos.est.service;

import igor.kos.est.dto.request.TransportationRequest;
import igor.kos.est.dto.response.TransportationResponse;

import java.util.List;

public interface TransportationService {

    List<TransportationResponse> findAll();

    TransportationResponse findById(Long id);

    TransportationResponse saveTransportation(TransportationRequest transportation);

    TransportationResponse updateTransportation(TransportationRequest transportation, Long id);

    void deleteTransportation(Long id);
}
