package com.fiap.tech.restaurante.adapter.controller;

import com.fiap.tech.restaurante.adapter.dto.ResponseReservationDTO;
import com.fiap.tech.restaurante.adapter.mapper.ReservationMapper;
import com.fiap.tech.restaurante.domain.entity.Reservation;
import com.fiap.tech.restaurante.usecase.FindReservationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationControllerTest {

    @Mock
    private FindReservationUseCase findReservationUseCase;

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationController reservationController;

    private UUID validId;
    private UUID invalidId;
    private Reservation reservation;
    private ResponseReservationDTO responseReservationDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validId = UUID.randomUUID();
        invalidId = UUID.randomUUID();

        reservation = new Reservation();
        reservation.setId(validId);

        responseReservationDTO = new ResponseReservationDTO();
        responseReservationDTO.setId(validId);
    }

    @Test
    void findReservationById_ShouldReturnResponseReservationDTO_WhenReservationExists() {
        when(findReservationUseCase.findReservation(validId)).thenReturn(reservation);
        when(reservationMapper.toDTO(reservation)).thenReturn(responseReservationDTO);

        ResponseReservationDTO result = reservationController.findReservationById(validId);

        assertNotNull(result);
        assertEquals(validId, result.getId());
        verify(findReservationUseCase, times(1)).findReservation(validId);
        verify(reservationMapper, times(1)).toDTO(reservation);
    }

    @Test
    void findReservationById_ShouldThrowResponseStatusException_WhenReservationDoesNotExist() {
        when(findReservationUseCase.findReservation(invalidId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            reservationController.findReservationById(invalidId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(findReservationUseCase, times(1)).findReservation(invalidId);
        verify(reservationMapper, never()).toDTO(any());
    }
}
