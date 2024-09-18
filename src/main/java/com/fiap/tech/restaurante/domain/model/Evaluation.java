package com.fiap.tech.restaurante.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Evaluation {
    private Long id;
    private Long idRestaurant;
    private UUID idReserve;
    private String evaluation;
    private int grade;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
