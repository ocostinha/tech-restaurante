package com.fiap.tech.restaurante.application.controller;

import com.fiap.tech.restaurante.application.dto.AvailableResponseDTO;
import com.fiap.tech.restaurante.domain.mappers.AvailableMapper;
import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.domain.useCase.availability.FindAvailabilityUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AvailableControllerTest {

	@InjectMocks
	private AvailableController availableController;

	@Mock
	private FindAvailabilityUseCase findAvailabilityUseCase;

	@Mock
	private AvailableMapper availableMapper;

	private Available available;

	private AvailableResponseDTO availableResponseDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		available = new Available(1L, 1L, LocalDate.now(), LocalTime.of(12, 0), 10, null, null);
		availableResponseDTO = new AvailableResponseDTO(1L, LocalDate.now(), LocalTime.of(12, 0), 10);
	}

	@Test
	void shouldReturnAvailabilityWhenSeatsAndHourAreNull() {
		List<Available> availableList = new ArrayList<>();
		availableList.add(available);
		when(findAvailabilityUseCase.execute(null, null, 1L)).thenReturn(availableList);
		when(availableMapper.toResponseDTO(any(Available.class))).thenReturn(availableResponseDTO);

		List<AvailableResponseDTO> result = availableController.findAvailability(null, null, 1L);

		assertFalse(result.isEmpty());
		verify(findAvailabilityUseCase).execute(null, null, 1L);
		verify(availableMapper, times(1)).toResponseDTO(any(Available.class));
	}

	@Test
	void shouldReturnAvailabilityBySeatsAndHour() {
		List<Available> availableList = new ArrayList<>();
		availableList.add(available);
		when(findAvailabilityUseCase.execute(5, LocalTime.of(12, 0), 1L)).thenReturn(availableList);
		when(availableMapper.toResponseDTO(any(Available.class))).thenReturn(availableResponseDTO);

		List<AvailableResponseDTO> result = availableController.findAvailability(5, LocalTime.of(12, 0), 1L);

		assertFalse(result.isEmpty());
		verify(findAvailabilityUseCase).execute(5, LocalTime.of(12, 0), 1L);
		verify(availableMapper, times(1)).toResponseDTO(any(Available.class));
	}

	@Test
	void shouldReturnAvailabilityBySeats() {
		List<Available> availableList = new ArrayList<>();
		availableList.add(available);
		when(findAvailabilityUseCase.execute(5, null, 1L)).thenReturn(availableList);
		when(availableMapper.toResponseDTO(any(Available.class))).thenReturn(availableResponseDTO);

		List<AvailableResponseDTO> result = availableController.findAvailability(5, null, 1L);

		assertFalse(result.isEmpty());
		verify(findAvailabilityUseCase).execute(5, null, 1L);
		verify(availableMapper, times(1)).toResponseDTO(any(Available.class));
	}

	@Test
	void shouldReturnAvailabilityByHour() {
		List<Available> availableList = new ArrayList<>();
		availableList.add(available);
		when(findAvailabilityUseCase.execute(null, LocalTime.of(12, 0), 1L)).thenReturn(availableList);
		when(availableMapper.toResponseDTO(any(Available.class))).thenReturn(availableResponseDTO);

		List<AvailableResponseDTO> result = availableController.findAvailability(null, LocalTime.of(12, 0), 1L);

		assertFalse(result.isEmpty());
		verify(findAvailabilityUseCase).execute(null, LocalTime.of(12, 0), 1L);
		verify(availableMapper, times(1)).toResponseDTO(any(Available.class));
	}

}
