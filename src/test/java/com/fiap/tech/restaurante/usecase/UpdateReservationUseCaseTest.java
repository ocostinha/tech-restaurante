package com.fiap.tech.restaurante.usecase;

import com.fiap.tech.restaurante.domain.entity.Available;
import com.fiap.tech.restaurante.domain.entity.Reservation;
import com.fiap.tech.restaurante.domain.entity.SaveAvailabilityUseCase;
import com.fiap.tech.restaurante.domain.repository.ReservationRepository;
import com.fiap.tech.restaurante.exception.UnprocessableEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateReservationUseCaseTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private FindAvailabilityUseCase findAvailabilityUseCase;

    @Mock
    private SaveAvailabilityUseCase saveAvailabilityUseCase;

    @InjectMocks
    private UpdateReservationUseCase updateReservationUseCase;

    private UUID reservationId;
    private UUID restaurantId;
    private Reservation reservation;
    private Available available;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        reservationId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();

        reservation = new Reservation(
                reservationId,
                restaurantId,
                "nome teste",
                "nometeste@email.com",
                LocalDate.now(),
                12,
                5,
                1,
                null,
                null
        );

        available = new Available(
                UUID.randomUUID(),
                restaurantId,
                LocalDate.now(),
                12,
                10,
                null,
                null
        );
    }

    @Test
    void shouldUpdateReservationSuccessfully() throws UnprocessableEntityException {

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(findAvailabilityUseCase.findAvailabilityRestaurantIdAndDateAndHour(any(), any(), anyInt())).thenReturn(available);

        Reservation updatedReservation = updateReservationUseCase.updateReservation(
                reservationId, restaurantId, "nome teste", "nometeste@email.com", LocalDate.now(), 12, 5
        );


        assertEquals("nome teste", updatedReservation.getReservationOwnerName());
        assertEquals("nometeste@email.com", updatedReservation.getReservationOwnerEmail());
        verify(reservationRepository).save(any(Reservation.class));
        verify(saveAvailabilityUseCase).save(any(Available.class));
    }

    @Test
    void shouldThrowExceptionWhenReservationNotFound() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () ->
                updateReservationUseCase.updateReservation(reservationId, restaurantId,
                        "nome teste", "nometeste@email.com", LocalDate.now(), 12, 5));

        assertEquals("Reserva não encontrada", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNoAvailabilityFound() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(findAvailabilityUseCase.findAvailabilityRestaurantIdAndDateAndHour(any(), any(), anyInt())).thenThrow(
                new UnprocessableEntityException("Nenhuma disponibilidade encontrada para a data e hora selecionadas")
        );

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () ->
                updateReservationUseCase.updateReservation(reservationId, restaurantId,
                        "nome teste", "nometeste@email.com", LocalDate.now(), 12, 5));

        assertEquals("Nenhuma disponibilidade encontrada para a data e hora selecionadas", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNotEnoughSeatsAvailable() {
        available.setAvailableSeats(2);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(findAvailabilityUseCase.findAvailabilityRestaurantIdAndDateAndHour(any(), any(), anyInt())).thenReturn(available);

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () ->
                updateReservationUseCase.updateReservation(reservationId, restaurantId,
                        "nome teste", "nometeste@email.com", LocalDate.now(), 12, 5));

        assertEquals("Não há assentos suficientes disponíveis", exception.getMessage());
    }
}
