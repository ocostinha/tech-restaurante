package com.fiap.tech.restaurante.domain.useCase.availability;

import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.infra.entity.AvailableEntity;
import com.fiap.tech.restaurante.infra.repository.AvailableRepository;
import mocks.AvailableMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateAvailableUseCaseTest {

	@Mock
	private AvailableRepository availableRepository;

	@InjectMocks
	private UpdateAvailableUseCase updateAvailableUseCase;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldUpdateAvailability() {
		AvailableEntity entity = AvailableMock.mockEntity();
		Available domain = AvailableMock.mockDomain();
		when(availableRepository.findByIdRestaurantAndDateAndHour(domain.getIdRestaurant(), domain.getDate(),
				domain.getHour()))
			.thenReturn(Optional.of(entity));

		updateAvailableUseCase.execute(domain.getIdRestaurant(), 1, domain.getDate(), domain.getHour());

		verify(availableRepository, times(1)).save(any(AvailableEntity.class));
	}

	@Test
	void shouldThrowExceptionOnUpdateAvailability() {
		Available domain = AvailableMock.mockDomain();
		when(availableRepository.findByIdRestaurantAndDateAndHour(domain.getIdRestaurant(), domain.getDate(),
				domain.getHour()))
			.thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> updateAvailableUseCase.execute(domain.getIdRestaurant(), 1, domain.getDate(), domain.getHour()));

		verify(availableRepository, times(0)).save(any(AvailableEntity.class));
		Assertions.assertEquals("Available not found", exception.getMessage());
	}

}