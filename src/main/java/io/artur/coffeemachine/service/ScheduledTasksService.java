package io.artur.coffeemachine.service;

import io.artur.coffeemachine.repository.DrinkStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ScheduledTasksService {

    private final DrinkStatisticsRepository drinkStatisticsRepository;

    /**
     * Scheduled task to clean up old drink statistics records that are older than 5 years.
     *
     * <p>This method is executed daily at midnight as defined by the cron expression {@code "0 0 0 * * ?"}.
     * It calculates the timestamp representing the time 5 years ago from the current moment
     * and deletes all drink statistics entries created before that timestamp.</p>
     *
     * <p>This helps manage the size of the statistics data, ensuring that only relevant
     * records within the last 5 years are retained, as per the non-functional requirement
     * to store statistics for up to 5 years.</p>
     *
     * <p><b>Cron schedule:</b> The task runs every day at 00:00 (midnight).</p>
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void cleanUpOldStatistics() {
        Instant fiveYearsAgoInstant = Instant.now().minusSeconds(5L * 365 * 24 * 60 * 60);
        Timestamp fiveYearsAgoTimestamp = Timestamp.from(fiveYearsAgoInstant);
        drinkStatisticsRepository.deleteAllByCreatedAtBefore(fiveYearsAgoTimestamp);
    }
}
