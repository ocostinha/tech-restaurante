package mocks;

import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.infra.entity.AvailableEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AvailableMock {

	public static Available mockDomain() {
		return Available.builder()
			.id(1L)
			.idRestaurant(1L)
			.availableSeats(10)
			.date(LocalDate.now())
			.hour(LocalTime.of(12, 0))
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}

	public static AvailableEntity mockEntity() {
		return AvailableEntity.builder()
			.id(1L)
			.idRestaurant(1L)
			.availableSeats(10)
			.date(LocalDate.now())
			.hour(LocalTime.of(12, 0))
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}

}
