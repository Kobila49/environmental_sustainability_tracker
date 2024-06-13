package igor.kos.est.repository;

import igor.kos.est.entity.DailyEmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyEmissionRepository extends JpaRepository<DailyEmission, Long> {
    Optional<DailyEmission> findByUserIdAndEmissionDate(Long userId, LocalDate emissionDate);

    List<DailyEmission> findAllByUserId(Long id);
}
