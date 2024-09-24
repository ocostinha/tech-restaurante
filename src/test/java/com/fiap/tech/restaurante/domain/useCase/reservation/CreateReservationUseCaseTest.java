package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.availability.FindAvailabilityByDataAndHourUseCase;
import com.fiap.tech.restaurante.domain.useCase.availability.UpdateAvailableUseCase;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CreateReservationUseCaseTest {

    @InjectMocks
    private CreateReservationUseCase createReservationUseCase;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private FindAvailabilityByDataAndHourUseCase findAvailabilityByDataAndHourUseCase;

    @Mock
    private UpdateAvailableUseCase updateAvailableUseCase;

    @Mock
    private ReservationMapper reservationMapper;

    private Reservation reservationRequest;
    private ReservationEntity reservationEntity;
    private Reservation reservation;
    private Available availability;

    @BeforeEach
    void setUp() {
        openMocks(this);

        reservationRequest = new Reservation(null, 1L, "Test Test", "test@test.com", LocalDate.now(), LocalTime.of(12, 0), 4, null, null, null);

        reservationEntity = ReservationEntity.builder()
                .id(1L)
                .idRestaurant(1L)
                .seatsReserved(4)
                .reservationDate(LocalDate.now())
                .reservationHour(LocalTime.of(12, 0))
                .build();

        reservation = new Reservation(1L, 1L, "Test Test", "test@test.com", LocalDate.now(), LocalTime.of(12, 0), 4, null, null, null);

        availability = new Available(1L, LocalDate.now(), LocalTime.of(12, 0), 1L, 10);
    }

    @Test
    void shouldCreateReservationSuccessfully() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(new Object()));
        when(findAvailabilityByDataAndHourUseCase.execute(anyLong(), any(LocalDate.class), any(LocalTime.class))).thenReturn(availability);
        when(reservationMapper.toEntity(any(Reservation.class))).thenReturn(reservationEntity);
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);
        when(reservationMapper.toDomain(any(ReservationEntity.class))).thenReturn(reservation);

        Reservation result = createReservationUseCase.execute(reservationRequest);

        assertEquals(1L, result.getId());
        verify(restaurantRepository).findById(1L);
        verify(findAvailabilityByDataAndHourUseCase).execute(1L, LocalDate.now(), LocalTime.of(12, 0));
        verify(reservationRepository).save(reservationEntity);
        verify(updateAvailableUseCase).execute(1L, -4, LocalDate.now(), LocalTime.of(12, 0));
    }

    @Test
    void shouldThrowBusinessExceptionWhenRestaurantNotFound() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> createReservationUseCase.execute(reservationRequest));

        assertEquals("Restaurante não encontrado", exception.getMessage());
        verify(restaurantRepository).findById(1L);
        verify(findAvailabilityByDataAndHourUseCase, never()).execute(anyLong(), any(LocalDate.class), any(LocalTime.class));
        verify(reservationRepository, never()).save(any(ReservationEntity.class));
        verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(LocalDate.class), any(LocalTime.class));
    }

    @Test
    void shouldThrowBusinessExceptionWhenNoSeatsAvailable() {
        availability.setAvailableSeats(2); // Apenas 2 assentos disponíveis
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(new Object()));
        when(findAvailabilityByDataAndHourUseCase.execute(anyLong(), any(LocalDate.class), any(LocalTime.class))).thenReturn(availability);

        BusinessException exception = assertThrows(BusinessException.class, () -> createReservationUseCase.execute(reservationRequest));

        assertEquals("Não há assentos disponíveis para o horário solicitado.", exception.getMessage());
        verify(restaurantRepository).findById(1L);
        verify(findAvailabilityByDataAndHourUseCase).execute(1L, LocalDate.now(), LocalTime.of(12, 0));
        verify(reservationRepository, never()).save(any(ReservationEntity.class));
        verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(LocalDate.class), any(LocalTime.class));
    }
}
