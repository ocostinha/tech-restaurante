package mocks;

import com.fiap.tech.restaurante.application.dto.ReservationResponseDTO;
import com.fiap.tech.restaurante.domain.enums.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationResponseDTOMock {

	public static ReservationResponseDTO mock() {
		return new ReservationResponseDTO(1L, ReservationStatus.CONFIRMED.name(), 5, "Cliente", "cliente@email.com.br",
				LocalDate.now(), LocalTime.now(), LocalDateTime.now(), LocalDateTime.now());
	}

}
