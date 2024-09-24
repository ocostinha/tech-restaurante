package com.fiap.tech.restaurante.domain.useCase.availability;

import com.fiap.tech.restaurante.infra.entity.AvailableEntity;
import com.fiap.tech.restaurante.infra.entity.RestaurantEntity;
import com.fiap.tech.restaurante.infra.repository.AvailableRepository;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class CreateAvailabilityUseCase {

	private final RestaurantRepository restaurantRepository;

	private final AvailableRepository availableRepository;

	public void execute() {
		List<RestaurantEntity> restaurants = restaurantRepository.findAll();
		LocalDate reservationDate = LocalDate.now().plusDays(1);

		for (RestaurantEntity restaurant : restaurants) {
			int totalSeats = restaurant.getNumberOfSeats();
			int initialAvailability = (int) (totalSeats * 0.9);

			LocalTime openingTime = restaurant.getOpenAt();
			LocalTime closingTime = restaurant.getCloseAt();

			for (LocalTime time = openingTime; time.isBefore(closingTime); time = time.plusHours(1)) {
				if (time.isBefore(restaurant.getIntervalStart())
						|| time.isAfter(restaurant.getIntervalFinish().minusHours(1))) {
					AvailableEntity availableEntity = new AvailableEntity();

					availableEntity.setIdRestaurant(restaurant.getId());
					availableEntity.setDate(reservationDate);
					availableEntity.setHour(time);
					availableEntity.setAvailableSeats(initialAvailability);

					availableRepository.save(availableEntity);
				}
			}
		}
	}

}
