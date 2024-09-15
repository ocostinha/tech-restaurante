package com.fiap.tech.restaurante.addapter.controller;

import com.fiap.tech.restaurante.addapter.dto.RequestReservationDTO;
import com.fiap.tech.restaurante.addapter.dto.ResponseReservationDTO;
import com.fiap.tech.restaurante.addapter.mapper.ReservationMapper;
import com.fiap.tech.restaurante.domain.entity.Reservation;
import com.fiap.tech.restaurante.usecase.UpdateReservationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private UpdateReservationUseCase updateReservationUseCase;

    @Mock
    private ReservationMapper reservationMapper;

    private RequestReservationDTO requestReservationDTO;
    private Reservation reservation;
    private ResponseReservationDTO responseReservationDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UUID restaurantId = UUID.randomUUID();
        UUID reservationId = UUID.randomUUID();
        String ownerName = "nome teste";
        String ownerEmail = "test@test.com";
        LocalDate reservationDate = LocalDate.now();
        int reservationHour = 12;
        int numberOfSeats = 5;
        int status = 1;


        requestReservationDTO = new RequestReservationDTO(restaurantId, ownerName, ownerEmail, reservationDate, reservationHour, numberOfSeats);

        reservation = new Reservation(
                reservationId,
                restaurantId,
                ownerName,
                ownerEmail,
                reservationDate,
                reservationHour,
                numberOfSeats,
                status,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        responseReservationDTO = new ResponseReservationDTO(reservationId, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void shouldUpdateReservationSuccessfully() {

        when(updateReservationUseCase.updateReservation(
                any(UUID.class),
                any(UUID.class),
                anyString(),
                anyString(),
                any(LocalDate.class),
                anyInt(),
                anyInt())
        ).thenReturn(reservation);

        when(reservationMapper.toDTO(any(Reservation.class))).thenReturn(responseReservationDTO);


        ResponseReservationDTO result = reservationController.updateReservation(
                UUID.randomUUID(),
                requestReservationDTO
        );

        assertEquals(responseReservationDTO.getId(), result.getId());
        assertEquals(responseReservationDTO.getCreatedAt(), result.getCreatedAt());
        assertEquals(responseReservationDTO.getUpdatedAt(), result.getUpdatedAt());

        verify(updateReservationUseCase).updateReservation(
                any(UUID.class),
                any(UUID.class),
                anyString(),
                anyString(),
                any(LocalDate.class),
                anyInt(),
                anyInt()
        );
        verify(reservationMapper).toDTO(any(Reservation.class));
    }
}
