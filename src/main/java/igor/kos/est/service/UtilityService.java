package igor.kos.est.service;

import igor.kos.est.dto.request.UtilityRequest;
import igor.kos.est.dto.response.UtilityResponse;
import igor.kos.est.entity.Utility;

import java.util.List;

public interface UtilityService {

    List<UtilityResponse> findAll();

    UtilityResponse findById(Long id);

    UtilityResponse save(UtilityRequest request);

    UtilityResponse update(UtilityRequest request, Long id);

    void delete(Long id);

    Utility getUtility(Long id);
}
