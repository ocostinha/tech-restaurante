package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.availability.UpdateAvailableUseCase;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CancelReservationUseCaseTest {

    @InjectMocks
    private CancelReservationUseCase cancelReservationUseCase;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UpdateAvailableUseCase updateAvailableUseCase;

    @Mock
    private ReservationMapper reservationMapper;

    private ReservationEntity reservationEntity;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        reservationEntity = ReservationEntity.builder()
                .id(1L)
                .idRestaurant(1L)
                .reservationOwnerName("Test Owner")
                .reservationOwnerEmail("owner@test.com")
                .reservationDate(null)
                .reservationHour(null)
                .seatsReserved(4)
                .status(ReservationStatus.CONFIRMED)
                .build();

        reservation = Reservation.builder()
                .id(1L)
                .idRestaurant(1L)
                .reservationOwnerName("Test Owner")
                .reservationOwnerEmail("owner@test.com")
                .seatsReserved(4)
                .status(ReservationStatus.CANCELED)
                .build();
    }

    @Test
    void shouldCancelReservationSuccessfully() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservationEntity));
        when(reservationMapper.toDomain(reservationEntity)).thenReturn(reservation);
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);

        Reservation result = cancelReservationUseCase.execute(1L);

        assertEquals(ReservationStatus.CANCELED, result.getStatus());

        verify(reservationRepository).findById(1L);
        verify(reservationRepository).save(reservationEntity);
    }

    @Test
    void shouldThrowExceptionWhenReservationNotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cancelReservationUseCase.execute(1L));

        verify(reservationRepository).findById(1L);
        verify(reservationRepository, never()).save(any());
        verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(), any());
    }
}
