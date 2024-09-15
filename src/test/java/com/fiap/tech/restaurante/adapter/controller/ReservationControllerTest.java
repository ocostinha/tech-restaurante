package com.fiap.tech.restaurante.adapter.controller;

import com.fiap.tech.restaurante.application.controller.ReservationController;
import com.fiap.tech.restaurante.application.dto.ReservationResponseDTO;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.reservation.FindReservationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationControllerTest {

    @Mock
    private FindReservationUseCase findReservationUseCase;

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationController reservationController;

    private Long validId;
    private Long invalidId;
    private Reservation reservation;
    private ReservationResponseDTO responseReservationDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validId = 1L;
        invalidId = 2L;

        reservation = new Reservation();
        reservation.setId(validId);

        responseReservationDTO = new ReservationResponseDTO();
        responseReservationDTO.setId(validId);
    }

    @Test
    void findReservationById_ShouldReturnResponseReservationDTO_WhenReservationExists() {
        when(findReservationUseCase.findReservation(validId)).thenReturn(reservation);
        when(reservationMapper.toResponseDTO(reservation)).thenReturn(responseReservationDTO);

        ReservationResponseDTO result = reservationController.findReservationById(validId);

        assertNotNull(result);
        assertEquals(validId, result.getId());
        verify(findReservationUseCase, times(1)).findReservation(validId);
        verify(reservationMapper, times(1)).toResponseDTO(reservation);
    }

    @Test
    void findReservationById_ShouldThrowResponseStatusException_WhenReservationDoesNotExist() {
        when(findReservationUseCase.findReservation(invalidId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            reservationController.findReservationById(invalidId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(findReservationUseCase, times(1)).findReservation(invalidId);
        verify(reservationMapper, never()).toResponseDTO(any());
    }
}
