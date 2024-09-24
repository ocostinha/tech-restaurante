package com.fiap.tech.restaurante.domain.useCase.availability;

import com.fiap.tech.restaurante.domain.exception.UnprocessableEntityException;
import com.fiap.tech.restaurante.domain.mappers.AvailableMapperImpl;
import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.infra.entity.AvailableEntity;
import com.fiap.tech.restaurante.infra.repository.AvailableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FindAvailabilityByDataAndHourUseCaseTest {

	@InjectMocks
	private FindAvailabilityByDataAndHourUseCase findAvailabilityByDataAndHourUseCase;

	@Mock
	private AvailableRepository availableRepository;

	@Mock
	private AvailableMapperImpl mapper;

	private Long restaurantId;

	private AvailableEntity available;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		restaurantId = 1L;
		available = new AvailableEntity(1L, restaurantId, LocalDate.now(), LocalTime.of(12, 0), 10, null, null);
	}

	@Test
	void shouldFindAvailabilityByRestaurantIdDateAndHour() {
		when(availableRepository.findByIdRestaurantAndDateAndHour(any(), any(), any()))
			.thenReturn(Optional.of(available));
		doCallRealMethod().when(mapper).toDomain(any(AvailableEntity.class));

		Available result = findAvailabilityByDataAndHourUseCase.execute(restaurantId, LocalDate.now(),
				LocalTime.of(12, 0));

		assertEquals(available.getIdRestaurant(), result.getIdRestaurant());
		verify(availableRepository).findByIdRestaurantAndDateAndHour(any(), any(), any());
	}

	@Test
	void shouldThrowExceptionWhenNoAvailabilityFound() {
		when(availableRepository.findByIdRestaurantAndDateAndHour(any(), any(), any())).thenReturn(Optional.empty());

		UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class,
				() -> findAvailabilityByDataAndHourUseCase.execute(restaurantId, LocalDate.now(), LocalTime.of(12, 0)));

		assertEquals("Nenhuma disponibilidade encontrada para a data e hora selecionadas", exception.getMessage());
	}

}
