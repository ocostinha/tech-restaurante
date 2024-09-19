package com.fiap.tech.restaurante.usecase;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.exception.UnprocessableEntityException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapperImpl;
import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.availability.FindAvailabilityByDataAndHourUseCase;
import com.fiap.tech.restaurante.domain.useCase.availability.UpdateAvailableUseCase;
import com.fiap.tech.restaurante.domain.useCase.reservation.UpdateReservationUseCase;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateReservationUseCaseTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private FindAvailabilityByDataAndHourUseCase findAvailabilityByDataAndHourUseCase;

    @Mock
    private UpdateAvailableUseCase updateAvailableUseCase;

    @Mock
    private ReservationMapperImpl mapper;

    @InjectMocks
    private UpdateReservationUseCase updateReservationUseCase;

    private Long reservationId;
    private ReservationEntity reservationEntity;
    private Reservation reservation;
    private Available available;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        reservationId = 1L;

        reservationEntity = new ReservationEntity(
                reservationId,
                1L,
                "nome teste",
                "nometeste@email.com",
                LocalDate.now(),
                LocalTime.of(12, 0),
                5,
                ReservationStatus.CONFIRMED,
                null,
                null
        );

        reservation = new Reservation(
                reservationId,
                1L,
                "nome teste",
                "nometeste@email.com",
                LocalDate.now(),
                LocalTime.of(12, 0),
                5,
                ReservationStatus.CONFIRMED,
                null,
                null
        );

        available = new Available(
                1L,
                1L,
                LocalDate.now(),
                LocalTime.of(12, 0),
                10,
                null,
                null
        );
    }

    @Test
    void shouldUpdateReservationSuccessfully() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservationEntity));
        when(findAvailabilityByDataAndHourUseCase.execute(any(), any(), any())).thenReturn(available);

        doCallRealMethod().when(mapper).toDomain(any(ReservationEntity.class));
        doCallRealMethod().when(mapper).update(any(Reservation.class), any(ReservationEntity.class));
        doNothing().when(updateAvailableUseCase).execute(anyLong(), anyInt(), any(), any());
        when(reservationRepository.save(any())).thenReturn(reservationEntity);

        Reservation updatedReservation = updateReservationUseCase.execute(
                reservationId, reservation
        );

        assertEquals("nome teste", updatedReservation.getReservationOwnerName());
        assertEquals("nometeste@email.com", updatedReservation.getReservationOwnerEmail());
        verify(reservationRepository).save(any(ReservationEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenReservationNotFound() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () ->
                updateReservationUseCase.execute(reservationId, reservation));

        assertEquals("Reserva não encontrada", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNoAvailabilityFound() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservationEntity));
        when(findAvailabilityByDataAndHourUseCase.execute(any(), any(), any())).thenThrow(
                new UnprocessableEntityException("Nenhuma disponibilidade encontrada para a data e hora selecionadas")
        );

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () ->
                updateReservationUseCase.execute(reservationId, reservation));

        assertEquals("Nenhuma disponibilidade encontrada para a data e hora selecionadas", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNotEnoughSeatsAvailable() {
        available.setAvailableSeats(2);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservationEntity));
        when(findAvailabilityByDataAndHourUseCase.execute(any(), any(), any())).thenReturn(available);

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () ->
                updateReservationUseCase.execute(reservationId, reservation));

        assertEquals("Não há assentos suficientes disponíveis", exception.getMessage());
    }
}
