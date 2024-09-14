package com.fiap.tech.restaurante.controller;
import com.fiap.tech.restaurante.controller.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.core.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantResponseDTO> findRestaurants(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String neighborhood,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String type) {

        return restaurantService.findRestaurants(name, neighborhood, city, state, type);
    }
}
