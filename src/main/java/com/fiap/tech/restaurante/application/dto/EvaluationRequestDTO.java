package com.fiap.tech.restaurante.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EvaluationRequestDTO {

    @NotNull
    private Long idRestaurant;

    @NotNull
    private UUID idReserve;

    @NotBlank
    private String evaluation;

    @Min(1)
    @Max(10)
    private int grade;
}
