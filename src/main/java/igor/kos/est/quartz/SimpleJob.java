package igor.kos.est.quartz;

import igor.kos.est.repository.DailyEmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class SimpleJob implements Job {

    private final DailyEmissionRepository dailyEmissionRepository;

    @Override
    public void execute(JobExecutionContext context) {
        log.info("Executing SimpleJob - {}", context.getFireTime());
        final var dailyEmissions = dailyEmissionRepository.findAll();

        log.info("Total daily emissions: {}", dailyEmissions.size());
        dailyEmissions.forEach(de -> log.info("Daily emission: {}", de));
    }
}
