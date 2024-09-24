package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.domain.exception.UnprocessableEntityException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapperImpl;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindReservationByIdUseCaseTest {

    @Mock
    private ReservationRepository repository;

    @Mock
    private ReservationMapperImpl mapper;

    @InjectMocks
    private FindReservationByIdUseCase findReservationByIdUseCase;

    private Long validId;
    private Long invalidId;
    private ReservationEntity reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validId = 1L;
        invalidId = 2L;
        reservation = new ReservationEntity();
        reservation.setId(validId);
    }

    @Test
    void findReservation_ShouldReturnReservation_WhenValidIdIsGiven() {
        when(repository.findById(validId)).thenReturn(Optional.of(reservation));
        doCallRealMethod().when(mapper).toDomain(any(ReservationEntity.class));

        Reservation foundReservation = findReservationByIdUseCase.execute(validId);

        assertNotNull(foundReservation);
        assertEquals(validId, foundReservation.getId());
        verify(repository, times(1)).findById(validId);
    }

    @Test
    void findReservation_ShouldThrowUnprocessableEntityException_WhenIdIsNull() {
        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class,
                () -> findReservationByIdUseCase.execute(null));

        assertEquals("O id da reserva não pode ser nulo", exception.getMessage());
        verify(repository, never()).findById(any());
    }

    @Test
    void findReservation_ShouldThrowReservationNotFoundException_WhenReservationDoesNotExist() {
        when(repository.findById(invalidId)).thenReturn(Optional.empty());
        doCallRealMethod().when(mapper).toDomain(any(ReservationEntity.class));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> findReservationByIdUseCase.execute(invalidId));

        assertEquals("Reserva não encontrada", exception.getMessage());
        verify(repository, times(1)).findById(invalidId);
    }
}
