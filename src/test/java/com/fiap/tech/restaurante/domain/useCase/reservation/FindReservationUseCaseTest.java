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
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

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
        openMocks(this);

        reservationEntity = ReservationEntity.builder()
                .id(1L)
                .idRestaurant(1L)
                .seatsReserved(4)
                .reservationDate(LocalDate.now())
                .reservationHour(LocalTime.of(12, 0))
                .status(ReservationStatus.CONFIRMED)
                .build();

        reservation = new Reservation(1L, 1L, "Test Test", "test@test.com", LocalDate.now(), LocalTime.of(12, 0), 4, ReservationStatus.CONFIRMED, null, null);
    }

    @Test
    void shouldFindReservationsWithAllFilters() {
        when(reservationRepository.findAll(any(Specification.class)))
                .thenReturn(Collections.singletonList(reservationEntity));
        when(reservationMapper.toDomain(any(ReservationEntity.class)))
                .thenReturn(reservation);

        List<Reservation> result = findReservationUseCase.execute(
                1L,
                LocalDate.now(),
                LocalTime.of(12, 0),
                ReservationStatus.CONFIRMED
        );

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(reservationRepository).findAll(any(Specification.class));
        verify(reservationMapper).toDomain(reservationEntity);
    }

    @Test
    void shouldFindReservationsWithPartialFilters() {
        when(reservationRepository.findAll(any(Specification.class)))
                .thenReturn(Collections.singletonList(reservationEntity));
        when(reservationMapper.toDomain(any(ReservationEntity.class)))
                .thenReturn(reservation);

        List<Reservation> result = findReservationUseCase.execute(
                1L,
                null,
                null,
                ReservationStatus.CONFIRMED
        );

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(reservationRepository).findAll(any(Specification.class));
        verify(reservationMapper).toDomain(reservationEntity);
    }

    @Test
    void shouldReturnEmptyListWhenNoReservationsFound() {
        when(reservationRepository.findAll(any(Specification.class)))
                .thenReturn(Collections.emptyList());

        List<Reservation> result = findReservationUseCase.execute(
                1L,
                LocalDate.now().plusDays(1),
                LocalTime.of(18, 0),
                ReservationStatus.CANCELED
        );

        assertEquals(0, result.size());
        verify(reservationRepository).findAll(any(Specification.class));
        verify(reservationMapper, never()).toDomain(any(ReservationEntity.class));
    }

    @Test
    void shouldFindReservationsWithNoFilters() {
        when(reservationRepository.findAll(any(Specification.class)))
                .thenReturn(Collections.singletonList(reservationEntity));
        when(reservationMapper.toDomain(any(ReservationEntity.class)))
                .thenReturn(reservation);

        List<Reservation> result = findReservationUseCase.execute(
                null,
                null,
                null,
                null
        );

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(reservationRepository).findAll(any(Specification.class));
        verify(reservationMapper).toDomain(reservationEntity);
    }
}
