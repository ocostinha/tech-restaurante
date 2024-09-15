package com.fiap.tech.restaurante.usecase;

import com.fiap.tech.restaurante.domain.entity.Available;
import com.fiap.tech.restaurante.domain.repository.AvailableRepository;
import com.fiap.tech.restaurante.exception.UnprocessableEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindAvailabilityUseCaseTest {

    @InjectMocks
    private FindAvailabilityUseCase findAvailabilityUseCase;

    @Mock
    private AvailableRepository availableRepository;

    private UUID restaurantId;
    private Available available;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantId = UUID.randomUUID();
        available = new Available(UUID.randomUUID(), restaurantId, LocalDate.now(), 12, 10, null, null);
    }

    @Test
    void shouldFindAvailabilityByRestaurantIdDateAndHour() {
        when(availableRepository.findAvailability(any(), any(), anyInt())).thenReturn(Optional.of(available));

        Available result = findAvailabilityUseCase.findAvailabilityRestaurantIdAndDateAndHour(restaurantId, LocalDate.now(), 12);

        assertEquals(available.getRestaurantId(), result.getRestaurantId());
        verify(availableRepository).findAvailability(any(), any(), anyInt());
    }

    @Test
    void shouldThrowExceptionWhenNoAvailabilityFound() {
        when(availableRepository.findAvailability(any(), any(), anyInt())).thenReturn(Optional.empty());

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> findAvailabilityUseCase.findAvailabilityRestaurantIdAndDateAndHour(restaurantId, LocalDate.now(), 12));

        assertEquals("Nenhuma disponibilidade encontrada para a data e hora selecionadas", exception.getMessage());
    }

    @Test
    void shouldFindAllAvailabilityWhenSeatsAndHourAreNull() {
        List<Available> allAvailability = new ArrayList<>();
        allAvailability.add(available);
        when(availableRepository.findAll()).thenReturn(allAvailability);

        List<Available> result = findAvailabilityUseCase.findAvailability(null, null);

        assertFalse(result.isEmpty());
        verify(availableRepository).findAll();
    }

    @Test
    void shouldFindAvailabilityBySeatsAndHour() {
        List<Available> availabilityBySeatsAndHour = new ArrayList<>();
        availabilityBySeatsAndHour.add(available);
        when(availableRepository.findBySeatsAndHour(anyInt(), anyInt())).thenReturn(availabilityBySeatsAndHour);

        List<Available> result = findAvailabilityUseCase.findAvailability(5, 12);

        assertFalse(result.isEmpty());
        verify(availableRepository).findBySeatsAndHour(anyInt(), anyInt());
    }

    @Test
    void shouldFindAvailabilityBySeats() {
        List<Available> availabilityBySeats = new ArrayList<>();
        availabilityBySeats.add(available);
        when(availableRepository.findBySeats(anyInt())).thenReturn(availabilityBySeats);

        List<Available> result = findAvailabilityUseCase.findAvailability(5, null);

        assertFalse(result.isEmpty());
        verify(availableRepository).findBySeats(anyInt());
    }

    @Test
    void shouldFindAvailabilityByHour() {
        List<Available> availabilityByHour = new ArrayList<>();
        availabilityByHour.add(available);
        when(availableRepository.findByHour(anyInt())).thenReturn(availabilityByHour);

        List<Available> result = findAvailabilityUseCase.findAvailability(null, 12);

        assertFalse(result.isEmpty());
        verify(availableRepository).findByHour(anyInt());
    }
}
