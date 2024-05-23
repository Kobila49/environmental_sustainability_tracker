package igor.kos.est.repository;

import igor.kos.est.entity.FoodEmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodEmissionRepository extends JpaRepository<FoodEmission, Long> {
}
