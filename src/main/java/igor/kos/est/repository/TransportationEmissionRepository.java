package igor.kos.est.repository;

import igor.kos.est.entity.TransportationEmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportationEmissionRepository extends JpaRepository<TransportationEmission, Long>{
}
