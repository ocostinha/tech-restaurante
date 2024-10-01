package com.fiap.tech.restaurante.domain.useCase.availability;

import com.fiap.tech.restaurante.infra.entity.AvailableEntity;
import com.fiap.tech.restaurante.infra.repository.AvailableRepository;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import mocks.RestaurantMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateAvailabilityUseCaseTest {

	@Mock
	private RestaurantRepository restaurantRepository;

	@Mock
	private AvailableRepository availableRepository;

	@InjectMocks
	private CreateAvailabilityUseCase createAvailabilityUseCase;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldCreateAvailabilityForRestaurantAvailableFor8Hours() {
		when(restaurantRepository.findAll()).thenReturn(List.of(RestaurantMock.mockEntity()));
		when(availableRepository.save(any())).thenReturn(null);

		createAvailabilityUseCase.execute();

		verify(availableRepository, times(8)).save(any(AvailableEntity.class));
	}

}