package com.fiap.tech.restaurante.scheduler;

import com.fiap.tech.restaurante.infra.entity.AvailableEntity;
import com.fiap.tech.restaurante.infra.entity.RestaurantEntity;
import com.fiap.tech.restaurante.infra.repository.AvailableRepository;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class AvailabilityScheduler {

    private final RestaurantRepository restaurantRepository;
    private final AvailableRepository availableRepository;

    public AvailabilityScheduler(RestaurantRepository restaurantRepository, AvailableRepository availableRepository) {
        this.restaurantRepository = restaurantRepository;
        this.availableRepository = availableRepository;
    }

    @Scheduled(cron = "0 0 4 * * ?")
    public void generateDailyAvailability() {
        List<RestaurantEntity> restaurants = restaurantRepository.findAll();
        LocalDate currentDate = LocalDate.now();

        for (RestaurantEntity restaurant : restaurants) {
            int totalSeats = restaurant.getNumberOfSeats();
            int initialAvailability = (int) (totalSeats * 0.9);

            LocalTime openingTime = restaurant.getOpenAt();
            LocalTime closingTime = restaurant.getCloseAt();

            for (LocalTime time = openingTime; time.isBefore(closingTime); time = time.plusHours(1)) {
                AvailableEntity availableEntity = new AvailableEntity();
                availableEntity.setIdRestaurant(restaurant.getId());
                availableEntity.setDate(currentDate);
                availableEntity.setHour(time);
                availableEntity.setAvailableSeats(initialAvailability);

                availableRepository.save(availableEntity);
            }
        }
    }
}
