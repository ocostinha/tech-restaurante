package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.availability.UpdateAvailableUseCase;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.MockitoAnnotations.openMocks;

class CancelReservationUseCaseTest {

	@InjectMocks
	private CancelReservationUseCase cancelReservationUseCase;

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private UpdateAvailableUseCase updateAvailableUseCase;

	@Mock
	private ReservationMapper reservationMapper;

	private ReservationEntity reservationEntity;

	private Reservation reservation;

	@BeforeEach
	void setUp() {
		openMocks(this);

		reservationEntity = ReservationEntity.builder()
			.id(1L)
			.idRestaurant(1L)
			.seatsReserved(4)
			.status(ReservationStatus.CONFIRMED)
			.reservationDate(LocalDate.now())
			.reservationHour(LocalTime.of(12, 0))
			.build();

		reservation = new Reservation(1L, 1L, "Test Test", "test@teste.com", reservationEntity.getReservationDate(),
				reservationEntity.getReservationHour(), reservationEntity.getSeatsReserved(),
				ReservationStatus.CANCELED, null, null);

		reservation = Reservation.builder()
			.id(1L)
			.idRestaurant(1L)
			.reservationOwnerName("Test Owner")
			.reservationOwnerEmail("owner@test.com")
			.seatsReserved(4)
			.status(ReservationStatus.CANCELED)
			.build();
	}

	@Test
	void shouldCancelReservationSuccessfully() {
		when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservationEntity));
		when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);
		when(reservationMapper.toDomain(any(ReservationEntity.class))).thenReturn(reservation);

		Reservation result = cancelReservationUseCase.execute(1L);

		assertEquals(ReservationStatus.CANCELED, result.getStatus());
		verify(reservationRepository).findById(1L);
		verify(reservationRepository).save(reservationEntity);
		verify(updateAvailableUseCase).execute(reservationEntity.getIdRestaurant(),
				reservationEntity.getSeatsReserved(), reservationEntity.getReservationDate(),
				reservationEntity.getReservationHour());
	}

	@Test
	void shouldThrowResourceNotFoundExceptionWhenReservationDoesNotExist() {
		when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> cancelReservationUseCase.execute(1L));

		assertEquals("Reserva não encontrada", exception.getMessage());
		verify(reservationRepository).findById(1L);
		verify(reservationRepository, never()).save(any(ReservationEntity.class));
		verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(LocalDate.class),
				any(LocalTime.class));
	}

	@Test
	void shouldThrowExceptionWhenReservationNotFound() {
		when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> cancelReservationUseCase.execute(1L));

		verify(reservationRepository).findById(1L);
		verify(reservationRepository, never()).save(any());
		verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(), any());
	}

}
