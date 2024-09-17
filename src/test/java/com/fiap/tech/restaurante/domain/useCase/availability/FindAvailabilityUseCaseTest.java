package com.fiap.tech.restaurante.domain.useCase.availability;

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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindAvailabilityUseCaseTest {
    @InjectMocks
    private FindAvailabilityUseCase findAvailabilityUseCase;

    @Mock
    private AvailableRepository availableRepository;

    private AvailableEntity available;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        available = new AvailableEntity(1L, 1L, LocalDate.now(), LocalTime.of(12, 0), 10, null, null);
    }

    @Test
    void shouldFindAllAvailabilityWhenSeatsAndHourAreNull() {
        List<AvailableEntity> allAvailability = new ArrayList<>();
        allAvailability.add(available);
        when(availableRepository.findAll()).thenReturn(allAvailability);

        List<Available> result = findAvailabilityUseCase.execute(null, null, 1L);

        assertFalse(result.isEmpty());
        verify(availableRepository).findAll();
    }

    @Test
    void shouldFindAvailabilityBySeatsAndHour() {
        List<AvailableEntity> availabilityBySeatsAndHour = new ArrayList<>();
        availabilityBySeatsAndHour.add(available);
        when(availableRepository.findByAvailableSeatsGreaterThanEqualAndHourAndIdRestaurant(anyInt(), any(), any())).thenReturn(availabilityBySeatsAndHour);

        List<Available> result = findAvailabilityUseCase.execute(5, LocalTime.of(12, 0), 1L);

        assertFalse(result.isEmpty());
        verify(availableRepository).findByAvailableSeatsGreaterThanEqualAndHourAndIdRestaurant(anyInt(), any(), 1L);
    }

    @Test
    void shouldFindAvailabilityBySeats() {
        List<AvailableEntity> availabilityBySeats = new ArrayList<>();
        availabilityBySeats.add(available);
        when(availableRepository.findByAvailableSeatsGreaterThanEqualAndIdRestaurant(anyInt(),1L )).thenReturn(availabilityBySeats);

        List<Available> result = findAvailabilityUseCase.execute(5, null, 1L);

        assertFalse(result.isEmpty());
        verify(availableRepository).findByAvailableSeatsGreaterThanEqualAndIdRestaurant(anyInt(), 1L);
    }

    @Test
    void shouldFindAvailabilityByHour() {
        List<AvailableEntity> availabilityByHour = new ArrayList<>();
        availabilityByHour.add(available);
        when(availableRepository.findByHourAndIdRestaurant(any(), 1L)).thenReturn(availabilityByHour);

        List<Available> result = findAvailabilityUseCase.execute(null, LocalTime.of(12,0), 1L);

        assertFalse(result.isEmpty());
        verify(availableRepository).findByHourAndIdRestaurant(any(), 1L);
    }
}