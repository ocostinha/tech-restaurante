package com.fiap.tech.restaurante.controller;
import com.fiap.tech.restaurante.commoms.exception.BusinessException;
import com.fiap.tech.restaurante.controller.dto.RestaurantRequestDTO;
import com.fiap.tech.restaurante.controller.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.core.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRestaurant(@RequestBody RestaurantRequestDTO requestDTO) {
        try {
            RestaurantResponseDTO response = restaurantService.createRestaurant(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado.");
        }
    }
}
