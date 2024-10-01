package mocks;

import com.fiap.tech.restaurante.application.dto.RestaurantRequestDTO;
import com.fiap.tech.restaurante.domain.model.Restaurant;
import com.fiap.tech.restaurante.infra.entity.RestaurantEntity;

import java.time.LocalTime;

public class RestaurantMock {

	public static Restaurant mockDomain() {
		return Restaurant.builder()
			.id(1L)
			.name("Restaurante Teste")
			.address("Rua teste")
			.number(123)
			.neighborhood("Brás")
			.city("São Paulo")
			.state("SP")
			.openAt(LocalTime.of(11, 0))
			.closeAt(LocalTime.of(23, 0))
			.cuisineType("Italiana")
			.intervalStart(LocalTime.of(14, 0))
			.intervalFinish(LocalTime.of(18, 0))
			.numberOfSeats(10)
			.build();
	}

	public static RestaurantEntity mockEntity() {
		return RestaurantEntity.builder()
			.id(1L)
			.name("Restaurante Teste")
			.address("Rua teste")
			.number(123)
			.neighborhood("Brás")
			.city("São Paulo")
			.state("SP")
			.openAt(LocalTime.of(11, 0))
			.closeAt(LocalTime.of(23, 0))
			.cuisineType("Italiana")
			.intervalStart(LocalTime.of(14, 0))
			.intervalFinish(LocalTime.of(18, 0))
			.numberOfSeats(10)
			.build();
	}

	public static RestaurantRequestDTO mockRequest(String name) {
		return RestaurantRequestDTO.builder()
			.name(name)
			.address("Rua teste")
			.number(123)
			.neighborhood("Brás")
			.city("São Paulo")
			.state("SP")
			.openAt(LocalTime.of(11, 0).toString())
			.closeAt(LocalTime.of(23, 0).toString())
			.cuisineType("Italiana")
			.intervalStart(LocalTime.of(14, 0).toString())
			.intervalFinish(LocalTime.of(18, 0).toString())
			.numberOfSeats(10)
			.build();
	}

}
