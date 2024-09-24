package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.domain.exception.UnprocessableEntityException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import com.fiap.tech.restaurante.domain.useCase.availability.FindAvailabilityByDataAndHourUseCase;
import com.fiap.tech.restaurante.domain.useCase.availability.UpdateAvailableUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UpdateReservationUseCaseTest {

	@InjectMocks
	private UpdateReservationUseCase updateReservationUseCase;

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private FindAvailabilityByDataAndHourUseCase findAvailabilityByDataAndHourUseCase;

	@Mock
	private UpdateAvailableUseCase updateAvailableUseCase;

	@Mock
	private ReservationMapper reservationMapper;

	private ReservationEntity existingReservationEntity;

	private Reservation reservationUpdateRequest;

	private Reservation updatedReservation;

	private Available availability;

	@BeforeEach
	void setUp() {
		openMocks(this);

		existingReservationEntity = ReservationEntity.builder()
			.id(1L)
			.idRestaurant(1L)
			.seatsReserved(4)
			.reservationDate(LocalDate.now())
			.reservationHour(LocalTime.of(12, 0))
			.status(ReservationStatus.CONFIRMED)
			.build();

		reservationUpdateRequest = new Reservation(1L, 1L, "Test Test", "test@test.com", LocalDate.now(),
				LocalTime.of(12, 0), 6, ReservationStatus.CONFIRMED, null, null);

		updatedReservation = new Reservation(1L, 1L, "Test Test", "test@test.com", LocalDate.now(), LocalTime.of(12, 0),
				6, ReservationStatus.CONFIRMED, null, null);

		availability = new Available(1L, 1L, LocalDate.now(), LocalTime.of(12, 0), 10, LocalDateTime.now(),
				LocalDateTime.now());
	}

	@Test
	void shouldUpdateReservationSuccessfully() {
		when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(existingReservationEntity));
		when(findAvailabilityByDataAndHourUseCase.execute(anyLong(), any(LocalDate.class), any(LocalTime.class)))
			.thenReturn(availability);
		when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(existingReservationEntity);
		when(reservationMapper.toDomain(any(ReservationEntity.class))).thenReturn(updatedReservation);
		when(reservationMapper.update(any(Reservation.class), any(ReservationEntity.class)))
			.thenReturn(existingReservationEntity);

		Reservation result = updateReservationUseCase.execute(1L, reservationUpdateRequest);

		assertEquals(6, result.getSeatsReserved());
		verify(reservationRepository).findById(1L);
		verify(findAvailabilityByDataAndHourUseCase).execute(1L, LocalDate.now(), LocalTime.of(12, 0));
		verify(updateAvailableUseCase).execute(1L, -2, LocalDate.now(), LocalTime.of(12, 0)); // Diferença
																								// de
																								// assentos
																								// reservados
		verify(reservationRepository).save(existingReservationEntity);
		verify(reservationMapper).toDomain(existingReservationEntity);
	}

	@Test
	void shouldThrowBusinessExceptionWhenReservationStatusIsNotConfirmed() {
		existingReservationEntity.setStatus(ReservationStatus.CANCELED);
		when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(existingReservationEntity));

		BusinessException exception = assertThrows(BusinessException.class,
				() -> updateReservationUseCase.execute(1L, reservationUpdateRequest));

		assertEquals("Reservas finalizadas ou canceladas não podem ser alteradas.", exception.getMessage());
		verify(reservationRepository).findById(1L);
		verify(findAvailabilityByDataAndHourUseCase, never()).execute(anyLong(), any(LocalDate.class),
				any(LocalTime.class));
		verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(LocalDate.class),
				any(LocalTime.class));
		verify(reservationRepository, never()).save(any(ReservationEntity.class));
		verify(reservationMapper, never()).toDomain(any(ReservationEntity.class));
	}

	@Test
	void shouldThrowUnprocessableEntityExceptionWhenSeatsAreNotAvailable() {
		availability.setAvailableSeats(1);
		when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(existingReservationEntity));
		when(findAvailabilityByDataAndHourUseCase.execute(anyLong(), any(LocalDate.class), any(LocalTime.class)))
			.thenReturn(availability);

		UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class,
				() -> updateReservationUseCase.execute(1L, reservationUpdateRequest));

		assertEquals("Não há assentos suficientes disponíveis", exception.getMessage());
		verify(reservationRepository).findById(1L);
		verify(findAvailabilityByDataAndHourUseCase).execute(1L, LocalDate.now(), LocalTime.of(12, 0));
		verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(LocalDate.class),
				any(LocalTime.class));
		verify(reservationRepository, never()).save(any(ReservationEntity.class));
		verify(reservationMapper, never()).toDomain(any(ReservationEntity.class));
	}

	@Test
	void shouldThrowUnprocessableEntityExceptionWhenReservationNotFound() {
		when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

		UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class,
				() -> updateReservationUseCase.execute(1L, reservationUpdateRequest));

		assertEquals("Reserva não encontrada", exception.getMessage());
		verify(reservationRepository).findById(1L);
		verify(findAvailabilityByDataAndHourUseCase, never()).execute(anyLong(), any(LocalDate.class),
				any(LocalTime.class));
		verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(LocalDate.class),
				any(LocalTime.class));
		verify(reservationRepository, never()).save(any(ReservationEntity.class));
		verify(reservationMapper, never()).toDomain(any(ReservationEntity.class));
	}

}
