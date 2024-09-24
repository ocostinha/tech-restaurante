package mocks;

import com.fiap.tech.restaurante.application.dto.RestaurantRequestDTO;

public class RestaurantRequestDTOMock {

	public static RestaurantRequestDTO mock(String restaurantName) {
		return new RestaurantRequestDTO(restaurantName, "Rua teste", 1, "Bairro Teste", "Teste", "TE", "Brasileira",
				"11:00", "22:00", "15:00", "18:00", 20);
	}

}
