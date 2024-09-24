package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.domain.exception.UnprocessableEntityException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class FindReservationByIdUseCaseTest {

    @InjectMocks
    private FindReservationByIdUseCase findReservationByIdUseCase;

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
                .build();

        reservation = new Reservation(1L, 1L, "Test Test", "test@test.com", null, null, 4, null, null, null);
    }

    @Test
    void shouldFindReservationByIdSuccessfully() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservationEntity));
        when(reservationMapper.toDomain(any(ReservationEntity.class))).thenReturn(reservation);

        Reservation result = findReservationByIdUseCase.execute(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(reservationRepository).findById(1L);
        verify(reservationMapper).toDomain(reservationEntity);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenReservationNotFound() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> findReservationByIdUseCase.execute(1L));

        assertEquals("Reserva não encontrada", exception.getMessage());
        verify(reservationRepository).findById(1L);
        verify(reservationMapper, never()).toDomain(any(ReservationEntity.class));
    }

    @Test
    void shouldThrowUnprocessableEntityExceptionWhenReservationIdIsNull() {
        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> findReservationByIdUseCase.execute(null));

        assertEquals("O id da reserva não pode ser nulo", exception.getMessage());
        verify(reservationRepository, never()).findById(any());
        verify(reservationMapper, never()).toDomain(any(ReservationEntity.class));
    }
}
