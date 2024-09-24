package com.fiap.tech.restaurante.application.controller;

import com.fiap.tech.restaurante.application.dto.ReservationRequestDTO;
import com.fiap.tech.restaurante.application.dto.ReservationResponseDTO;
import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapperImpl;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.reservation.FindReservationByIdUseCase;
import com.fiap.tech.restaurante.domain.useCase.reservation.UpdateReservationUseCase;
import mocks.ReservationResponseDTOMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservationControllerTest {

	@InjectMocks
	private ReservationController reservationController;

	@Mock
	private UpdateReservationUseCase updateReservationUseCase;

	@Mock
	private ReservationMapperImpl reservationMapper;

	@Mock
	private FindReservationByIdUseCase findReservationByIdUseCase;

	private Reservation reservation;

	private ReservationResponseDTO responseReservationDTO;

	private ReservationRequestDTO requestReservationDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		Long restaurantId = 1L;
		Long reservationId = 1L;
		String ownerName = "nome teste";
		String ownerEmail = "test@test.com";
		LocalDate reservationDate = LocalDate.now();
		LocalTime reservationHour = LocalTime.of(12, 0);
		int numberOfSeats = 5;
		ReservationStatus status = ReservationStatus.CONFIRMED;

		requestReservationDTO = new ReservationRequestDTO(restaurantId, ownerName, ownerEmail, reservationDate, "12:00",
				numberOfSeats);

		reservation = new Reservation(reservationId, restaurantId, ownerName, ownerEmail, reservationDate,
				reservationHour, numberOfSeats, status, LocalDateTime.now(), LocalDateTime.now());

		responseReservationDTO = ReservationResponseDTOMock.mock();
	}

	@Test
	void shouldUpdateReservationSuccessfully() {
		when(updateReservationUseCase.execute(anyLong(), any(Reservation.class))).thenReturn(reservation);
		doCallRealMethod().when(reservationMapper).toResponseDTO(any(Reservation.class));
		doCallRealMethod().when(reservationMapper).toDomain(any(ReservationRequestDTO.class));

		ReservationResponseDTO result = reservationController.updateReservation(1L, requestReservationDTO);

		assertEquals(responseReservationDTO.getId(), result.getId());

		verify(updateReservationUseCase).execute(any(), any(Reservation.class));
		verify(reservationMapper).toResponseDTO(any(Reservation.class));
	}

	@Test
	void findReservationById_ShouldReturnResponseReservationDTO_WhenReservationExists() {
		when(findReservationByIdUseCase.execute(1L)).thenReturn(reservation);
		when(reservationMapper.toResponseDTO(reservation)).thenReturn(responseReservationDTO);

		ReservationResponseDTO result = reservationController.findReservationById(1L);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		verify(findReservationByIdUseCase, times(1)).execute(1L);
		verify(reservationMapper, times(1)).toResponseDTO(reservation);
	}

	@Test
	void findReservationById_ShouldThrowResponseStatusException_WhenReservationDoesNotExist() {
		when(findReservationByIdUseCase.execute(2L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
				() -> reservationController.findReservationById(2L));

		assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
		verify(findReservationByIdUseCase, times(1)).execute(2L);
		verify(reservationMapper, never()).toResponseDTO(any());
	}

}