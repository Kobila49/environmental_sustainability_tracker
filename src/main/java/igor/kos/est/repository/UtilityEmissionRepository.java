package igor.kos.est.repository;

import igor.kos.est.entity.UtilityEmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilityEmissionRepository extends JpaRepository<UtilityEmission, Long> {
}
