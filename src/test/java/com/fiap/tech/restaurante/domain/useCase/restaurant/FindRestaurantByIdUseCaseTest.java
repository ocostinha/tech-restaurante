package com.fiap.tech.restaurante.domain.useCase.restaurant;

import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.domain.mappers.RestaurantMapperImpl;
import com.fiap.tech.restaurante.infra.entity.RestaurantEntity;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import mocks.RestaurantMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FindRestaurantByIdUseCaseTest {

	@Mock
	private RestaurantRepository restaurantRepository;

	@Mock
	private RestaurantMapperImpl mapper;

	@InjectMocks
	private FindRestaurantByIdUseCase findRestaurantByIdUseCase;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldFindRestaurantById() {
		when(restaurantRepository.findById(1L)).thenReturn(Optional.ofNullable(RestaurantMock.mockEntity()));
		when(mapper.toDomain(any(RestaurantEntity.class))).thenReturn(RestaurantMock.mockDomain());

		assertNotNull(findRestaurantByIdUseCase.execute(1L));
	}

	@Test
	void shouldThrowWhenFindRestaurantById() {
		when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> findRestaurantByIdUseCase.execute(1L));

		Assertions.assertEquals("Restaurante não encontrado", exception.getMessage());
	}

}