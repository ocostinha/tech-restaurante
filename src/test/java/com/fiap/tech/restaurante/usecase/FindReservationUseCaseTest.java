package com.fiap.tech.restaurante.usecase;

import com.fiap.tech.restaurante.domain.entity.Reservation;
import com.fiap.tech.restaurante.domain.repository.ReservationRepository;
import com.fiap.tech.restaurante.exception.ReservationNotFoundException;
import com.fiap.tech.restaurante.exception.UnprocessableEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindReservationUseCaseTest {

    @Mock
    private ReservationRepository repository;

    @InjectMocks
    private FindReservationUseCase findReservationUseCase;

    private UUID validId;
    private UUID invalidId;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validId = UUID.randomUUID();
        invalidId = UUID.randomUUID();
        reservation = new Reservation();
        reservation.setId(validId);
    }

    @Test
    void findReservation_ShouldReturnReservation_WhenValidIdIsGiven() {
        when(repository.findReservationById(validId)).thenReturn(reservation);

        Reservation foundReservation = findReservationUseCase.findReservation(validId);

        assertNotNull(foundReservation);
        assertEquals(validId, foundReservation.getId());
        verify(repository, times(1)).findReservationById(validId);
    }

    @Test
    void findReservation_ShouldThrowUnprocessableEntityException_WhenIdIsNull() {
        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class,
                () -> findReservationUseCase.findReservation(null));

        assertEquals("O id da reserva não pode ser nulo", exception.getMessage());
        verify(repository, never()).findReservationById(any());
    }

    @Test
    void findReservation_ShouldThrowReservationNotFoundException_WhenReservationDoesNotExist() {
        when(repository.findReservationById(invalidId)).thenReturn(null);

        ReservationNotFoundException exception = assertThrows(ReservationNotFoundException.class,
                () -> findReservationUseCase.findReservation(invalidId));

        assertEquals("A reserva não foi encontrada com o id: " + invalidId, exception.getMessage());
        verify(repository, times(1)).findReservationById(invalidId);
    }
}
