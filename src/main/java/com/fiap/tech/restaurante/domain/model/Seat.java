package com.fiap.tech.restaurante.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Seat {
    private Long id;
    private Long idRestaurant;
    private Integer availableSeats;
}
