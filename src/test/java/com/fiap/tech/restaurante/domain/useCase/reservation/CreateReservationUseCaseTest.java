package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapperImpl;
import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.availability.FindAvailabilityByDataAndHourUseCase;
import com.fiap.tech.restaurante.domain.useCase.availability.UpdateAvailableUseCase;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.entity.RestaurantEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

class CreateReservationUseCaseTest {

	@InjectMocks
	private CreateReservationUseCase createReservationUseCase;

	@Mock
	private RestaurantRepository restaurantRepository;

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private FindAvailabilityByDataAndHourUseCase findAvailabilityByDataAndHourUseCase;

	@Mock
	private UpdateAvailableUseCase updateAvailableUseCase;

	@Mock
	private ReservationMapperImpl mapper;

	private Reservation reservationRequest;

	private ReservationEntity reservationEntity;

	private Available available;

	private RestaurantEntity restaurantEntity;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		reservationRequest = Reservation.builder()
			.idRestaurant(1L)
			.reservationOwnerName("Owner")
			.reservationOwnerEmail("owner@test.com")
			.reservationDate(LocalDate.now())
			.reservationHour(LocalTime.of(12, 0))
			.seatsReserved(4)
			.build();

		reservationEntity = ReservationEntity.builder()
			.id(1L)
			.idRestaurant(1L)
			.reservationOwnerName("Owner")
			.reservationOwnerEmail("owner@test.com")
			.reservationDate(LocalDate.now())
			.reservationHour(LocalTime.of(12, 0))
			.seatsReserved(4)
			.build();

		available = new Available(1L, 1L, null, null, 10, null, null);

		restaurantEntity = new RestaurantEntity();
		restaurantEntity.setId(1L);
		restaurantEntity.setName("Restaurant Test");
	}

	@Test
	void shouldCreateReservationSuccessfully() {
		when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantEntity));
		when(findAvailabilityByDataAndHourUseCase.execute(any(), any(), any())).thenReturn(available);
		doCallRealMethod().when(mapper).toEntity(any(Reservation.class));
		doCallRealMethod().when(mapper).toDomain(any(ReservationEntity.class));
		when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);

		Reservation result = createReservationUseCase.execute(reservationRequest);

		assertEquals(1L, result.getId());
		verify(restaurantRepository).findById(1L);
		verify(findAvailabilityByDataAndHourUseCase).execute(1L, LocalDate.now(), LocalTime.of(12, 0));
		verify(reservationRepository).save(mapper.toEntity(reservationRequest));
		verify(updateAvailableUseCase).execute(1L, -4, LocalDate.now(), LocalTime.of(12, 0));
	}

	@Test
	void shouldThrowBusinessExceptionWhenRestaurantNotFound() {
		when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

		BusinessException exception = assertThrows(BusinessException.class,
				() -> createReservationUseCase.execute(reservationRequest));

		assertEquals("Restaurante não encontrado", exception.getMessage());
		verify(restaurantRepository).findById(1L);
		verify(findAvailabilityByDataAndHourUseCase, never()).execute(anyLong(), any(LocalDate.class),
				any(LocalTime.class));
		verify(reservationRepository, never()).save(any(ReservationEntity.class));
		verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(LocalDate.class),
				any(LocalTime.class));
	}

	@Test
	void shouldThrowBusinessExceptionWhenNoSeatsAvailable() {
		available.setAvailableSeats(2); // Apenas 2 assentos disponíveis
		when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantEntity));
		when(findAvailabilityByDataAndHourUseCase.execute(anyLong(), any(LocalDate.class), any(LocalTime.class)))
			.thenReturn(available);

		BusinessException exception = assertThrows(BusinessException.class,
				() -> createReservationUseCase.execute(reservationRequest));

		assertEquals("Não há assentos disponíveis para o horário solicitado.", exception.getMessage());
		verify(restaurantRepository).findById(1L);
		verify(findAvailabilityByDataAndHourUseCase).execute(1L, LocalDate.now(), LocalTime.of(12, 0));
		verify(reservationRepository, never()).save(any(ReservationEntity.class));
		verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(LocalDate.class),
				any(LocalTime.class));
		when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurantEntity));
		when(findAvailabilityByDataAndHourUseCase.execute(anyLong(), any(), any())).thenReturn(available);
		when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);
		when(mapper.toEntity(any(Reservation.class))).thenReturn(reservationEntity);
		when(mapper.toDomain(any(ReservationEntity.class))).thenReturn(reservationRequest);
	}

	@Test
	void shouldThrowExceptionWhenRestaurantNotFound() {
		when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(BusinessException.class, () -> createReservationUseCase.execute(reservationRequest));

		verify(restaurantRepository).findById(1L);
		verify(findAvailabilityByDataAndHourUseCase, never()).execute(anyLong(), any(), any());
		verify(reservationRepository, never()).save(any(ReservationEntity.class));
		verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(), any());
	}

	@Test
	void shouldThrowExceptionWhenNoSeatsAvailable() {
		available.setAvailableSeats(2);
		when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurantEntity));
		when(findAvailabilityByDataAndHourUseCase.execute(anyLong(), any(), any())).thenReturn(available);

		assertThrows(BusinessException.class, () -> createReservationUseCase.execute(reservationRequest));

		verify(restaurantRepository).findById(1L);
		verify(findAvailabilityByDataAndHourUseCase).execute(anyLong(), any(), any());
		verify(reservationRepository, never()).save(any(ReservationEntity.class));
		verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(), any());
	}

}
