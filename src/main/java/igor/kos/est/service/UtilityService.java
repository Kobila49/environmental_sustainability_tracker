package igor.kos.est.service;

import igor.kos.est.dto.request.UtilityRequest;
import igor.kos.est.dto.response.UtilityResponse;

import java.util.List;

public interface UtilityService {

    List<UtilityResponse> findAll();

    UtilityResponse findById(Long id);

    UtilityResponse save(UtilityRequest utility);

    UtilityResponse update(UtilityRequest utility, Long id);

    void delete(Long id);

}
