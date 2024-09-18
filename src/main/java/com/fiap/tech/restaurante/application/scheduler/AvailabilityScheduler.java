package com.fiap.tech.restaurante.application.scheduler;

import com.fiap.tech.restaurante.domain.useCase.availability.CreateAvailabilityUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AvailabilityScheduler {

    private final CreateAvailabilityUseCase createAvailabilityUseCase;

    @Scheduled(cron = "0 0 4 * * ?")
    public void generateDailyAvailability() {
        createAvailabilityUseCase.execute();
    }
}
