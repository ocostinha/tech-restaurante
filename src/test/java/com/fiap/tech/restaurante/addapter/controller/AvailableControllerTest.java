package com.fiap.tech.restaurante.addapter.controller;

import com.fiap.tech.restaurante.addapter.dto.AvailableDTO;
import com.fiap.tech.restaurante.addapter.mapper.AvailableMapper;
import com.fiap.tech.restaurante.domain.entity.Available;
import com.fiap.tech.restaurante.usecase.FindAvailabilityUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private AvailableDTO availableDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        available = new Available(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), 12, 10, null, null);
        availableDTO = new AvailableDTO(UUID.randomUUID(), LocalDate.now(), 12, 10);
    }

    @Test
    void shouldReturnAvailabilityWhenSeatsAndHourAreNull() {
        List<Available> availableList = new ArrayList<>();
        availableList.add(available);
        when(findAvailabilityUseCase.findAvailability(null, null)).thenReturn(availableList);
        when(availableMapper.toDTO(any(Available.class))).thenReturn(availableDTO);

        List<AvailableDTO> result = availableController.findAvailability(null, null);

        assertFalse(result.isEmpty());
        verify(findAvailabilityUseCase).findAvailability(null, null);
        verify(availableMapper, times(1)).toDTO(any(Available.class));
    }

    @Test
    void shouldReturnAvailabilityBySeatsAndHour() {
        List<Available> availableList = new ArrayList<>();
        availableList.add(available);
        when(findAvailabilityUseCase.findAvailability(5, 12)).thenReturn(availableList);
        when(availableMapper.toDTO(any(Available.class))).thenReturn(availableDTO);

        List<AvailableDTO> result = availableController.findAvailability(5, 12);

        assertFalse(result.isEmpty());
        verify(findAvailabilityUseCase).findAvailability(5, 12);
        verify(availableMapper, times(1)).toDTO(any(Available.class));
    }

    @Test
    void shouldReturnAvailabilityBySeats() {
        List<Available> availableList = new ArrayList<>();
        availableList.add(available);
        when(findAvailabilityUseCase.findAvailability(5, null)).thenReturn(availableList);
        when(availableMapper.toDTO(any(Available.class))).thenReturn(availableDTO);

        List<AvailableDTO> result = availableController.findAvailability(5, null);

        assertFalse(result.isEmpty());
        verify(findAvailabilityUseCase).findAvailability(5, null);
        verify(availableMapper, times(1)).toDTO(any(Available.class));
    }

    @Test
    void shouldReturnAvailabilityByHour() {
        List<Available> availableList = new ArrayList<>();
        availableList.add(available);
        when(findAvailabilityUseCase.findAvailability(null, 12)).thenReturn(availableList);
        when(availableMapper.toDTO(any(Available.class))).thenReturn(availableDTO);

        List<AvailableDTO> result = availableController.findAvailability(null, 12);

        assertFalse(result.isEmpty());
        verify(findAvailabilityUseCase).findAvailability(null, 12);
        verify(availableMapper, times(1)).toDTO(any(Available.class));
    }
}
