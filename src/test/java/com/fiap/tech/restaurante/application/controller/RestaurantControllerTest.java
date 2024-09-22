package com.fiap.tech.restaurante.application.controller;

import com.fiap.tech.restaurante.application.dto.RestaurantRequestDTO;
import com.fiap.tech.restaurante.application.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.domain.mappers.RestaurantMapper;
import com.fiap.tech.restaurante.domain.model.Restaurant;
import com.fiap.tech.restaurante.domain.useCase.restaurant.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {

    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Mock
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @Mock
    private FindRestaurantUseCase findRestaurantUseCase;

    @Mock
    private FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    @Mock
    private DeleteRestaurantByIdUseCase deleteRestaurantByIdUseCase;

    @Mock
    private RestaurantMapper restaurantMapper;

    private RestaurantRequestDTO requestDTO;
    private RestaurantResponseDTO responseDTO;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDTO = new RestaurantRequestDTO("Teste Restaurante", "Rua Teste", 123, "Bairro Teste", "Cidade Teste", "SP", "Italiana", "08:00", "22:00", "15:00", "17:00", 50);

        restaurant = new Restaurant(1L, "Teste Restaurante", "Rua Teste", 123, "Bairro Teste", "Cidade Teste", "SP", "Italiana", LocalTime.of(8, 0), LocalTime.of(22, 0), LocalTime.of(15, 0), LocalTime.of(17, 0), 50, LocalDateTime.now(), LocalDateTime.now());

        responseDTO = new RestaurantResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Teste Restaurante");
        responseDTO.setAddress("Rua Teste");
        responseDTO.setNumber(123);
        responseDTO.setNeighborhood("Bairro Teste");
        responseDTO.setCity("Cidade Teste");
        responseDTO.setState("SP");
        responseDTO.setCuisineType("Italiana");
        responseDTO.setOpenAt(LocalTime.of(8, 0));
        responseDTO.setCloseAt(LocalTime.of(22, 0));
        responseDTO.setIntervalStart(LocalTime.of(15, 0));
        responseDTO.setIntervalFinish(LocalTime.of(17, 0));
        responseDTO.setNumberOfSeats(50);
        responseDTO.setCreatedAt(LocalDateTime.now());
        responseDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void shouldCreateRestaurantSuccessfully() {
        when(createRestaurantUseCase.execute(any(Restaurant.class))).thenReturn(restaurant);
        when(restaurantMapper.toDomain(any(RestaurantRequestDTO.class))).thenReturn(restaurant);
        when(restaurantMapper.toResponse(any(Restaurant.class))).thenReturn(responseDTO);

        RestaurantResponseDTO result = restaurantController.createRestaurant(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO.getId(), result.getId());
        assertEquals(responseDTO.getName(), result.getName());

        verify(createRestaurantUseCase).execute(any(Restaurant.class));
        verify(restaurantMapper).toDomain(any(RestaurantRequestDTO.class));
        verify(restaurantMapper).toResponse(any(Restaurant.class));
    }

    @Test
    void shouldUpdateRestaurantSuccessfully() {
        when(updateRestaurantUseCase.execute(anyLong(), any(Restaurant.class))).thenReturn(restaurant);
        when(restaurantMapper.toDomain(any(RestaurantRequestDTO.class))).thenReturn(restaurant);
        when(restaurantMapper.toResponse(any(Restaurant.class))).thenReturn(responseDTO);

        RestaurantResponseDTO result = restaurantController.editRestaurant(1L, requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO.getId(), result.getId());
        assertEquals(responseDTO.getName(), result.getName());

        verify(updateRestaurantUseCase).execute(anyLong(), any(Restaurant.class));
        verify(restaurantMapper).toDomain(any(RestaurantRequestDTO.class));
        verify(restaurantMapper).toResponse(any(Restaurant.class));
    }

    @Test
    void shouldFindRestaurantByIdSuccessfully() {
        when(findRestaurantByIdUseCase.execute(anyLong())).thenReturn(restaurant);
        when(restaurantMapper.toResponse(any(Restaurant.class))).thenReturn(responseDTO);

        RestaurantResponseDTO result = restaurantController.getRestaurant(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(findRestaurantByIdUseCase).execute(anyLong());
        verify(restaurantMapper).toResponse(any(Restaurant.class));
    }

    @Test
    void shouldThrowExceptionWhenRestaurantNotFound() {
        when(findRestaurantByIdUseCase.execute(anyLong())).thenThrow(new ResourceNotFoundException("Restaurante não encontrado"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> restaurantController.getRestaurant(1L));

        assertEquals("Restaurante não encontrado", exception.getMessage());
        verify(findRestaurantByIdUseCase).execute(anyLong());
        verify(restaurantMapper, never()).toResponse(any(Restaurant.class));
    }

    @Test
    void shouldDeleteRestaurantSuccessfully() {
        restaurantController.deleteRestaurant(1L);

        verify(deleteRestaurantByIdUseCase).execute(anyLong());
    }

    @Test
    void shouldFindRestaurantsSuccessfully() {
        when(findRestaurantUseCase.execute(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(Collections.singletonList(restaurant));
        when(restaurantMapper.toResponse(any(Restaurant.class))).thenReturn(responseDTO);

        List<RestaurantResponseDTO> result = restaurantController.findRestaurants("name", "neighborhood", "city", "state", "cuisine");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        verify(findRestaurantUseCase).execute(anyString(), anyString(), anyString(), anyString(), anyString());
        verify(restaurantMapper).toResponse(any(Restaurant.class));
    }
}
