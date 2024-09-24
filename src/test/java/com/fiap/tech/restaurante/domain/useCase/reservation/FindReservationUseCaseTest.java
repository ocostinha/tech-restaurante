package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FindReservationUseCaseTest {

    @InjectMocks
    private FindReservationUseCase findReservationUseCase;

    @Mock
    private ReservationRepository reservationRepository;

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
                .reservationOwnerName("Owner")
                .reservationOwnerEmail("owner@test.com")
                .reservationDate(LocalDate.now())
                .reservationHour(LocalTime.of(12, 0))
                .seatsReserved(4)
                .status(ReservationStatus.CONFIRMED)
                .build();

        reservation = Reservation.builder()
                .id(1L)
                .idRestaurant(1L)
                .reservationOwnerName("Owner")
                .reservationOwnerEmail("owner@test.com")
                .reservationDate(LocalDate.now())
                .reservationHour(LocalTime.of(12, 0))
                .seatsReserved(4)
                .status(ReservationStatus.CONFIRMED)
                .build();
    }

    @Test
    void shouldReturnReservationsByRestaurantId() {
        when(reservationRepository.findAll(any(Specification.class))).thenReturn(List.of(reservationEntity));
        when(reservationMapper.toDomain(reservationEntity)).thenReturn(reservation);

        List<Reservation> result = findReservationUseCase.execute(1L, null, null, null);

        assertEquals(1, result.size());
        assertEquals("Owner", result.get(0).getReservationOwnerName());

        verify(reservationRepository).findAll(any(Specification.class));
        verify(reservationMapper).toDomain(reservationEntity);
    }

    @Test
    void shouldReturnReservationsByDate() {
        when(reservationRepository.findAll(any(Specification.class))).thenReturn(List.of(reservationEntity));
        when(reservationMapper.toDomain(reservationEntity)).thenReturn(reservation);

        List<Reservation> result = findReservationUseCase.execute(null, LocalDate.now(), null, null);

        assertEquals(1, result.size());
        assertEquals(LocalDate.now(), result.get(0).getReservationDate());

        verify(reservationRepository).findAll(any(Specification.class));
        verify(reservationMapper).toDomain(reservationEntity);
    }

    @Test
    void shouldReturnReservationsByHour() {
        when(reservationRepository.findAll(any(Specification.class))).thenReturn(List.of(reservationEntity));
        when(reservationMapper.toDomain(reservationEntity)).thenReturn(reservation);

        List<Reservation> result = findReservationUseCase.execute(null, null, LocalTime.of(12, 0), null);

        assertEquals(1, result.size());
        assertEquals(LocalTime.of(12, 0), result.get(0).getReservationHour());

        verify(reservationRepository).findAll(any(Specification.class));
        verify(reservationMapper).toDomain(reservationEntity);
    }

    @Test
    void shouldReturnReservationsByStatus() {
        when(reservationRepository.findAll(any(Specification.class))).thenReturn(List.of(reservationEntity));
        when(reservationMapper.toDomain(reservationEntity)).thenReturn(reservation);

        List<Reservation> result = findReservationUseCase.execute(null, null, null, ReservationStatus.CONFIRMED);

        assertEquals(1, result.size());
        assertEquals(ReservationStatus.CONFIRMED, result.get(0).getStatus());

        verify(reservationRepository).findAll(any(Specification.class));
        verify(reservationMapper).toDomain(reservationEntity);
    }

    @Test
    void shouldReturnEmptyListWhenNoReservationsFound() {
        when(reservationRepository.findAll(any(Specification.class))).thenReturn(List.of());

        List<Reservation> result = findReservationUseCase.execute(null, null, null, null);

        assertTrue(result.isEmpty());

        verify(reservationRepository).findAll(any(Specification.class));
        verify(reservationMapper, never()).toDomain(any(ReservationEntity.class));
    }
}
