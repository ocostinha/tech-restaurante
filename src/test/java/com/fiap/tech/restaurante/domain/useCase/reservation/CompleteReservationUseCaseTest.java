package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CompleteReservationUseCaseTest {

	@InjectMocks
	private CompleteReservationUseCase completeReservationUseCase;

	@Mock
	private ReservationRepository reservationRepository;

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
			.reservationOwnerName("Test Owner")
			.reservationOwnerEmail("owner@test.com")
			.reservationDate(null)
			.reservationHour(null)
			.seatsReserved(4)
			.status(ReservationStatus.CONFIRMED)
			.build();

		reservation = Reservation.builder()
			.id(1L)
			.idRestaurant(1L)
			.reservationOwnerName("Test Owner")
			.reservationOwnerEmail("owner@test.com")
			.seatsReserved(4)
			.status(ReservationStatus.COMPLETED)
			.build();
	}

	@Test
	void shouldCompleteReservationSuccessfully() {
		when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservationEntity));
		when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);
		when(reservationMapper.toDomain(any(ReservationEntity.class))).thenReturn(reservation);

		Reservation result = completeReservationUseCase.execute(1L);

		assertEquals(ReservationStatus.COMPLETED, result.getStatus());
		verify(reservationRepository).findById(1L);
		verify(reservationRepository).save(reservationEntity);
		verify(reservationMapper).toDomain(reservationEntity);
	}

	@Test
	void shouldThrowBusinessExceptionWhenReservationNotFound() {
		when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

		BusinessException exception = assertThrows(BusinessException.class,
				() -> completeReservationUseCase.execute(1L));

		assertEquals("Reserva não encontrada", exception.getMessage());
		verify(reservationRepository).findById(1L);
		verify(reservationRepository, never()).save(any(ReservationEntity.class));
		verify(reservationMapper, never()).toDomain(any(ReservationEntity.class));
	}

	@Test
	void shouldThrowExceptionWhenReservationNotFound() {
		when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(BusinessException.class, () -> completeReservationUseCase.execute(1L));

		verify(reservationRepository).findById(1L);
		verify(reservationRepository, never()).save(any());
	}

}
